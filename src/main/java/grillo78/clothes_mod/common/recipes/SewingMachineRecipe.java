package grillo78.clothes_mod.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.PingResult;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SewingMachineRecipe implements Recipe<Container> {
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
    public boolean matches(Container pInv, Level pLevel) {
        boolean match = true;
        for (int i = 0; i < 9; i++) {
            if(pInv.getItem(i).getItem() != ingredients.get(i))
                match = false;
        }

        return match;
    }

    public static boolean hasEnough(ItemStack stack, Player player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize() && count < stack.getCount(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item.getItem() == stack.getItem())
                count += item.getCount();
        }
        return count>=stack.getCount();
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return new ItemStack(result, resultAmount);
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x <= this.width && y <= this.height;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return new ItemStack(result, resultAmount);
    }

    public Item getResult() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.Serializers.SEWING_MACHINE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.SEWING_MACHINE_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<SewingMachineRecipe> {

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
        public SewingMachineRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            List<Item> ingredients = new ArrayList();
            List<Integer> ingredientsAmount = new ArrayList();
            int size = pBuffer.readInt();
            for (int i = 0; i < size; i++) {
                ingredients.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf())));
                ingredientsAmount.add(pBuffer.readInt());
            }
            Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf()));
            int resultAmount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            return new SewingMachineRecipe(pRecipeId,ingredients, ingredientsAmount, result, resultAmount, duration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SewingMachineRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.ingredients.size());
            for (int i = 0; i < pRecipe.ingredients.size(); i++) {
                System.out.println(ForgeRegistries.ITEMS.getKey(pRecipe.ingredients.get(i)));
                pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.ingredients.get(i)).toString());
                pBuffer.writeInt(pRecipe.ingredientsAmount.get(i));
            }
            pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.result).toString());
            pBuffer.writeInt(pRecipe.resultAmount);
            pBuffer.writeInt(pRecipe.duration);

        }
    }
}
