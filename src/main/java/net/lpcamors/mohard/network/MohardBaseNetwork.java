package net.lpcamors.mohard.network;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.network.packet.ClientboundUpdateEnderAltarItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MohardBaseNetwork {

    public static final String VERSION = "1.0";
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    private static SimpleChannel CHANNEL;

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MohardMain.MODID, "network")).
                networkProtocolVersion(() -> VERSION).
                clientAcceptedVersions(s -> true).
                serverAcceptedVersions(s -> true).simpleChannel();
        CHANNEL = net;

        net.messageBuilder(ClientboundUpdateEnderAltarItems.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .consumerMainThread(ClientboundUpdateEnderAltarItems::handle)
                .encoder(ClientboundUpdateEnderAltarItems::encode)
                .decoder(ClientboundUpdateEnderAltarItems::new).add();

    }

    public static <MSG> void sendToServer(MSG message){
        CHANNEL.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer){
        CHANNEL.sendTo(message, serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
