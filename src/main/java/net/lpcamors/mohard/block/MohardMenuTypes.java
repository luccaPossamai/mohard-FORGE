package net.lpcamors.mohard.block;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.essence_altar.gui.EssenceAltarMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MohardMenuTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MohardMain.MODID);

    public static void initiate(IEventBus bus){
        CONTAINERS.register(bus);
    }
    public static final RegistryObject<MenuType<EssenceAltarMenu>> ENDER_ALTAR =
            CONTAINERS.register("essence_altar", () ->
                    new MenuType<>(EssenceAltarMenu::new, FeatureFlags.VANILLA_SET));

}
