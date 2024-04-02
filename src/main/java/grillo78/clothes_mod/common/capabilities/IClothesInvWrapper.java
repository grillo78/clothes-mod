package grillo78.clothes_mod.common.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public interface IClothesInvWrapper {

    IItemHandler getInventory();

    void syncToAll(World level);

    INBT writeNBT();

    void readNBT(INBT compound);
}
