package net.punjarrr.morebricks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;
import java.util.function.Supplier;

/**
 * A vanilla-style brick family made from one stone type:
 * bricks, cracked bricks, stairs, slab, wall and chiseled bricks.
 */
public final class BrickSet {
    /** Stone name, e.g. "granite". */
    public final String name;
    /** The raw stone, e.g. granite. */
    public final Block stone;
    /** The polished stone the bricks are crafted from, e.g. polished granite. */
    public final Block polished;
    /** The last vanilla block of this stone in the Building Blocks tab; bricks are listed after it. */
    public final Block insertAfter;

    private Supplier<Block> bricks, crackedBricks, stairs, slab, wall, chiseledBricks;

    BrickSet(String name, Block stone, Block polished, Block insertAfter) {
        this.name = name;
        this.stone = stone;
        this.polished = polished;
        this.insertAfter = insertAfter;
    }

    void register(BlockRegistrar registrar) {
        bricks = registrar.register(name + "_bricks", Block::new, this::properties);
        crackedBricks = registrar.register("cracked_" + name + "_bricks", Block::new, this::properties);
        stairs = registrar.register(name + "_brick_stairs",
                properties -> new StairBlock(bricks.get().defaultBlockState(), properties) {}, this::properties);
        slab = registrar.register(name + "_brick_slab", SlabBlock::new, this::properties);
        wall = registrar.register(name + "_brick_wall", WallBlock::new, () -> properties().forceSolidOn());
        chiseledBricks = registrar.register("chiseled_" + name + "_bricks", Block::new, this::properties);
    }

    private BlockBehaviour.Properties properties() {
        //? if >=1.20.4 {
        return BlockBehaviour.Properties.ofFullCopy(stone);
        //?} else
        /*return BlockBehaviour.Properties.copy(stone);*/
    }

    public Block bricks() { return bricks.get(); }
    public Block crackedBricks() { return crackedBricks.get(); }
    public Block stairs() { return stairs.get(); }
    public Block slab() { return slab.get(); }
    public Block wall() { return wall.get(); }
    public Block chiseledBricks() { return chiseledBricks.get(); }

    /** All blocks of the set, in vanilla's Building Blocks tab order. */
    public List<Block> blocks() {
        return List.of(bricks(), crackedBricks(), stairs(), slab(), wall(), chiseledBricks());
    }
}
