package grillo78.clothes_mod.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.player.Player;

public class ClothesLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M>  {


    public ClothesLayer(LivingEntityRenderer<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        pLivingEntity.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(inv -> {
            for (int i = 0; i < inv.getInventory().getSlots(); i++) {
                if (inv.getInventory().getStackInSlot(i).getItem() instanceof Cloth)
                    ((Cloth) inv.getInventory().getStackInSlot(i).getItem()).renderCloth(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick, pAgeInTicks, pNetHeadYaw, pHeadPitch, getParentModel());
            }
        });
    }
}
