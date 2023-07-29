package net.lpcamors.mohard.capability.essence_altar;

import net.lpcamors.mohard.capability.MohardCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EssenceAltarCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private EssenceAltarCapability ALTAR_HANDLER;

    @Nonnull
    private EssenceAltarCapability getInventory() {
        if (ALTAR_HANDLER == null) {
            ALTAR_HANDLER = new EssenceAltarCapability();
        }
        return ALTAR_HANDLER;
    }

    private final LazyOptional<EssenceAltarCapability> lazyInventory = LazyOptional.of(this::getInventory);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == MohardCapabilities.ALTAR_CAPABILITY){
            return (LazyOptional<T>)(lazyInventory);
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        return this.getInventory().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.getInventory().deserializeNBT(nbt);
    }

}
