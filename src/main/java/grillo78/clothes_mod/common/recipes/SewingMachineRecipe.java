package grillo78.clothes_mod.common.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class SewingMachineRecipe implements IRecipe<IInventory> {
    private ResourceLocation id;
    private Item[] ingredients;
    private Item result;
    private int resultAmount;
    private int width = 3;
    private int height = 3;

    public SewingMachineRecipe(ResourceLocation id, Item[] ingredients, Item result, int resultAmount) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.resultAmount = resultAmount;
    }


    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(IInventory pInv, World pLevel) {
        boolean match = true;

        for(int i = 0; i < this.width; ++i) {
            for (int j = 0; j < this.height; j++) {
                if(ingredients [j + i * 3] !=pInv.getItem(j + i * 3).getItem())
                    match = false;
            }
        }

        return match;
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return new ItemStack(result, resultAmount);
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x <= this.width && y <= this.height;
    }

    @Override
    public ItemStack getResultItem() {
        return new ItemStack(result, resultAmount);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.Serializers.SEWING_MACHINE;
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.SEWING_MACHINE_RECIPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SewingMachineRecipe> {

        @Override
        public SewingMachineRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            return null;
        }

        @Nullable
        @Override
        public SewingMachineRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            return null;
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, SewingMachineRecipe pRecipe) {

        }
    }
}
