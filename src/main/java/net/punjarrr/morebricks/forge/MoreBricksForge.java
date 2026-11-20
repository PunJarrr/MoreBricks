package net.punjarrr.morebricks.forge;

//? if forge {
/*import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.punjarrr.morebricks.MoreBricks;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(MoreBricks.MOD_ID)
public final class MoreBricksForge {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MoreBricks.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MoreBricks.MOD_ID);

    public MoreBricksForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.register(MoreBricksForge::register);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(MoreBricksForge::addToCreativeTab);
    }

    private static Supplier<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, Supplier<BlockBehaviour.Properties> properties) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> factory.apply(properties.get()));
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.BUILDING_BLOCKS) return;
        for (BrickSet set : ModBlocks.SETS) {
            ItemStack previous = new ItemStack(set.insertAfter);
            for (Block block : set.blocks()) {
                ItemStack stack = new ItemStack(block);
                event.getEntries().putAfter(previous, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                previous = stack;
            }
        }
    }
}
*///?}
