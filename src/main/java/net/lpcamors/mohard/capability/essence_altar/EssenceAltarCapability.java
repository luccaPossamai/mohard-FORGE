package net.lpcamors.mohard.capability.essence_altar;

import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class EssenceAltarCapability extends ItemStackHandler {

    public static final int SLOTS = 9;

    public EssenceAltarCapability(){
        super(SLOTS);
    }

    @Override
    public int getSlots() {
        return super.getSlots();
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return stack.getItem() instanceof EssenceItem;
    }
}
