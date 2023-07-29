package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.data.MohardTags;
import net.lpcamors.mohard.item.MohardEssences;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MohardItemTagsProvider extends ItemTagsProvider {

    public MohardItemTagsProvider(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, TagsProvider<Block> p_256467_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255871_, p_256035_, p_256467_.contentsGetter(), MohardMain.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider p_256380_){
        for(RegistryObject<Item> en: MohardEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem) {
                tag(MohardTags.Items.ESSENCE).add(en.get());
            }
        }
    }
}
