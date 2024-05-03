package grillo78.clothes_mod.common.capabilities;

import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.SyncCap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;
import org.checkerframework.checker.units.qual.C;


public class ClothesInvWrapper implements IClothesInvWrapper {

    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private IItemHandler inventory = new ItemStackHandler(items);
    private Player player;

    public ClothesInvWrapper(Player player) {
        this.player = player;
    }

    @Override
    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public void syncToAll(Level level) {
        level.players().forEach(playerEntity -> PacketHandler.INSTANCE.sendTo(new SyncCap(writeNBT(), player.getId()), ((ServerPlayer) playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT));
    }

    @Override
    public Tag writeNBT() {
        CompoundTag tag = new CompoundTag();

        for (int i = 0; i < inventory.getSlots(); i++) {
            tag.put(String.valueOf(i), inventory.getStackInSlot(i).save(new CompoundTag()));
        }

        return tag;
    }

    @Override
    public void readNBT(Tag compound) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.insertItem(i, ItemStack.of(((CompoundTag)compound).getCompound(String.valueOf(i))), false);
        }
    }
}
