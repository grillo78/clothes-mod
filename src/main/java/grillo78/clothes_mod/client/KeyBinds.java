package grillo78.clothes_mod.client;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBinds {

    public static final KeyMapping OPEN_INVENTORY = new KeyMapping("key." + ClothesMod.MOD_ID + ".open_inventory", GLFW.GLFW_KEY_C, "key." + ClothesMod.MOD_ID + ".category");

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBinds.OPEN_INVENTORY);
    }
}
