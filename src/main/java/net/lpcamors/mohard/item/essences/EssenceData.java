package net.lpcamors.mohard.item.essences;

import net.lpcamors.mohard.events.BossDamage;
import net.lpcamors.mohard.item.MohardEssences;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public record EssenceData(Supplier<? extends EntityType<? extends Mob>> type, EssenceStats essenceStats) {


    public EssenceData(EntityType<? extends Mob> type, EssenceStats essenceStats) {
        this(() -> type, essenceStats);
    }

    public EssenceData {
        EssenceData.Helper.ESSENCE_DATA_LIST.add(this);
    }

    public EntityType<?> entityType() {
        return this.type().get();
    }

    public static class Helper {

        public static final List<EssenceData> ESSENCE_DATA_LIST = new ArrayList<>();
        public static final List<MutableComponent> ESSENCE_DESCRIPTION = Arrays.asList(
                Component.translatable("essence.description.health"),
                Component.translatable("essence.description.raw_armor"),
                Component.translatable("essence.description.damage"),
                Component.translatable("essence.description.armor_penetration"),
                Component.translatable("essence.description.agility"));

        private static Map<EssenceData, EssenceItem> ESSENCE_DATA_ESSENCE_ITEM_MAP;
        private static Map<EntityType<?>, List<EssenceItem>> ENTITY_ESSENCE_MAP;

        public static void setupEssenceMap(){
            Map<EssenceData, EssenceItem> map = new HashMap<>();
            MohardEssences.ESSENCE_ITEMS.getEntries().stream().map(RegistryObject::get).toList().stream().filter(item -> item instanceof EssenceItem).toList().forEach(item -> map.put(((EssenceItem)item).getEssenceData(), (EssenceItem)item));
            ESSENCE_DATA_ESSENCE_ITEM_MAP = map;
        }

        public static void setupEntityMap(){
            Map<EntityType<?>, List<EssenceItem>> map = new HashMap<>();
            MohardEssences.ESSENCE_ITEMS.getEntries().stream().map(RegistryObject::get).toList().stream().filter(item -> item instanceof EssenceItem).toList().forEach(item -> {
                List<EssenceItem> essenceItems = map.getOrDefault(((EssenceItem) item).getType(), new ArrayList<>());
                essenceItems.add((EssenceItem) item);
                map.put(((EssenceItem) item).getType(), essenceItems);
            });
            ENTITY_ESSENCE_MAP = map;
        }

        public static double calculateUpgrades(double value, int upgradeValue){
            if (upgradeValue > 0 && (value > 0 || value < 0)) {
                double newValue = value + (2 * upgradeValue) - 1;
                value = (value < 0 && newValue > 0) ? 0 : newValue;
            }
            return value;
        }

        public static EssenceItem getEssenceItemByEssenceData(EssenceData essenceData){
            return ESSENCE_DATA_ESSENCE_ITEM_MAP.getOrDefault(essenceData, MohardEssences.SQUID_ESSENCE.get());
        }

        @Nullable
        public static EssenceItem getEssenceItemByEntity(LivingEntity livingEntity){
            List<EssenceItem> essenceItems = ENTITY_ESSENCE_MAP.getOrDefault(livingEntity.getType(), new ArrayList<>()).stream().filter(essenceItem -> essenceItem.testCondition(livingEntity)).toList();
            if(!essenceItems.isEmpty()){
                if(essenceItems.size() > 1){
                    return essenceItems.stream().filter(EssenceItem::isAlternative).toList().get(0);
                } else {
                    return essenceItems.get(0);
                }
            } else {
                return null;
            }
        }

        public static int getEssenceLevel(ItemStack stack){
            return stack.hasTag() ? stack.getTag().getInt("Level") : 0;
        }


        public static ItemStack upgradeLevel(ItemStack stack){
            if(canUpgradeEssence(stack)){
                ItemStack itemStack = stack.copy();
                CompoundTag tag = itemStack.getOrCreateTag();
                String levelS = "Level";
                int level = tag.getInt(levelS);
                tag.remove(levelS);
                tag.putInt(levelS, level + 1);
                itemStack.setTag(tag);
                return itemStack;
            }
            return stack;
        }


        public static boolean canUpgradeEssence(ItemStack stack){
            return getEssenceLevel(stack) < 5;
        }

        public static boolean isUnactive(ItemStack stack){
            if(stack.hasTag() && stack.getTag().contains("Unactive")){
                return stack.getTag().getBoolean("Unactive");
            }
            return false;
        }

        public static void unactivate(ItemStack stack, boolean unactive){
            stack.getOrCreateTag().putBoolean("Unactive", unactive);
        }


        public static boolean isBoss(EssenceItem essenceItem){
            return isBoss(essenceItem.getType());
        }

        public static boolean isBoss(EntityType<?> entityType){
            return BossDamage.isValidBossFight(entityType);
        }

    }
}
