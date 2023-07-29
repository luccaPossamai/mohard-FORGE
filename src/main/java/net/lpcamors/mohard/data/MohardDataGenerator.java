package net.lpcamors.mohard.data;


import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.data.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MohardMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MohardDataGenerator {

    private MohardDataGenerator(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(true, new MohardAdvancementProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(true, new MohardBlockStateProvider(output, existingFileHelper));
        generator.addProvider(true, new MohardItemModelProvider(output, existingFileHelper));
        generator.addProvider(true, new MohardRecipeProvider(output));
        BlockTagsProvider blockTags = new MohardBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTags);
        generator.getVanillaPack(true).addProvider(p_253851_ -> new MohardBlockTagsProvider(p_253851_, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(true, new MohardItemTagsProvider(output,lookupProvider, blockTags, existingFileHelper));
    }

}
