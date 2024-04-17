package grillo78.clothes_mod.client.screen;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.menu.SewingMachineMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SewingMachineScreen extends AbstractContainerScreen<SewingMachineMenu> {

    private static final ResourceLocation SEWING_MACHINE_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/sewing_machine.png");

    public SewingMachineScreen(SewingMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTicks, int pX, int pY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(SEWING_MACHINE_LOCATION, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
