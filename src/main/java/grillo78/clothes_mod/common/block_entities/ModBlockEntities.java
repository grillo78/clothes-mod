package grillo78.clothes_mod.common.block_entities;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ClothesMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<SewingMachineBlockEntity>> SEWING_MACHINE = register("sewing_machine", ()->BlockEntityType.Builder.of(SewingMachineBlockEntity::new, ModBlocks.SEWING_MACHINE.get()).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type){
        return BLOCK_ENTITIES.register(name, type);
    }
}
