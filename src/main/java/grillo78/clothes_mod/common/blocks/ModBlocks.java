package grillo78.clothes_mod.common.blocks;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.items.ClothesItemGroup;
import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ClothesMod.MOD_ID);

    public static Block SEWING_MACHINE = register("sewing_machine", new SewingMachineBlock(AbstractBlock.Properties.of(Material.METAL).strength(5.0F, 6.0F).sound(SoundType.METAL)));

    private static <T extends Block> T register(String name, T block) {
        BLOCKS.register(name, () -> block);
        ModItems.register(name, new BlockItem(block, new Item.Properties().tab(ClothesItemGroup.INSTANCE)));
        return block;
    }
}
