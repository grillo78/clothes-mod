package grillo78.clothes_mod.common.container.slots;

import grillo78.clothes_mod.common.items.Cloth;
import grillo78.clothes_mod.common.items.ClothesSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ClothSlot extends SlotItemHandler {

    private ClothesSlot slot;

    public ClothSlot(IItemHandler pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY);
        this.slot = ClothesSlot.getFromID(pIndex);
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return pStack.getItem() instanceof Cloth && ((Cloth) pStack.getItem()).getSlot() == slot;
    }
}
