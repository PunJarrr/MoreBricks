package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS;
import static net.minecraft.data.recipes.RecipeCategory.DECORATIONS;

/**
 * Vanilla-style recipes, matching how stone and deepslate bricks are made:
 * polished stone -> bricks -> stairs/slab/wall/chiseled, bricks smelt into cracked bricks,
 * and every shape is also available from the stonecutter.
 */
final class ModRecipeProvider extends FabricRecipeProvider {
    ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                for (BrickSet set : ModBlocks.SETS) {
                    shaped(BUILDING_BLOCKS, set.bricks(), 4)
                            .define('#', set.polished)
                            .pattern("##")
                            .pattern("##")
                            .unlockedBy(getHasName(set.polished), has(set.polished))
                            .save(output);
                    stairBuilder(set.stairs(), Ingredient.of(set.bricks()))
                            .unlockedBy(getHasName(set.bricks()), has(set.bricks()))
                            .save(output);
                    slab(BUILDING_BLOCKS, set.slab(), set.bricks());
                    wall(DECORATIONS, set.wall(), set.bricks());
                    chiseled(BUILDING_BLOCKS, set.chiseledBricks(), set.slab());
                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(set.bricks()), BUILDING_BLOCKS, CookingBookCategory.BLOCKS, set.crackedBricks(), 0.1F, 200)
                            .unlockedBy(getHasName(set.bricks()), has(set.bricks()))
                            .save(output);

                    for (Block base : List.of(set.stone, set.polished, set.bricks())) {
                        if (base != set.bricks()) stonecutterResultFromBase(BUILDING_BLOCKS, set.bricks(), base);
                        stonecutterResultFromBase(BUILDING_BLOCKS, set.stairs(), base);
                        stonecutterResultFromBase(BUILDING_BLOCKS, set.slab(), base, 2);
                        stonecutterResultFromBase(DECORATIONS, set.wall(), base);
                        stonecutterResultFromBase(BUILDING_BLOCKS, set.chiseledBricks(), base);
                    }
                }
            }
        };
    }

    @Override
    public String getName() {
        return "MoreBricks recipes";
    }
}
//?}
