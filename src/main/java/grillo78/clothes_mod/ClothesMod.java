package grillo78.clothes_mod;

import grillo78.clothes_mod.client.KeyBinds;
import grillo78.clothes_mod.client.datagen.ModItemModelProvider;
import grillo78.clothes_mod.client.entity.ClothesLayer;
import grillo78.clothes_mod.client.screen.InventoryScreen;
import grillo78.clothes_mod.client.screen.SewingMachineScreen;
import grillo78.clothes_mod.common.blocks.ModBlocks;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.container.ModContainers;
import grillo78.clothes_mod.common.items.ModItems;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.OpenInventory;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ClothesMod.MOD_ID)
public class ClothesMod {
    public static final String MOD_ID = "clothes_mod";

    public ClothesMod() {
        MinecraftForge.EVENT_BUS.register(new SpecialRuntimeEvents());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityEnterWorld);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClone);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);

        ModContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipes.Serializers.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            MinecraftForge.EVENT_BUS.addListener(this::keyPressed);
        });
    }

    public void gatherData(GatherDataEvent event){
        event.getGenerator().addProvider(new ModItemModelProvider(event.getGenerator(), MOD_ID, event.getExistingFileHelper()));
    }

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void onEntityEnterWorld(final PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap -> {
            cap.syncToAll(event.getEntity().level);
        });
    }

    private void onPlayerClone(final PlayerEvent.Clone event) {
        event.getOriginal().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(h ->
                event.getPlayer().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(c -> c.readNBT(h.writeNBT()))
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void doClientStuff(FMLClientSetupEvent event) {
        KeyBinds.registerKeys();
        event.getMinecraftSupplier().get().getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
            renderer.addLayer(new ClothesLayer(renderer));
        });
        ScreenManager.register(ModContainers.INVENTORY_CONTAINER, InventoryScreen::new);
    }

    @OnlyIn(Dist.CLIENT)
    public void keyPressed(InputEvent.KeyInputEvent event) {
        if (KeyBinds.OPEN_INVENTORY.isDown() && event.getAction() == GLFW.GLFW_PRESS) {
            PacketHandler.INSTANCE.sendToServer(new OpenInventory());
        }
    }

    public static class SpecialRuntimeEvents {

        @SubscribeEvent
        public void playerCapabilitiesInjection(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(new ResourceLocation(MOD_ID, "clothes"), new ClothesProvider());
            }
        }
    }
}
