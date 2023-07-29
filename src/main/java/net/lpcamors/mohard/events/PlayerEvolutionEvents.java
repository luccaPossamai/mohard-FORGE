package net.lpcamors.mohard.events;

import net.lpcamors.mohard.capability.MohardCapabilities;
import net.lpcamors.mohard.capability.level_mechanic.LevelMechanicCapability;
import net.lpcamors.mohard.item.MohardAttributes;
import net.lpcamors.mohard.item.essences.EssenceData;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.lpcamors.mohard.network.MohardBaseNetwork;
import net.lpcamors.mohard.network.packet.ClientboundUpdateEnderAltarItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class PlayerEvolutionEvents {

    public static void setStats(NonNullList<ItemStack> lista, Player player){
        List<Attribute> attributes = MohardAttributes.Helper.getAttributes();
        Map<Attribute, Double> statsForPlayer = attributes.stream().collect(Collectors.toMap(attribute -> attribute, Attribute::getDefaultValue));
        double velocidade = 0.1D;
        statsForPlayer.put(Attributes.MOVEMENT_SPEED, velocidade);
        statsForPlayer.put(ForgeMod.ENTITY_GRAVITY.get(), 0.08);

        for(ItemStack item : lista){
            if(item.getItem() instanceof EssenceItem essence){
                if(EssenceData.Helper.isUnactive(item)){
                    continue;
                }
                int upgradeLevel = EssenceData.Helper.getEssenceLevel(item);
                Map<Attribute, Double> essenceStats = essence.getEssenceData(item).essenceStats().getStats(upgradeLevel);
                for (Attribute attribute : attributes) {
                    RangedAttribute att = (RangedAttribute) attribute;
                    double value = essenceStats.get(att) + statsForPlayer.get(attribute);
                    statsForPlayer.put(attribute, Math.min(Math.max(att.getMinValue(), value), att.getMaxValue()));

                }
            }
        }
        double agility = statsForPlayer.get(MohardAttributes.AGILITY);
        statsForPlayer.put(Attributes.MOVEMENT_SPEED, velocidade + (velocidade * agility / 125));
        statsForPlayer.put(ForgeMod.ENTITY_GRAVITY.get(), Mth.clamp(0.08 - (agility * 1E-4), 0.07, 0.09));
        statsForPlayer.put(Attributes.ATTACK_SPEED, Mth.clamp(4 + agility * 1.5E2, 3, 5));
        statsForPlayer.forEach((attribute, aDouble) -> player.getAttribute(attribute).setBaseValue(aDouble));
        if(!player.level().isClientSide) {
            MohardBaseNetwork.sendToPlayer(new ClientboundUpdateEnderAltarItems(lista), (ServerPlayer) player);
            player.level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).ifPresent(levelMechanicCapability -> {
                levelMechanicCapability.updateMeasures((ServerLevel) player.level());
            });
        }

    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getEntity();
        PlayerEvolutionEvents.setStats(PlayerEvolutionEvents.getItems(player), player);
        player.setHealth(player.getMaxHealth());
    }

    @SubscribeEvent
    public static void playerLogIn(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        PlayerEvolutionEvents.setStats(PlayerEvolutionEvents.getItems(player), player);
        player.level().players().forEach(PlayerEvolutionEvents::sendPlayersLoginLevelMessage);
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerChangedDimensionEvent event){
        Player player = event.getEntity();
        PlayerEvolutionEvents.setStats(PlayerEvolutionEvents.getItems(player), player);
    }


    public static NonNullList<ItemStack> getItems(Player player){
        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        player.getCapability(MohardCapabilities.ALTAR_CAPABILITY).ifPresent(inv->{
            for(int i = 0; i < 9 ; i++){
                items.set(i, inv.getStackInSlot(i));
            }
        });
        return items;
    }



    public static void sendPlayerLevelMessage(Player player){
        player.level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).ifPresent(levelMechanicCapability -> player.sendSystemMessage(Component.translatable("mohard.server.player_level").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(LevelMechanicCapability.Helper.getPlayerLevel(player))).withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.UNDERLINE))));
    }
    public static void sendPlayersLoginLevelMessage(Player player){
        player.level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).ifPresent(levelMechanicCapability -> {
            player.sendSystemMessage(Component.translatable("mohard.server.player_login_level").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(levelMechanicCapability.getAbsoluteLevel())).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE)));
            player.sendSystemMessage(Component.translatable("mohard.server.player_login_deviation").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(levelMechanicCapability.getStandardDeviation())).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE)));
        });
    }

}
