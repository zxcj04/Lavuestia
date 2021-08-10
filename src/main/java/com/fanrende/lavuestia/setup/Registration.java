package com.fanrende.lavuestia.setup;

import com.fanrende.lavuestia.Lavuestia;
import com.fanrende.lavuestia.blocks.infuser.MetalInfuserBlock;
import com.fanrende.lavuestia.blocks.infuser.MetalInfuserContainer;
import com.fanrende.lavuestia.blocks.infuser.MetalInfuserTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration
{
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lavuestia.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lavuestia.MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Lavuestia.MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Lavuestia.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Lavuestia.MODID);

	public static void init()
	{
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<MetalInfuserBlock> METALINFUSER_BLOCK = BLOCKS.register("metal_infuser_block", MetalInfuserBlock::new);
	public static final RegistryObject<BlockItem> METALINFUSER_BLOCK_ITEM = ITEMS.register("metal_infuser_block", () -> new BlockItem(METALINFUSER_BLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
	public static final RegistryObject<TileEntityType<MetalInfuserTile>> METALINFUSER_BLOCK_TILE = TILES.register("metal_infuser_block", () -> TileEntityType.Builder.of(MetalInfuserTile::new, METALINFUSER_BLOCK.get()).build(null));
	public static final RegistryObject<ContainerType<MetalInfuserContainer>> METALINFUSER_BLOCK_CONTAINER = CONTAINERS.register("metal_infuser_block", () -> IForgeContainerType.create((windowId, inv, data) ->
	{
		BlockPos pos = data.readBlockPos();
		return new MetalInfuserContainer(windowId, inv.player.getCommandSenderWorld(), pos, inv);
	}));
}
