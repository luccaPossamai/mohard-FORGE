package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.block.MohardBlocks;
import net.lpcamors.mohard.data.MohardTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class MohardRecipeProvider extends RecipeProvider {

    public MohardRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MohardBlocks.ESSENCE_ALTAR.get())
                .define('L', ItemTags.LOGS)
                .define('D', Blocks.DEEPSLATE)
                .define('E', MohardTags.Items.ESSENCE)
                .pattern(" E ")
                .pattern("LDL")
                .pattern("LDL")
                .unlockedBy("has_item", has(MohardTags.Items.ESSENCE))
                .save(consumer);
    }
}
