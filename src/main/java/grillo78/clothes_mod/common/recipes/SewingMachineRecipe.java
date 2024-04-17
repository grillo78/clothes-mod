package grillo78.clothes_mod.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import grillo78.clothes_mod.common.block_entities.SewingMachineBlockEntity;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class SewingMachineRecipe implements Recipe<SewingMachineBlockEntity> {
    private ResourceLocation id;
    private Item[] ingredients;
    private Item result;
    private int resultAmount;
    private int width = 3;
    private int height = 3;
    private int duration = 3;

    public SewingMachineRecipe(ResourceLocation id, Item[] ingredients, Item result, int resultAmount, int duration) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.resultAmount = resultAmount;
        this.duration = duration;
    }


    /**
     * Used to check if a recipe matches current crafting SewingMachineBlockEntity
     */
    public boolean matches(SewingMachineBlockEntity pInv, Level pLevel) {
        boolean match = true;
        for (int i = 0; i < 9; i++) {
            if(pInv.getItem(i).getItem() != ingredients[i])
                match = false;
        }

        return match;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public ItemStack assemble(SewingMachineBlockEntity pContainer, RegistryAccess pRegistryAccess) {
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
            Item ingredients[] = new Item[9];
            JsonArray serializedIngredients = pJson.get("ingredients").getAsJsonArray();
            for (int i = 0; i < 9; i++) {
                ingredients[i] = ForgeRegistries.ITEMS.getValue(new ResourceLocation(serializedIngredients.get(i).getAsString()));
            }
            JsonObject resultObject = pJson.get("result").getAsJsonObject();
            Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultObject.get("item").getAsString()));
            int resultAmount = resultObject.get("count").getAsInt();
            int duration = pJson.has("duration")? pJson.get("duration").getAsInt() : 100;
            return new SewingMachineRecipe(pRecipeId, ingredients, result, resultAmount, duration);
        }

        @Nullable
        @Override
        public SewingMachineRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Item[] ingredients = new Item[9];
            for (int i = 0; i < 9; i++) {
                ingredients[i]=ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf()));
            }
            Item result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(pBuffer.readUtf()));
            int resultAmount = pBuffer.readInt();
            int duration = pBuffer.readInt();
            return new SewingMachineRecipe(pRecipeId,ingredients, result, resultAmount, duration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SewingMachineRecipe pRecipe) {
            for (int i = 0; i < 9; i++) {
                pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.ingredients[i]).toString());
            }
            pBuffer.writeUtf(ForgeRegistries.ITEMS.getKey(pRecipe.result).toString());
            pBuffer.writeInt(pRecipe.resultAmount);
            pBuffer.writeInt(pRecipe.duration);

        }
    }
}
