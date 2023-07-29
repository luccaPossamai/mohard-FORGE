package net.lpcamors.mohard.events;

import net.lpcamors.mohard.item.MohardEssences;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MohardClientEvents {

    @SubscribeEvent
    public static void initiateEssencesColors(RegisterColorHandlersEvent.Item event){
        for(RegistryObject<Item> en: MohardEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem essence) {
                if(essence.hasOwnTexture()) continue;
                event.register((p_198141_1_, p_198141_2_) -> essence.getColor(p_198141_2_), essence);
            }
        }
    }

}
