package grillo78.clothes_mod.common.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ClothesProvider implements ICapabilityProvider, INBTSerializable {

    @CapabilityInject(IClothesInvWrapper.class)
    public static final EntityCapability<IClothesInvWrapper> CLOTHES_INVENTORY = null;
    private final LazyOptional<IClothesInvWrapper> inventory;

    public ClothesProvider(PlayerEntity player) {
        this.inventory = LazyOptional.of(() -> new ClothesInvWrapper(player));
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
                .orElseGet(CompoundNBT::new);
        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        inventory.ifPresent(items -> items.readNBT(nbt));
    }
}
