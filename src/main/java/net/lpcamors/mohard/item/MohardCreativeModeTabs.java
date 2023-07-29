package net.lpcamors.mohard.item;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.MohardBlocks;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MohardCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MohardMain.MODID);

    public static void initiate(IEventBus bus){
        CREATIVE_MODE_TABS.register(bus);
    }

    public static final RegistryObject<CreativeModeTab> ESSENCES = CREATIVE_MODE_TABS.register("essence_tab",
            () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(MohardEssences.ZOMBIE_ESSENCE.get()))
                .title(Component.translatable("creativetab.essence_tab"))
                .displayItems((parameters, output) -> {
                    output.accept(MohardBlocks.ESSENCE_ALTAR.get());
                    MohardEssences.ESSENCE_ITEMS.getEntries().forEach(itemRegistryObject -> {
                        if(itemRegistryObject.get() instanceof EssenceItem essenceItem) {
                            if(!essenceItem.isSecret()) output.accept(essenceItem);
                        }
                    });
                })
                .build());
}
