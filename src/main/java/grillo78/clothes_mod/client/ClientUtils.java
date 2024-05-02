package grillo78.clothes_mod.client;

import grillo78.clothes_mod.client.screen.SewingMachineScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ClientUtils {

    public static void openSewingMachine(){
        Minecraft.getInstance().setScreen(new SewingMachineScreen(Component.empty()));
    }
}
