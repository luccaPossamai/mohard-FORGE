package net.lpcamors.mohard.events;

import net.lpcamors.mohard.data.MohardTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class BossDamage {

    public static float getBossBattleTypeDamage(LivingEntity entity, DamageSource source, double damage,double rawArmor, double penetration){
        EntityType<?> type = entity.getType();
        double finalDamage = damage;
        Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
        if (bossType.isPresent()) {
            finalDamage = bossType.get().apllyFight(source, finalDamage,rawArmor, penetration);
        }
        if(entity.getTags().contains(EntityEvolutionEvents.STRONG_CREATURE)){
            finalDamage = Type.MONSTER.apllyFight(source, finalDamage,rawArmor, penetration);
        }

        return (float) finalDamage;
    }

    public static boolean isValidBossFight(LivingEntity entity){
        if(entity.getTags().contains(EntityEvolutionEvents.STRONG_CREATURE)) return true;
        return isValidBossFight(entity.getType());
    }
    public static boolean isValidBossFight(EntityType<?> type){
        Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
        return bossType.isPresent() && bossType.get().getType().equals(type);
    }

    /**
     * Variables :
     *  -> 'c' is responsible to adjust the penetration 'sensitivity'
     *      when it gets higher, the damage decreases;
     *  -> 'k' is the angle curve adjustment, it takes care of the
     *      angle between the curve and the x line. When it gets
     *      higher, the damage also decreases;
     *  -> (amount / k) * logB(x + 1), B = x + 1 + x * log(x + 1)(1 + c - (p * c /100))
     */
    private static DoubleBinaryOperator baseLogFunc(){
        return logFunc(1, 400);
    }

    protected static DoubleBinaryOperator logFunc(float k, float c){
        return (amount, penetration) -> (amount * Math.log(amount + 1)) / (k * Math.log(amount + 1 + (amount * Math.log(amount + 1) * (c + 1 - (c / 1E2F) * (penetration)))));
    }


    public enum Type implements IExtensibleEnum {

        MONSTER(EntityType.EGG, damageSource -> false, value -> value, (value, penetration) -> value / (11 - (Math.log10(value + 1) / Math.log10(2 - (penetration / 722))))),
        DRAGON(EntityType.ENDER_DRAGON, damageSource -> false, value -> value * 1.25F, BossDamage.logFunc(2F, 1000)),
        GUARDIAN(EntityType.ELDER_GUARDIAN, damageSource -> false, value -> value * 1.5F, BossDamage.baseLogFunc()),
        WITHER(EntityType.WITHER, damageSource -> false, value -> value * 1.1D, BossDamage.logFunc(1.4F, 100)),
        WARDEN(EntityType.WARDEN, damageSource -> damageSource.is(DamageTypes.FIREWORKS), value -> value * 1.75F, BossDamage.logFunc(1.7F, 700))
        ;

        private final EntityType<?> type;
        private final DoubleFunction<Double> weaknessMultiplier;
        private final Function<DamageSource, Boolean> weaknessCondition;

        private final DoubleBinaryOperator damageFunction;



        Type(EntityType<?> type, Function<DamageSource, Boolean> weaknessCondition, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            this.type = type;
            this.weaknessMultiplier = weaknessMultiplier;
            this.weaknessCondition = weaknessCondition;
            this.damageFunction = damageFunction;
        }

        public EntityType<?> getType() {
            return type;
        }

        private float apllyFight(DamageSource source, double damage, double rawArmor, double penetration){
            double finalDamage = this.damageFunction.applyAsDouble(damage, penetration - (rawArmor/Math.abs(rawArmor)) * (Math.log(rawArmor)/Math.log(1.15F)));
            boolean flag = this.weaknessCondition.apply(source);
            if(flag){
                finalDamage = this.weaknessMultiplier.apply(finalDamage);
            }
            return Math.round(finalDamage);
        }
        public static Type create(String string, EntityType<?> type, Function<DamageSource, Boolean> weaknessFunc, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            throw new IllegalStateException("Enum not extended");
        }
    }


}
