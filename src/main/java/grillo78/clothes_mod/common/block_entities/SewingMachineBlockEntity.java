package grillo78.clothes_mod.common.block_entities;

import grillo78.clothes_mod.common.menu.ModMenus;
import grillo78.clothes_mod.common.menu.SewingMachineMenu;
import grillo78.clothes_mod.common.recipes.ModRecipes;
import grillo78.clothes_mod.common.recipes.SewingMachineRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SewingMachineBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    protected NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    private SewingMachineRecipe currentRecipe = null;
    private final RecipeType<SewingMachineRecipe> recipeType = ModRecipes.Types.SEWING_MACHINE_RECIPE.get();
    private int currentProgress = 0;

    public SewingMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SEWING_MACHINE.get(), pPos, pBlockState);
    }

    public SewingMachineBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SewingMachineRecipe checkRecipe(){
        return level.getRecipeManager().getRecipeFor(recipeType, this, level).orElse(null);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    public NonNullList<ItemStack> getInventory() {
        return items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new SewingMachineMenu(ModMenus.SEWING_CONTAINER, pContainerId, pInventory, this);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.put("inventory", ContainerHelper.saveAllItems(new CompoundTag(), this.items));
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt.getCompound("inventory"), items);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isEmpty())
                empty = false;
        }
        return empty;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        items.get(pSlot).shrink(pAmount);
        return items.get(pSlot);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        items.set(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        SewingMachineBlockEntity sewingMachine = (SewingMachineBlockEntity) blockEntity;
        SewingMachineRecipe recipe = sewingMachine.checkRecipe();
        if(recipe != null && sewingMachine.getItem(9).isEmpty()){
            if(recipe != sewingMachine.currentRecipe)
                sewingMachine.currentRecipe = recipe;
            sewingMachine.currentProgress++;
            if(sewingMachine.currentProgress == sewingMachine.currentRecipe.getDuration()) {
                ItemStack result = sewingMachine.currentRecipe.assemble(sewingMachine, level.registryAccess());
                sewingMachine.setItem(9, result);
                sewingMachine.currentRecipe = null;
                sewingMachine.currentProgress = 0;
            }
        }
    }
}
