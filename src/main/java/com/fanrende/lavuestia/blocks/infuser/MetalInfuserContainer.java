package com.fanrende.lavuestia.blocks.infuser;

import com.fanrende.lavuestia.setup.Registration;
import com.fanrende.lavuestia.tools.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MetalInfuserContainer extends Container
{
	private final TileEntity tileEntity;
	private final IItemHandler playerInventory;

	private final int playerInvIndexStart;
	private final int playerInvIndexEnd;

	public MetalInfuserContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory)
	{
		super(Registration.METALINFUSER_BLOCK_CONTAINER.get(), windowId);

		this.tileEntity = world.getBlockEntity(pos);
		this.playerInventory = new InvWrapper(playerInventory);

		if(tileEntity instanceof MetalInfuserTile)
		{
			MetalInfuserTile tile = (MetalInfuserTile) tileEntity;

			addSlot(new SlotItemHandler(tile.getItemHandler(), 0, 64, 24));
			addSlot(new SlotItemHandler(tile.getItemHandler(), 1, 100, 24));
		}

		playerInvIndexStart = this.slots.size();
		playerInvIndexEnd = playerInvIndexStart + 36;

		layoutPlayerInventorySlots(10, 70);

		trackPower();
	}

	@Override
	public boolean stillValid(PlayerEntity playerEntity)
	{
		return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, Registration.METALINFUSER_BLOCK.get());
	}

	private void trackPower()
	{
		// because of 64 bits and 32 bits difference between server and client on dedicated server
		// we need two "int" to store and transfer

		addDataSlot(new IntReferenceHolder()
		{
			@Override
			public int get()
			{
				return getEnergy() & 0xffff;
			}

			@Override
			public void set(int value)
			{
				tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h ->
				{
					int energyStored = h.getEnergyStored() & 0xffff0000;
					( (CustomEnergyStorage) h ).setEnergy(energyStored + ( value & 0xffff ));
				});
			}
		});

		addDataSlot(new IntReferenceHolder()
		{
			@Override
			public int get()
			{
				return ( getEnergy() >> 16 ) & 0xffff;
			}

			@Override
			public void set(int value)
			{
				tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h ->
				{
					int energyStored = h.getEnergyStored() & 0x0000ffff;
					( (CustomEnergyStorage) h ).setEnergy(energyStored | ( value << 16 ));
				});
			}
		});
	}

	public int getEnergy()
	{
		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
	}

	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
	{
		for (int j = 0; j < verAmount; j++)
		{
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	private void layoutPlayerInventorySlots(int leftCol, int topRow)
	{
		// Player inventory
		addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem())
		{
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();
			if (index < playerInvIndexStart)
			{
				if (!this.moveItemStackTo(stack, playerInvIndexStart, playerInvIndexEnd, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, itemstack);
			}
			else
			{
				if (!this.moveItemStackTo(stack, 0, playerInvIndexStart - 1, false))
				{
					return ItemStack.EMPTY;
				}
				else if (index < playerInvIndexStart + 27)
				{
					if (!this.moveItemStackTo(stack, playerInvIndexStart + 27, playerInvIndexEnd, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index < playerInvIndexEnd && !this.moveItemStackTo(stack, playerInvIndexStart, playerInvIndexStart + 27, false))
				{
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();
			}

			if (stack.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}

		return itemstack;
	}
}
