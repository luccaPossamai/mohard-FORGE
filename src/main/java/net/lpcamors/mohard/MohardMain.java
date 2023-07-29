package net.lpcamors.mohard;

import com.mojang.logging.LogUtils;
import net.lpcamors.mohard.block.MohardBlocks;
import net.lpcamors.mohard.block.MohardMenuTypes;
import net.lpcamors.mohard.block.MohardTileEntityTypes;
import net.lpcamors.mohard.block.essence_altar.EssenceAltarTileEntityRenderer;
import net.lpcamors.mohard.block.essence_altar.gui.EssenceAltarScreen;
import net.lpcamors.mohard.item.MohardAttributes;
import net.lpcamors.mohard.item.MohardCreativeModeTabs;
import net.lpcamors.mohard.item.MohardEssences;
import net.lpcamors.mohard.item.essences.EssenceData;
import net.lpcamors.mohard.network.MohardBaseNetwork;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MohardMain.MODID)
public class MohardMain {
    public static final String MODID = "mohard";
    public static final Logger LOGGER = LogUtils.getLogger();


    public MohardMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        MohardEssences.initiate(modEventBus);
        MohardAttributes.initiate(modEventBus);
        MohardBlocks.initiate(modEventBus);
        MohardMenuTypes.initiate(modEventBus);
        MohardTileEntityTypes.initiate(modEventBus);
        MohardCreativeModeTabs.initiate(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(MohardBaseNetwork::register);
        EssenceData.Helper.setupEssenceMap();
        EssenceData.Helper.setupEntityMap();

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MohardMenuTypes.ENDER_ALTAR.get(), EssenceAltarScreen::new);
            BlockEntityRenderers.register(MohardTileEntityTypes.ENDER_ALTAR.get(), EssenceAltarTileEntityRenderer::new);
        }
    }
}
