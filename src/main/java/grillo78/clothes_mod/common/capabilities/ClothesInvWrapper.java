package grillo78.clothes_mod.common.capabilities;

import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.SyncCap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ClothesInvWrapper implements IClothesInvWrapper{

    private IItemHandler inventory = new ItemStackHandler(9);

    @Override
    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public void syncToAll(World level) {
        level.players().forEach(playerEntity -> PacketHandler.INSTANCE.sendTo(new SyncCap(writeNBT(), playerEntity.getId()),((ServerPlayerEntity)playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT));
    }

    @Override
    public INBT writeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory,null);
    }

    @Override
    public void readNBT(INBT compound) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound);
    }
}
