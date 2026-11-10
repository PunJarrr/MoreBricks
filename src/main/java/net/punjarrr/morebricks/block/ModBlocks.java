package net.punjarrr.morebricks.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.punjarrr.morebricks.MoreBricks;

import java.util.function.Function;

public class ModBlocks {
    public static final Block GRANITE_BRICKS = register("granite_bricks",
            AbstractBlock.Settings.copy(Blocks.GRANITE), Block::new);
    public static final Block DIORITE_BRICKS = register("diorite_bricks",
            AbstractBlock.Settings.copy(Blocks.DIORITE), Block::new);
    public static final Block ANDESITE_BRICKS = register("andesite_bricks",
            AbstractBlock.Settings.copy(Blocks.ANDESITE), Block::new);

    public static final Block GRANITE_BRICK_STAIRS = register("granite_brick_stairs",
            AbstractBlock.Settings.copy(GRANITE_BRICKS),
            s -> new StairsBlock(GRANITE_BRICKS.getDefaultState(), s) {});
    public static final Block DIORITE_BRICK_STAIRS = register("diorite_brick_stairs",
            AbstractBlock.Settings.copy(DIORITE_BRICKS),
            s -> new StairsBlock(DIORITE_BRICKS.getDefaultState(), s) {});
    public static final Block ANDESITE_BRICK_STAIRS = register("andesite_brick_stairs",
            AbstractBlock.Settings.copy(ANDESITE_BRICKS),
            s -> new StairsBlock(ANDESITE_BRICKS.getDefaultState(), s) {});

    public static final Block GRANITE_BRICK_SLAB = register("granite_brick_slab",
            AbstractBlock.Settings.copy(GRANITE_BRICKS), SlabBlock::new);
    public static final Block DIORITE_BRICK_SLAB = register("diorite_brick_slab",
            AbstractBlock.Settings.copy(DIORITE_BRICKS), SlabBlock::new);
    public static final Block ANDESITE_BRICK_SLAB = register("andesite_brick_slab",
            AbstractBlock.Settings.copy(ANDESITE_BRICKS), SlabBlock::new);

    private static Block register(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, Block> factory) {
        Identifier id = Identifier.of(MoreBricks.MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Block block = factory.apply(settings.registryKey(blockKey));
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, new Item.Settings().registryKey(itemKey)));
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static void initialize() {}
}
