package net.punjarrr.morebricks.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.punjarrr.morebricks.core.MoreBricks;

public class Blocks
{
    public static final Block GRANITE_BRICKS = Blocks.register("granite_bricks", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.DIRT_BROWN).requiresTool().strength(1.5f, 6.0f)), Items.POLISHED_GRANITE, ItemGroups.BUILDING_BLOCKS);
    public static final Block DIORITE_BRICKS = Blocks.register("diorite_bricks", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(1.5f, 6.0f)), Items.POLISHED_DIORITE, ItemGroups.BUILDING_BLOCKS);
    public static final Block ANDESITE_BRICKS = Blocks.register("andesite_bricks", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.DIRT_BROWN).requiresTool().strength(1.5f, 6.0f)), Items.POLISHED_ANDESITE, ItemGroups.BUILDING_BLOCKS);

    private static Block register(String id, Block block, Item previousItem, ItemGroup group)
    {
        registerBlockItem(id, block, previousItem, group);
        return Registry.register(Registries.BLOCK, new Identifier(MoreBricks.MOD_ID, id), block);
    }

    private static BlockItem registerBlockItem(String id, Block block, Item previousItem, ItemGroup group)
    {
        BlockItem item = Registry.register(Registries.ITEM, new Identifier(MoreBricks.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(previousItem, item));

        return item;
    }

    public static void registerModBlocks()
    {
        MoreBricks.LOGGER.info("Registering blocks for " + MoreBricks.MOD_ID);
    }
}
