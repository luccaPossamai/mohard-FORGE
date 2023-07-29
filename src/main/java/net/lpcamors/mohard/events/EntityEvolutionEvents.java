package net.lpcamors.mohard.events;

import net.lpcamors.mohard.capability.MohardCapabilities;
import net.lpcamors.mohard.capability.level_mechanic.LevelMechanicCapability;
import net.lpcamors.mohard.item.MohardAttributes;
import net.lpcamors.mohard.item.essences.EssenceData;
import net.lpcamors.mohard.item.essences.EssenceGiver;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class EntityEvolutionEvents {

    public static final String STRONG_CREATURE = "StrongCreature";

    @SubscribeEvent
    public static void entitySpawn(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity livingEntity) {
            if(!(livingEntity instanceof Player)) {
                if (!hasTag(livingEntity) && !livingEntity.level().isClientSide()) {
                    int plusDifficulty = 0;
                    double worldLevel = livingEntity.level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).orElse(null).getRangeLevel();
                    if(livingEntity.getRandom().nextInt(100) < Math.floor(worldLevel / 50D)){
                        plusDifficulty = 1;
                        livingEntity.getTags().add(STRONG_CREATURE);
                        generateStrongMonster(livingEntity);
                    }
                    setLevel(livingEntity, worldLevel, plusDifficulty);
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingDespawn(MobSpawnEvent.AllowDespawn event){
        Mob mob = event.getEntity();
        if(!mob.isPersistenceRequired()){
            int level = getEntityLevel(mob);
            LevelMechanicCapability cap = event.getEntity().level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).orElse(null);
            double worldLevel = cap.getAbsoluteLevel();
            double sd = cap.getStandardDeviation();
            Player player = mob.level().getNearestPlayer(mob, -1.0D);
            if(worldLevel - sd > level || level > worldLevel + sd) {
                double d0 = player != null ? player.distanceToSqr(mob) : 0;
                int i = 15;
                int j = i * i;
                if (d0 > (double)j) {
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }

    }

    @SubscribeEvent
    public static void dropEssence(LivingDropsEvent event){
        if(event.getEntity() != null) {
            LivingEntity livingEntity = event.getEntity();
            RandomSource randomSource = livingEntity.getRandom();
            Level world = livingEntity.level();
            EssenceItem essence = EssenceData.Helper.getEssenceItemByEntity(livingEntity);
            boolean hurtByPlayer = hurtByPlayer(livingEntity) || event.isRecentlyHit();

            if (essence != null && !livingEntity.isBaby()) {
                int level = getEntityLevel(livingEntity);
                boolean flagStrongMonster = livingEntity.getTags().contains(STRONG_CREATURE);
                int plusUpgrade = flagStrongMonster ? 2 : 0;
                int positiveUpgrade = Math.min(3, (int) Math.floor(level / 60F)) + plusUpgrade;
                int lootLevel = event.getLootingLevel();
                boolean shouldDecreasePercentage = false;
                boolean unactive = randomSource.nextFloat() * 10000 <= 1;
                int lootingLevel = lootLevel + 1;
                if (!essence.isValid() || flagStrongMonster) {
                    event.getDrops().add(new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new EssenceGiver(essence).withPositiveUpgradesLevel(positiveUpgrade).unactive(unactive).castToItemStack()));
                    shouldDecreasePercentage = true;
                    lootingLevel--;
                }
                TargetingConditions player = TargetingConditions.forCombat().range(10D);
                AABB bb = livingEntity.getBoundingBox().inflate(10D);
                int j = world.getNearbyEntities(Player.class, player, livingEntity, bb).size();
                if (j > 0 || hurtByPlayer) {
                    while (lootingLevel > 0) {
                        lootingLevel--;
                        unactive = randomSource.nextFloat() * 10000 <= 1;
                        if (randomSource.nextInt(100) < (30 - (shouldDecreasePercentage ? 15 : 0))) {
                            event.getDrops().add(new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new EssenceGiver(essence).withPositiveUpgradesLevel(positiveUpgrade).unactive(unactive).castToItemStack()));
                        }
                    }
                }
            }
        }
    }

    public static int getEntityLevel(LivingEntity livingEntity){
        if(hasTag(livingEntity)) {
            return Integer.parseInt(livingEntity.getTags().stream().filter(s -> s.split(":", 2)[0].equals("MohardLevel")).toList().get(0).split(":", 2)[1]);
        }
        return 0;
    }

    private static void setLevel(LivingEntity livingEntity, double worldLevel, int plusDifficulty) {

        double difficulty = getDifficultyMultiplier(livingEntity.level().getDifficulty().getId() + plusDifficulty); //[0.5 ,2.5]
        @Nullable EssenceItem essenceItem = EssenceData.Helper.getEssenceItemByEntity(livingEntity);

        Map<Attribute, Double> statsForEntity = new HashMap<>();
        List<Attribute> attributes = MohardAttributes.Helper.getAttributes();

        //POPULATE THE MAP WITH THE BASE ATTRIBUTES
        attributes.forEach(attribute -> statsForEntity.put(attribute, livingEntity.getAttribute(attribute) != null ? livingEntity.getAttribute(attribute).getValue() : attribute.getDefaultValue()));
        double baseSpeed = livingEntity.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);

        //ADD TO THE MAP THE ESSENCE STATS
        if(essenceItem != null){
            double nEssence =  worldLevel / 20;
            Map<Attribute, Double> statsFromEssence = essenceItem.getEssenceData().essenceStats().getBaseStats();
            attributes.forEach(attribute -> {
                double essenceValue = nEssence * statsFromEssence.get(attribute);
                if((attribute.equals(Attributes.MAX_HEALTH) || attribute.equals(MohardAttributes.RAW_ARMOR)) && EssenceData.Helper.isBoss(livingEntity.getType())) {
                    essenceValue = getAttributeValueForBoss(attribute, worldLevel);
                }
                double newValue = statsForEntity.get(attribute) + essenceValue;
                statsForEntity.put(attribute, newValue);
            });
        }

        //ADD LEVELED ATTRIBUTES TO MAP
        attributes.forEach(attribute -> {
            double value = statsForEntity.get(attribute);
            if(attribute.equals(MohardAttributes.RAW_ARMOR)){
                value = value + ((worldLevel * 2e-1) * difficulty) + ((worldLevel * worldLevel * 2e-3));
            }
            if(attribute.equals(Attributes.MAX_HEALTH)){
                value = value + ((worldLevel / 5) * difficulty) + ((worldLevel * worldLevel * 2e-3));
            }
            statsForEntity.put(attribute, value);
        });

        //ADD THE ATTRIBUTES OF THE MAP TO THE ENTITY
        attributes.forEach(attribute -> {
            if (livingEntity.getAttribute(attribute) != null) {
                double value = statsForEntity.get(attribute);
                livingEntity.getAttribute(attribute).setBaseValue(value);
                if (attribute.equals(MohardAttributes.AGILITY)) {
                    livingEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Math.min(1.50D * baseSpeed, baseSpeed + (baseSpeed * value / 125)));
                    livingEntity.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(Mth.clamp(0.08 - (value * 1E-4), 0.07, 0.09));
                }
            }
        });

        livingEntity.setHealth(Math.round(statsForEntity.get(Attributes.MAX_HEALTH)));
        livingEntity.getTags().add("MohardLevel:"+(int)Math.round(worldLevel));
    }

    private static double getDifficultyMultiplier(int difficulty){
        return 1 + (0.5D * (difficulty - 1));
    }

    private static boolean hasTag(LivingEntity livingEntity) {
        return livingEntity.getTags().stream().anyMatch(s -> s.split(":", 2)[0].equals("MohardLevel"));
    }
    private static Double getAttributeValueForBoss(Attribute attribute, double level){
        if(attribute.equals(Attributes.MAX_HEALTH)){
            return (Math.pow(2, level) * 2E-2) + (Math.pow(1, level) * 3E1);
        } else if(attribute.equals(MohardAttributes.RAW_ARMOR)){
            return (Math.pow(2, level) * 4E-3) + (Math.pow(1, level) * 4.5E-1);
        } else {
            return 0D;
        }

    }

    private static void generateStrongMonster(LivingEntity livingEntity){
        livingEntity.setCustomName(Component.literal("").append(livingEntity.getDisplayName()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BusEvents{
        @SubscribeEvent
        public static void addAttributes(EntityAttributeModificationEvent event){
            for(EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                for(Attribute att : getSetupAttributes()) {

                    if(!event.has(entityType, att)){
                        event.add(entityType, att, att.getDefaultValue());
                    }
                }
            }
        }
        private static List<Attribute> getSetupAttributes(){
            return Arrays.asList(Attributes.MAX_HEALTH,
                    MohardAttributes.DAMAGE,
                    MohardAttributes.AGILITY,
                    MohardAttributes.ARMOR_PENETRATION,
                    MohardAttributes.RAW_ARMOR);
        }

    }

    private static boolean hurtByPlayer(LivingEntity livingEntity){
        return (livingEntity.getKillCredit() instanceof Player);
    }

}
