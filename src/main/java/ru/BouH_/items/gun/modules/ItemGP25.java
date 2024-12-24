package ru.BouH_.items.gun.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.Main;
import ru.BouH_.entity.projectile.EntityWog25;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.ALauncherModuleBase;

import java.util.List;

public class ItemGP25 extends ALauncherModuleBase {
    public ItemGP25(String name, String shootSound, String reloadSound, int shootCd, int reloadCd, AAmmo aAmmo, int maxAmmo) {
        super(name, shootSound, reloadSound, shootCd, reloadCd, aAmmo, maxAmmo);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(EnumChatFormatting.GREEN + I18n.format("modifiers.description.grenade_launcher") + " " + this.getAmmo().getItemStackDisplayName(stack));
    }

    @Override
    public void shoot(AGunBase aGunBase, ItemStack stack, EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        player.getEntityData().setInteger("cdShoot", this.getShootCd());
        this.playShootSound(player);
        if (!player.worldObj.isRemote) {
            float inaccuracy = scoping ? 1.0f : 2.0f;
            EntityWog25 grenade = new EntityWog25(player.worldObj, player, 2.0f, inaccuracy);
            player.worldObj.spawnEntityInWorld(grenade);
        } else {
            if (player instanceof EntityPlayerSP) {
                aGunBase.addRecoil(3.0f, 3.0f);
            }
            this.getAAmmo().generateSmoke(player, stack, aGunBase, 18);
        }
    }

    @Override
    public boolean reload(AGunBase aGunBase, ItemStack stack, EntityPlayer player, boolean unReloading) {
        if (unReloading) {
            while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") > 0) {
                if (!player.inventory.addItemStackToInventory(new ItemStack(this.getAmmo()))) {
                    player.dropItemWithOffset(this.getAmmo(), 1, 1.0f);
                }
                stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("metaAmmo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") - 1);
            }
        } else if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
            while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") < this.getMaxAmmo() && (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this.getAmmo()))) {
                stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("metaAmmo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") + 1);
            }
        }
        return true;
    }
}
