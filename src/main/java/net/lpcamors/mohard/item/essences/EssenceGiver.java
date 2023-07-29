package net.lpcamors.mohard.item.essences;

import net.lpcamors.mohard.capability.level_mechanic.LevelMechanicCapability;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EssenceGiver {

    private final List<EssenceItem> essenceItems;
    private int upgradeLevel = 0;
    private boolean unactive;

    public EssenceGiver(EssenceItem essenceItem){
        this(List.of(essenceItem));
    }
    public EssenceGiver(){
        this(validEssences());
    }

    public EssenceGiver(int level){
        this(validEssencesAboveLevel(level));
    }

    private EssenceGiver(List<EssenceItem> essenceItems){
        this.essenceItems = essenceItems;

    }

    public ItemStack castToItemStack(){
        ItemStack itemStack = new ItemStack(this.essenceItems.get(this.essenceItems.size() > 1 ? new Random().nextInt(this.essenceItems.size()) : 0));
        if(EssenceData.Helper.canUpgradeEssence(itemStack)){
            while(this.upgradeLevel > 0){
                this.upgradeLevel--;
                itemStack = EssenceData.Helper.upgradeLevel(itemStack);
            }
        }
        EssenceData.Helper.unactivate(itemStack, this.unactive);
        return itemStack;
    }


    public EssenceGiver unactive(boolean unactive){
        this.unactive = unactive;
        return this;
    }

    public EssenceGiver withPositiveUpgradesLevel(int i){
        this.upgradeLevel = Mth.clamp(i, 0, 5);
        return this;
    }


    private static List<EssenceItem> validEssencesAboveLevel(int level){
        return validEssences().stream().filter(essenceItem -> LevelMechanicCapability.Helper.getEssenceBaseLevel(essenceItem.getEssenceData()) < level).collect(Collectors.toList());
    }
    private static List<EssenceItem> validEssences(){
        return allEssences().stream().filter(EssenceItem::isValid).collect(Collectors.toList());
    }

    private static List<EssenceItem> allEssences(){
        return EssenceData.Helper.ESSENCE_DATA_LIST.stream().map(EssenceData.Helper::getEssenceItemByEssenceData).collect(Collectors.toList());
    }

}
