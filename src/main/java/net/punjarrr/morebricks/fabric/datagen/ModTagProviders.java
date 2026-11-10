package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;
//? if >=26.2 {
import net.minecraft.tags.BlockItemTags;
//?} else
/*import net.minecraft.tags.ItemTags;*/

import java.util.concurrent.CompletableFuture;

import static net.punjarrr.morebricks.fabric.datagen.MoreBricksDataGenerator.key;

final class ModTagProviders {
    //? if >=26.2 {
    // Since 26.2 these are block-and-item tag pairs
    private static final TagKey<Item> STAIRS = BlockItemTags.STAIRS.item();
    private static final TagKey<Item> SLABS = BlockItemTags.SLABS.item();
    private static final TagKey<Item> WALLS = BlockItemTags.WALLS.item();
    //?} else {
    /*private static final TagKey<Item> STAIRS = ItemTags.STAIRS;
    private static final TagKey<Item> SLABS = ItemTags.SLABS;
    private static final TagKey<Item> WALLS = ItemTags.WALLS;
    *///?}

    private ModTagProviders() {}

    static final class Blocks extends FabricTagsProvider.BlockTagsProvider {
        Blocks(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            for (BrickSet set : ModBlocks.SETS) {
                for (Block block : set.blocks()) add(BlockTags.MINEABLE_WITH_PICKAXE, block);
                add(BlockTags.STAIRS, set.stairs());
                add(BlockTags.SLABS, set.slab());
                add(BlockTags.WALLS, set.wall());
            }
        }

        private void add(TagKey<Block> tag, Block block) {
            //? if >=1.21.6 {
            builder(tag).add(key(block));
            //?} else
            /*getOrCreateTagBuilder(tag).add(key(block));*/
        }
    }

    static final class Items extends FabricTagsProvider.ItemTagsProvider {
        Items(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, Blocks blockTags) {
            super(output, registries, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            copy(BlockTags.STAIRS, STAIRS);
            copy(BlockTags.SLABS, SLABS);
            copy(BlockTags.WALLS, WALLS);
        }
    }
}
//?}
