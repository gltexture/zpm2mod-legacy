package ru.BouH_.items.gun.ammo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import ru.BouH_.entity.particle.EntityParticleSpark;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.render.Shell;

public class AGaussAmmo extends AAmmo {
    private final float volume;
    private final Shell shell;

    public AGaussAmmo(Item item, Shell shell, float volume) {
        super(item, shell, volume);
        this.volume = volume;
        this.shell = shell;
    }

    @SideOnly(Side.CLIENT)
    public void generateSmoke(EntityPlayer player, ItemStack stack, AGunBase aGunBase, int count) {
        Vec3 lookVec = player.getLookVec();
        float f1 = aGunBase.particleSmokePosMultiplier();
        double x = player.posX;
        double y = player.posY;
        if (player instanceof EntityOtherPlayerMP) {
            y = player.posY + (player.getEyeHeight() - 0.22f);
        }
        double z = player.posZ;
        if (player instanceof EntityPlayerSP) {
            double d1 = (70 - Minecraft.getMinecraft().gameSettings.fovSetting) * 0.0175d;
            f1 += d1;
            y += d1 * 0.15f;
        }
        double posX = x + lookVec.xCoord * f1;
        double posY = y + (lookVec.yCoord - 0.16f) * f1;
        double posZ = z + lookVec.zCoord * f1;
        if (player instanceof EntityPlayerSP) {
            if (!aGunBase.isPlayerInOpticScope(stack)) {
                posX -= MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.125f;
                posZ -= MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.125f;
            }
        }
        for (int i = 0; i < 24; i++) {
            if (!player.isInsideOfMaterial(Material.water)) {
                float f = 0.2f + i * 0.2f;
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleSpark(player.worldObj, posX, posY, posZ, lookVec.xCoord * 0.1f * f, lookVec.yCoord * 0.1f * f, lookVec.zCoord * 0.1f * f));
            }
        }
    }

    public float getVolume() {
        return this.volume;
    }

    public Shell getShell() {
        return this.shell;
    }
}
