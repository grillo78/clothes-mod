package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.Craft;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import grillo78.clothes_mod.common.recipes.SewingMachineRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ScrollPanel;

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

    public SewingMachineScreen(Component pTitle) {
        super(pTitle);
        recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.Types.SEWING_MACHINE_RECIPE.get());
        result = recipes.get(itemIndex).getResultItem();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        addRenderableWidget(prevIndexButton = new Button(leftPos + 10, topPos + 10, 10, 10,Component.literal("<"), (button) -> {
            if (itemIndex > 0)
                itemIndex--;
            else
                itemIndex = recipes.size() - 1;
            button.changeFocus(false);
            itemName = (result = recipes.get(itemIndex).getResultItem()).getHoverName().getString();
            scrollPanel.updateContent(recipes.get(itemIndex));
        }));
        addRenderableWidget(nextIndexButton = new Button(leftPos + 95, topPos + 10, 10, 10,Component.literal(">"), (button) -> {
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
        addRenderableWidget(craftButton = new Button(leftPos + 110, topPos + 10, 50, 10,Component.translatable("gui.craft"), (button) -> {
            PacketHandler.INSTANCE.sendToServer(new Craft(recipes.get(itemIndex).getId()));
            System.out.println("Send craft packet");
        }));

        scrollPanel = new IngredientsList(Minecraft.getInstance(), 60, imageHeight - 30, topPos + 25, leftPos + imageWidth - 65, recipes.get(itemIndex));
        addRenderableWidget(scrollPanel);
    }

    @Override
    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(poseStack, pPartialTick, pMouseX, pMouseY);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        super.render(poseStack, pMouseX, pMouseY, pPartialTick);
        Minecraft.getInstance().font.draw(poseStack, itemName,leftPos + 58-Minecraft.getInstance().font.width(itemName)/2, topPos + 12, Color.WHITE.hashCode());
        renderItem(poseStack, leftPos + 52, topPos + 83, 115, 115, 115, result);
    }

    private static void renderItem(PoseStack pose, double x, double y, float xScale, float yScale, float zScale, ItemStack item) {
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
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)x, (double)y, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(xScale,yScale,zScale);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        BakedModel pBakedModel = Minecraft.getInstance().getItemRenderer().getModel(item,null, null, 0);
        boolean flag = !pBakedModel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(item, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, pBakedModel);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    private void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0,SEWING_MACHINE_LOCATION);
        blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
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
        protected void drawPanel(PoseStack pose, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
            pose.pushPose();
            for (ItemStack stack : ingredients) {
                if (stack != null) {
                    RenderSystem.enableBlend();
                    SewingMachineScreen.renderItem(pose, left + 10, relativeY, 15, 15, 15, stack);
                    Screen.drawString(pose,Minecraft.getInstance().font, String.valueOf(stack.getCount()), left + 20, relativeY, SewingMachineRecipe.hasEnough(stack,Minecraft.getInstance().player) ? Color.WHITE.hashCode() : Color.RED.hashCode());
                    RenderSystem.disableBlend();
                }
                relativeY += 20;
            }
            pose.popPose();
        }


        @Override
        public NarrationPriority narrationPriority() {
            return NarrationPriority.NONE;
        }

        @Override
        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }
}
