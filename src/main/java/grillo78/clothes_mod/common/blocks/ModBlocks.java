package grillo78.clothes_mod.common.blocks;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ClothesMod.MOD_ID);

    public static RegistryObject<Block> SEWING_MACHINE = register("sewing_machine", ()->new SewingMachineBlock(Block.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> blockObject = BLOCKS.register(name, block);
        ModItems.register(name, ()->new BlockItem(blockObject.get(), new Item.Properties()));
        return blockObject;
    }
}
