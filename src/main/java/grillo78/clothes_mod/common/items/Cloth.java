package grillo78.clothes_mod.common.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface Cloth {

    ClothesSlot getSlot();

    @OnlyIn(Dist.CLIENT)
    void renderCloth(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, Entity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, PlayerModel parentModel);

    default boolean canDropOnDeath(PlayerEntity player, ItemStack stack){
        return true;
    }
}
