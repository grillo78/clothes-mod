package grillo78.clothes_mod.common.container;

import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.container.slots.ClothSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class InventoryContainer extends Container {
    public InventoryContainer(@Nullable ContainerType<?> pMenuType, int pContainerId, PlayerEntity player) {
        super(pMenuType, pContainerId);
        player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap->{
                addSlot(new ClothSlot(cap.getInventory(), 0,80,8));
                addSlot(new ClothSlot(cap.getInventory(), 1,80,26));
                addSlot(new ClothSlot(cap.getInventory(), 2,80,44));
                addSlot(new ClothSlot(cap.getInventory(), 3,80,62));
                addSlot(new ClothSlot(cap.getInventory(), 4,98,26));
                addSlot(new ClothSlot(cap.getInventory(), 5,116,26));
                addSlot(new ClothSlot(cap.getInventory(), 6,134,26));
                addSlot(new ClothSlot(cap.getInventory(), 7,152,26));
                addSlot(new ClothSlot(cap.getInventory(), 8,98,44));
        });
        addPlayerSlots(new InvWrapper(player.inventory));
    }

    protected void addPlayerSlots(InvWrapper playerInventory) {
        int yStart = 30 + 18 * 3;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + yStart;
                this.addSlot(new SlotItemHandler(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = yStart + 58;
//            if (row != locked)
            this.addSlot(new SlotItemHandler(playerInventory, row, x, y));
//            else
//                this.addSlot(new LockedSlot(playerInventory, row, x, y));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack transferred = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        int otherSlots = this.slots.size() - 36;

        if (slot != null && slot.hasItem()) {
            ItemStack current = slot.getItem();
            transferred = current.copy();

            if (index < otherSlots) {
                if (!this.moveItemStackTo(current, otherSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 0, otherSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (current.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return transferred;
    }
}
