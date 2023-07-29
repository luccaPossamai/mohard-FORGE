package net.lpcamors.mohard.item.essences;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface EssencePredicate<T extends LivingEntity> {
    boolean test(T entity);
}

