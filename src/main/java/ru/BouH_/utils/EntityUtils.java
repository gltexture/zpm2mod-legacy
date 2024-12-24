package ru.BouH_.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.proxy.CommonProxy;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
    private static final List<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();

    public static boolean isEntityInLowRadiation(EntityLivingBase entityLivingBase) {
        int x = MathHelper.floor_double(entityLivingBase.posX);
        int z = MathHelper.floor_double(entityLivingBase.posZ);
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entityLivingBase;
            if (entityPlayer.inventory.hasItem(Item.getItemFromBlock(BlocksZp.uranium))) {
                return true;
            }
        } else {
            if (entityLivingBase instanceof AZombieBase) {
                AZombieBase aZombieBase = (AZombieBase) entityLivingBase;
                if (aZombieBase.getModifierId() == 1) {
                    return true;
                }
            }
            if (entityLivingBase.getHeldItem() != null && entityLivingBase.getHeldItem().getItem() == Item.getItemFromBlock(BlocksZp.uranium)) {
                return true;
            }
        }
        if (isInBlock(entityLivingBase, BlocksZp.radiation1) || entityLivingBase.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_rad1) {
            return true;
        }
        if (entityLivingBase.worldObj.loadedEntityList != null) {
            for (Object o : entityLivingBase.worldObj.loadedEntityList) {
                if (o instanceof AZombieBase) {
                    AZombieBase aZombieBase = (AZombieBase) o;
                    if (entityLivingBase.getDistanceToEntity(aZombieBase) <= 4.0f) {
                        if (aZombieBase.getModifierId() == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEntityInHighRadiation(EntityLivingBase entityLivingBase) {
        int x = MathHelper.floor_double(entityLivingBase.posX);
        int z = MathHelper.floor_double(entityLivingBase.posZ);
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entityLivingBase;
            if (entityPlayer.inventory.hasItem(ItemsZp.uran_material)) {
                return true;
            }
        } else {
            if (entityLivingBase instanceof AZombieBase) {
                AZombieBase aZombieBase = (AZombieBase) entityLivingBase;
                if (aZombieBase.getModifierId() == 2) {
                    return true;
                }
            }
            if (entityLivingBase.getHeldItem() != null && entityLivingBase.getHeldItem().getItem() == ItemsZp.uran_material) {
                return true;
            }
        }
        if (isInBlock(entityLivingBase, BlocksZp.radiation2) || entityLivingBase.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_rad2) {
            return true;
        }
        if (entityLivingBase.worldObj.loadedEntityList != null) {
            for (Object o : entityLivingBase.worldObj.loadedEntityList) {
                if (o instanceof AZombieBase) {
                    AZombieBase aZombieBase = (AZombieBase) o;
                    if (entityLivingBase.getDistanceToEntity(aZombieBase) <= 4.0f) {
                        if (aZombieBase.getModifierId() == 2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean canEntitySeeEntity(Entity entity1, Entity entity2, boolean checkFov) {
        Vec3 vecTarget = Vec3.createVectorHelper(entity1.posX, entity1.posY + entity1.getEyeHeight(), entity1.posZ);
        Vec3 vecMob1 = Vec3.createVectorHelper(entity2.posX, entity2.posY + (entity2.boundingBox.maxY - entity2.boundingBox.minY) * 0.25f, entity2.posZ);
        Vec3 vecMob2 = Vec3.createVectorHelper(entity2.posX, entity2.posY + entity2.getEyeHeight(), entity2.posZ);
        if (checkFov) {
            Vec3 vec1 = entity1.getLookVec().normalize();
            Vec3 vec2 = Vec3.createVectorHelper(entity2.posX - entity1.posX, entity2.posY - (entity1.posY + (double) entity1.getEyeHeight()), entity2.posZ - entity1.posZ).normalize();
            checkFov = vec1.dotProduct(vec2) <= 0.1f;
        }
        if (entity1.worldObj.rayTraceBlocks(vecTarget, vecMob1, false, true, false) == null || entity1.worldObj.rayTraceBlocks(vecTarget, vecMob2, false, true, false) == null) {
            return !checkFov;
        }
        return false;
    }

    public static boolean isBlockInAABB(Entity entity, int x, int y, int z, Block block, AxisAlignedBB p_147461_1_) {
        EntityUtils.collidingBoundingBoxes.clear();
        if (block != Blocks.wooden_door && block != Blocks.iron_door) {
            block.addCollisionBoxesToList(entity.worldObj, x, y, z, p_147461_1_, EntityUtils.collidingBoundingBoxes, entity);
        }
        return EntityUtils.collidingBoundingBoxes.isEmpty();
    }

    public static boolean isInArmor(EntityLivingBase pl, Item helmet, Item chestPlate, Item leggings, Item boots) {
        return ((helmet == null || (pl.getEquipmentInSlot(4) != null && pl.getEquipmentInSlot(4).getItem() == helmet)) && (chestPlate == null || (pl.getEquipmentInSlot(3) != null && pl.getEquipmentInSlot(3).getItem() == chestPlate)) && (leggings == null || (pl.getEquipmentInSlot(2) != null && pl.getEquipmentInSlot(2).getItem() == leggings)) && (boots == null || (pl.getEquipmentInSlot(1) != null && pl.getEquipmentInSlot(1).getItem() == boots)));
    }

    public static float armorCalculations(float dmg, Entity ent) {
        float i = 1.0f;
        if (ent instanceof EntityLivingBase) {
            for (int j = 1; j < 5; j++) {
                ItemStack equipment = ((EntityLivingBase) ent).getEquipmentInSlot(j);
                if (equipment != null && equipment.getItem() instanceof ItemArmor) {
                    ItemArmor armor = ((ItemArmor) ((EntityLivingBase) ent).getEquipmentInSlot(j).getItem());
                    i -= materialProtection(armor.getArmorMaterial());
                }
            }
        }
        return dmg * i;
    }

    public static float armorCalculationsId(float dmg, Entity ent, int id) {
        float i = 1;
        if (ent instanceof EntityLivingBase) {
            if (((EntityLivingBase) ent).getEquipmentInSlot(id) != null) {
                ItemArmor armor = ((ItemArmor) ((EntityLivingBase) ent).getEquipmentInSlot(id).getItem());
                i -= materialProtection(armor.getArmorMaterial());
            }
        }
        return dmg * i;
    }

    private static float materialProtection(ItemArmor.ArmorMaterial armorMaterial) {
        float i = 0;
        if (armorMaterial == ItemArmor.ArmorMaterial.IRON) {
            i += 0.1f;
        } else if (armorMaterial == ItemArmor.ArmorMaterial.DIAMOND) {
            i += 0.12f;
        } else if (armorMaterial == ItemArmor.ArmorMaterial.GOLD) {
            i += 0.025f;
        } else if (armorMaterial == ItemArmor.ArmorMaterial.CHAIN) {
            i += 0.06f;
        } else if (armorMaterial == ItemsZp.steel_mat) {
            i += 0.15f;
        } else if (armorMaterial == ItemsZp.titan_mat || armorMaterial == ItemsZp.kevlar_mat) {
            i += 0.2f;
        } else if (armorMaterial == ItemArmor.ArmorMaterial.CLOTH || armorMaterial == ItemsZp.cloth_mat || armorMaterial == ItemsZp.special_costume_mat || armorMaterial == ItemsZp.special_costume2_mat || armorMaterial == ItemsZp.special_costume3_mat) {
            i += 0.02f;
        }
        return i;
    }

    public static boolean isInBlock(Entity entity, Block block) {
        World world = entity.worldObj;
        AxisAlignedBB axisAlignedBB = entity.boundingBox;
        int i = MathHelper.floor_double(axisAlignedBB.minX);
        int j = MathHelper.floor_double(axisAlignedBB.maxX + 0.999D);
        int k = MathHelper.floor_double(axisAlignedBB.minY);
        int l = MathHelper.floor_double(axisAlignedBB.maxY + 0.999D);
        int i1 = MathHelper.floor_double(axisAlignedBB.minZ);
        int j1 = MathHelper.floor_double(axisAlignedBB.maxZ + 0.999D);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    if (world.getBlock(k1, l1, i2) == block) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isFullyInMaterial(Entity entity, Material material) {
        World world = entity.worldObj;
        AxisAlignedBB axisAlignedBB = entity.boundingBox;
        int x = MathHelper.floor_double(entity.posX);
        int z = MathHelper.floor_double(entity.posZ);
        return world.getBlock(x, (int) axisAlignedBB.minY, z).getMaterial() == material && world.getBlock(x, (int) (axisAlignedBB.maxY - 0.1D), z).getMaterial() == material;
    }

    public static boolean isBoilingWater(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial() != Material.water) {
            return false;
        }
        return (world.getBlock(x, y - 2, z) == Blocks.fire || world.getBlock(x, y - 2, z).getMaterial() == Material.lava) && (world.getBlock(x, y - 1, z).getMaterial() == Material.rock || world.getBlock(x, y - 1, z).getMaterial() == Material.iron);
    }
}
