package grillo78.clothes_mod.common.capabilities;

import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public interface IClothesInvWrapper {

    IItemHandler getInventory();

    void syncToAll(Level level);

    Tag writeNBT();

    void readNBT(Tag compound);
}
