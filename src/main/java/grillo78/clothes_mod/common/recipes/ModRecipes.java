package grillo78.clothes_mod.common.recipes;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static class Serializers {
        public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ClothesMod.MOD_ID);

        public static RecipeSerializer<?> SEWING_MACHINE = register("sewing_machine", new SewingMachineRecipe.Serializer());

        private static RecipeSerializer register(String name, RecipeSerializer serializer){
            SERIALIZERS.register(name, () -> serializer);
            return serializer;
        }
    }

    public static class Types {
        public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ClothesMod.MOD_ID);
        public static final RegistryObject<RecipeType<SewingMachineRecipe>> SEWING_MACHINE_RECIPE = registerType("sewing_machine");

        private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerType(String typeName) {
            return TYPES.register(typeName, ()->new RecipeType<T>() {
                @Override
                public String toString() {
                    return ClothesMod.MOD_ID + ":" + typeName;
                }
            });
        }
    }
}
