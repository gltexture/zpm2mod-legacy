package ru.BouH_.items.gun.base.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.fun.PacketShootingLauncher;
import ru.BouH_.network.packets.gun.PacketReloading;
import ru.BouH_.utils.SoundUtils;

public abstract class ALauncherBase extends AGunBase {

    public ALauncherBase(String name, String shootSound, String reloadSound, int ammo, int cdReload, int cdShoot, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, GunType.RPG, ammo, cdReload, -1, cdShoot, -1, -1, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, 0, 0, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @SideOnly(Side.CLIENT)
    public void shootPacket(ItemStack stack, EntityPlayer player) {
        if (this.isReadyToShoot(stack, player)) {
            if (Main.settingsZp.autoReload.isFlag()) {
                if (!this.canShoot(stack, player)) {
                    if (this.canStartReloading(player, stack, false)) {
                        NetworkHandler.NETWORK.sendToServer(new PacketReloading(false));
                        this.reloadStart(stack, player, false);
                        return;
                    }
                }
            }
            if (this.chooseTarget() != null) {
                NetworkHandler.NETWORK.sendToServer(new PacketShootingLauncher(this.chooseTarget().entityId));
            }
            this.shoot2(player, stack, null);
        }
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
    }

    public boolean canShoot(ItemStack stack, EntityPlayer player) {
        if (player.worldObj.isRemote) {
            if (this.chooseTarget() == null) {
                return false;
            }
        }
        return super.canShoot(stack, player);
    }

    public void shoot2(EntityPlayer player, ItemStack stack, Entity target) {
        World world = player.worldObj;
        if (this.canShoot(stack, player)) {
            this.onShootPre(stack, player);
            int i = 3;
            if (player.worldObj.isRemote && player instanceof EntityPlayerSP) {
                i = 2;
            }
            player.getEntityData().setInteger("cdFlash", i);
            player.getEntityData().setInteger("cdShoot", this.getCdShoot());
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", 0);
            if (!world.isRemote) {
                if (target != null) {
                    this.rocket(player, target);
                }
                if (!player.capabilities.isCreativeMode) {
                    if (!world.getGameRules().getGameRuleBooleanValue("doWeaponUnbreakable")) {
                        stack.damageItem(1, player);
                        if (stack.stackSize == 0) {
                            player.setCurrentItemOrArmor(0, null);
                        }
                    }
                }
                if (Main.rand.nextFloat() <= stack.getCurrentDurability() * this.jamChanceMultiplier * 0.00000125f) {
                    stack.getTagCompound().getCompoundTag(Main.MODID).setBoolean("isJammed", true);
                }
            } else {
                this.playShootSound(stack, player);
                if (player instanceof EntityPlayerSP) {
                    this.addRecoil(this.getRecoilHorizontal(), this.getRecoilVertical());
                }
                this.gunFX(stack, player, this.shouldDropShell);
            }
        } else {
            if (world.isRemote) {
                if (Main.settingsZp.autoReload.isFlag()) {
                    if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
                        if (this.canStartReloading(player, stack, false)) {
                            NetworkHandler.NETWORK.sendToServer(new PacketReloading(false));
                            this.reloadStart(stack, player, false);
                            return;
                        }
                    }
                }
                if (player instanceof EntityPlayerSP) {
                    SoundUtils.playClientSound(player, (Main.MODID + ":holster"), 2.5f, this.getScaledPitch(player));
                    GunItemRender.instance.addRecoilAnimation(0.5f);
                }
            }
            player.getEntityData().setInteger("cdShoot", 5);
        }
        this.onShootPost(stack, player);
    }

    public abstract void rocket(EntityPlayer player, Entity target);

    public abstract Entity chooseTarget();
}
