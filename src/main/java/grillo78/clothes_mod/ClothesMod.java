package grillo78.clothes_mod;

import grillo78.clothes_mod.client.KeyBinds;
import grillo78.clothes_mod.client.ModModelLayers;
import grillo78.clothes_mod.client.entity.ClothesLayer;
import grillo78.clothes_mod.client.screen.InventoryScreen;
import grillo78.clothes_mod.common.blocks.ModBlocks;
import grillo78.clothes_mod.common.capabilities.ClothesProvider;
import grillo78.clothes_mod.common.items.Cloth;
import grillo78.clothes_mod.common.items.ModItems;
import grillo78.clothes_mod.common.menu.ModMenus;
import grillo78.clothes_mod.common.network.PacketHandler;
import grillo78.clothes_mod.common.network.packets.OpenInventory;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.RegisterEvent;
import org.lwjgl.glfw.GLFW;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ClothesMod.MOD_ID)
public class ClothesMod {
    public static final String MOD_ID = "clothes_mod";
    private static final ResourceKey<CreativeModeTab> CLOTHES = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID, "tab"));

    public ClothesMod(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(new SpecialRuntimeEvents());

        context.getModEventBus().addListener(this::setup);
        context.getModEventBus().addListener(this::registerTabs);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityEnterWorld);
        MinecraftForge.EVENT_BUS.addListener(this::onStartTracking);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerDeath);

        ModMenus.CONTAINERS.register(context.getModEventBus());
        ModItems.ITEMS.register(context.getModEventBus());
        ModBlocks.BLOCKS.register(context.getModEventBus());
        ModRecipes.Serializers.SERIALIZERS.register(context.getModEventBus());
        ModRecipes.Types.TYPES.register(context.getModEventBus());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            context.getModEventBus().addListener(this::doClientStuff);
            context.getModEventBus().addListener(this::registerKey);
            context.getModEventBus().addListener(this::addLayers);
            context.getModEventBus().addListener(this::registerLayerDefinitions);
            MinecraftForge.EVENT_BUS.addListener(this::keyPressed);
        });
    }

    public void registerTabs(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(CLOTHES, CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SEWING_MACHINE.get()))
                    .title(Component.literal("Clothes"))
                    .displayItems((params, output) -> {
                        ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(new ItemStack(itemRegistryObject.get())));
                    })
                    .build());

        });
    }

    public void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide)
            event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap -> {
                cap.syncToAll(event.getEntity().level());
            });
    }

    private void onEntityEnterWorld(EntityJoinLevelEvent event) {
        if (!event.getEntity().level().isClientSide)
            event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap -> {
                cap.syncToAll(event.getEntity().level());
            });
    }

    private void onStartTracking(PlayerEvent.StartTracking event) {
        if (!event.getEntity().level().isClientSide)
            event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(cap -> {
                cap.syncToAll(event.getEntity().level());
            });
    }

    private void onPlayerClone(final PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(h ->
                event.getEntity().getCapability(ClothesProvider.CLOTHES_INVENTORY).ifPresent(c -> {
                    c.readNBT(h.writeNBT());
                    c.syncToAll(event.getEntity().level());
                })
        );
        event.getOriginal().invalidateCaps();
    }

    public void onPlayerDeath(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
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
    private void addLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
            ((PlayerRenderer) renderer).addLayer(new ClothesLayer((PlayerRenderer) renderer));
        });
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
                event.addCapability(new ResourceLocation(MOD_ID, "clothes"), new ClothesProvider((Player) event.getObject()));
            }
        }
    }
}
