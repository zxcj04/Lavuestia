package com.fanrende.lavuestia.blocks.infuser;

import com.fanrende.lavuestia.setup.Registration;
import com.fanrende.lavuestia.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalInfuserTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
	private final NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);

	private final ItemStackHandler item = this.createItemHandler(stacks);
	private final ItemStackHandler itemAutomation = this.createAutomationItemHandler(stacks);
	private final CustomEnergyStorage energy = this.createEnergyStorage();

	private final LazyOptional<IItemHandler> itemAutomationLazy = LazyOptional.of(() -> itemAutomation);
	private final LazyOptional<IEnergyStorage> energyLazy = LazyOptional.of(() -> energy);

	private int counter = 100;

	public MetalInfuserTile()
	{
		super(Registration.METALINFUSER_BLOCK_TILE.get());
	}

	public int getCounter()
	{
		return counter;
	}

	@Override
	public void tick()
	{
		if (counter > 0)
			counter--;

		if (counter <= 0)
		{
			counter = 100;

			stacks.set(1, stacks.get(0));
			stacks.set(0, ItemStack.EMPTY);
		}
	}

	@Override
	public void load(BlockState state, CompoundNBT tag)
	{
		CompoundNBT invTag = tag.getCompound("inv");
		item.deserializeNBT(invTag);

		CompoundNBT energyTag = tag.getCompound("energy");
		energy.deserializeNBT(energyTag);

		super.load(state, tag);
	}

	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		tag.put("inv", item.serializeNBT());
		tag.put("energy", energy.serializeNBT());

		return super.save(tag);
	}

	public ItemStackHandler getItemHandler()
	{
		return item;
	}

	private ItemStackHandler createItemHandler(NonNullList<ItemStack> stacks)
	{
		return new ItemStackHandler(stacks)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				setChanged();
			}

			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
			{
				if (slot == 1)
					return stack;

				return super.insertItem(slot, stack, simulate);
			}
		};
	}

	private ItemStackHandler createAutomationItemHandler(NonNullList<ItemStack> stacks)
	{
		return new ItemStackHandler(stacks)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				setChanged();
			}

			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
			{
				if (slot == 1)
					return stack;

				return super.insertItem(slot, stack, simulate);
			}

			@Nonnull
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate)
			{
				if (slot == 0)
					return ItemStack.EMPTY;

				return super.extractItem(slot, amount, simulate);
			}
		};
	}

	private CustomEnergyStorage createEnergyStorage()
	{
		return new CustomEnergyStorage(100000, 1000, 0);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return itemAutomationLazy.cast();
		if (cap == CapabilityEnergy.ENERGY)
			return energyLazy.cast();

		return super.getCapability(cap, side);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Nullable
	@Override
	public Container createMenu(
			int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity
	)
	{
		return new MetalInfuserContainer(windowId, level, worldPosition, playerInventory);
	}
}
