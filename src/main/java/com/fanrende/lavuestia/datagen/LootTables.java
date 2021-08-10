package com.fanrende.lavuestia.datagen;

import com.fanrende.lavuestia.setup.Registration;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider
{
	public LootTables(DataGenerator dataGeneratorIn)
	{
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables()
	{
		lootTables.put(Registration.METALINFUSER_BLOCK.get(), createSelfDropTable("metalinfuser_block", Registration.METALINFUSER_BLOCK.get()));
	}
}
