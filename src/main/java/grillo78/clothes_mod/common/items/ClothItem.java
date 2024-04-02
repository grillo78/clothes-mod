package grillo78.clothes_mod.common.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClothItem extends Item implements Cloth {

    private final ClothesSlot slot;

    public ClothItem(Properties pProperties, ClothesSlot slot) {
        super(pProperties.stacksTo(1));
        this.slot = slot;
    }

    @Override
    public ClothesSlot getSlot() {
        return slot;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderCloth(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, Entity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel bipedModel) {
        PlayerModel model = new PlayerModel(0.08F, false);
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

        model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTexture())), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    protected ResourceLocation getTexture() {
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/entity/clothes/" + getRegistryName().getPath() + ".png");
    }
}
