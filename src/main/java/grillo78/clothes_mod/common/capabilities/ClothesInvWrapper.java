package grillo78.clothes_mod.common.capabilities;

import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.SyncCap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;
import org.checkerframework.checker.units.qual.C;

import java.util.List;


public class ClothesInvWrapper {

    private IItemHandler inventory = new ItemStackHandler(9);
    private Player player;

    public ClothesInvWrapper(Player player) {
        this.player = player;
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public void syncToAll(Level level) {
        level.players().forEach(playerEntity -> PacketHandler.INSTANCE.sendTo(new SyncCap(writeNBT(), player.getId()), ((ServerPlayer) playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT));
    }

    public CompoundTag writeNBT() {
        CompoundTag tag = new CompoundTag();

        CompoundTag inventoryCompound = new CompoundTag();

        for (int i = 0; i < inventory.getSlots(); i++) {
            inventoryCompound.put(String.valueOf(i), inventory.getStackInSlot(i).serializeNBT());
        }

        tag.put("inventory", inventoryCompound);

        return tag;
    }

    public void readNBT(Tag compound) {
        if(compound instanceof CompoundTag && ((CompoundTag) compound).contains("inventory")){
            CompoundTag inventoryCompound = ((CompoundTag) compound).getCompound("inventory");

            for (int i = 0; i < inventoryCompound.size(); i++) {
                ItemStack tempStack = ItemStack.of(inventoryCompound.getCompound(String.valueOf(i)));
                if (tempStack.getItem() == inventory.getStackInSlot(i).getItem()) {
                    inventory.getStackInSlot(i).deserializeNBT(inventoryCompound.getCompound(String.valueOf(i)));
                } else
                    inventory.insertItem(i, tempStack, false);

            }
        }
    }
}
