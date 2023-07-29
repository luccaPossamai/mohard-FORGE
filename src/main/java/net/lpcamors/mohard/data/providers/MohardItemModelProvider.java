package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.item.MohardEssences;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class MohardItemModelProvider extends ItemModelProvider {

    public MohardItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MohardMain.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile templateSpawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));
        getBuilder("essence").parent(templateSpawnEgg).texture("layer0", "item/" + "essence_1").texture("layer1", "item/" + "essence_2");
        withExistingParent("essence_altar", new ResourceLocation(MohardMain.MODID, "block/essence_altar"));

        for(RegistryObject<Item> en: MohardEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem essence) {
                String nome = en.getId().toString();
                if(essence.hasOwnTexture()) continue;
                withExistingParent(nome, modLoc("item/essence"));
            }
        }
    }

}
