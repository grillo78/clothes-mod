package grillo78.clothes_mod.common.block_entities;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.blocks.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntities {
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ClothesMod.MOD_ID);

    public static final TileEntityType<SewingMachineBlockEntity> SEWING_MACHINE = register("sewing_machine",TileEntityType.Builder.of(SewingMachineBlockEntity::new, ModBlocks.SEWING_MACHINE).build(null));

    private static <T extends TileEntity> TileEntityType<T> register(String name, TileEntityType<T> type){
        BLOCK_ENTITIES.register(name, ()->type);
        return type;
    }
}
