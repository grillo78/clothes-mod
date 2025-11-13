package grillo78.clothes_mod.common.blocks;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.items.ClothesItemGroup;
import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(ClothesMod.MOD_ID);

    public static Block SEWING_MACHINE = register("sewing_machine", new SewingMachineBlock(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    private static <T extends Block> T register(String name, T block) {
        BLOCKS.register(name, () -> block);
        ModItems.register(name, new BlockItem(block, new Item.Properties()));
        return block;
    }
}
