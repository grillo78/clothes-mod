package grillo78.clothes_mod.common.network;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.network.packets.Craft;
import grillo78.clothes_mod.common.network.packets.OpenInventory;
import grillo78.clothes_mod.common.network.packets.SyncCap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel INSTANCE;
    private static int nextId = 0;

    /**
     * create the network channel and register the packets
     */
    public static void init() {
        // Create the Network channel
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ClothesMod.MOD_ID, "network"),() -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);
        register(OpenInventory.class, new OpenInventory());
        register(SyncCap.class, new SyncCap());
        register(Craft.class, new Craft());
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message) {
        INSTANCE.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
