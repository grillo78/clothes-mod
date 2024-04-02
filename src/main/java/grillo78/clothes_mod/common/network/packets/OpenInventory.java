package grillo78.clothes_mod.common.network.packets;

import grillo78.clothes_mod.common.container.InventoryContainer;
import grillo78.clothes_mod.common.container.ModContainers;
import grillo78.clothes_mod.common.network.IMessage;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenInventory implements IMessage<OpenInventory> {
    @Override
    public void encode(OpenInventory message, PacketBuffer buffer) {

    }

    @Override
    public OpenInventory decode(PacketBuffer buffer) {
        return new OpenInventory();
    }

    @Override
    public void handle(OpenInventory message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            NetworkHooks.openGui(supplier.get().getSender(),
                    new SimpleNamedContainerProvider((id, playerInventory, playerEntity) -> new InventoryContainer(ModContainers.INVENTORY_CONTAINER, id, playerEntity), StringTextComponent.EMPTY));
        });
        supplier.get().setPacketHandled(true);
    }
}
