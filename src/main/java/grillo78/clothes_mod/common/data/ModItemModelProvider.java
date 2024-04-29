package grillo78.clothes_mod.common.data;

import grillo78.clothes_mod.ClothesMod;
import grillo78.clothes_mod.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ClothesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(registryObject -> {
            Item item = registryObject.get();
//            if (!existingFileHelper.exists(new ResourceLocation(ClothesMod.MOD_ID, "item/" + ForgeRegistries.ITEMS.getKey(item).getPath()), ModelProvider.MODEL)) {
                if (!(item instanceof BlockItem)) {
                    ModelFile itemGenerated = getExistingFile(modLoc( "item/clothes"));
                    getBuilder(ForgeRegistries.ITEMS.getKey(item).getPath()).parent(itemGenerated).texture("0", "entity/clothes/" + ForgeRegistries.ITEMS.getKey(item).getPath());
                }
//            }
        });
    }
}
