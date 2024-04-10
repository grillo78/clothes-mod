package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.container.SewingMachineContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SewingMachineScreen extends ContainerScreen<SewingMachineContainer> {
    public SewingMachineScreen(SewingMachineContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/sewing_machine.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pMatrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
