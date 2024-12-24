package ru.BouH_.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntityBrewingStand;
import ru.BouH_.init.ItemsZp;

import java.util.List;

public class TileBrewingStand extends TileEntityBrewingStand {
    private ItemStack[] brewingItemStacks = new ItemStack[6];
    private int brewTime;
    private int brewCd;
    private int filledSlots;
    private Item ingredientID;
    private String field_145942_n;
    private boolean isCatalised;

    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }

    public void updateEntity() {
        if (this.brewTime > 0) {
            this.brewTime -= this.isCatalised ? 2 : 1;
            if (this.brewTime == 0) {
                this.brewPotions();
                if (this.brewingItemStacks[5] != null) {
                    this.brewingItemStacks[5].getItem().setDamage(this.brewingItemStacks[5], this.brewingItemStacks[5].getMetadata() + 1);
                    if (this.brewingItemStacks[5].getMetadata() >= this.brewingItemStacks[5].getMaxDurability()) {
                        this.brewingItemStacks[5] = null;
                    }
                }
                this.markDirty();
            } else if (!this.canBrew()) {
                this.reset();
            } else if (this.ingredientID != this.brewingItemStacks[3].getItem()) {
                this.reset();
            } else if (this.brewingItemStacks[4] == null) {
                this.reset();
            } else if (this.brewingItemStacks[5] != null && !this.isCatalised) {
                this.isCatalised = true;
                this.reset();
            } else if (this.brewingItemStacks[5] == null && this.isCatalised) {
                this.isCatalised = false;
                this.reset();
            }
        } else if (this.canBrew()) {
            this.brewTime = 3600;
            this.ingredientID = this.brewingItemStacks[3].getItem();
        }

        int i = this.getFilledSlots();

        if (i != this.filledSlots) {
            this.filledSlots = i;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, i, 2);
        }

        super.updateEntity();
    }

    private void reset() {
        this.brewCd = 20;
        this.brewTime = 0;
        this.markDirty();
    }

    public int getBrewTime() {
        return this.brewTime;
    }

    public boolean canBrew() {
        if (this.brewCd == 0) {
            if (this.brewingItemStacks[3] != null && this.brewingItemStacks[3].stackSize > 0 && (this.brewingItemStacks[4] != null && (this.brewingItemStacks[4].getItem() == ItemsZp.chemicals1 || this.brewingItemStacks[4].getItem() == ItemsZp.chemicals2))) {
                ItemStack itemstack = this.brewingItemStacks[3];

                if (!itemstack.getItem().isPotionIngredient(itemstack)) {
                    return false;
                } else {
                    boolean flag = false;

                    for (int i = 0; i < 3; ++i) {
                        if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() instanceof ItemPotion) {
                            int j = this.brewingItemStacks[i].getMetadata();
                            int k = this.func_145936_c(j, itemstack);

                            if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
                                flag = true;
                                break;
                            }

                            List list = Items.potionitem.getEffects(j);
                            List list1 = Items.potionitem.getEffects(k);

                            if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k) {
                                flag = true;
                                break;
                            }
                        }
                    }

                    return flag;
                }
            } else {
                return false;
            }
        } else {
            this.brewCd--;
            return false;
        }
    }

    public void brewPotions() {
        if (this.canBrew()) {
            ItemStack itemstack = this.brewingItemStacks[3];

            for (int i = 0; i < 3; ++i) {
                if (this.brewingItemStacks[4] != null) {
                    if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() instanceof ItemPotion) {
                        int j = this.brewingItemStacks[i].getMetadata();
                        int k = this.func_145936_c(j, itemstack);
                        List list = Items.potionitem.getEffects(j);
                        List list1 = Items.potionitem.getEffects(k);

                        this.brewingItemStacks[4].getItem().setDamage(this.brewingItemStacks[4], this.brewingItemStacks[4].getMetadata() + 1);
                        if (this.brewingItemStacks[4].getMetadata() >= this.brewingItemStacks[4].getMaxDurability()) {
                            this.brewingItemStacks[4] = null;
                        }

                        if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null)) {
                            if (j != k) {
                                this.brewingItemStacks[i].setMetadata(k);
                            }
                        } else if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
                            this.brewingItemStacks[i].setMetadata(k);
                        }
                    }
                }
            }

            if (itemstack.getItem().hasContainerItem(itemstack)) {
                this.brewingItemStacks[3] = itemstack.getItem().getContainerItem(itemstack);
            } else {
                --this.brewingItemStacks[3].stackSize;

                if (this.brewingItemStacks[3].stackSize <= 0) {
                    this.brewingItemStacks[3] = null;
                }
            }
            net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(brewingItemStacks);
        }
    }

    public int func_145936_c(int p_145936_1_, ItemStack p_145936_2_) {
        return p_145936_2_ == null ? p_145936_1_ : (p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_1_);
    }

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.brewingItemStacks.length) {
                this.brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.brewTime = p_145839_1_.getShort("BrewTime");

        if (p_145839_1_.hasKey("CustomName", 8)) {
            this.field_145942_n = p_145839_1_.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BrewTime", (short) this.brewTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.brewingItemStacks.length; ++i) {
            if (this.brewingItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.brewingItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.isCustomInventoryName()) {
            p_145841_1_.setString("CustomName", this.field_145942_n);
        }
    }

    public ItemStack getStackInSlot(int p_70301_1_) {
        return p_70301_1_ >= 0 && p_70301_1_ < this.brewingItemStacks.length ? this.brewingItemStacks[p_70301_1_] : null;
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        if (p_70298_1_ >= 0 && p_70298_1_ < this.brewingItemStacks.length) {
            ItemStack itemstack = this.brewingItemStacks[p_70298_1_];
            this.brewingItemStacks[p_70298_1_] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        if (p_70304_1_ >= 0 && p_70304_1_ < this.brewingItemStacks.length) {
            ItemStack itemstack = this.brewingItemStacks[p_70304_1_];
            this.brewingItemStacks[p_70304_1_] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (p_70299_1_ >= 0 && p_70299_1_ < this.brewingItemStacks.length) {
            this.brewingItemStacks[p_70299_1_] = p_70299_2_;
        }
    }


    @SideOnly(Side.CLIENT)
    public void func_145938_d(int p_145938_1_) {
        this.brewTime = p_145938_1_;
    }

    public int getFilledSlots() {
        int i = 0;

        for (int j = 0; j < 3; ++j) {
            if (this.brewingItemStacks[j] != null) {
                i |= 1 << j;
            }
        }

        return i;
    }
}
