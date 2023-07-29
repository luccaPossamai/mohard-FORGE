package net.lpcamors.mohard.block.essence_altar;

import net.lpcamors.mohard.block.MohardTileEntityTypes;
import net.lpcamors.mohard.block.essence_altar.gui.EssenceAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EssenceAltarTileEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    public EssenceAltarTileEntity(BlockPos pos, BlockState state) {
        super(MohardTileEntityTypes.ENDER_ALTAR.get(), pos, state);
    }


    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("ender_altar");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new EssenceAltarMenu(containerId, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return this.getItems().get(i);
    }

    @Override
    public ItemStack removeItem(int i, int count) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), i, count);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;


    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.getItems(), i);
    }

    @Override
    public void setItem(int i, @NotNull ItemStack item) {
        this.getItems().set(i, item);
        if (item.getCount() > this.getMaxStackSize()) {
            item.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player playerEntity) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(playerEntity.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }

    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }


    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }




}
