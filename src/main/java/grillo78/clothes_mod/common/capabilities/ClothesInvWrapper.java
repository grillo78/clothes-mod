package grillo78.clothes_mod.common.capabilities;

import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.SyncCap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;


public class ClothesInvWrapper implements IClothesInvWrapper {

    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private IItemHandler inventory = new ItemStackHandler(items);

    @Override
    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public void syncToAll(Level level) {
        level.players().forEach(playerEntity -> PacketHandler.INSTANCE.sendTo(new SyncCap(writeNBT(), playerEntity.getId()), ((ServerPlayer) playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT));
    }

    @Override
    public Tag writeNBT() {
        return ContainerHelper.saveAllItems(new CompoundTag(), this.items);
    }

    @Override
    public void readNBT(Tag compound) {
        ContainerHelper.loadAllItems((CompoundTag) compound, this.items);
    }
}
