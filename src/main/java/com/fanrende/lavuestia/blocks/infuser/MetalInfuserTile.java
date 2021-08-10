package com.fanrende.lavuestia.blocks.infuser;

import com.fanrende.lavuestia.data.CapabilityContainerItemHandler;
import com.fanrende.lavuestia.setup.Registration;
import com.fanrende.lavuestia.tools.AutomationItemHandler;
import com.fanrende.lavuestia.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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
import java.util.HashMap;

public class MetalInfuserTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
	private final ItemStackHandler item = this.createItemHandler();
	private final CustomEnergyStorage energy = this.createEnergyStorage();

	private final LazyOptional<IItemHandler> itemLazy = LazyOptional.of(() -> item);
	private final LazyOptional<IItemHandler> itemAutomationLazy = LazyOptional.of(this::createAutomationItemHandler);
	private final LazyOptional<IEnergyStorage> energyLazy = LazyOptional.of(() -> energy);

	public MetalInfuserTile()
	{
		super(Registration.METALINFUSER_BLOCK_TILE.get());
	}

	@Override
	public void tick()
	{

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

	private ItemStackHandler createItemHandler()
	{
		return new ItemStackHandler(2)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				setChanged();
			}
		};
	}

	private AutomationItemHandler createAutomationItemHandler()
	{
		HashMap<Integer, AutomationItemHandler.IO_TYPE> available = new HashMap<>();

		available.put(0, AutomationItemHandler.IO_TYPE.INPUT);
		available.put(1, AutomationItemHandler.IO_TYPE.OUTPUT);

		return new AutomationItemHandler(item, available);
	}

	private CustomEnergyStorage createEnergyStorage()
	{
		return new CustomEnergyStorage(100000, 1000, 0);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return itemAutomationLazy.cast();
		if(cap == CapabilityContainerItemHandler.CONTAINER_ITEM_HANDLER_CAPABILITY)
			return itemLazy.cast();
		if(cap == CapabilityEnergy.ENERGY)
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
