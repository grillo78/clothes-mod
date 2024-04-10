package grillo78.clothes_mod.common.container;

import grillo78.clothes_mod.common.block_entities.SewingMachineBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class SewingMachineContainer extends Container {
    public SewingMachineContainer(@Nullable ContainerType<?> pMenuType, int pContainerId, PlayerInventory inv, SewingMachineBlockEntity blockEntity) {
        super(pMenuType, pContainerId);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                System.out.println("index: " + (j + i * 3));
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(), j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 9, 124, 35));

        addPlayerSlots(new InvWrapper(inv));
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
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }
}
