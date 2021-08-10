package com.fanrende.lavuestia.datagen;

import com.fanrende.lavuestia.setup.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider
{
	public Recipes(DataGenerator generatorIn)
	{
		super(generatorIn);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
	{
		ShapedRecipeBuilder.shaped(Registration.METALINFUSER_BLOCK.get())
				.pattern("#x#")
				.pattern("sos")
				.pattern("#r#")
				.define('#', Blocks.BRICKS)
				.define('x', Tags.Items.INGOTS_BRICK)
				.define('s', Tags.Items.SAND)
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('o', Tags.Items.OBSIDIAN)
				.group("lavuestia")
				.unlockedBy("obsidian", InventoryChangeTrigger.Instance.hasItems(Blocks.OBSIDIAN))
				.save(consumer);
	}
}
