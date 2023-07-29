package net.lpcamors.mohard.block.essence_altar.gui;

import net.lpcamors.mohard.block.MohardMenuTypes;
import net.lpcamors.mohard.block.essence_altar.EssenceSlot;
import net.lpcamors.mohard.capability.MohardCapabilities;
import net.lpcamors.mohard.capability.essence_altar.EssenceAltarCapability;
import net.lpcamors.mohard.events.PlayerEvolutionEvents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EssenceAltarMenu extends AbstractContainerMenu {

    private final @Nullable Container altarTileEntity;

    public EssenceAltarMenu(int i, Inventory playerInventory) {
        this(i, playerInventory, new SimpleContainer(9));
    }

    public EssenceAltarMenu(int containerId, Inventory invPlayer, @Nullable Container altarTileEntity) {
        super(MohardMenuTypes.ENDER_ALTAR.get(), containerId);
        this.altarTileEntity = altarTileEntity;
        EssenceAltarCapability inventory = this.loadItems(invPlayer.player);
        NonNullList<Slot> altarSlots = NonNullList.withSize(45, new Slot(new SimpleContainer(45), 0, 0, 0));
        altarSlots.clear();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Slot slot = new SlotItemHandler(inventory, j + i * 3, 62 + j * 18, 17 + i * 18);
                altarSlots.set(j + i * 3, slot);
                this.addSlot(slot);
            }
        }
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                Slot slot = new Slot(invPlayer, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18);
                altarSlots.set(i1 + k * 9 + 9, slot);
                this.addSlot(slot);
            }
        }
        for (int l = 0; l < 9; ++l) {
            Slot slot = new Slot(invPlayer, l, 8 + l * 18, 142);
            altarSlots.set(36 + l, slot);
            this.addSlot(slot);
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.altarTileEntity == null ? player.isUsingItem() : this.altarTileEntity.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < 0 || p_82846_2_ > 8) {
                if (EssenceSlot.mayPlaceItem(itemstack)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (p_82846_2_ > 8 && p_82846_2_ < 36) {
                    if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (p_82846_2_ > 35 && p_82846_2_ < 45) {
                    if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_82846_1_, itemstack1);
        }

        return itemstack;
    }

    @Mod.EventBusSubscriber
    public static class AltarEvent{
        @SubscribeEvent
        public static void saveAltarItems(PlayerContainerEvent.Close event){
            Player player = event.getEntity();
            if(event.getContainer() instanceof EssenceAltarMenu && player.isAlive()) {
                NonNullList<ItemStack> lista = NonNullList.withSize(9, ItemStack.EMPTY);
                for(int i = 0; i < 9; i++){
                    lista.set(i, event.getContainer().slots.get(i).getItem());
                }
                if(player.isUsingItem()){
                    player.stopUsingItem();
                }
                PlayerEvolutionEvents.setStats(lista, player);
                PlayerEvolutionEvents.sendPlayerLevelMessage(player);
            }
        }
    }

    public EssenceAltarCapability loadItems(Player player){
        return player.getCapability(MohardCapabilities.ALTAR_CAPABILITY).orElse(new EssenceAltarCapability());
    }
}
