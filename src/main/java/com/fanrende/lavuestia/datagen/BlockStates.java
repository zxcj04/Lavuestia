package com.fanrende.lavuestia.datagen;

import com.fanrende.lavuestia.Lavuestia;
import com.fanrende.lavuestia.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider
{
	public BlockStates(DataGenerator generator, ExistingFileHelper exFileHelper)
	{
		super(generator, Lavuestia.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		registerMetalInfuserBlock();
	}

	private void registerMetalInfuserBlock()
	{
		ResourceLocation texture = new ResourceLocation(Lavuestia.MODID, "block/infuser/metal_infuser_block");
		ResourceLocation texture_front = new ResourceLocation(Lavuestia.MODID, "block/infuser/metal_infuser_block_front");
		ResourceLocation texture_front_powered = new ResourceLocation(Lavuestia.MODID, "block/infuser/metal_infuser_block_front_powered");

		BlockModelBuilder modelBuilder = models().cube("metal_infuser_block", texture, texture, texture_front, texture, texture, texture);
		BlockModelBuilder modelBuilderPowered = models().cube("metal_infuser_block_powered", texture, texture, texture_front_powered, texture, texture, texture);

		modelBuilder.texture("particle", modLoc("block/infuser/metal_infuser_block_front"));
		modelBuilderPowered.texture("particle", modLoc("block/infuser/metal_infuser_block_front_powered"));

		orientedBlock(Registration.METALINFUSER_BLOCK.get(), state ->
		{
			if(state.getValue(BlockStateProperties.POWERED))
				return modelBuilderPowered;
			else
				return modelBuilder;
		});
	}

	private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc)
	{
		getVariantBuilder(block).forAllStates(state ->
		{
			Direction dir = state.getValue(BlockStateProperties.FACING);
			return ConfiguredModel.builder()
					.modelFile(modelFunc.apply(state))
					.rotationX(dir.getAxis() == Direction.Axis.Y ? dir.getAxisDirection().getStep() * -90 : 0)
					.rotationY(dir.getAxis() != Direction.Axis.Y ? ( ( dir.get2DDataValue() + 2 ) % 4 ) * 90 : 0)
					.build();
		});
	}
}
