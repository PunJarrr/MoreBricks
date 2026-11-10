package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

final class ModBlockLootProvider extends FabricBlockLootSubProvider {
    ModBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void generate() {
        for (BrickSet set : ModBlocks.SETS) {
            for (Block block : set.blocks()) {
                if (block == set.slab()) add(block, createSlabItemTable(block));
                else dropSelf(block);
            }
        }
    }
}
//?}
