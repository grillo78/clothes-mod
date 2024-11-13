package grillo78.clothes_mod.mixin.client;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.client.entity.ClothesLayer;
import grillo78.clothes_mod.client.texture.AlphaMaskTexture;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer {

    public MixinPlayerRenderer(EntityRendererProvider.Context pContext, EntityModel pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Inject(method = "getTextureLocation", at = @At("RETURN"), cancellable = true)
    public void getTexture(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> ci){
        List<ResourceLocation> masks = new ArrayList<>();
        AtomicReference<String> append = new AtomicReference<>("");
        player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(clothes->{
            for (int i = 0; i < clothes.getInventory().getSlots(); i++) {
                Item item = clothes.getInventory().getStackInSlot(i).getItem();
                if(item instanceof Cloth) {
                    masks.add(((Cloth) item).getAlphaMask(player));
                    append.set(append.get() + item.getDescriptionId());
                }
            }
        });
        ci.setReturnValue(AlphaMaskTexture.getTexture(player.getSkinTextureLocation(), new ResourceLocation(ClothesMod.MOD_ID, player.getSkinTextureLocation().getPath() + append), masks));
    }
}
