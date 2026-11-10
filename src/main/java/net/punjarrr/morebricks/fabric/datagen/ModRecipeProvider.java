package net.punjarrr.morebricks.fabric.datagen;

//? if fabric {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.punjarrr.morebricks.block.BrickSet;
import net.punjarrr.morebricks.block.ModBlocks;
//? if >=26.3 {
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
//?}
//? if >=26.1
import net.minecraft.world.item.crafting.CookingBookCategory;
//? if <1.21.2 {
/*import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;
*///?}
//? if >=1.20.2 && <26.3
/*import net.minecraft.data.recipes.RecipeOutput;*/
//? if <1.20.2 {
/*import net.minecraft.data.recipes.FinishedRecipe;
import java.util.function.Consumer;
*///?}

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
        //? if >=1.20.5 {
        super(output, registries);
        //?} else
        /*super(output);*/
    }

    //? if >=26.3 {
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new Recipes(recipes, advancements);
    }
    //?} elif >=1.21.2 {
    /*@Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new Recipes(registries, output);
    }
    *///?}

    //? if >=1.20.2 && <1.21.2 {
    /*private RecipeOutput output;

    @Override
    public void buildRecipes(RecipeOutput output) {
        this.output = output;
        new Recipes().buildRecipes();
    }
    *///?}

    //? if <1.20.2 {
    /*private Consumer<FinishedRecipe> output;

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        this.output = output;
        new Recipes().buildRecipes();
    }
    *///?}

    @Override
    public String getName() {
        return "MoreBricks recipes";
    }

    //? if >=1.21.2 {
    private static final class Recipes extends RecipeProvider {
        //? if >=26.3 {
        Recipes(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
            super(recipes, advancements);
        }
        //?} else {
        /*Recipes(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }
        *///?}

        @Override
    //?} else {
    /*// Before 1.21.2 the vanilla recipe helpers are static and take the output; these adapters
    // give them the instance-method shape used since 1.21.2, so the recipe list is shared.
    private final class Recipes {
        private ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
            return ShapedRecipeBuilder.shaped(category, result, count);
        }

        private RecipeBuilder stairBuilder(ItemLike result, Ingredient material) {
            return RecipeProvider.stairBuilder(result, material);
        }

        private void slab(RecipeCategory category, ItemLike result, ItemLike material) {
            RecipeProvider.slab(output, category, result, material);
        }

        private void wall(RecipeCategory category, ItemLike result, ItemLike material) {
            RecipeProvider.wall(output, category, result, material);
        }

        private void chiseled(RecipeCategory category, ItemLike result, ItemLike material) {
            RecipeProvider.chiseled(output, category, result, material);
        }

        private void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material) {
            RecipeProvider.stonecutterResultFromBase(output, category, result, material);
        }

        private void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike material, int count) {
            RecipeProvider.stonecutterResultFromBase(output, category, result, material, count);
        }

    *///?}
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
                //? if >=26.1 {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(set.bricks()), BUILDING_BLOCKS, CookingBookCategory.BLOCKS, set.crackedBricks(), 0.1F, 200)
                //?} else
                /*SimpleCookingRecipeBuilder.smelting(Ingredient.of(set.bricks()), BUILDING_BLOCKS, set.crackedBricks(), 0.1F, 200)*/
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
    }
}
//?}
