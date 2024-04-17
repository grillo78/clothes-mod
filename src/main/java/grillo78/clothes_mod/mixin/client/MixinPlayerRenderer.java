package grillo78.clothes_mod.mixin.client;

import grillo78.clothes_mod.client.entity.ClothesLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer {

    public MixinPlayerRenderer(EntityRendererProvider.Context pContext, EntityModel pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "<init>", at = @At("RETURN"))
    public void shouldRenderAtSqrDistance(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci){
        this.addLayer(new ClothesLayer(this));
    }
}
