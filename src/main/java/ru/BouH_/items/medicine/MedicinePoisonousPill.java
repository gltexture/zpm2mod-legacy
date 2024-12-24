package ru.BouH_.items.medicine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicinePoisonousPill extends MedicineZp {
    public MedicinePoisonousPill(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
        this.setContainerItem(this);
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.eat;
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(this.getContainerItem());
        stack.setMetadata(itemStack.getMetadata() + 1);
        return stack;
    }

    public ItemStack onItemUseFinish(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        ItemStack stack = new ItemStack(this.getContainerItem());
        stack.setMetadata(p_77654_1_.getMetadata() + 1);
        return stack;
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.hasTagCompound()) {
            stack.setTagInfo(Main.MODID, new NBTTagCompound());
            stack.getTagCompound().getCompoundTag(Main.MODID).setByte("poisonous", (byte) 1);
        }
    }
}
