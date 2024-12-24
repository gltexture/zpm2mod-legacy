package ru.BouH_.render.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.EnumFireModes;
import ru.BouH_.items.tools.ItemBinoculars;
import ru.BouH_.utils.EntityUtils;

@SideOnly(Side.CLIENT)
public class ModelPlayer extends ModelBiped {
    public ModelPlayer(float p_i1148_1_) {
        super(p_i1148_1_, 0.0F, 64, 32);
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        EntityPlayer player = (EntityPlayer) p_78087_7_;
        boolean lie = PlayerMiscData.getPlayerData(player).isLying();
        if (lie) {
            float f3 = 0.0F;
            float f4 = 0.0F;
            this.bipedLeftArm.rotateAngleX = 3.3f;
            this.bipedBody.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
            this.bipedHeadwear.rotationPointY = 0.0F;
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            if (EntityUtils.isFullyInMaterial(player, Material.water)) {
                p_78087_1_ = player.ticksExisted * 0.75f;
                this.bipedRightLeg.rotateAngleY += MathHelper.cos(p_78087_1_) * 0.05f;
                this.bipedLeftLeg.rotateAngleY -= MathHelper.cos(p_78087_1_) * 0.05f;
                this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6f) * 0.15f;
                this.bipedLeftLeg.rotateAngleX = -MathHelper.cos(p_78087_1_ * 0.6f) * 0.15f;
                this.bipedLeftLeg.rotateAngleZ = -0.1f;
                this.bipedRightLeg.rotateAngleZ = 0.1f;
                this.bipedHead.rotateAngleX = -0.8f;
                this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX - MathHelper.cos(p_78087_1_ * 0.2f) * 0.1f;
            } else {
                int x = MathHelper.floor_double(player.posX + player.getLookVec().xCoord * 0.5f);
                int y = MathHelper.floor_double(player.posY - 1.0f);
                int z = MathHelper.floor_double(player.posZ + player.getLookVec().zCoord * 0.5f);
                boolean flag = player.worldObj.getBlock(x, y, z).isPassable(player.worldObj, x, y, z) || player.worldObj.getBlock(x, y, z).getCollisionBoundingBoxFromPool(player.worldObj, x, y, z) == null;
                this.bipedHead.rotateAngleX = MathHelper.clamp_float(p_78087_5_ / (180F / (float) Math.PI) - 1.0f, -2.5f, flag ? 0.2f : -0.8f);
                this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                this.bipedRightLeg.rotateAngleZ = MathHelper.cos(p_78087_1_ * 0.8F) * 0.5F * p_78087_2_ + 0.1f;
                this.bipedLeftLeg.rotateAngleZ = MathHelper.cos(p_78087_1_ * 0.8F + (float) Math.PI) * 0.5F * p_78087_2_ - 0.1f;
                this.bipedRightLeg.rotateAngleX = 0;
                this.bipedLeftLeg.rotateAngleX = 0;
            }
            this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
            this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
            this.bipedRightArm.rotateAngleY = (p_78087_4_ / (360F / (float) Math.PI)) * 0.3f;
            this.bipedLeftArm.rotateAngleY = (p_78087_4_ / (360F / (float) Math.PI)) * -0.3f;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_1_ * 0.6f) * 0.2f + MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
            this.bipedBody.rotateAngleY = MathHelper.cos(p_78087_1_ * 0.8f) * 0.13f + (p_78087_4_ / (360F / (float) Math.PI)) * 0.3f;
            this.bipedHead.rotateAngleY = MathHelper.cos(p_78087_1_ * 0.5F) * 0.1F + (p_78087_4_ / (360F / (float) Math.PI)) * 0.3f;
            this.bipedHead.rotateAngleZ = 0;
            this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
            this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
            this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
            if (this.aimedBow) {
                this.bipedRightArm.rotateAngleZ = 0.0F;
                this.bipedLeftArm.rotateAngleZ = 0.0F;
                this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.7F) + this.bipedHead.rotateAngleY;
                this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
                this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
            }
        } else {
            if (this.isSneak) {
                this.bipedBody.rotateAngleX = 0.4F;
                this.bipedRightLeg.rotationPointZ = 3.5F;
                this.bipedLeftLeg.rotationPointZ = 3.5F;
                this.bipedRightLeg.rotationPointY = 8.5F;
                this.bipedLeftLeg.rotationPointY = 8.5F;
            }
            this.bipedLeftLeg.rotateAngleZ = 0;
            this.bipedRightLeg.rotateAngleZ = 0;
        }
        if (player.getHeldItem() != null) {
            float f3 = 0.0F;
            float f4 = 0.0F;
            if (player.getHeldItem().hasTagCompound()) {
                if (player.getHeldItem().getItem() instanceof AGunBase) {
                    AGunBase iGunBase = (AGunBase) player.getHeldItem().getItem();
                    AGunBase.GunType gunType = iGunBase.getRenderType();
                    if (iGunBase.isInReloadingAnim(player)) {
                        this.bipedLeftArm.rotateAngleY = 0.4F;
                        if (lie) {
                            this.bipedLeftArm.rotateAngleX = -3.0f;
                            this.bipedLeftArm.rotateAngleZ = -0.1f;
                            this.bipedRightArm.rotateAngleY = -0.6f;
                            this.bipedRightArm.rotateAngleZ = 0.4f;
                            this.bipedRightArm.rotateAngleX = -2.9f;
                        } else {
                            this.bipedLeftArm.rotateAngleX = -1.2f;
                            this.bipedRightArm.rotateAngleY = -0.4F;
                            this.bipedRightArm.rotateAngleZ = -0.6f;
                            this.bipedRightArm.rotateAngleX = -1.1f;
                        }
                        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                    } else {
                        if (gunType == AGunBase.GunType.RIFLE2 || gunType == AGunBase.GunType.CROSSBOW || gunType == AGunBase.GunType.RIFLE || gunType == AGunBase.GunType.RPG) {
                            this.bipedRightArm.rotateAngleZ = 0.0F;
                            if (lie) {
                                this.bipedLeftArm.rotateAngleZ = -0.2F;
                                this.bipedLeftArm.rotateAngleY = -0.2F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
                                this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                                this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                            } else {
                                this.bipedLeftArm.rotateAngleZ = 0.0F;
                                this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
                                if (iGunBase.getCurrentFireMode(player.getHeldItem()) == EnumFireModes.SAFE) {
                                    this.bipedRightArm.rotateAngleX = Math.min(-((float) Math.PI / 3.0f) + this.bipedHead.rotateAngleX, 0);
                                    this.bipedLeftArm.rotateAngleX = Math.min(-((float) Math.PI / 3.0f) + this.bipedHead.rotateAngleX, 0);
                                } else {
                                    this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                                    this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                                }
                            }
                            this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.7F) + this.bipedHead.rotateAngleY;
                            this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                            this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                            this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                            this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                        } else if (gunType == AGunBase.GunType.PISTOL || gunType == AGunBase.GunType.PISTOL2) {
                            this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.6F) + this.bipedHead.rotateAngleY;
                            if (lie) {
                                this.bipedLeftArm.rotateAngleX = 3.3f;
                                this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                            } else {
                                if (iGunBase.getCurrentFireMode(player.getHeldItem()) == EnumFireModes.SAFE) {
                                    this.bipedRightArm.rotateAngleX = Math.min(-((float) Math.PI / 3.0f) + this.bipedHead.rotateAngleX, 0);
                                } else {
                                    this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                                }
                            }
                            this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                            this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                            this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.04F;
                        }
                    }
                }
            } else if (player.getHeldItem().getItem() instanceof ItemBinoculars) {
                this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.6F) + this.bipedHead.rotateAngleY;
                if (lie) {
                    this.bipedLeftArm.rotateAngleX = 3.3f;
                    this.bipedRightArm.rotateAngleX = -((float) Math.PI / 1.45f) + this.bipedHead.rotateAngleX;
                } else {
                    this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                }
                this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.04F;
            }
        }
    }
}
