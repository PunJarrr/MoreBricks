package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

import static net.punjarrr.morebricks.fabric.datagen.MoreBricksDataGenerator.key;

final class ModTagProviders {
    private ModTagProviders() {}

    static final class Blocks extends FabricTagsProvider.BlockTagsProvider {
        Blocks(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            for (BrickSet set : ModBlocks.SETS) {
                set.blocks().forEach(block -> builder(BlockTags.MINEABLE_WITH_PICKAXE).add(key(block)));
                builder(BlockItemTags.STAIRS).add(key(set.stairs()));
                builder(BlockItemTags.SLABS).add(key(set.slab()));
                builder(BlockItemTags.WALLS).add(key(set.wall()));
            }
        }
    }

    static final class Items extends FabricTagsProvider.ItemTagsProvider {
        Items(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, Blocks blockTags) {
            super(output, registries, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            copy(BlockItemTags.STAIRS);
            copy(BlockItemTags.SLABS);
            copy(BlockItemTags.WALLS);
        }
    }
}
//?}
