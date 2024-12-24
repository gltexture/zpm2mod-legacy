package ru.BouH_.items.gun.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.skills.SkillManager;

public class GunLivingEvent {

    @SubscribeEvent()
    public void onUpdate(TickEvent.PlayerTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            EntityPlayer player = ev.player;
            ItemStack stack = player.getHeldItem();
            PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);

            if (player.getEntityData().getInteger("cdShoot") == 0) {
                if (playerMiscData.getGunInaccuracyTimer() > 0) {
                    playerMiscData.setGunInaccuracyTimer(playerMiscData.getGunInaccuracyTimer() - 1);
                } else {
                    if (playerMiscData.getGunInaccuracy() > 0) {
                        playerMiscData.setGunInaccuracy(Math.max(playerMiscData.getGunInaccuracy() - (0.125f + SkillManager.instance.getSkillBonus(SkillManager.instance.gunSmith, player, 0.0075f)), 0));
                    }
                }
            }

            if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase && stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isEquipped")) {
                AGunBase aGunBase = (AGunBase) stack.getItem();

                if (player.getEntityData().getInteger("cdReload") > 0) {
                    player.getEntityData().setInteger("cdReload", player.getEntityData().getInteger("cdReload") - 1);
                }

                if (!player.worldObj.isRemote) {
                    if (player.getEntityData().getInteger("cdShoot") > 0) {
                        player.getEntityData().setInteger("cdShoot", player.getEntityData().getInteger("cdShoot") - 1);
                    }
                }

                if (player.getEntityData().getInteger("cdUse") > 0) {
                    player.getEntityData().setInteger("cdUse", player.getEntityData().getInteger("cdUse") - 1);
                }

                if (player.getEntityData().getInteger("cdFlash") > 0) {
                    player.getEntityData().setInteger("cdFlash", player.getEntityData().getInteger("cdFlash") - 1);
                }

                if (aGunBase.isInReloadingAnim(player)) {
                    boolean flag = player.getEntityData().getBoolean("interruptReloading");
                    if (player.getEntityData().getBoolean("interruptReloading")) {
                        if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == 0 || stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == aGunBase.getMaxAmmo()) {
                            flag = false;
                        }
                    }
                    if (player.getEntityData().getInteger("cdReload") <= aGunBase.getCdReload() / 2 && flag) {
                        if (player.worldObj.isRemote) {
                            if (player instanceof EntityPlayerSP) {
                                aGunBase.reloadFinish(stack, player, true);
                            }
                        } else {
                            aGunBase.reloadFinish(stack, player, true);
                        }
                    } else if (player.getEntityData().getInteger("cdReload") == 0) {
                        if (aGunBase.reload(stack, player, aGunBase.isInUnReloading(player))) {
                            if (player.worldObj.isRemote) {
                                if (player instanceof EntityPlayerSP) {
                                    aGunBase.reloadFinish(stack, player, true);
                                }
                            } else {
                                aGunBase.reloadFinish(stack, player, true);
                            }
                        }
                    }
                }
            } else {
                if (player.worldObj.isRemote) {
                    if (player instanceof EntityPlayerSP) {
                        AGunBase.resetPartiallyGun(player);
                    }
                } else {
                    AGunBase.resetPartiallyGun(player);
                }
            }
        }
    }
}