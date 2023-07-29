package net.lpcamors.mohard.capability.level_mechanic;

import net.lpcamors.mohard.capability.MohardCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LevelMechanicCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private LevelMechanicCapability levelMechanicCapability;

    @Nonnull
    private LevelMechanicCapability getCap() {
        if (levelMechanicCapability == null) {
            levelMechanicCapability = new LevelMechanicCapability();
        }
        return levelMechanicCapability;
    }

    private final LazyOptional<LevelMechanicCapability> lazyCap = LazyOptional.of(this::getCap);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == MohardCapabilities.LEVEL_MECHANIC_CAPABILITY) {
            return (LazyOptional<T>) (lazyCap);
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        LevelMechanicCapability cap = this.getCap();
        tag.putDouble("Level", cap.level);
        tag.putDouble("Variance", cap.variance);
        tag.putDouble("StandardDeviation", cap.standardDeviation);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.getCap().level = tag.getDouble("Level");
        this.getCap().variance = tag.getDouble("Variance");
        this.getCap().standardDeviation = tag.getDouble("StandardDeviation");
    }
}
