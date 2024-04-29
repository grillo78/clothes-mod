package grillo78.clothes_mod.common.menu;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ClothesMod.MOD_ID);

    public static final MenuType<InventoryMenu> INVENTORY_CONTAINER = register("inventory", new MenuType<>((windowId, playerInventory) -> new InventoryMenu(ModMenus.INVENTORY_CONTAINER, windowId, playerInventory.player)));


    private static <T extends MenuType<?>> T register(String name, T containerType) {
        CONTAINERS.register(name, () -> containerType);
        return containerType;
    }
}
