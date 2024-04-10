package grillo78.clothes_mod.common.block_entities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SewingMachineBlockEntity extends TileEntity implements ITickableTileEntity {

    private IItemHandler inventory = new ItemStackHandler(10);

    public SewingMachineBlockEntity() {
        super(ModBlockEntities.SEWING_MACHINE);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, nbt.get("inventory"));
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public void tick() {

    }
}
