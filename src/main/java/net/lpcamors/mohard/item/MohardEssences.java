package net.lpcamors.mohard.item;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.item.essences.EssenceData;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.lpcamors.mohard.item.essences.EssencePredicate;
import net.lpcamors.mohard.item.essences.MohardEssenceData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class MohardEssences {
    public static final DeferredRegister<Item> ESSENCE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MohardMain.MODID);

    public static void initiate(IEventBus bus){
        ESSENCE_ITEMS.register(bus);
    }

    public static final RegistryObject<EssenceItem> AXOLOTL_ESSENCE = register("axolotl", MohardEssenceData.AXOLOTL, 16499171, 10890612);
    public static final RegistryObject<EssenceItem> BAT_ESSENCE = register("bat", MohardEssenceData.BAT, 4996656, 986895);
    public static final RegistryObject<EssenceItem> BEE_ESSENCE = register("bee", MohardEssenceData.BEE, 15582019, 4400155);
    public static final RegistryObject<EssenceItem> BLAZE_ESSENCE = register("blaze", MohardEssenceData.BLAZE, 16167425, 16775294);
    public static final RegistryObject<EssenceItem> CAT_ESSENCE = register("cat", MohardEssenceData.CAT, 15714446, 9794134);
    public static final RegistryObject<EssenceItem> CAVE_SPIDER_ESSENCE = register("cave_spider", MohardEssenceData.CAVE_SPIDER, 803406, 11013646);
    public static final RegistryObject<EssenceItem> CHICKEN_ESSENCE = register("chicken", MohardEssenceData.CHICKEN, 10592673, 16711680);
    public static final RegistryObject<EssenceItem> COD_ESSENCE = register("cod", MohardEssenceData.COD, 12691306, 15058059);
    public static final RegistryObject<EssenceItem> COW_ESSENCE = register("cow", MohardEssenceData.COW, 4470310, 10592673);
    public static final RegistryObject<EssenceItem> CREEPER_ESSENCE = register("creeper", MohardEssenceData.CREEPER, 894731, 0);
    public static final RegistryObject<EssenceItem> DOLPHIN_ESSENCE = register("dolphin", MohardEssenceData.DOLPHIN, 2243405, 16382457);
    public static final RegistryObject<EssenceItem> DONKEY_ESSENCE = register("donkey", MohardEssenceData.DONKEY, 5457209, 8811878);
    public static final RegistryObject<EssenceItem> DROWNED_ESSENCE = register("drowned", MohardEssenceData.DROWNED, 9433559, 7969893);
    public static final RegistryObject<EssenceItem> ENDERMAN_ESSENCE = register("enderman", MohardEssenceData.ENDERMAN, 1447446, 0);
    public static final RegistryObject<EssenceItem> ENDERMITE_ESSENCE = register("endermite", MohardEssenceData.ENDERMITE, 1447446, 7237230);
    public static final RegistryObject<EssenceItem> EVOKER_ESSENCE = register("evoker", MohardEssenceData.EVOKER, 9804699, 1973274);
    public static final RegistryObject<EssenceItem> FOX_ESSENCE = register("fox", MohardEssenceData.FOX, 14005919, 13396256);
    public static final RegistryObject<EssenceItem> FROG_ESSENCE = register("frog", MohardEssenceData.FROG, 13661252, 16762748);
    public static final RegistryObject<EssenceItem> GHAST_ESSENCE = register("ghast", MohardEssenceData.GHAST, 16382457, 12369084);
    public static final RegistryObject<EssenceItem> GOAT_ESSENCE = register("goat", MohardEssenceData.GOAT, 10851452, 5589310);
    public static final RegistryObject<EssenceItem> GUARDIAN_ESSENCE = register("guardian", MohardEssenceData.GUARDIAN, 5931634, 15826224);
    public static final RegistryObject<EssenceItem> HOGLIN_ESSENCE = register("hoglin", MohardEssenceData.HOGLIN, 13004373, 6251620);
    public static final RegistryObject<EssenceItem> HORSE_ESSENCE = register("horse", MohardEssenceData.HORSE, 12623485, 15656192);
    public static final RegistryObject<EssenceItem> HUSK_ESSENCE = register("husk", MohardEssenceData.HUSK, 7958625, 15125652);
    public static final RegistryObject<EssenceItem> LLAMA_ESSENCE = register("llama", MohardEssenceData.LLAMA, 12623485, 10051392);
    public static final RegistryObject<EssenceItem> MAGMA_CUBE_ESSENCE = register("magma_cube", MohardEssenceData.MAGMA_CUBE, 3407872, 16579584);
    public static final RegistryObject<EssenceItem> MOOSHROOM_ESSENCE = register("mooshroom", MohardEssenceData.MOOSHROOM, 10489616, 12040119);
    public static final RegistryObject<EssenceItem> MULE_ESSENCE = register("mule", MohardEssenceData.MULE, 1769984, 5321501);
    public static final RegistryObject<EssenceItem> OCELOT_ESSENCE = register("ocelot", MohardEssenceData.OCELOT, 15720061, 5653556);
    public static final RegistryObject<EssenceItem> PANDA_ESSENCE = register("panda", MohardEssenceData.PANDA, 15198183, 1776418);
    public static final RegistryObject<EssenceItem> PARROT_ESSENCE = register("parrot", MohardEssenceData.PARROT, 1622015, 16765952);
    public static final RegistryObject<EssenceItem> PHANTOM_ESSENCE = register("phantom", MohardEssenceData.PHANTOM, 5267876, 701445);
    public static final RegistryObject<EssenceItem> PIG_ESSENCE = register("pig", MohardEssenceData.PIG, 15771042, 14377823);
    public static final RegistryObject<EssenceItem> PIGLIN_ESSENCE = register("piglin", MohardEssenceData.PIGLIN, 10051392, 16380836);
    public static final RegistryObject<EssenceItem> PIGLIN_BRUTE_ESSENCE = register("piglin_brute", MohardEssenceData.PIGLIN_BRUTE, 5843472, 16380836);
    public static final RegistryObject<EssenceItem> PILLAGER_ESSENCE = register("pillager", MohardEssenceData.PILLAGER, 5451574, 9804699);
    public static final RegistryObject<EssenceItem> POLAR_BEAR_ESSENCE = register("polar_bear", MohardEssenceData.POLAR_BEAR, 15921906, 9803152);
    public static final RegistryObject<EssenceItem> PUFFERFISH_ESSENCE = register("pufferfish", MohardEssenceData.PUFFERFISH, 16167425, 3654642);
    public static final RegistryObject<EssenceItem> RABBIT_ESSENCE = register("rabbit", MohardEssenceData.RABBIT, 10051392, 7555121);
    public static final RegistryObject<EssenceItem> RAVAGER_ESSENCE = register("ravager", MohardEssenceData.RAVAGER, 7697520, 5984329);
    public static final RegistryObject<EssenceItem> SALMON_ESSENCE = register("salmon", MohardEssenceData.SALMON, 14174535, 12562610);
    public static final RegistryObject<EssenceItem> SHEEP_ESSENCE = register("sheep", MohardEssenceData.SHEEP, 15198183, 16758197);
    public static final RegistryObject<EssenceItem> SHULKER_ESSENCE = register("shulker", MohardEssenceData.SHULKER, 9725844, 5060690);
    public static final RegistryObject<EssenceItem> SILVERFISH_ESSENCE = register("silverfish", MohardEssenceData.SILVERFISH, 7237230, 3158064);
    public static final RegistryObject<EssenceItem> SKELETON_ESSENCE = register("skeleton", MohardEssenceData.SKELETON, 12698049, 4802889);
    public static final RegistryObject<EssenceItem> SKELETON_HORSE_ESSENCE = register("skeleton_horse", MohardEssenceData.SKELETON_HORSE, 6842447, 15066584);
    public static final RegistryObject<EssenceItem> SLIME_ESSENCE = register("slime", MohardEssenceData.SLIME, 5349438, 8306542);
    public static final RegistryObject<EssenceItem> SPIDER_ESSENCE = register("spider", MohardEssenceData.SPIDER, 3419431, 11013646);
    public static final RegistryObject<EssenceItem> SQUID_ESSENCE = register("squid", MohardEssenceData.SQUID, 2243405, 7375001);
    public static final RegistryObject<EssenceItem> GLOW_SQUID_ESSENCE = register("glow_squid", MohardEssenceData.GLOW_SQUID, 611926, 8778172);
    public static final RegistryObject<EssenceItem> STRAY_ESSENCE = register("stray", MohardEssenceData.STRAY, 6387319, 14543594);
    public static final RegistryObject<EssenceItem> STRIDER_ESSENCE = register("strider", MohardEssenceData.STRIDER, 10236982, 5065037);
    public static final RegistryObject<EssenceItem> TRADER_LLAMA_ESSENCE = register("trader_llama", MohardEssenceData.TRADER_LLAMA, 15377456, 4547222);
    public static final RegistryObject<EssenceItem> TROPICAL_FISH_ESSENCE = register("tropical_fish", MohardEssenceData.TROPICAL_FISH, 15690005, 16775663);
    public static final RegistryObject<EssenceItem> TURTLE_ESSENCE = register("turtle", MohardEssenceData.TURTLE, 15198183, 44975);
    public static final RegistryObject<EssenceItem> VEX_ESSENCE = register("vex", MohardEssenceData.VEX, 8032420, 15265265);
    public static final RegistryObject<EssenceItem> VILLAGER_ESSENCE = register("villager", MohardEssenceData.VILLAGER, 5651507, 12422002);
    public static final RegistryObject<EssenceItem> VINDICATOR_ESSENCE = register("vindicator", MohardEssenceData.VINDICATOR, 9804699, 2580065);
    public static final RegistryObject<EssenceItem> WANDERING_TRADER_ESSENCE = register("wandering_trader", MohardEssenceData.WANDERING_TRADER, 4547222, 15377456);
    public static final RegistryObject<EssenceItem> WITCH_ESSENCE = register("witch", MohardEssenceData.WITCH, 3407872, 5349438);
    public static final RegistryObject<EssenceItem> WITHER_SKELETON_ESSENCE = register("wither_skeleton", MohardEssenceData.WITHER_SKELETON, 1315860, 4672845);
    public static final RegistryObject<EssenceItem> WOLF_ESSENCE = register("wolf", MohardEssenceData.WOLF, 14144467, 13545366);
    public static final RegistryObject<EssenceItem> ZOGLIN_ESSENCE = register("zoglin", MohardEssenceData.ZOGLIN, 13004373, 15132390);
    public static final RegistryObject<EssenceItem> ZOMBIE_ESSENCE = register("zombie", MohardEssenceData.ZOMBIE, 44975, 7969893);
    public static final RegistryObject<EssenceItem> ZOMBIE_HORSE_ESSENCE = register("zombie_horse", MohardEssenceData.ZOMBIE_HORSE, 3232308, 9945732);
    public static final RegistryObject<EssenceItem> ZOMBIE_VILLAGER_ESSENCE = register("zombie_villager", MohardEssenceData.ZOMBIE_VILLAGER, 5651507, 7969893);
    public static final RegistryObject<EssenceItem> ZOMBIFIED_PIGLIN_ESSENCE = register("zombified_piglin", MohardEssenceData.ZOMBIFIED_PIGLIN, 15373203, 5009705);
    public static final RegistryObject<EssenceItem> IRON_GOLEM_ESSENCE = register("iron_golem", MohardEssenceData.IRON_GOLEM, 14405058, 7643954 );
    public static final RegistryObject<EssenceItem> ILLUSIONER_ESSENCE = registerSecret("illusioner", MohardEssenceData.ILLUSIONER, 1267859, 2653297, entity -> true);
    public static final RegistryObject<EssenceItem> SNOW_GOLEM_ESSENCE = register("snow_golem", MohardEssenceData.SNOW_GOLEM, 14283506, 8496292);
    public static final RegistryObject<EssenceItem> KILLER_BUNNY_ESSENCE = registerSecret("easter", MohardEssenceData.KILLER_BUNNY, 16777215, 7735831, entity -> (entity instanceof Rabbit rabbit && rabbit.getVariant().equals(Rabbit.Variant.EVIL)), true);
    public static final RegistryObject<EssenceItem> BLUE_AXOLOTL_ESSENCE = registerSecret("blue_axolotl", MohardEssenceData.BLUE_AXOLOTL, 6903523, 14058539, entity -> (entity instanceof Axolotl axolotl && axolotl.getVariant().equals(Axolotl.Variant.BLUE)));
    public static final RegistryObject<EssenceItem> CHARGED_CREEPER_ESSENCE = registerSecret("charged_creeper", MohardEssenceData.CHARGED_CREEPER, 3721346, 2987007, entity -> (entity instanceof Creeper creeper && creeper.isPowered()));
    public static final RegistryObject<EssenceItem> ELDER_GUARDIAN_ESSENCE = registerBoss("elder_guardian", MohardEssenceData.ELDER_GUARDIAN, 13552826, 7632531);
    public static final RegistryObject<EssenceItem> ENDER_DRAGON_ESSENCE = registerBoss("ender_dragon", MohardEssenceData.ENDER_DRAGON, 1842204, 14711290);
    public static final RegistryObject<EssenceItem> WITHER_ESSENCE = registerBoss("wither", MohardEssenceData.WITHER, 1315860, 5075616);
    public static final RegistryObject<EssenceItem> WARDEN_ESSENCE = registerBoss("warden", MohardEssenceData.WARDEN, 1001033, 3790560);



    public static RegistryObject<EssenceItem> registerBoss(String string, @Nonnull EssenceData essenceData, int color1, int color2){
        return ESSENCE_ITEMS.register(string+"_essence", () -> new EssenceItem(getEssenceProperties().rarity(Rarity.EPIC), essenceData, color1, color2, entity -> true).withOwnTexture(false));
    }

    public static RegistryObject<EssenceItem> register(String string, @Nonnull EssenceData essenceData, int color1, int color2){
        return register(string, essenceData, color1, color2,(livingEntity) -> true);
    }

    public static RegistryObject<EssenceItem> register(String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup) {
        return register(string, essenceData, color1, color2, sup, false);
    }

    public static RegistryObject<EssenceItem> register(String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return register(getEssenceProperties(), string, essenceData, color1, color2, sup, withOwnTexture);
    }

    public static RegistryObject<EssenceItem> register(Item.Properties properties, String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return ESSENCE_ITEMS.register(string+"_essence", () -> new EssenceItem(properties, essenceData, color1, color2, sup).withOwnTexture(withOwnTexture));
    }
    public static RegistryObject<EssenceItem> registerSecret(String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup) {
        return registerSecret(string, essenceData, color1, color2, sup, false);
    }
    public static RegistryObject<EssenceItem> registerSecret(String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return registerSecret(getEssenceProperties(), string, essenceData, color1, color2, sup, withOwnTexture);
    }
    public static RegistryObject<EssenceItem> registerSecret(Item.Properties properties, String string, @Nonnull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return ESSENCE_ITEMS.register(string+"_essence", () -> new EssenceItem(properties.rarity(Rarity.EPIC), essenceData, color1, color2, sup).withOwnTexture(withOwnTexture).secret());
    }


    public static EssenceItem.Properties getEssenceProperties(){
        return new EssenceItem.Properties().stacksTo(64);
    }

}
