package grillo78.clothes_mod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class ClothesLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public ClothesLayer(IEntityRenderer p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, AbstractClientPlayerEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pLivingEntity.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(inv -> {
            for (int i = 0; i < inv.getInventory().getSlots(); i++) {
                if (inv.getInventory().getStackInSlot(i).getItem() instanceof Cloth)
                    ((Cloth) inv.getInventory().getStackInSlot(i).getItem()).renderCloth(pMatrixStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch, getParentModel());
            }
        });
    }
}
