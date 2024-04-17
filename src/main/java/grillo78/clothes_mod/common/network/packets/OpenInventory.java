package grillo78.clothes_mod.common.network.packets;

import grillo78.clothes_mod.common.menu.InventoryMenu;
import grillo78.clothes_mod.common.menu.ModMenus;
import grillo78.clothes_mod.common.network.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenInventory implements IMessage<OpenInventory> {
    @Override
    public void encode(OpenInventory message, FriendlyByteBuf buffer) {

    }

    @Override
    public OpenInventory decode(FriendlyByteBuf buffer) {
        return new OpenInventory();
    }

    @Override
    public void handle(OpenInventory message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            supplier.get().getSender().openMenu(new SimpleMenuProvider((id, playerInventory, playerEntity) -> new InventoryMenu(ModMenus.INVENTORY_CONTAINER, id, playerEntity), Component.empty()));
        });
        supplier.get().setPacketHandled(true);
    }
}
