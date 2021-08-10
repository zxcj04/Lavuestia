package com.fanrende.lavuestia.datagen;

import com.fanrende.lavuestia.Lavuestia;
import com.fanrende.lavuestia.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider
{
	public Items(
			DataGenerator generator, String modid, ExistingFileHelper existingFileHelper
	)
	{
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		withExistingParent(Registration.METALINFUSER_BLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Lavuestia.MODID, "block/metal_infuser_block"));
	}
}
