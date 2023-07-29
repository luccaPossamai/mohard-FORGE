package net.lpcamors.mohard.block.essence_altar.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lpcamors.mohard.item.MohardAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Inventory;

public class EssenceAltarScreen extends AbstractContainerScreen<EssenceAltarMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("mohard", "textures/gui/altar.png");
    private boolean showingStats = false;

    public EssenceAltarScreen(EssenceAltarMenu p_i51093_1_, Inventory p_i51093_2_, Component p_i51093_3_) {
        super(p_i51093_1_, p_i51093_2_, p_i51093_3_);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        initButton();

    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        bind();
        RenderSystem.enableBlend();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        p_283065_.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(this.showingStats) {
            for (int k = 0; k < 5; k++) {
                if(Minecraft.getInstance().player == null) break;
                bind();
                p_283065_.blit(CONTAINER_LOCATION, i - 40, j + 1 + (k * 20), 0, 166, 40, 21);
                p_283065_.blit(CONTAINER_LOCATION, i - 44, j + 4 + (k * 20), 41 + (k * 13), 166, 13, 13);
                Attribute att = MohardAttributes.Helper.getAttributes().get(k);
                double stat = Minecraft.getInstance().player.getAttributeBaseValue(att);
                Component message = Component.literal(""+(int)stat);
                p_283065_.drawString(this.font, message.getVisualOrderText(), (i - 18 - this.font.width(message.getVisualOrderText()) / 2),  j + 7 + (k * 20), 4210752, false);

            }
        }
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        this.renderBackground(p_283479_);
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(p_283479_, p_283661_, p_281248_);
    }



    public void bind(){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_LOCATION);
    }
    public void initButton(){
        EssenceAltarMenuButton button = new EssenceAltarMenuButton(this);
        button.setPosition(((this.width - this.imageWidth) / 2) + 5, ((this.height - this.imageHeight) / 2 ) + 5);
        this.addRenderableWidget(button);
    }
    public boolean isShowingStats() {
        return showingStats;
    }

    public void setShowingStats(boolean showingStats) {
        this.showingStats = showingStats;
    }

}