package grillo78.clothes_mod.common.network.packets;

import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.network.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCap implements IMessage<SyncCap> {

    private CompoundTag compoundNBT = new CompoundTag();
    private int id;

    public SyncCap(Tag compoundNBT, int id) {
        this.compoundNBT.put("inv", compoundNBT);
        this.id = id;
    }

    public SyncCap() {
    }

    @Override
    public void encode(SyncCap message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.compoundNBT);
        buffer.writeInt(message.id);
    }

    @Override
    public SyncCap decode(FriendlyByteBuf buffer) {
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
