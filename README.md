# MoreBricks

Adds vanilla-style brick sets for **granite**, **diorite** and **andesite**, for Fabric and NeoForge.

Each stone gets four blocks, in the Building Blocks tab right after its polished stone:

| Block | Recipe |
|---|---|
| Bricks | 4 polished stone in a 2×2 → 4 bricks |
| Brick Stairs | 6 bricks → 4 stairs |
| Brick Slab | 3 bricks → 6 slabs |
| Brick Wall | 6 bricks → 6 walls |

Every shape can also be made in a stonecutter from the raw stone, polished stone or bricks.

## Supported versions

| Minecraft | Fabric | NeoForge |
|---|:-:|:-:|
| 26.3 | ✓ | ✓ |
| 26.2 | ✓ | ✓ |
| 26.1 – 26.1.2 | ✓ | ✓ (26.1.2) |
| 1.21.11 | ✓ | ✓ |
| 1.21.9 – 1.21.10 | ✓ | ✓ (1.21.10) |
| 1.21.6 – 1.21.8 | ✓ | ✓ (1.21.8) |
| 1.21.5 | ✓ | ✓ |
| 1.21.4 | ✓ | ✓ |
| 1.21 – 1.21.1 | ✓ | ✓ |
| 1.20.5 – 1.20.6 | ✓ | ✓ (1.20.6) |
| 1.20.3 – 1.20.4 | ✓ | ✓ (1.20.4) |
| 1.20 – 1.20.1 | ✓ | — |

The Fabric version requires [Fabric API](https://modrinth.com/mod/fabric-api).

## Development

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

### Common tasks

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

### Adding a block

1. Register it in `BrickSet` (or `ModBlocks`) so both loaders pick it up.
2. Add its model, recipe, loot table and tags in `fabric/datagen/`.
3. Add its name to `assets/morebricks/lang/en_us.json` and its texture to `assets/morebricks/textures/block/`.
4. Run `./gradlew runDatagen` and commit the regenerated `src/generated/` files.

## Releasing

Releases are uploaded to [Modrinth](https://modrinth.com/mod/morebricks) and
[CurseForge](https://www.curseforge.com/projects/922038) by the `Publish` GitHub Actions workflow.

1. Bump `mod.version` in `stonecutter.properties.toml` and add the changes to `CHANGELOG.md`.
2. Commit and push.
3. Create a GitHub release tagged `v<mod version>` (for example `v2.0.0`), with the changes as its description.

The workflow builds every version, uploads each jar with its Minecraft versions and loader, and attaches
the jars to the GitHub release. It needs two repository secrets (Settings → Secrets and variables → Actions):

- `MODRINTH_TOKEN`: a [Modrinth personal access token](https://modrinth.com/settings/pats) with the
  *Create versions* scope.
- `CURSEFORGE_TOKEN`: a [CurseForge API token](https://legacy.curseforge.com/account/api-tokens).

To check the upload settings locally without uploading anything, run `./gradlew publishMods` with no
tokens set; it does a dry run.

## Contributing

Issues and pull requests are welcome on [GitHub](https://github.com/PunJarrr/MoreBricks). Please run
`./gradlew runDatagen` and commit the regenerated files if your change touches blocks or recipes.

## Credits

The granite, diorite and andesite brick textures come from the [Vanilla Tweaks](https://vanillatweaks.net/)
resource pack, used under the [Vanilla Tweaks terms](https://vanillatweaks.net/terms/). See [credits.txt](credits.txt),
which is also included in every jar. Keep this credit on every page where the mod is published.

## License

The code is [MIT](LICENSE) licensed. Textures from Vanilla Tweaks remain under the Vanilla Tweaks terms.
