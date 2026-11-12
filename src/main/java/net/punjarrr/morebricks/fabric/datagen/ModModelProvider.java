package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.data.BlockFamily;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

final class ModModelProvider extends FabricModelProvider {
    ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        for (BrickSet set : ModBlocks.SETS) {
            BlockFamily family = new BlockFamily.Builder(set.bricks())
                    .stairs(set.stairs())
                    .slab(set.slab())
                    .wall(set.wall())
                    .getFamily();
            generators.family(set.bricks()).generateFor(family);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        // Block items use their block models
    }
}
//?}
