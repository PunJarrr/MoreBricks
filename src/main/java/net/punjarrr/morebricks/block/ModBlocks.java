package net.punjarrr.morebricks.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.punjarrr.morebricks.MoreBricks;

public class ModBlocks {
    public static final Block GRANITE_BRICKS = ModBlocks.register("granite_bricks",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(Instrument.BASEDRUM)
                    .requiresTool()
                    .strength(1.5f, 6.0f)));
    public static final Block DIORITE_BRICKS = ModBlocks.register("diorite_bricks",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OFF_WHITE)
                    .instrument(Instrument.BASEDRUM)
                    .requiresTool()
                    .strength(1.5f, 6.0f)));
    public static final Block ANDESITE_BRICKS = ModBlocks.register("andesite_bricks",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(Instrument.BASEDRUM)
                    .requiresTool()
                    .strength(1.5f, 6.0f)));

    public static final Block GRANITE_BRICK_STAIRS = ModBlocks.register("granite_brick_stairs",
            (Block)new StairsBlock(GRANITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(GRANITE_BRICKS)));
    public static final Block DIORITE_BRICK_STAIRS = ModBlocks.register("diorite_brick_stairs",
            (Block)new StairsBlock(DIORITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(DIORITE_BRICKS)));
    public static final Block ANDESITE_BRICK_STAIRS = ModBlocks.register("andesite_brick_stairs",
            (Block)new StairsBlock(ANDESITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(ANDESITE_BRICKS)));

    public static final Block GRANITE_BRICK_SLAB = ModBlocks.register("granite_brick_slab",
            (Block)new SlabBlock(AbstractBlock.Settings.copy(GRANITE_BRICKS)));
    public static final Block DIORITE_BRICK_SLAB = ModBlocks.register("diorite_brick_slab",
            (Block)new SlabBlock(AbstractBlock.Settings.copy(DIORITE_BRICKS)));
    public static final Block ANDESITE_BRICK_SLAB = ModBlocks.register("andesite_brick_slab",
            (Block)new SlabBlock(AbstractBlock.Settings.copy(ANDESITE_BRICKS)));

    private static Block register(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MoreBricks.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MoreBricks.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static void addBlockItemsToBuildingBlocksItemGroup(FabricItemGroupEntries entries) {
        // Granite Bricks
        entries.addAfter(Items.POLISHED_GRANITE_SLAB, ModBlocks.GRANITE_BRICKS);
        entries.addAfter(ModBlocks.GRANITE_BRICKS, ModBlocks.GRANITE_BRICK_STAIRS);
        entries.addAfter(ModBlocks.GRANITE_BRICK_STAIRS, ModBlocks.GRANITE_BRICK_SLAB);

        // Diorite Bricks
        entries.addAfter(Items.POLISHED_DIORITE_SLAB, ModBlocks.DIORITE_BRICKS);
        entries.addAfter(ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICK_STAIRS);
        entries.addAfter(ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.DIORITE_BRICK_SLAB);

        // Andesite Bricks
        entries.addAfter(Items.POLISHED_ANDESITE_SLAB, ModBlocks.ANDESITE_BRICKS);
        entries.addAfter(ModBlocks.ANDESITE_BRICKS, ModBlocks.ANDESITE_BRICK_STAIRS);
        entries.addAfter(ModBlocks.ANDESITE_BRICK_STAIRS, ModBlocks.ANDESITE_BRICK_SLAB);
    }

    public static void registerModBlocks() {
        MoreBricks.LOGGER.info("Registering ModBlocks for " + MoreBricks.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModBlocks::addBlockItemsToBuildingBlocksItemGroup);
    }
}
