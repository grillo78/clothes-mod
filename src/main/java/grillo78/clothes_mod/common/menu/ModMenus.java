package grillo78.clothes_mod.common.menu;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.block_entities.SewingMachineBlockEntity;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ClothesMod.MOD_ID);

    public static final MenuType<InventoryMenu> INVENTORY_CONTAINER = register("inventory", new MenuType<>((windowId, playerInventory) -> new InventoryMenu(ModMenus.INVENTORY_CONTAINER, windowId, playerInventory.player), FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<SewingMachineMenu> SEWING_CONTAINER = register("sewing", new MenuType<>((IContainerFactory<SewingMachineMenu>)(windowId, playerInventory, data) -> {
        return new SewingMachineMenu(ModMenus.SEWING_CONTAINER, windowId, playerInventory,(SewingMachineBlockEntity) playerInventory.player.level().getBlockEntity(data.readBlockPos()));
    }, FeatureFlags.DEFAULT_FLAGS));

    private static <T extends MenuType<?>> T register(String name, T containerType) {
        CONTAINERS.register(name, () -> containerType);
        return containerType;
    }
}
