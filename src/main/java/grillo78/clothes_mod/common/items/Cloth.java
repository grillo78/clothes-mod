package grillo78.clothes_mod.common.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface Cloth {

    ClothesSlot getSlot();

    @OnlyIn(Dist.CLIENT)
    void renderCloth(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LocalPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel parentModel);
}