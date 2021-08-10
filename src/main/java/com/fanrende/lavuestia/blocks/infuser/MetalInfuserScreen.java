package com.fanrende.lavuestia.blocks.infuser;

import com.fanrende.lavuestia.Lavuestia;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MetalInfuserScreen extends ContainerScreen<MetalInfuserContainer>
{
	private final ResourceLocation GUI = new ResourceLocation(Lavuestia.MODID, "textures/gui/metalinfuser_gui.png");

	public MetalInfuserScreen(MetalInfuserContainer container, PlayerInventory inventory, ITextComponent name)
	{
		super(container, inventory, name);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		drawString(matrixStack, Minecraft.getInstance().font, "Metal Infuser", 10, 10, 0xffffff);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(GUI);
		int relX = ( this.width - this.imageWidth ) / 2;
		int relY = ( this.height - this.imageHeight ) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
	}
}
