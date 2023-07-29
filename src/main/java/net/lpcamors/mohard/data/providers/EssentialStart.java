package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.MohardBlocks;
import net.lpcamors.mohard.data.MohardTags;
import net.lpcamors.mohard.item.MohardEssences;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EssentialStart implements ForgeAdvancementProvider.AdvancementGenerator {


    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<Advancement> saver, @NotNull ExistingFileHelper existingFileHelper) {
        Advancement advancement = Advancement.Builder.advancement().display(MohardEssences.ZOMBIE_ESSENCE.get(), path("root", true), path("root", false), loc("textures/gui/advancements/backgrounds/deepslate.png"), FrameType.TASK, true, true, false).addCriterion("get_essence", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MohardTags.Items.ESSENCE).build())).save(saver, "essentials/root");
        Advancement advancement1 = Advancement.Builder.advancement().parent(advancement).display(MohardBlocks.ESSENCE_ALTAR.get(), path("craft_essence_altar", true), path("craft_essence_altar", false), null, FrameType.TASK, true, true, false).addCriterion("craft_essence_altar", InventoryChangeTrigger.TriggerInstance.hasItems(MohardBlocks.ESSENCE_ALTAR.get())).save(saver, "essentials/craft_essence_altar");
        secretEssence(MohardEssences.BLUE_AXOLOTL_ESSENCE.get(), "blue_axolotl").parent(advancement).save(saver, "essentials/blue_axolotl");
        secretEssence(MohardEssences.KILLER_BUNNY_ESSENCE.get(), "killer_bunny").parent(advancement).save(saver, "essentials/killer_bunny");
        secretEssence(MohardEssences.CHARGED_CREEPER_ESSENCE.get(), "charged_creeper").parent(advancement).save(saver, "essentials/charged_creeper");

    }


    private static Advancement.Builder secretEssence(EssenceItem essenceItem, String name){
        return Advancement.Builder.advancement().display(essenceItem, path(name, true), path(name, false), null, FrameType.CHALLENGE, true, true, true).addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(essenceItem));
    }
    private static ResourceLocation loc(String string){
        return new ResourceLocation(MohardMain.MODID, string);
    }

    private static Component path(String name, boolean title){
        return Component.translatable("advancements.essentials."+name+"."+(title ? "title" : "description"));
    }
}
