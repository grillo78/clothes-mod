package grillo78.clothes_mod.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

public class ClothesProvider implements ICapabilityProvider, ICapabilitySerializable {

    public static final Capability<IClothesInvWrapper> CLOTHES_INVENTORY = CapabilityManager.get(new CapabilityToken<>(){});
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
    public Tag serializeNBT() {
        Tag nbt = inventory.map(items -> items.writeNBT())
                .orElseGet(CompoundTag::new);
        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        inventory.ifPresent(items -> items.readNBT(nbt));
    }
}
