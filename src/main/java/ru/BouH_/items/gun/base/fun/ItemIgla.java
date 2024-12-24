package ru.BouH_.items.gun.base.fun;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.fun.rockets.EntityIglaRocket;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.fun.PacketIglaInfoC;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.utils.TraceUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemIgla extends ALauncherBase {
    public ItemIgla(String name, String shootSound, String reloadSound, int ammo, int cdReload, int cdShoot, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, cdShoot, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @Override
    public void rocket(EntityPlayer player, Entity target) {
        EntityIglaRocket entityIglaRocket = new EntityIglaRocket(player.worldObj, player, 0);
        player.worldObj.spawnEntityInWorld(entityIglaRocket);
        entityIglaRocket.setInfo(target);
        NetworkHandler.NETWORK.sendToAllAround(new PacketIglaInfoC(entityIglaRocket.getEntityId(), target.entityId), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
    }

    public Entity chooseTarget() {
        Entity entity = TraceUtils.getMouseOver(EntityDetectableRocketZp.class, 1.0f, 256, 32.0f);
        if (entity != null && !EntityIglaRocket.rockets.contains(entity.getClass())) {
            return null;
        }
        return entity;
    }

    @Override
    public boolean makeReload(ItemStack stack, EntityPlayer player, boolean unReloading) {
        if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
            if (unReloading) {
                while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                    if (!player.inventory.addItemStackToInventory(new ItemStack(this.getAmmo()))) {
                        if (!player.worldObj.isRemote) {
                            player.dropItemWithOffset(this.getAmmo(), 1, 1.0f);
                        }
                    }
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") - 1);
                }
            } else {
                while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") < this.getMaxAmmo() && (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this.getAmmo()))) {
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") + 1);
                }
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int particleSmokeCount() {
        return 14;
    }

    @Override
    public List<String> weaponDescriptionText(ItemStack stack) {
        List<String> list = new ArrayList<>();
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilHorizontal"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilHorizontal(), this.collectModuleHorizontalRecoil(stack))));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilVertical"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilVertical(), this.collectModuleVerticalRecoil(stack))));
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.fireRate") + EnumChatFormatting.GRAY, 1200 / this.getFireRate(), I18n.format("weapon.gui.desc.rate")));
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.reloadTime") + EnumChatFormatting.GRAY, this.getCdReload() / 20.0f, I18n.format("weapon.gui.desc.sec")));
        list.add("");
        return list;
    }

    @Override
    public void onShootPost(ItemStack stack, EntityPlayer player) {
        if (!player.worldObj.isRemote && !this.isSilenced(stack)) {
            PlayerMiscData.getPlayerData(player).addPlayerLoudness((int) this.getShootVolume(stack));
        }
    }
}
