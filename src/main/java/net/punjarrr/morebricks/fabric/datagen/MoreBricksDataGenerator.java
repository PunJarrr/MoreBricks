package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

/**
 * Generates block states, models, recipes, loot tables and tags into `src/generated/{minecraft version}/`.
 * Run with `./gradlew runDatagen` on each Fabric version; the NeoForge build reuses the output.
 */
public final class MoreBricksDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockLootProvider::new);
        ModTagProviders.Blocks blockTags = pack.addProvider(ModTagProviders.Blocks::new);
        pack.addProvider((output, registries) -> new ModTagProviders.Items(output, registries, blockTags));
    }

    static ResourceKey<Block> key(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }
}
//?}
