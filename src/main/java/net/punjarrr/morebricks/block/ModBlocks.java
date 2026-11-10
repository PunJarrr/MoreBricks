package net.punjarrr.morebricks.block;

import net.minecraft.world.level.block.Blocks;

import java.util.List;

public final class ModBlocks {
    public static final BrickSet GRANITE = new BrickSet("granite", Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB);
    public static final BrickSet DIORITE = new BrickSet("diorite", Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB);
    public static final BrickSet ANDESITE = new BrickSet("andesite", Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB);

    public static final List<BrickSet> SETS = List.of(GRANITE, DIORITE, ANDESITE);

    private ModBlocks() {}

    public static void register(BlockRegistrar registrar) {
        for (BrickSet set : SETS) set.register(registrar);
    }
}
