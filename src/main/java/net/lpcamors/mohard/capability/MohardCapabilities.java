package net.lpcamors.mohard.capability;

import net.lpcamors.mohard.capability.essence_altar.EssenceAltarCapability;
import net.lpcamors.mohard.capability.level_mechanic.LevelMechanicCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MohardCapabilities {

    public static final Capability<EssenceAltarCapability> ALTAR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<LevelMechanicCapability> LEVEL_MECHANIC_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public void registerCap(RegisterCapabilitiesEvent event){
        event.register(EssenceAltarCapability.class);
        event.register(LevelMechanicCapability.class);
    }

}
