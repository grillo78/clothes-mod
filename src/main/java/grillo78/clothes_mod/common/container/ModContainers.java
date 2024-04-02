package grillo78.clothes_mod.common.container;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ClothesMod.MOD_ID);

    public static final ContainerType<InventoryContainer> INVENTORY_CONTAINER = register("inventory", IForgeContainerType.create(((windowId, inv, data) -> new InventoryContainer(ModContainers.INVENTORY_CONTAINER, windowId, inv.player))));

    private static <T extends ContainerType<?>> T register(String name, T containerType) {
        CONTAINERS.register(name, () -> containerType);
        return containerType;
    }
}
