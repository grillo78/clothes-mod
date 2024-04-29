package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.Craft;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import grillo78.clothes_mod.common.recipes.SewingMachineRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.client.model.SeparatePerspectiveModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SewingMachineScreen extends Screen {

    private static final ResourceLocation SEWING_MACHINE_LOCATION = new ResourceLocation(ClothesMod.MOD_ID, "textures/gui/sewing_machine.png");

    protected int imageWidth = 176;
    protected int imageHeight = 166;
    protected int leftPos;
    protected int topPos;
    protected int itemIndex;
    protected Button prevIndexButton;
    protected Button nextIndexButton;
    protected Button craftButton;
    protected String itemName;
    protected IngredientsList scrollPanel;
    protected List<SewingMachineRecipe> recipes;
    protected ItemStack result;

    public SewingMachineScreen(ITextComponent pTitle) {
        super(pTitle);
        recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.Types.SEWING_MACHINE_RECIPE);
        result = recipes.get(itemIndex).getResultItem();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        addButton(prevIndexButton = new Button(leftPos + 10, topPos + 10, 10, 10,new StringTextComponent("<"), (button) -> {
            if (itemIndex > 0)
                itemIndex--;
            else
                itemIndex = recipes.size() - 1;
            button.changeFocus(false);
            itemName = (result = recipes.get(itemIndex).getResultItem()).getHoverName().getString();
            scrollPanel.updateContent(recipes.get(itemIndex));
        }));
        addButton(nextIndexButton = new Button(leftPos + 95, topPos + 10, 10, 10,new StringTextComponent(">"), (button) -> {
            if (itemIndex < recipes.size() - 1)
                itemIndex++;
            else
                itemIndex = 0;
            button.changeFocus(false);
            itemName =(result = recipes.get(itemIndex).getResultItem()).getHoverName().getString();
            scrollPanel.updateContent(recipes.get(itemIndex));
        }));
        itemName = result.getHoverName().getString();
//        addRenderableWidget(itemName = new StringWidget(leftPos + 52, topPos + 10, 10, 10, text, Minecraft.getInstance().font));
        addButton(craftButton = new Button(leftPos + 110, topPos + 10, 50, 10,new TranslationTextComponent("gui.craft"), (button) -> {
            PacketHandler.INSTANCE.sendToServer(new Craft(recipes.get(itemIndex).getId()));
            System.out.println("Send craft packet");
        }));

        scrollPanel = new IngredientsList(Minecraft.getInstance(), 60, imageHeight - 30, topPos + 25, leftPos + imageWidth - 65, recipes.get(itemIndex));
        addWidget(scrollPanel);
    }

    @Override
    public void render(MatrixStack MatrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(MatrixStack, pPartialTick, pMouseX, pMouseY);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        super.render(MatrixStack, pMouseX, pMouseY, pPartialTick);
        Minecraft.getInstance().font.draw(MatrixStack, itemName,leftPos + 58-Minecraft.getInstance().font.width(itemName)/2, topPos + 12, Color.WHITE.hashCode());
        renderItem(MatrixStack, leftPos + 52, topPos + 83, 115, 115, 115, result);
    }

    private static void renderItem(MatrixStack pose, double x, double y, float xScale, float yScale, float zScale, ItemStack item) {
//        pose.pushPose();
//        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        pose.translate(x, y, (100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
//        pose.scale(xScale, -yScale, zScale);
//        RenderSystem.applyModelViewMatrix();
//        Minecraft.getInstance().getItemRenderer().render(item, ItemTransforms.TransformType.GUI, false, pose, Minecraft.getInstance().renderBuffers().bufferSource(),15728880, OverlayTexture.NO_OVERLAY,Minecraft.getInstance().getItemRenderer().getModel(item,null, null, 0));
//        RenderSystem.enableDepthTest();
//        pose.popPose();
        RenderSystem.pushMatrix();
        Minecraft.getInstance().textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        pose.pushPose();
        pose.translate((double)x, (double)y, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
        pose.scale(1.0F, -1.0F, 1.0F);
        pose.scale(xScale,yScale,zScale);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        IBakedModel pBakedmodel = Minecraft.getInstance().getItemRenderer().getModel(item, null, null);
        boolean flag = !pBakedmodel.usesBlockLight();
        if (flag) {
            RenderHelper.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(item, ItemCameraTransforms.TransformType.GUI, false, pose, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, pBakedmodel);
        irendertypebuffer$impl.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    private void renderBg(MatrixStack MatrixStack, float pPartialTick, int pMouseX, int pMouseY) {
        Minecraft.getInstance().textureManager.bind(SEWING_MACHINE_LOCATION);
        blit(MatrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    private static class IngredientsList extends ScrollPanel {

        List<ItemStack> ingredients = new ArrayList<>();

        public IngredientsList(Minecraft client, int width, int height, int top, int left, SewingMachineRecipe startRecipe) {
            super(client, width, height, top, left);
            updateContent(startRecipe);
        }

        private void updateContent(SewingMachineRecipe recipe) {
            ingredients.clear();
            for (int i = 0; i < recipe.getIngredientsItems().size(); i++) {
                ingredients.add(new ItemStack(recipe.getIngredientsItems().get(i), recipe.getIngredientsAmount().get(i)));
            }
            scrollDistance = -8F;
        }

        @Override
        public int getContentHeight() {
            int height = (ingredients.size() * 20);
            if (height < this.bottom - this.top - 20)
                height = this.bottom - this.top - 20;
            return height;
        }

        @Override
        protected void drawPanel(MatrixStack pose, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
            pose.pushPose();
            for (ItemStack stack : ingredients) {
                if (stack != null) {
                    RenderSystem.enableBlend();
                    SewingMachineScreen.renderItem(pose, left + 10, relativeY, 15, 15, 15, stack);
                    Screen.drawString(pose, Minecraft.getInstance().font, String.valueOf(stack.getCount()), left + 20, relativeY, SewingMachineRecipe.hasEnough(stack,Minecraft.getInstance().player) ? Color.WHITE.hashCode() : Color.RED.hashCode());
                    RenderSystem.disableBlend();
                }
                relativeY += 20;
            }
            pose.popPose();
        }
    }
}
