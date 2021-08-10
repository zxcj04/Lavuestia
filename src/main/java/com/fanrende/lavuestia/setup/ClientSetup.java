package com.fanrende.lavuestia.setup;

import com.fanrende.lavuestia.Lavuestia;
import com.fanrende.lavuestia.blocks.infuser.MetalInfuserScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Lavuestia.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup
{
	public static void init(final FMLClientSetupEvent event)
	{
		ScreenManager.register(Registration.METALINFUSER_BLOCK_CONTAINER.get(), MetalInfuserScreen::new);
	}
}
