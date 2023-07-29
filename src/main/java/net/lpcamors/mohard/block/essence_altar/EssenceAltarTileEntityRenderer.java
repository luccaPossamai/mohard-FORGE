package net.lpcamors.mohard.block.essence_altar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.lpcamors.mohard.capability.MohardCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

public class EssenceAltarTileEntityRenderer implements BlockEntityRenderer<EssenceAltarTileEntity> {
    private final ItemRenderer itemRenderer;

    public EssenceAltarTileEntityRenderer(BlockEntityRendererProvider.Context p_173602_) {
        this.itemRenderer = p_173602_.getItemRenderer();
    }

    @Override
    public void render(EssenceAltarTileEntity altar, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderItems(altar, matrix, combinedLightIn, combinedOverlayIn, bufferIn, altar.getBlockState().getValue(EssenceAltarBlock.FACING), altar.getLevel().getGameTime());
        matrix.pushPose();
        matrix.translate(0D, 0D, 0D);
        matrix.scale(1F, 1F, 1F);
        matrix.popPose();
    }
    private void renderItems(EssenceAltarTileEntity altar, PoseStack matrix, int j, int u, MultiBufferSource renderType, Direction direction, float partialTicks){
        if(Minecraft.getInstance().player != null) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getCapability(MohardCapabilities.ALTAR_CAPABILITY).ifPresent(enderAltarCapability -> {
                List<ItemStack> itemStacks = IntStream.range(0, 9).mapToObj(enderAltarCapability::getStackInSlot).filter(itemStack -> itemStack != ItemStack.EMPTY).toList();
                float roll = partialTicks * 5;
                for(int i = 0; i < itemStacks.size(); ++i) {
                    ItemStack itemstack = itemStacks.get(i);
                    float angle = roll + (i * (360F / itemStacks.size()));
                    double radius = itemStacks.size() == 1 ? 0 : 0.3D;
                    matrix.pushPose();
                    matrix.translate(0.5D, 0.9D, 0.5D);
                    matrix.mulPose(Axis.YP.rotationDegrees(angle));
                    matrix.translate(radius, 0D , 0D);
                    matrix.scale(0.375F, 0.375F, 0.375F);

                    this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, j, u, matrix, renderType, altar.getLevel(), 1);
                    matrix.popPose();
                }
            });
        }


    }


}
