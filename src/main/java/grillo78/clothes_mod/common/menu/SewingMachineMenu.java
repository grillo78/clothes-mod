package grillo78.clothes_mod.common.menu;

import grillo78.clothes_mod.common.block_entities.SewingMachineBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class SewingMachineMenu extends AbstractContainerMenu {
    public SewingMachineMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Container container, SewingMachineBlockEntity blockEntity) {
        super(pMenuType, pContainerId);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(blockEntity, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        this.addSlot(new Slot(blockEntity, 9, 124, 35));

        addPlayerSlots(new InvWrapper(container));
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
            this.addSlot(new SlotItemHandler(playerInventory, row, x, y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
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

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
