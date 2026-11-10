package net.punjarrr.morebricks.fabric;

//? if fabric {
import net.fabricmc.api.ModInitializer;
//? if >=26.1 {
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
//?} else
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;*/
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.punjarrr.morebricks.MoreBricks;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.function.Function;
import java.util.function.Supplier;

public final class MoreBricksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBlocks.register(MoreBricksFabric::register);

        //? if >=26.1 {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> {
            for (BrickSet set : ModBlocks.SETS) {
                Block previous = set.insertAfter;
                for (Block block : set.blocks()) {
                    output.insertAfter(previous, block);
                    previous = block;
                }
            }
        });
        //?} else {
        /*ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            for (BrickSet set : ModBlocks.SETS) {
                Block previous = set.insertAfter;
                for (Block block : set.blocks()) {
                    entries.addAfter(previous, block);
                    previous = block;
                }
            }
        });
        *///?}
    }

    private static Supplier<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, Supplier<BlockBehaviour.Properties> properties) {
        Identifier id = MoreBricks.id(name);
        BlockBehaviour.Properties blockProperties = properties.get();
        Item.Properties itemProperties = new Item.Properties();
        //? if >=1.21.2 {
        blockProperties.setId(ResourceKey.create(Registries.BLOCK, id));
        itemProperties.setId(ResourceKey.create(Registries.ITEM, id)).useBlockDescriptionPrefix();
        //?}

        Block block = Registry.register(BuiltInRegistries.BLOCK, id, factory.apply(blockProperties));
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, itemProperties));
        return () -> block;
    }
}
//?}
