package net.lpcamors.mohard.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MohardAdvancementProvider extends ForgeAdvancementProvider {

    private static final List<AdvancementGenerator> LIST = List.of(new EssentialStart());

    public MohardAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existingFileHelper) {
        super(output, lookup, existingFileHelper, LIST);
    }




}
