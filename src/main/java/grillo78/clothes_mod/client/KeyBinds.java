package grillo78.clothes_mod.client;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBinds {

    public static final KeyBinding OPEN_INVENTORY = new KeyBinding("key." + ClothesMod.MOD_ID + ".open_inventory", GLFW.GLFW_KEY_C, "key." + ClothesMod.MOD_ID + ".category");

    public static void registerKeys() {
        ClientRegistry.registerKeyBinding(KeyBinds.OPEN_INVENTORY);
    }
}
