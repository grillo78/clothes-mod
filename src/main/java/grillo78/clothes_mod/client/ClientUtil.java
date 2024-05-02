package grillo78.clothes_mod.client;

import grillo78.clothes_mod.client.screen.SewingMachineScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientUtil {
    @OnlyIn(Dist.CLIENT)
    public static void openSewingMachineScreen() {
        Minecraft.getInstance().setScreen(new SewingMachineScreen(Component.empty()));
    }
}
