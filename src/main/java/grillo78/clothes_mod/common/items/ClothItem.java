package grillo78.clothes_mod.common.items;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.client.ModModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class ClothItem extends Item implements Cloth {

    private final ClothesSlot slot;

    public ClothItem(Item.Properties pProperties, ClothesSlot slot) {
        super(pProperties.stacksTo(1));
        this.slot = slot;
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
    public void renderCloth(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LocalPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel bipedModel) {
        PlayerModel model = new PlayerModel(getModelPart(), false);
        model.head.copyFrom(bipedModel.head);
        model.hat.copyFrom(bipedModel.hat);
        model.body.copyFrom(bipedModel.body);
        model.jacket.copyFrom(bipedModel.jacket);
        model.leftArm.copyFrom(bipedModel.leftArm);
        model.leftSleeve.copyFrom(bipedModel.leftSleeve);
        model.rightArm.copyFrom(bipedModel.rightArm);
        model.rightSleeve.copyFrom(bipedModel.rightSleeve);
        model.leftLeg.copyFrom(bipedModel.leftLeg);
        model.leftPants.copyFrom(bipedModel.leftPants);
        model.rightLeg.copyFrom(bipedModel.rightLeg);
        model.rightPants.copyFrom(bipedModel.rightPants);

        model.young = false;

        model.head.copyFrom(bipedModel.head);
        model.hat.copyFrom(bipedModel.hat);
        model.body.copyFrom(bipedModel.body);
        model.jacket.copyFrom(bipedModel.jacket);
        model.leftArm.copyFrom(bipedModel.leftArm);
        model.leftSleeve.copyFrom(bipedModel.leftSleeve);
        model.rightArm.copyFrom(bipedModel.rightArm);
        model.rightSleeve.copyFrom(bipedModel.rightSleeve);
        model.leftLeg.copyFrom(bipedModel.leftLeg);
        model.leftPants.copyFrom(bipedModel.leftPants);
        model.rightLeg.copyFrom(bipedModel.rightLeg);
        model.rightPants.copyFrom(bipedModel.rightPants);

        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture())), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    protected ResourceLocation getTexture() {
        return new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "textures/entity/clothes/" + ForgeRegistries.ITEMS.getKey(this).getPath() + ".png");
    }
}
