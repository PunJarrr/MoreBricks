#!/bin/bash
# Boots a dev server for one or more targets, places each kind of MoreBricks block over RCON,
# stops the server and reports problems.
#
#   scripts/smoke-test.sh 26.3-fabric 26.3-neoforge
#   scripts/smoke-test.sh all
#
# Running it accepts the Minecraft EULA (https://aka.ms/MinecraftEULA) for the dev servers in run/.
set -u
ROOT=$(cd "$(dirname "$0")/.." && pwd)
OUT=$ROOT/build/smoke-test
mkdir -p "$OUT"

TARGETS=("$@")
if [ "${1:-}" = all ]; then
  TARGETS=($(grep -oE 'target\("[^"]+"(, "[a-z]+")+\)' "$ROOT/settings.gradle.kts" \
    | grep -oE '"[^"]+"' | tr -d '"' | awk '/^[0-9]/ { v = $0; next } { print v "-" $0 }'))
fi
[ ${#TARGETS[@]} -eq 0 ] && { echo "usage: $0 <version-loader>... | all"; exit 2; }

FAILED=0
for P in "${TARGETS[@]}"; do
  V=${P%-*}; L=${P##*-}
  if [ "$L" = fabric ]; then DIR=$ROOT/run/$V; else DIR=$ROOT/run/$V-neoforge; fi
  mkdir -p "$DIR"
  echo "eula=true" > "$DIR/eula.txt"
  printf 'level-type=minecraft\\:flat\nonline-mode=false\nspawn-protection=0\nmax-tick-time=-1\nenable-rcon=true\nrcon.port=25575\nrcon.password=smoketest\n' > "$DIR/server.properties"
  LOG=$OUT/$P.log; : > "$LOG"

  ARGS=(); [ "$L" = fabric ] && ARGS=(--args=nogui)
  ( cd "$ROOT" && ./gradlew ":$P:runServer" ${ARGS[@]+"${ARGS[@]}"} < /dev/null > "$LOG" 2>&1; echo "GRADLE_EXIT=$?" >> "$LOG" ) &

  STATUS=timeout
  for _ in $(seq 1 900); do
    if grep -q 'RCON running' "$LOG"; then STATUS=started; break; fi
    if grep -q 'GRADLE_EXIT=' "$LOG"; then STATUS=exited; break; fi
    sleep 1
  done

  PLACED=0
  if [ "$STATUS" = started ]; then
    CMDS=("forceload add 0 0")
    for b in granite_bricks cracked_diorite_bricks andesite_brick_stairs granite_brick_slab diorite_brick_wall chiseled_andesite_bricks; do
      CMDS+=("setblock 0 100 0 morebricks:$b")
    done
    PLACED=$(python3 -I "$ROOT/scripts/rcon.py" 25575 smoketest "${CMDS[@]}" 2>&1 | grep -c 'Changed the block')
    python3 -I "$ROOT/scripts/rcon.py" 25575 smoketest stop > /dev/null 2>&1
  fi
  for _ in $(seq 1 180); do grep -q 'GRADLE_EXIT=' "$LOG" && break; sleep 1; done

  ERRORS=$(sed 's/\x1b\[[0-9;]*m//g' "$LOG" | grep -E '/ERROR\]|Exception' | grep -vE 'No key layers' | head -5)
  if [ "$STATUS" = started ] && [ "$PLACED" = 6 ] && grep -q 'GRADLE_EXIT=0' "$LOG" && [ -z "$ERRORS" ]; then
    echo "PASS $P"
  else
    echo "FAIL $P ($STATUS, placed $PLACED/6, see $LOG)"; [ -n "$ERRORS" ] && echo "$ERRORS"
    FAILED=1
  fi
done
exit $FAILED
