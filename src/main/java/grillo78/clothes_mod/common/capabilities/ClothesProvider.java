package grillo78.clothes_mod.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ClothesProvider implements ICapabilityProvider, ICapabilitySerializable {

    @CapabilityInject(IClothesInvWrapper.class)
    public static final Capability<IClothesInvWrapper> CLOTHES_INVENTORY = null;
    private final LazyOptional<IClothesInvWrapper> inventory;

    public ClothesProvider() {
        this.inventory = LazyOptional.of(() -> new ClothesInvWrapper());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CLOTHES_INVENTORY) {
            return inventory.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        INBT nbt = inventory.map(items -> items.writeNBT())
                .orElseGet(CompoundNBT::new);
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        inventory.ifPresent(items -> items.readNBT(nbt));
    }
}
