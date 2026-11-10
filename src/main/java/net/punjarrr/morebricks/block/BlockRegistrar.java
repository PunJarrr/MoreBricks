package net.punjarrr.morebricks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Registers a block and its block item. Each mod loader provides its own implementation,
 * since Fabric registers immediately and NeoForge registers through deferred registers.
 */
@FunctionalInterface
public interface BlockRegistrar {
    Supplier<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, Supplier<BlockBehaviour.Properties> properties);
}
