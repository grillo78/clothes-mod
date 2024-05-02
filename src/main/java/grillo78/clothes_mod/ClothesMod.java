package grillo78.clothes_mod;

import grillo78.clothes_mod.client.KeyBinds;
import grillo78.clothes_mod.client.ModModelLayers;
import grillo78.clothes_mod.client.screen.InventoryScreen;
import grillo78.clothes_mod.common.blocks.ModBlocks;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import grillo78.clothes_mod.common.items.ModItems;
import grillo78.clothes_mod.common.menu.ModMenus;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.OpenInventory;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ClothesMod.MOD_ID)
public class ClothesMod {
    public static final String MOD_ID = "clothes_mod";
    public static final CreativeModeTab TAB = new CreativeModeTab("clothes"){
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.SEWING_MACHINE.get());
        }
    };

    public ClothesMod() {
        MinecraftForge.EVENT_BUS.register(new SpecialRuntimeEvents());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityEnterWorld);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerDeath);

        ModMenus.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipes.Serializers.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipes.Types.TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKey);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayerDefinitions);
            MinecraftForge.EVENT_BUS.addListener(this::keyPressed);
        });
    }

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void onEntityEnterWorld(final PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap -> {
            cap.syncToAll(event.getEntity().level);
        });
    }

    private void onPlayerClone(final PlayerEvent.Clone event) {
        event.getOriginal().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(h ->
                event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(c -> c.readNBT(h.writeNBT()))
        );
    }

    public void onPlayerDeath(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            Player player = (Player) event.getEntity();
            player.getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(a -> {
                IItemHandler inventory = a.getInventory();
                for (int i = 0; i < inventory.getSlots(); ++i) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof Cloth && ((Cloth) stack.getItem()).canDropOnDeath(player, stack) == true) {
                        player.drop(stack, true, true);
                        inventory.insertItem(i, ItemStack.EMPTY, false);
                    }
                }
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CLOTHES, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.01F), false), 64, 64));
    }

    @OnlyIn(Dist.CLIENT)
    public void registerKey(RegisterKeyMappingsEvent event) {
        KeyBinds.registerKeys(event);
    }

    @OnlyIn(Dist.CLIENT)
    public void doClientStuff(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenus.INVENTORY_CONTAINER, InventoryScreen::new);
    }

    @OnlyIn(Dist.CLIENT)
    public void keyPressed(InputEvent.Key event) {
        if (KeyBinds.OPEN_INVENTORY.isDown() && event.getAction() == GLFW.GLFW_PRESS) {
            PacketHandler.INSTANCE.sendToServer(new OpenInventory());
        }
    }

    public static class SpecialRuntimeEvents {

        @SubscribeEvent
        public void playerCapabilitiesInjection(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                event.addCapability(new ResourceLocation(MOD_ID, "clothes"), new ClothesProvider());
            }
        }
    }
}
