package grillo78.clothes_mod.client.datagen;

import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(registryObject -> {
            Item item = registryObject.get();
//            if (!existingFileHelper.exists(new ResourceLocation(ClothesMod.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath()), ModelProvider.MODEL)) {
            if (!(item instanceof BlockItem)) {
                ModelFile itemGenerated = getExistingFile(modLoc( "item/clothes"));
                getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).parent(itemGenerated).texture("0", "entity/clothes/" + BuiltInRegistries.ITEM.getKey(item).getPath());
            }
//            }
        });
    }
}
