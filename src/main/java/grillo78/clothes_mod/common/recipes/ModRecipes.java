package grillo78.clothes_mod.common.recipes;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static class Serializers {
        public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ClothesMod.MOD_ID);

        public static IRecipeSerializer<?> SEWING_MACHINE = register("sewing_machine", new SewingMachineRecipe.Serializer());

        private static IRecipeSerializer register(String name, IRecipeSerializer serializer){
            SERIALIZERS.register(name, () -> serializer);
            return serializer;
        }
    }

    public static class Types {
        public static final IRecipeType<SewingMachineRecipe> SEWING_MACHINE_RECIPE = registerType(new ResourceLocation(ClothesMod.MOD_ID, "meat_grinding"));

        private static <T extends IRecipe<?>> IRecipeType<T> registerType(ResourceLocation typeName) {
            return IRecipeType.register(typeName.toString());
        }
    }
}
