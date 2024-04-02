package grillo78.clothes_mod.client.datagen;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(ModItems.GREEN_CAP);
        singleTexture(ModItems.RED_WHITE_CAP);
        singleTexture(ModItems.GLASSES);
        singleTexture(ModItems.ANCHOR_SHIRT);
        singleTexture(ModItems.DINOSAUR_SHIRT);
        singleTexture(ModItems.PIZZA_SHIRT);
        singleTexture(ModItems.PLANET_SHIRT);
        singleTexture(ModItems.SKULL_SHIRT);
        singleTexture(ModItems.SMILE_SHIRT);
        singleTexture(ModItems.COOKIE_SHIRT);
        singleTexture(ModItems.BROWN_SHORTS);
        singleTexture(ModItems.GRAY_PANTS);
        singleTexture(ModItems.DARK_BLUE_SHORTS);
        singleTexture(ModItems.DARK_BLUE_JEANS);
        singleTexture(ModItems.BLUE_JEANS);
        singleTexture(ModItems.LIGHT_GRAY_JEANS);
        singleTexture(ModItems.PURPLE_JEANS);
        singleTexture(ModItems.BLUE_SHOES);
        singleTexture(ModItems.GREEN_SHOES);
        singleTexture(ModItems.DARK_BLUE_SANDALS);
        singleTexture(ModItems.LIGHT_BLUE_SANDALS);
        singleTexture(ModItems.BLACK_SHOES);
        singleTexture(ModItems.GRAY_SANDALS);
        singleTexture(ModItems.DARK_CYAN_SHOES);
    }

    private void singleTexture(Item item) {
        withExistingParent(item.getRegistryName().toString(),new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(ClothesMod.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }
}
