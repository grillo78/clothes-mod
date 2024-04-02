package grillo78.clothes_mod.common.items;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ClothesItemGroup  extends ItemGroup {
    public static final ItemGroup INSTANCE = new ClothesItemGroup(ClothesMod.MOD_ID);
    public ClothesItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.ANCHOR_SHIRT);
    }
}
