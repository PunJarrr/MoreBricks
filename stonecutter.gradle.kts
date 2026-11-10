stonecutter active "26.3-fabric"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)

    // Applies version- and loader-specific values from `stonecutter.properties.toml`
    properties {
        tags(version, loader)
    }

    // Enables `//? if fabric {` and `//? if neoforge {` in source files
    constants {
        match(loader, "fabric", "neoforge")
    }

    replacements {
        // Mojang renamed ResourceLocation to Identifier in 1.21.11
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }

        // Fabric API renamed its data generation classes to match Mojang's names in 26.1
        string(current.parsed >= "26.1") {
            replace("FabricDataOutput", "FabricPackOutput")
            replace("FabricBlockLootTableProvider", "FabricBlockLootSubProvider")
            replace("FabricTagProvider", "FabricTagsProvider")
            replace("BlockTagProvider", "BlockTagsProvider")
            replace("ItemTagProvider", "ItemTagsProvider")
        }

        // Model generation moved to Minecraft's client code in 1.21.4
        string(current.parsed >= "1.21.4") {
            replace("net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider", "net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider")
            replace("net.minecraft.data.models.", "net.minecraft.client.data.models.")
        }
    }
}
