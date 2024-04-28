package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.menu.InventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InventoryScreen extends AbstractContainerScreen<InventoryMenu> {


    private static final ResourceLocation CLOTHES_INVENTORY_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/inventory.png");
    private static final ResourceLocation HAT_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/hat.png");
    private static final ResourceLocation SHIRT_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/shirt.png");
    private static final ResourceLocation PANTS_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/pants.png");
    private static final ResourceLocation SHOES_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/shoes.png");
    private static final ResourceLocation BACK_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/back.png");
    private static final ResourceLocation JACKET_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/jacket.png");
    private static final ResourceLocation GLOVES_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/gloves.png");
    private static final ResourceLocation WRIST_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/wrist.png");
    private static final ResourceLocation BELT_SLOT_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/container/belt.png");

    public InventoryScreen(InventoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pX, int pY) {

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTicks) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(pGuiGraphics, pMouseX,pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTicks, int pX, int pY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(CLOTHES_INVENTORY_LOCATION, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        Minecraft.getInstance().player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(inv->{
            if(inv.getInventory().getStackInSlot(0).isEmpty()) {
                pGuiGraphics.blit(HAT_SLOT_LOCATION, relX + 80, relY + 8, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(1).isEmpty()) {
                pGuiGraphics.blit(SHIRT_SLOT_LOCATION, relX + 80, relY + 26, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(2).isEmpty()) {
                pGuiGraphics.blit(PANTS_LOCATION, relX + 80, relY + 44, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(3).isEmpty()) {
                pGuiGraphics.blit(SHOES_SLOT_LOCATION, relX + 80, relY + 62, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(4).isEmpty()) {
                pGuiGraphics.blit(BACK_SLOT_LOCATION, relX + 98, relY + 26, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(5).isEmpty()) {
                pGuiGraphics.blit(JACKET_SLOT_LOCATION, relX + 116, relY + 26, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(6).isEmpty()) {
                pGuiGraphics.blit(GLOVES_SLOT_LOCATION, relX + 134, relY + 26, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(7).isEmpty()) {
                pGuiGraphics.blit(WRIST_SLOT_LOCATION, relX + 152, relY + 26, 0, 0, 16, 16);
            }
            if(inv.getInventory().getStackInSlot(8).isEmpty()) {
                pGuiGraphics.blit(BELT_SLOT_LOCATION, relX + 98, relY + 44, 0, 0, 16, 16);
            }
        });
        net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics,relX + 51, relY+ 75, 30,(float) (relX +  51) - pX, (float) (relY + 75 - 50) - pY, this.minecraft.player);
    }
}
