package net.lpcamors.mohard.block.essence_altar.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lpcamors.mohard.MohardMain;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EssenceAltarMenuButton extends AbstractWidget {

    private final ResourceLocation ALTAR_GUI = new ResourceLocation(MohardMain.MODID, "textures/gui/altar.png");
    private final EssenceAltarScreen screen;

    public EssenceAltarMenuButton(EssenceAltarScreen altarScreen) {
        super( 0, 0, 11, 11, Component.empty());
        this.screen = altarScreen;
    }


    protected int getYImage(boolean p_230989_1_) {
        if (!this.active) {
            return 2;
        } else if (p_230989_1_) {
            return 1;
        }
        return 0;
    }


    @Override
    protected boolean isValidClickButton(int p_230987_1_) {
        return true;
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int p_268034_, int p_268009_, float p_268085_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ALTAR_GUI);
        RenderSystem.enableBlend();
        int i = this.getYImage(this.isHovered);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        guiGraphics.blit(ALTAR_GUI, this.getX(), this.getY(), 176, (i * 11), 11, 11);
    }

    @Override
    public void onClick(double p_230982_1_, double p_230982_3_) {
        NonNullList<ItemStack> lista = NonNullList.withSize(9, ItemStack.EMPTY);
        for(int i = 0; i < 9; i++){
            lista.set(i, this.screen.getMenu().slots.get(i).getItem());
        }
        this.screen.setShowingStats(!this.screen.isShowingStats());
    }


    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput p_259858_) {

    }
}
