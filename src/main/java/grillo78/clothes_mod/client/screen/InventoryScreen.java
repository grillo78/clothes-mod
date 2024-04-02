package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.container.InventoryContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InventoryScreen extends ContainerScreen<InventoryContainer> {

    public InventoryScreen(InventoryContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {

    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(pMatrixStack, pMouseX,pMouseY);
    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/inventory.png"));
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(pMatrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        Minecraft.getInstance().player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(inv->{
            if(inv.getInventory().getStackInSlot(0).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/hat.png"));
                this.blit(pMatrixStack, relX + 80, relY + 8, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(1).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/shirt.png"));
                this.blit(pMatrixStack, relX + 80, relY + 26, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(2).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/pants.png"));
                this.blit(pMatrixStack, relX + 80, relY + 44, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(3).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/shoes.png"));
                this.blit(pMatrixStack, relX + 80, relY + 62, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(4).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/back.png"));
                this.blit(pMatrixStack, relX + 98, relY + 26, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(5).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/jacket.png"));
                this.blit(pMatrixStack, relX + 116, relY + 26, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(6).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/gloves.png"));
                this.blit(pMatrixStack, relX + 134, relY + 26, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(7).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/wrist.png"));
                this.blit(pMatrixStack, relX + 152, relY + 26, 0, 0, 16, 16, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(8).isEmpty()) {
                this.getMinecraft().getTextureManager().bind(new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/belt.png"));
                this.blit(pMatrixStack, relX + 98, relY + 44, 0, 0, 16, 16, 16, 16);
            }
        });
        net.minecraft.client.gui.screen.inventory.InventoryScreen.renderEntityInInventory(relX + 51, relY+ 75, 30, (float) (relX +  51) - pX, (float) (relY + 75 - 50) - pY, this.minecraft.player);
    }
}
