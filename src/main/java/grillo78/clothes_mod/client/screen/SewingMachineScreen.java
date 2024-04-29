package grillo78.clothes_mod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.Craft;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import grillo78.clothes_mod.common.recipes.SewingMachineRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
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
    protected StringWidget itemName;
    protected IngredientsList scrollPanel;
    protected List<SewingMachineRecipe> recipes;
    protected ItemStack result;

    public SewingMachineScreen(Component pTitle) {
        super(pTitle);
        recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.Types.SEWING_MACHINE_RECIPE.get());
        result = new ItemStack(recipes.get(itemIndex).getResult());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        addRenderableWidget(prevIndexButton = new Button.Builder(Component.literal("<"), (button) -> {
            if (itemIndex > 0)
                itemIndex--;
            else
                itemIndex = recipes.size() - 1;
            button.setFocused(false);
            itemName.setMessage((result = new ItemStack(recipes.get(itemIndex).getResult())).getHoverName());
            scrollPanel.updateContent(recipes.get(itemIndex));
        }).bounds(leftPos + 10, topPos + 10, 10, 10).build());
        addRenderableWidget(nextIndexButton = new Button.Builder(Component.literal(">"), (button) -> {
            if (itemIndex < recipes.size() - 1)
                itemIndex++;
            else
                itemIndex = 0;
            button.setFocused(false);
            itemName.setMessage((result = new ItemStack(recipes.get(itemIndex).getResult())).getHoverName());
            scrollPanel.updateContent(recipes.get(itemIndex));
        }).bounds(leftPos + 95, topPos + 10, 10, 10).build());
        Component text = result.getHoverName();
        addRenderableWidget(itemName = new StringWidget(leftPos + 52, topPos + 10, 10, 10, text, Minecraft.getInstance().font));
        addRenderableWidget(craftButton = new Button.Builder(Component.translatable("gui.craft"), (button) -> {
            PacketHandler.INSTANCE.sendToServer(new Craft(recipes.get(itemIndex).getId()));
            System.out.println("Send craft packet");
        }).bounds(leftPos + 110, topPos + 10, 50, 10).build());

        scrollPanel = new IngredientsList(Minecraft.getInstance(), 60, imageHeight - 30, topPos + 25, leftPos + imageWidth - 65, recipes.get(itemIndex));
        addRenderableWidget(scrollPanel);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderItem(pGuiGraphics, leftPos + 52, topPos + 83, 115, 115, 115, result);
    }

    private static void renderItem(GuiGraphics pGuiGraphics, double x, double y, float xScale, float yScale, float zScale, ItemStack item) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(x, y, 100);
        pGuiGraphics.pose().scale(xScale, -yScale, zScale);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GUI, 15728880, OverlayTexture.NO_OVERLAY, pGuiGraphics.pose(), pGuiGraphics.bufferSource(), null, 0);
        pGuiGraphics.pose().popPose();
    }

    private void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(SEWING_MACHINE_LOCATION, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
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
        protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
            guiGraphics.pose().pushPose();
            for (ItemStack stack : ingredients) {
                if (stack != null) {
                    RenderSystem.enableBlend();
                    SewingMachineScreen.renderItem(guiGraphics, left + 10, relativeY, 15, 15, 15, stack);
                    guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf(stack.getCount()), left + 20, relativeY, SewingMachineRecipe.hasEnough(stack,Minecraft.getInstance().player) ? Color.WHITE.hashCode() : Color.RED.hashCode());
                    RenderSystem.disableBlend();
                }
                relativeY += 20;
            }
            guiGraphics.pose().popPose();
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
