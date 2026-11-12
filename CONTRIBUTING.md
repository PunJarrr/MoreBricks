# Contributing to MoreBricks

Thanks for helping out! Bug reports and ideas go in the
[issue tracker](https://github.com/PunJarrr/MoreBricks/issues). This page explains how to build the mod
and change its code.

## How the project works

The project uses [Stonecutter](https://stonecutter.kikugie.dev/) to build every Minecraft version and
mod loader from one codebase. Read the
[Stonecutter beginner's guide](https://stonecutter.kikugie.dev/wiki/start/) before changing code.

- `src/main/` holds the shared source. Version- and loader-specific lines are marked with Stonecutter
  comments such as `//? if >=1.21.2 {` or `//? if neoforge {`. Code is written with Mojang's official names.
- `stonecutter.properties.toml` lists the mod version and the dependency versions for each target.
- `settings.gradle.kts` lists the targets. Each one builds in `versions/<minecraft>-<loader>/`.
- `src/generated/<minecraft>/` holds block states, models, recipes, loot tables and tags made by data
  generation. Don't edit these files by hand.

Java 25, 21 and 17 are needed for 26.x, 1.20.5–1.21.x and 1.20.1–1.20.4 respectively. Gradle downloads
any that are missing.

## Common tasks

Switch the version your IDE edits (this rewrites the Stonecutter comments in `src/`):

```bash
./gradlew "Set active project to 1.21.1-fabric"
```

Switch back to the committed version before committing:

```bash
./gradlew "Reset active project"
```

Regenerate data after changing blocks or recipes (one Fabric target, or all of them):

```bash
./gradlew :26.3-fabric:runDatagen
```

```bash
./gradlew runDatagen
```

Run the game for one target:

```bash
./gradlew :26.3-fabric:runClient
```

```bash
./gradlew :26.3-neoforge:runClient
```

Check that the mod loads: boots a dev server, places each kind of block, and reports errors
(running it accepts the [Minecraft EULA](https://aka.ms/MinecraftEULA) for the dev servers):

```bash
scripts/smoke-test.sh 26.3-fabric 26.3-neoforge
```

```bash
scripts/smoke-test.sh all
```

Build every jar into `build/libs/<mod version>/`:

```bash
./gradlew buildAndCollect
```

## Adding a block

1. Register it in `BrickSet` (or `ModBlocks`) so both loaders pick it up.
2. Add its model, recipe, loot table and tags in `fabric/datagen/`.
3. Add its name to `assets/morebricks/lang/en_us.json` and its texture to `assets/morebricks/textures/block/`.
4. Run `./gradlew runDatagen` and commit the regenerated `src/generated/` files.

## Pull requests

- Run `./gradlew runDatagen` and commit the regenerated files if your change touches blocks or recipes.
- Run `./gradlew "Reset active project"` before committing.
- Check at least one Fabric and one NeoForge version with `scripts/smoke-test.sh`.

## Releasing (maintainers)

Releases are uploaded to [Modrinth](https://modrinth.com/mod/morebricks) and
[CurseForge](https://www.curseforge.com/projects/922038)
by the `Publish` GitHub Actions workflow.

1. Bump `mod.version` in `stonecutter.properties.toml` and add the changes to `CHANGELOG.md`.
2. Commit and push.
3. Create a GitHub release tagged `v<mod version>` (for example `v2.0.0`), with the changes as its description.

The workflow builds every version, uploads each jar with its Minecraft versions and loader, and attaches
the jars to the GitHub release. NeoForge files are uploaded first and Fabric files last, each from the
oldest Minecraft version to the newest, so the newest Fabric file is at the top of each site's list.

It needs two repository secrets (Settings → Secrets and variables → Actions):

- `MODRINTH_TOKEN`: a [Modrinth personal access token](https://modrinth.com/settings/pats) with the
  *Create versions* scope.
- `CURSEFORGE_TOKEN`: a [CurseForge API token](https://legacy.curseforge.com/account/api-tokens).

To check the upload settings locally without uploading anything, run `./gradlew publishMods` with no
tokens set; it does a dry run.
