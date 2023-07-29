package net.lpcamors.mohard.block.essence_altar;

import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EssenceSlot extends Slot {

    public EssenceSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    public boolean mayPlace(ItemStack p_39111_) {
        return mayPlaceItem(p_39111_);
    }

    public static boolean mayPlaceItem(ItemStack p_39113_) {
        return p_39113_.getItem() instanceof EssenceItem;
    }

    public int getMaxStackSize() {
        return 1;
    }

}
