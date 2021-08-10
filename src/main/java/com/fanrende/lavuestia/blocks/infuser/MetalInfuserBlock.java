package com.fanrende.lavuestia.blocks.infuser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class MetalInfuserBlock extends Block
{
	public MetalInfuserBlock()
	{
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(4.0f)
				.lightLevel(state -> state.getValue(BlockStateProperties.POWERED)? 14: 0));
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new MetalInfuserTile();
	}

	@Override
	public ActionResultType use(
			BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit
	)
	{
		if(!world.isClientSide)
		{
			TileEntity tileEntity = world.getBlockEntity(pos);

			if(tileEntity instanceof MetalInfuserTile)
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getBlockPos());
			else
				throw new IllegalStateException("Our named container provider is missing!");
		}

		return ActionResultType.SUCCESS;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState state = defaultBlockState()
				.setValue(BlockStateProperties.FACING,context.getHorizontalDirection().getOpposite())
				.setValue(BlockStateProperties.POWERED, false);
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}
