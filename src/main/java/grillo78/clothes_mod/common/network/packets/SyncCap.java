package grillo78.clothes_mod.common.network.packets;

import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCap implements IMessage<SyncCap> {

    private CompoundNBT compoundNBT = new CompoundNBT();
    private int id;

    public SyncCap(INBT compoundNBT, int id) {
        this.compoundNBT.put("inv", compoundNBT);
        this.id = id;
    }

    public SyncCap() {
    }

    @Override
    public void encode(SyncCap message, PacketBuffer buffer) {
        buffer.writeNbt(message.compoundNBT);
        buffer.writeInt(message.id);
    }

    @Override
    public SyncCap decode(PacketBuffer buffer) {
        return new SyncCap(buffer.readNbt().get("inv"), buffer.readInt());
    }

    @Override
    public void handle(SyncCap message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            Minecraft.getInstance().level.getEntity(message.id).getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap->{
                cap.readNBT(message.compoundNBT.get("inv"));
            });
        });
    }
}
