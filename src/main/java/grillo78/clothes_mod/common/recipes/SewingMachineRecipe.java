package grillo78.clothes_mod.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SewingMachineRecipe implements IRecipe<IInventory> {
    private ResourceLocation id;
    private List<Item> ingredients;
    private List<Integer> ingredientsAmount;
    private Item result;
    private int resultAmount;
    private int width = 3;
    private int height = 3;
    private int duration = 3;

    public SewingMachineRecipe(ResourceLocation id, List<Item> ingredients, List<Integer> ingredientsAmount, Item result, int resultAmount, int duration) {
        this.id = id;
        this.ingredients = ingredients;
        this.ingredientsAmount = ingredientsAmount;
        this.result = result;
        this.resultAmount = resultAmount;
        this.duration = duration;
    }

    public List<Item> getIngredientsItems() {
        return ingredients;
    }

    public List<Integer> getIngredientsAmount() {
        return ingredientsAmount;
    }

    /**
     * Used to check if a recipe matches current crafting SewingMachineBlockEntity
     */
    public boolean matches(IInventory pInv, World pLevel) {
        boolean match = true;
        for (int i = 0; i < 9; i++) {
            if(pInv.getItem(i).getItem() != ingredients.get(i))
                match = false;
        }

        return match;
    }

    public static boolean hasEnough(ItemStack stack, PlayerEntity player) {
        int count = 0;
        for (int i = 0; i < player.inventory.getContainerSize() && count < stack.getCount(); i++) {
            ItemStack item = player.inventory.getItem(i);
            if(item.getItem() == stack.getItem())
                count += item.getCount();
        }
        return count>=stack.getCount();
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public ItemStack assemble(IInventory pIInventory) {
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
            List<Item> ingredients = new ArrayList();
            List<Integer> ingredientsAmount = new ArrayList();
            JsonArray serializedIngredients = pJson.get("ingredients").getAsJsonArray();
            for (int i = 0; i < serializedIngredients.size(); i++) {
                ingredients.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(serializedIngredients.get(i).getAsJsonObject().get("item").getAsString())));
                ingredientsAmount.add(serializedIngredients.get(i).getAsJsonObject().get("amount").getAsInt());
            }
            JsonObject resultObject = pJson.get("result").getAsJsonObject();
            Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultObject.get("item").getAsString()));
            int resultAmount = resultObject.get("count").getAsInt();
            int duration = pJson.has("duration")? pJson.get("duration").getAsInt() : 100;
            return new SewingMachineRecipe(pRecipeId, ingredients, ingredientsAmount, result, resultAmount, duration);
        }

        @Nullable
        @Override
        public SewingMachineRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            List<Item> ingredients = new ArrayList();
            List<Integer> ingredientsAmount = new ArrayList();
            for (int i = 0; i < pBuffer.readInt(); i++) {
                ingredients.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf())));
                ingredientsAmount.add(pBuffer.readInt());
            }
            Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf()));
            int resultAmount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            return new SewingMachineRecipe(pRecipeId,ingredients, ingredientsAmount, result, resultAmount, duration);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, SewingMachineRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.ingredients.size());
            for (int i = 0; i < pRecipe.ingredients.size(); i++) {
                pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.ingredients.get(i)).toString());
                pBuffer.writeInt(pRecipe.ingredientsAmount.get(i));
            }
            pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.result).toString());
            pBuffer.writeInt(pRecipe.resultAmount);
            pBuffer.writeInt(pRecipe.duration);

        }
    }
}