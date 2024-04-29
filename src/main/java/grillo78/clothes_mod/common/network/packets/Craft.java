package grillo78.clothes_mod.common.network.packets;

import grillo78.clothes_mod.common.network.IMessage;
import grillo78.clothes_mod.common.recipes.SewingMachineRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class Craft implements IMessage<Craft> {

    private ResourceLocation recipe;

    public Craft() {
    }

    public Craft(ResourceLocation recipe) {
        this.recipe = recipe;
    }

    @Override
    public void encode(Craft message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.recipe);
    }


    @Override
    public Craft decode(FriendlyByteBuf buffer) {
        return new Craft(buffer.readResourceLocation());
    }

    @Override
    public void handle(Craft message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Optional<? extends Recipe<?>> recipe = supplier.get().getSender().level().getRecipeManager().byKey(message.recipe);
            if (recipe.isPresent() && recipe.get() instanceof SewingMachineRecipe) {
                SewingMachineRecipe sewingRecipe = (SewingMachineRecipe) recipe.get();
                boolean invalidateCraft = false;
                for (int i = 0; i < sewingRecipe.getIngredientsItems().size() && !invalidateCraft; i++) {
                    if (!SewingMachineRecipe.hasEnough(new ItemStack(sewingRecipe.getIngredientsItems().get(i), sewingRecipe.getIngredientsAmount().get(i)), supplier.get().getSender()))
                        invalidateCraft = true;
                }
                if (!invalidateCraft) {
                    for (int j = 0; j < sewingRecipe.getIngredientsItems().size(); j++) {
                        int remainingItems = sewingRecipe.getIngredientsAmount().get(j);

                        for (int i = 0; i < supplier.get().getSender().getInventory().getContainerSize() && sewingRecipe.getIngredientsAmount().get(j) != 0; i++) {
                            if (supplier.get().getSender().getInventory().getItem(i).getItem() == sewingRecipe.getIngredientsItems().get(j)) {
                                if (supplier.get().getSender().getInventory().getItem(i).getCount() >= remainingItems) {
                                    supplier.get().getSender().getInventory().removeItem(i, remainingItems);
                                    remainingItems = 0;
                                } else {
                                    remainingItems -= supplier.get().getSender().getInventory().getItem(i).getCount();
                                    supplier.get().getSender().getInventory().removeItem(i, supplier.get().getSender().getInventory().getItem(i).getCount());
                                }
                            }
                        }
                    }
                    supplier.get().getSender().drop(new ItemStack(sewingRecipe.getResult()),false, false);
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
