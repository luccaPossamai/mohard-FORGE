package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.MohardBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MohardBlockTagsProvider extends BlockTagsProvider {
    public MohardBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MohardMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_){
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MohardBlocks.ESSENCE_ALTAR.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(MohardBlocks.ESSENCE_ALTAR.get());
    }

}
