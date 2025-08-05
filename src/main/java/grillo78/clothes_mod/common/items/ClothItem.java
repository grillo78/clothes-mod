package grillo78.clothes_mod.common.items;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.client.ModModelLayers;
import grillo78.clothes_mod.client.event.PreRenderCloth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;

public class ClothItem extends Item implements Cloth {

    private final ClothesSlot slot;
    private boolean hasMask;
    private ResourceLocation alphaMask;
    private ResourceLocation slimAlphaMask;

    public ClothItem(Item.Properties pProperties, ClothesSlot slot) {
        this(pProperties.stacksTo(1), slot, false, null);
    }

    public ClothItem(Item.Properties pProperties, ClothesSlot slot, boolean hasMask, ResourceLocation alphaMask) {
        super(pProperties);
        this.slot = slot;
        this.hasMask = hasMask;
        this.alphaMask = alphaMask;
        if(hasMask)
            slimAlphaMask = new ResourceLocation(alphaMask.getNamespace(), alphaMask.getPath().replace("_alphamask.png", "_slim_alphamask.png"));
    }

    @Override
    public ClothesSlot getSlot() {
        return slot;
    }

    @OnlyIn(Dist.CLIENT)
    public static ModelPart getModelPart() {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        return modelSet.bakeLayer(ModModelLayers.CLOTHES);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderCloth(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Player player, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel bipedModel) {
        PlayerModel model = new PlayerModel(getModelPart(), false);
        setModelProperties(player, model);
        copyFrom(model.head, bipedModel.head);
        copyFrom(model.hat, bipedModel.hat);
        copyFrom(model.body, bipedModel.body);
        copyFrom(model.jacket, bipedModel.jacket);
        copyFrom(model.leftArm, bipedModel.leftArm);
        copyFrom(model.leftSleeve, bipedModel.leftSleeve);
        copyFrom(model.rightArm, bipedModel.rightArm);
        copyFrom(model.rightSleeve, bipedModel.rightSleeve);
        copyFrom(model.leftLeg, bipedModel.leftLeg);
        copyFrom(model.leftPants, bipedModel.leftPants);
        copyFrom(model.rightLeg, bipedModel.rightLeg);
        copyFrom(model.rightPants, bipedModel.rightPants);
        model.young = bipedModel.young;
        MinecraftForge.EVENT_BUS.post(new PreRenderCloth(model, player, bipedModel));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture())), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void setModelProperties(Player pClientPlayer, PlayerModel model) {
        if (pClientPlayer.isSpectator()) {
            model.setAllVisible(false);
            model.head.visible = true;
            model.hat.visible = true;
        } else {
            model.setAllVisible(true);
            model.hat.visible = pClientPlayer.isModelPartShown(PlayerModelPart.HAT);
            model.jacket.visible = pClientPlayer.isModelPartShown(PlayerModelPart.JACKET);
            model.leftPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
            model.rightPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
            model.leftSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
            model.rightSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
            model.crouching = pClientPlayer.isCrouching();
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void copyFrom(ModelPart newBone, ModelPart oldBone) {
        newBone.copyFrom(oldBone);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean haveSmallArms(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            return ((AbstractClientPlayer) entity).getModelName().equalsIgnoreCase("slim");
        }
        return false;
    }

    public ResourceLocation getAlphaMask(Player player) {
        return hasMask ? (alphaMask != null ? (haveSmallArms(player)? slimAlphaMask :alphaMask) : new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "textures/entity/clothes/" + ForgeRegistries.ITEMS.getKey(this).getPath() + (haveSmallArms(player) ? "_slim_alphamask.png" : "_alphamask.png"))) : null;
    }

    protected ResourceLocation getTexture() {
        return new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "textures/entity/clothes/" + ForgeRegistries.ITEMS.getKey(this).getPath() + ".png");
    }
}
