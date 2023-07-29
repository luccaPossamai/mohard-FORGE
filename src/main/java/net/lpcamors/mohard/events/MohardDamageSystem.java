package net.lpcamors.mohard.events;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.capability.MohardCapabilities;
import net.lpcamors.mohard.data.MohardTags;
import net.lpcamors.mohard.item.MohardAttributes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

/**
 *  Sequence of event methods to process the game damage.
 *  Vanilla DamageSources will be processed based on the
 *  source nature.
 *
 *  The damage is process by the vanilla armor system, then,
 *  it will be scaled with the attribute of the entity that
 *  is causing the damage.
 */


@Mod.EventBusSubscriber()
public class MohardDamageSystem {

    /**
     *  Calculate the damage to be reduced by the armor
     *
     *  FinalDamage(D) have two processes:
     *   1° a >= 0 :
     *      D(damage(d), armor(a)) = d² / (a + d)
     *      D(d, a) = d² / (a + d)
     *
     *   2º a < 0 :
     *      D(d, a) = 2d - (d² / (|a| + d))
     *      D(d, a) = (d² + 2|a|d) / (|a| + d);
     *
     *  Summarizing, when the resultant armor is greater
     *  than or equal to zero, the damage is reduced based
     *  on the armor of the entity. But, when the armor is
     *  less than zero, the portion reduced by the armor is
     *  added to the final damage.
     *  a > 0: D = d - b(damage blocked);
     *  a < 0: D = d + b;
     *  a = 0: D = d.
     *
     */


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void receivedDamage(LivingHurtEvent event) {

        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        float originalDamage = event.getAmount();
        if(originalDamage > 0) {
            if (BossDamage.isValidBossFight(entity)) {
                double armorPen = getAttributeValue(source.getEntity(), MohardAttributes.ARMOR_PENETRATION);
                double rawArmor = getAttributeValue(entity, MohardAttributes.RAW_ARMOR);
                float amount = BossDamage.getBossBattleTypeDamage(entity, source, originalDamage, rawArmor, armorPen);
                event.setAmount(amount);
            } else {
                if (isDamageValid(source)) {
                    double rawArmor = getAttributeValue(entity, MohardAttributes.RAW_ARMOR);
                    double armorPen = getAttributeValue(source.getEntity(), MohardAttributes.ARMOR_PENETRATION);
                    double a = rawArmor - armorPen / 1.5;
                    double totalArmor = a == 0 ? 0 : (1 - ((a/Math.abs(a))* armorPen * 0.004)) * a;
                    if(rawArmor <= 0 && totalArmor > 0) {
                        totalArmor = rawArmor;
                    }
                    if (source.is(DamageTypeTags.BYPASSES_ARMOR) && totalArmor > 0) {
                        totalArmor = 0;
                    }
                    int k = 3;
                    if(totalArmor < 0) {
                        float damageByNegativeArmor = originalDamage * originalDamage / ((float) Math.abs(k * totalArmor) + originalDamage);
                        originalDamage = 2 * originalDamage - damageByNegativeArmor;
                        totalArmor = 0;
                    }
                    float amount = originalDamage * originalDamage / ((float)Math.abs(k * totalArmor) + originalDamage);
                    if(amount != amount){
                        MohardMain.LOGGER.error("Entity: "+event.getEntity()+" receiving NaN damage!");
                        MohardMain.LOGGER.error("DamageCause: "+event.getSource()+";");
                        if(event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity livingEntity){
                            MohardMain.LOGGER.error("DamageEntityLevel: "+ EntityEvolutionEvents.getEntityLevel(livingEntity));
                        }
                        amount = originalDamage;
                    }
                    event.setAmount(amount);

                }
            }
        }
    }


    /**
     *  Process the damage
     */

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void generalDamage(LivingHurtEvent event){
        Entity causador = event.getSource() != null ? event.getSource().getEntity() : null;
        if(causador == null) return;
        double attributeValue =  (float) getAttributeValue(causador, getAttributeSource(event.getSource()));
        double dano = calculateDamage(causador, event.getAmount(), attributeValue);
        event.setAmount((float)dano);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void explosionDamage(LivingHurtEvent event){
        Entity causador = event.getSource().getEntity();
        float dano = event.getAmount();
        if(event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            if(!(causador instanceof LivingEntity) && !(causador instanceof FireworkRocketEntity)){
                double worldLevel = event.getEntity().level().getCapability(MohardCapabilities.LEVEL_MECHANIC_CAPABILITY).orElse(null).getAbsoluteLevel();
                dano = (float) (dano + dano * (worldLevel / 200F));
            }
            event.setAmount(dano);
        }
    }

    @SubscribeEvent
    public static void damageDodge(LivingAttackEvent event){
        LivingEntity livingEntity = event.getEntity();
        Entity entity = event.getSource().getEntity();
        if(!livingEntity.level().isClientSide) {
            DamageSource damageSource = event.getSource();
            if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
            boolean flag = entity != null && (entity instanceof LivingEntity || (entity instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity));
            double toDodge = (event.getAmount() / (Math.max(1, livingEntity.getAttributeValue(MohardAttributes.AGILITY)) * 0.02 * (flag ? 1 : 10)));
            if (toDodge < 1) {
                event.setCanceled(true);
                livingEntity.level().playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, livingEntity.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }


    private static double getAttributeValue(Entity entity, Attribute attribute){
        if(attribute != null) {
            if (entity instanceof Projectile tiro) {
                if (tiro.getOwner() instanceof LivingEntity livingEntity) {
                    return getAttributeEspecificValue(livingEntity, attribute);
                }
            }
            if (entity instanceof LivingEntity livingEntity) {
                return getAttributeEspecificValue(livingEntity, attribute);
            }
        }
        return 0;
    }

    private static double getAttributeEspecificValue(LivingEntity livingEntity, Attribute attribute){
        if(attribute != null) {
            if (livingEntity.getAttribute(attribute) != null) {
                return livingEntity.getAttributeValue(attribute);
            }
        }
        return 0.0;
    }
    private static boolean isDamageValid(DamageSource damageSource){
        return (damageSource != null && damageSource.getEntity() != null);
    }

    @Nullable
    private static Attribute getAttributeSource(DamageSource damageSource) {
        if (damageSource.getEntity() != null) {
            return MohardAttributes.DAMAGE;
        }
        return null;
    }

    private static double calculateDamage(@Nullable Entity damager, double originalDano, double dano) {
        double efic = Math.min(2.5, (originalDano / 10) + ((!(damager instanceof Player) && !(damager instanceof Projectile projectile && projectile.getOwner() instanceof Player) && originalDano >= 0) ? 0.4 : 0));
        return (dano * efic) + originalDano;
    }



}
