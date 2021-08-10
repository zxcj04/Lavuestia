package com.fanrende.lavuestia.setup;


import com.fanrende.lavuestia.Lavuestia;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Lavuestia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup
{
	public static final ItemGroup ITEM_GROUP = new ItemGroup("lavuestia")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(Items.ACACIA_SAPLING);
		}
	};

	public static void init(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{

		});
	}

	@SubscribeEvent
	public static void serverLoad(RegisterCommandsEvent event)
	{

	}
}
