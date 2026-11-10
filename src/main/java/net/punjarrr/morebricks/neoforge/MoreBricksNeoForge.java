package net.punjarrr.morebricks.neoforge;

//? if neoforge {
/*import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.punjarrr.morebricks.MoreBricks;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(MoreBricks.MOD_ID)
public final class MoreBricksNeoForge {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MoreBricks.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MoreBricks.MOD_ID);

    public MoreBricksNeoForge(IEventBus modBus) {
        ModBlocks.register(MoreBricksNeoForge::register);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(MoreBricksNeoForge::addToCreativeTab);
    }

    private static Supplier<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, Supplier<BlockBehaviour.Properties> properties) {
        DeferredHolder<Block, Block> block = BLOCKS.register(name, id -> {
            BlockBehaviour.Properties blockProperties = properties.get();
            //? if >=1.21.2
            blockProperties.setId(ResourceKey.create(Registries.BLOCK, id));
            return factory.apply(blockProperties);
        });
        ITEMS.register(name, id -> {
            Item.Properties itemProperties = new Item.Properties();
            //? if >=1.21.2
            itemProperties.setId(ResourceKey.create(Registries.ITEM, id)).useBlockDescriptionPrefix();
            return new BlockItem(block.get(), itemProperties);
        });
        return block;
    }

    private static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.BUILDING_BLOCKS) return;
        for (BrickSet set : ModBlocks.SETS) {
            ItemStack previous = new ItemStack(set.insertAfter);
            for (Block block : set.blocks()) {
                ItemStack stack = new ItemStack(block);
                //? if >=1.21 {
                event.insertAfter(previous, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                //?} else
                /^event.getEntries().putAfter(previous, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);^/
                previous = stack;
            }
        }
    }
}
*///?}
