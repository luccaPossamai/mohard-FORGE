package net.lpcamors.mohard.capability.level_mechanic;

import net.lpcamors.mohard.MohardMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LevelMechanicCapabilitySetup {

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(new ResourceLocation(MohardMain.MODID, "level_mechanic"), new LevelMechanicCapabilityProvider());
    }
}
