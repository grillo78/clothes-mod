package grillo78.clothes_mod.common.items;

import grillo78.clothes_mod.ClothesMod;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ClothesMod.MOD_ID);

    public static final ClothItem GLASSES = registerClothes("glasses", ClothesSlot.HEAD);
    public static final ClothItem GREEN_CAP = registerClothes("green_cap", ClothesSlot.HEAD);
    public static final ClothItem RED_WHITE_CAP = registerClothes("red_white_cap", ClothesSlot.HEAD);

    public static final ClothItem ANCHOR_SHIRT = registerClothes("anchor_shirt", ClothesSlot.SHIRT);
    public static final ClothItem DINOSAUR_SHIRT = registerClothes("dinosaur_shirt", ClothesSlot.SHIRT);
    public static final ClothItem PIZZA_SHIRT = registerClothes("pizza_shirt", ClothesSlot.SHIRT);
    public static final ClothItem PLANET_SHIRT = registerClothes("planet_shirt", ClothesSlot.SHIRT);
    public static final ClothItem SKULL_SHIRT = registerClothes("skull_shirt", ClothesSlot.SHIRT);
    public static final ClothItem SMILE_SHIRT = registerClothes("smile_shirt", ClothesSlot.SHIRT);
    public static final ClothItem COOKIE_SHIRT = registerClothes("cookie_shirt", ClothesSlot.SHIRT);

    public static final ClothItem GRAY_SHORTS = registerClothes("gray_shorts", ClothesSlot.PANTS);
    public static final ClothItem GRAY_PANTS = registerClothes("gray_pants", ClothesSlot.PANTS);
    public static final ClothItem DARK_BLUE_SHORTS = registerClothes("black_shorts", ClothesSlot.PANTS);
    public static final ClothItem DARK_BLUE_JEANS = registerClothes("black_jeans", ClothesSlot.PANTS);
    public static final ClothItem BLUE_JEANS = registerClothes("blue_jeans", ClothesSlot.PANTS);
    public static final ClothItem LIGHT_GRAY_SHORTS = registerClothes("light_gray_shorts", ClothesSlot.PANTS);
    public static final ClothItem PURPLE_JEANS = registerClothes("purple_pants", ClothesSlot.PANTS);

    public static final ClothItem BLUE_SHOES = registerClothes("blue_shoes", ClothesSlot.SHOES);
    public static final ClothItem GREEN_SHOES = registerClothes("green_shoes", ClothesSlot.SHOES);
    public static final ClothItem DARK_BLUE_SANDALS = registerClothes("black_sandals", ClothesSlot.SHOES);
    public static final ClothItem LIGHT_BLUE_SANDALS = registerClothes("light_blue_shoes", ClothesSlot.SHOES);
    public static final ClothItem BLACK_SHOES = registerClothes("black_shoes", ClothesSlot.SHOES);
    public static final ClothItem GRAY_SANDALS = registerClothes("gray_sandals", ClothesSlot.SHOES);
    public static final ClothItem DARK_CYAN_SHOES = registerClothes("cyan_shoes", ClothesSlot.SHOES);

    private static ClothItem registerClothes(String name, ClothesSlot slot){
        return register(name, new ClothItem(new Item.Properties().tab(ClothesItemGroup.INSTANCE), slot));
    }

    public static <T extends Item> T register(String name, T itemType) {
        ITEMS.register(name, () -> itemType);
        return itemType;
    }
}
