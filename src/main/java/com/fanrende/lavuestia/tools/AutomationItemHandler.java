package com.fanrende.lavuestia.tools;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class AutomationItemHandler extends ItemStackHandler
{
	private ItemStackHandler internal;
	private HashMap<Integer, IO_TYPE> available;

	public AutomationItemHandler(ItemStackHandler internal, HashMap<Integer, IO_TYPE> available)
	{
		this.internal = internal;
		this.available = available;
	}

	public enum IO_TYPE
	{
		NONE, INPUT, OUTPUT, ALL;
	}

	private boolean isSlotAvail(int slot, IO_TYPE type)
	{
		if(internal.getSlots() <= slot)
			return false;

		IO_TYPE targetType = available.get(slot);

		if(targetType == IO_TYPE.ALL)
			return true;
		else if(targetType == IO_TYPE.NONE)
			return false;

		return targetType == type;
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack)
	{
		internal.setStackInSlot(slot, stack);
	}

	@Override
	public int getSlots()
	{
		return internal.getSlots();
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return internal.getStackInSlot(slot);
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
	{
		if(!isSlotAvail(slot, IO_TYPE.INPUT))
			return stack;

		return internal.insertItem(slot, stack, simulate);
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(!isSlotAvail(slot, IO_TYPE.OUTPUT))
			return ItemStack.EMPTY;

		return internal.extractItem(slot, amount, simulate);
	}
}
