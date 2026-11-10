package net.punjarrr.morebricks.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.punjarrr.morebricks.MoreBricks;
import net.punjarrr.morebricks.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup MOREBRICKS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(MoreBricks.MOD_ID, "morebricks"),
            ItemGroup.create(ItemGroup.Row.BOTTOM, 0)
                    .displayName(Text.translatable("itemGroup.morebricks.morebricks"))
                    .icon(() -> new ItemStack(ModBlocks.GRANITE_BRICKS))
                    .entries((context, entries) -> {
                        entries.add(ModBlocks.GRANITE_BRICKS);
                        entries.add(ModBlocks.GRANITE_BRICK_STAIRS);
                        entries.add(ModBlocks.GRANITE_BRICK_SLAB);
                        entries.add(ModBlocks.DIORITE_BRICKS);
                        entries.add(ModBlocks.DIORITE_BRICK_STAIRS);
                        entries.add(ModBlocks.DIORITE_BRICK_SLAB);
                        entries.add(ModBlocks.ANDESITE_BRICKS);
                        entries.add(ModBlocks.ANDESITE_BRICK_STAIRS);
                        entries.add(ModBlocks.ANDESITE_BRICK_SLAB);
                    })
                    .build()
    );

    public static void initialize() {}
}
