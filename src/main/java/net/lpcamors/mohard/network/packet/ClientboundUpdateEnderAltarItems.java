package net.lpcamors.mohard.network.packet;

import net.lpcamors.mohard.capability.MohardCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ClientboundUpdateEnderAltarItems {

    public final List<ItemStack> itemStacks;

    public ClientboundUpdateEnderAltarItems(List<ItemStack> itemStacks){
        this.itemStacks = itemStacks;
    }

    public ClientboundUpdateEnderAltarItems(FriendlyByteBuf byteBuf){
        List<ItemStack> itemStacks = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            itemStacks.add(byteBuf.readItem());
        }
        this.itemStacks = itemStacks;
    }

    public void encode(FriendlyByteBuf byteBuf) {
        for (ItemStack itemStack : this.itemStacks){
            byteBuf.writeItem(itemStack);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if(Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.getCapability(MohardCapabilities.ALTAR_CAPABILITY).ifPresent(enderAltarCapability -> {
                        for(int i = 0; i < 9; i++){
                            enderAltarCapability.setStackInSlot(i, this.itemStacks.get(i));
                        }
                    });
                }
            });

        });

        ctx.setPacketHandled(true);
    }
}
