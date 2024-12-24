package ru.BouH_.hook.server;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.blocks.BlockLayer;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.FluidsZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.inventory.GuiHandler;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleBookExplode;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.tiles.TileBrewingStand;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.SoundUtils;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public class BlockHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onEntityCollidedWithBlock(BlockPortal por, World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        if (p_149670_5_ instanceof EntityPlayer && p_149670_5_.ridingEntity == null) {
            p_149670_5_.setInPortal();
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onEntityCollidedWithBlock(BlockEndPortal por, World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        if (p_149670_5_ instanceof EntityPlayer && p_149670_5_.ridingEntity == null) {
            p_149670_5_.travelToDimension(1);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isLadder(BlockVine vine, IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static String getHurtSound(EntityPlayer pl) {
        return ((pl.isPotionActive(26) && pl.getActivePotionEffect(Potion.potionTypes[26]).getDuration() <= 16000) ? "mob.zombie.hurt" : "game.player.hurt");
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static TileEntity createNewTileEntity(BlockBrewingStand st, World p_149915_1_, int p_149915_2_) {
        return new TileBrewingStand();
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float getVolume(Block.SoundType sn) {
        return sn.volume + 1.5f;
    }

    @Hook
    public static void spawnEntity(MobSpawnerBaseLogic mob, Entity p_98265_1_) {
        p_98265_1_.getEntityData().setBoolean("spawner", true);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int getExpDrop(BlockOre blockOre, IBlockAccess worldIn, int meta, int fortune) {
        if (blockOre.getItemDropped(meta, Main.rand, fortune) != Item.getItemFromBlock(blockOre)) {
            int j1 = 0;
            if (blockOre == Blocks.coal_ore) {
                j1 = MathHelper.getRandomIntegerInRange(Main.rand, 0, 2);
            } else if (blockOre == Blocks.diamond_ore) {
                j1 = MathHelper.getRandomIntegerInRange(Main.rand, 3, 7);
            } else if (blockOre == Blocks.emerald_ore) {
                j1 = MathHelper.getRandomIntegerInRange(Main.rand, 3, 7);
            } else if (blockOre == Blocks.lapis_ore) {
                j1 = MathHelper.getRandomIntegerInRange(Main.rand, 2, 5);
            } else if (blockOre == Blocks.quartz_ore) {
                j1 = MathHelper.getRandomIntegerInRange(Main.rand, 0, 2);
            }
            return j1;
        }
        return 0;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean canCreatureTypeSpawnAtLocation(SpawnerAnimals spawnerAnimals, EnumCreatureType p_77190_0_, World p_77190_1_, int p_77190_2_, int p_77190_3_, int p_77190_4_) {
        Block block = p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
        if (p_77190_0_.getCreatureMaterial() == Material.water) {
            return (p_77190_1_.getBiomeGenForCoords(p_77190_2_, p_77190_4_) == BiomeGenBase.ocean || p_77190_1_.getBiomeGenForCoords(p_77190_2_, p_77190_4_) == BiomeGenBase.deepOcean) && p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_) == Blocks.water && p_77190_1_.getBlockMetadata(p_77190_2_, p_77190_3_, p_77190_4_) == 0 && p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_) == Blocks.water && p_77190_1_.getBlockMetadata(p_77190_2_, p_77190_3_ - 1, p_77190_4_) == 0 && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
        } else if (block instanceof BlockSlab || block instanceof BlockSnow || block instanceof BlockLayer || block instanceof BlockBush || World.doesBlockHaveSolidTopSurface(p_77190_1_, p_77190_2_, p_77190_3_ - 1, p_77190_4_)) {
            return !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).isNormalCube() && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).getMaterial().isLiquid() && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
        }
        return false;
    }

    @Hook
    public static void onUpdate(EntityArrow entityArrow) {
        int x = MathHelper.floor_double(entityArrow.posX);
        int y = (int) entityArrow.posY;
        int z = MathHelper.floor_double(entityArrow.posZ);
        if (entityArrow.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_acid || EntityUtils.isInBlock(entityArrow, BlocksZp.acidCloud) || EntityUtils.isInBlock(entityArrow, FluidsZp.acidblock)) {
            if (entityArrow.ticksExisted % 2 == 0) {
                if (entityArrow.worldObj.isRemote) {
                    BlockHook.acidEffects(entityArrow);
                    SoundUtils.playClientSound(x, y, z, "random.fizz", 1.2F, 1.2f + Main.rand.nextFloat() * 0.3f);
                }
            }
        }
    }

    @Hook
    public static void onUpdate(EntityItem item) {
        int x = MathHelper.floor_double(item.posX);
        int y = (int) item.posY;
        int z = MathHelper.floor_double(item.posZ);
        if (item.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_acid || EntityUtils.isInBlock(item, BlocksZp.acidCloud) || EntityUtils.isInBlock(item, FluidsZp.acidblock)) {
            if (item.ticksExisted % 2 == 0) {
                if (item.worldObj.isRemote) {
                    BlockHook.acidEffects(item);
                    SoundUtils.playClientSound(x, y, z, "random.fizz", 1.2F, 1.2f + Main.rand.nextFloat() * 0.3f);
                }
            }
            if (item.lifespan != 600) {
                item.age = 0;
            }
            item.lifespan = 600;
        } else {
            item.lifespan = 24000;
        }
        if (item.getEntityItem().getItem() == Items.rotten_flesh || item.getEntityItem().getItem() == ItemsZp.rotten_apple) {
            if (item.worldObj.isAirBlock(x, y, z)) {
                y -= 1;
            }
            Block block = item.worldObj.getBlock(x, y, z);
            if (block instanceof IGrowable) {
                IGrowable growable = (IGrowable) block;
                if (growable.canFertilize(item.worldObj, x, y, z, item.worldObj.isRemote)) {
                    item.getEntityData().setInteger("cdFertilize", item.getEntityData().getInteger("cdFertilize") + 1);
                    if (item.getEntityData().getInteger("cdFertilize") == 6000) {
                        for (int i = 0; i < item.getEntityItem().stackSize; i++) {
                            ItemDye.func_150919_a(new ItemStack(Items.dye), item.worldObj, x, y, z);
                        }
                        if (item.worldObj.isRemote) {
                            for (int i1 = 0; i1 < 15; ++i1) {
                                double db0 = Main.rand.nextGaussian() * 0.02D;
                                double db1 = Main.rand.nextGaussian() * 0.02D;
                                double db2 = Main.rand.nextGaussian() * 0.02D;
                                y = (int) item.posY;
                                item.worldObj.spawnParticle("happyVillager", x + (double) Main.rand.nextFloat(), y + (double) Main.rand.nextFloat() * block.getBlockBoundsMaxY(), z + (double) Main.rand.nextFloat(), db0, db1, db2);
                            }
                        }
                        item.setDead();
                    }
                }
            } else {
                item.getEntityData().setInteger("cdFertilize", 0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void acidEffects(Entity entity) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(entity.worldObj, entity.posX, entity.posY + 0.125f, entity.posZ, Main.rand.nextFloat() * 0.2 - 0.1f, Main.rand.nextFloat() * 0.2 - 0.1f, Main.rand.nextFloat() * 0.2 - 0.1f, new float[]{0.85f, 1.0f, 0.85f}, 1.2f - Main.rand.nextFloat() * 0.4f));
    }

    @SuppressWarnings("rawtypes")
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean enchantItem(ContainerEnchantment enchantment, EntityPlayer player, int id) {
        ItemStack itemstack = enchantment.tableInventory.getStackInSlot(0);
        if (enchantment.enchantLevels[id] > 0 && itemstack != null && (player.experienceLevel >= enchantment.enchantLevels[id] || player.capabilities.isCreativeMode)) {
            if (!player.worldObj.isRemote) {
                List list = EnchantmentHelper.buildEnchantmentList(Main.rand, itemstack, enchantment.enchantLevels[id]);
                boolean flag = itemstack.getItem() == Items.book;
                if (list != null) {
                    int summary = 0;
                    int max = (id + 1) * 8;
                    loop1:
                    for (int j = -2; j <= 2; j++) {
                        for (int k = -2; k <= 2; k++) {
                            if (Math.abs(j) != 2 || Math.abs(k) != 2) {
                                for (int o = 0; o < 2; o++) {
                                    int x = enchantment.posX + k;
                                    int y = enchantment.posY + o;
                                    int z = enchantment.posZ + j;
                                    if (player.worldObj.isAirBlock(enchantment.posX - (x - enchantment.posX) / 2 + k, y, enchantment.posZ - (z - enchantment.posZ) / 2 + j)) {
                                        if (player.worldObj.getBlock(x, y, z) == Blocks.bookshelf) {
                                            NetworkHandler.NETWORK.sendToAllAround(new ParticleBookExplode(x + Main.rand.nextFloat(), y + Main.rand.nextFloat(), z + Main.rand.nextFloat()), new NetworkRegistry.TargetPoint(player.dimension, x, y, z, 64));
                                            player.worldObj.setBlock(x, y, z, BlocksZp.empty_bookshelf);
                                            player.worldObj.setBlockMetadataWithNotify(x, y, z, Main.rand.nextInt(3), 0);
                                            if (++summary >= max) {
                                                break loop1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!player.capabilities.isCreativeMode) {
                        player.addExperienceLevel(-enchantment.enchantLevels[id]);
                    }
                    if (flag) {
                        itemstack.setItem(Items.enchanted_book);
                    }

                    int j = flag && list.size() > 1 ? Main.rand.nextInt(list.size()) : -1;

                    for (int k = 0; k < list.size(); ++k) {
                        EnchantmentData enchantmentdata = (EnchantmentData) list.get(k);

                        if (!flag || k != j) {
                            if (flag) {
                                Items.enchanted_book.addEnchantment(itemstack, enchantmentdata);
                            } else {
                                itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                            }
                        }
                    }
                    enchantment.onCraftMatrixChanged(enchantment.tableInventory);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean onBlockActivated(BlockBrewingStand st, World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!(p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_) instanceof TileBrewingStand)) {
            p_149727_1_.setTileEntity(p_149727_2_, p_149727_3_, p_149727_4_, new TileBrewingStand());
        }
        if (!p_149727_1_.isRemote) {
            AchievementManager.instance.triggerAchievement(AchievementManager.instance.goodChemist, p_149727_5_);
            p_149727_5_.openGui(Main.instance, GuiHandler.GuiId[0], p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        }
        return true;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void breakBlock(BlockBrewingStand st, World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        TileEntity tileentity = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

        if (tileentity instanceof TileBrewingStand) {
            TileBrewingStand TileBrewingStand = (TileBrewingStand) tileentity;

            for (int i1 = 0; i1 < TileBrewingStand.getSizeInventory(); ++i1) {
                ItemStack itemstack = TileBrewingStand.getStackInSlot(i1);

                if (itemstack != null) {
                    float f = Main.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = Main.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = Main.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = Main.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(p_149749_1_, (float) p_149749_2_ + f, (float) p_149749_3_ + f1, (float) p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) Main.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) Main.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) Main.rand.nextGaussian() * f3;
                        p_149749_1_.spawnEntityInWorld(entityitem);
                    }
                }
            }
            p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateEntity(TileEntityFurnace tileEntityFurnace) {
        boolean flag = tileEntityFurnace.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (tileEntityFurnace.furnaceBurnTime > 0) {
            --tileEntityFurnace.furnaceBurnTime;
        }

        if (!tileEntityFurnace.getWorld().isRemote) {
            if (tileEntityFurnace.furnaceBurnTime != 0 || tileEntityFurnace.getStackInSlot(1) != null && tileEntityFurnace.getStackInSlot(0) != null) {
                if (tileEntityFurnace.furnaceBurnTime == 0 && BlockHook.canSmelt(tileEntityFurnace)) {
                    tileEntityFurnace.currentItemBurnTime = tileEntityFurnace.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(tileEntityFurnace.getStackInSlot(1));

                    if (tileEntityFurnace.furnaceBurnTime > 0) {
                        flag1 = true;

                        if (tileEntityFurnace.getStackInSlot(1) != null) {
                            --tileEntityFurnace.getStackInSlot(1).stackSize;

                            if (tileEntityFurnace.getStackInSlot(1).stackSize == 0) {
                                tileEntityFurnace.setInventorySlotContents(1, tileEntityFurnace.getStackInSlot(1).getItem().getContainerItem(tileEntityFurnace.getStackInSlot(1)));
                            }
                        }
                    }
                }

                if (tileEntityFurnace.isBurning() && BlockHook.canSmelt(tileEntityFurnace)) {
                    if (tileEntityFurnace.getWorld().getTotalWorldTime() % 4 == 0) {
                        ++tileEntityFurnace.furnaceCookTime;
                    }
                    if (tileEntityFurnace.furnaceCookTime == 200) {
                        tileEntityFurnace.furnaceCookTime = 0;
                        tileEntityFurnace.smeltItem();
                        flag1 = true;
                    }
                } else {
                    tileEntityFurnace.furnaceCookTime = 0;
                }
            }

            if (flag != tileEntityFurnace.furnaceBurnTime > 0) {
                flag1 = true;
                BlockFurnace.updateFurnaceBlockState(tileEntityFurnace.furnaceBurnTime > 0, tileEntityFurnace.getWorld(), tileEntityFurnace.xCoord, tileEntityFurnace.yCoord, tileEntityFurnace.zCoord);
            }
        }
    }

    private static boolean canSmelt(TileEntityFurnace tileEntityFurnace) {
        if (tileEntityFurnace.getStackInSlot(0) == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(tileEntityFurnace.getStackInSlot(0));
            if (itemstack == null) {
                return false;
            }
            if (tileEntityFurnace.getStackInSlot(2) == null) {
                return true;
            }
            if (!tileEntityFurnace.getStackInSlot(2).isItemEqual(itemstack)) {
                return false;
            }
            int result = tileEntityFurnace.getStackInSlot(2).stackSize + itemstack.stackSize;
            return result <= tileEntityFurnace.getInventoryStackLimit() && result <= tileEntityFurnace.getStackInSlot(2).getMaxStackSize();
        }
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean smeltItem(TileEntityFurnace tileEntityFurnace) {
        if (tileEntityFurnace.getStackInSlot(0) != null && tileEntityFurnace.getStackInSlot(0).getItem() instanceof AGunBase) {
            ItemStack s = tileEntityFurnace.getStackInSlot(0);
            tileEntityFurnace.setInventorySlotContents(2, new ItemStack(ItemsZp.scrap_material, (int) Math.max((1.0f - (s.getCurrentDurability() / (float) s.getMaxDurability())) * 18, 3)));
            tileEntityFurnace.setInventorySlotContents(0, null);
            return true;
        } else if (tileEntityFurnace.getStackInSlot(0) != null && tileEntityFurnace.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlocksZp.uranium)) {
            if (Main.rand.nextFloat() <= 0.95f) {
                --tileEntityFurnace.getStackInSlot(0).stackSize;
                if (tileEntityFurnace.getStackInSlot(0).stackSize <= 0) {
                    tileEntityFurnace.setInventorySlotContents(0, null);
                }
                return true;
            }
        } else if (tileEntityFurnace.getStackInSlot(0) != null && tileEntityFurnace.getStackInSlot(0).getItem() == ItemsZp.scrap_material) {
            if (Main.rand.nextFloat() <= 0.7f) {
                --tileEntityFurnace.getStackInSlot(0).stackSize;
                if (tileEntityFurnace.getStackInSlot(0).stackSize <= 0) {
                    tileEntityFurnace.setInventorySlotContents(0, null);
                }
                return true;
            }
        } else if (tileEntityFurnace.getStackInSlot(0) != null && tileEntityFurnace.getStackInSlot(0).getItem() == Items.rotten_flesh) {
            if (Main.rand.nextFloat() <= 0.6f) {
                --tileEntityFurnace.getStackInSlot(0).stackSize;
                if (tileEntityFurnace.getStackInSlot(0).stackSize <= 0) {
                    tileEntityFurnace.setInventorySlotContents(0, null);
                }
                return true;
            }
        } else if (tileEntityFurnace.getStackInSlot(0).hasTagCompound()) {
            if (tileEntityFurnace.getStackInSlot(0).getTagCompound().getCompoundTag(Main.MODID).getByte("poisonous") == 1) {
                ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(tileEntityFurnace.getStackInSlot(0));
                itemstack.setTagInfo(Main.MODID, new NBTTagCompound());
                itemstack.getTagCompound().getCompoundTag(Main.MODID).setByte("poisonous", (byte) 1);
                if (tileEntityFurnace.getStackInSlot(2) == null) {
                    tileEntityFurnace.setInventorySlotContents(2, itemstack.copy());
                } else if (tileEntityFurnace.getStackInSlot(2).getItem() == itemstack.getItem()) {
                    tileEntityFurnace.getStackInSlot(2).stackSize += itemstack.stackSize;
                }
                --tileEntityFurnace.getStackInSlot(0).stackSize;
                if (tileEntityFurnace.getStackInSlot(0).stackSize <= 0) {
                    tileEntityFurnace.setInventorySlotContents(0, null);
                }
                return true;
            }
        }
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean canPlaceBlockOn(BlockDeadBush blockDeadBush, Block ground) {
        return ground == BlocksZp.debrisand || ground == BlocksZp.frozen_dirt || ground == Blocks.sand || ground == Blocks.hardened_clay || ground == Blocks.stained_hardened_clay || ground == Blocks.dirt;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean updateTick(BlockCrops blockCrops, World worldIn, int x, int y, int z, Random random) {
        if (Main.rand.nextFloat() < Math.exp(Math.max(y - 100, 0) * 0.1f)) {
            return true;
        }
        return Main.rand.nextBoolean();
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean updateTick(BlockStem blockStem, World worldIn, int x, int y, int z, Random random) {
        if (Main.rand.nextFloat() < Math.exp(Math.max(y - 100, 0) * 0.1f)) {
            return true;
        }
        return Main.rand.nextBoolean();
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean updateTick(BlockReed blockReed, World worldIn, int x, int y, int z, Random random) {
        if (Main.rand.nextFloat() < Math.exp(Math.max(y - 100, 0) * 0.1f)) {
            return true;
        }
        return Main.rand.nextBoolean();
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean updateTick(BlockNetherWart blockNetherWart, World worldIn, int x, int y, int z, Random random) {
        return Main.rand.nextFloat() <= 0.7f;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateTick(BlockDynamicLiquid blockDynamicLiquid, World worldIn, int x, int y, int z, Random random) {
        int l = BlockHook.func_149804_e(blockDynamicLiquid, worldIn, x, y, z);
        byte b0 = 1;

        if (blockDynamicLiquid.getMaterial() == Material.lava && !worldIn.provider.isHellWorld) {
            b0 = 2;
        }

        boolean flag = true;
        int i1 = blockDynamicLiquid.tickRate(worldIn);
        int j1;

        if (l > 0) {
            byte b1 = -100;
            blockDynamicLiquid.field_149815_a = 0;
            int l1 = blockDynamicLiquid.func_149810_a(worldIn, x - 1, y, z, b1);
            l1 = blockDynamicLiquid.func_149810_a(worldIn, x + 1, y, z, l1);
            l1 = blockDynamicLiquid.func_149810_a(worldIn, x, y, z - 1, l1);
            l1 = blockDynamicLiquid.func_149810_a(worldIn, x, y, z + 1, l1);
            j1 = l1 + b0;

            if (j1 >= 8 || l1 < 0) {
                j1 = -1;
            }

            if (BlockHook.func_149804_e(blockDynamicLiquid, worldIn, x, y + 1, z) >= 0) {
                int k1 = BlockHook.func_149804_e(blockDynamicLiquid, worldIn, x, y + 1, z);

                if (k1 >= 8) {
                    j1 = k1;
                } else {
                    j1 = k1 + 8;
                }
            }

            if (blockDynamicLiquid.field_149815_a >= 5 && blockDynamicLiquid.getMaterial() == Material.water) {
                if (worldIn.getBlock(x, y - 1, z).getMaterial().isSolid()) {
                    j1 = 0;
                } else if (worldIn.getBlock(x, y - 1, z).getMaterial() == blockDynamicLiquid.getMaterial() && worldIn.getBlockMetadata(x, y - 1, z) == 0) {
                    j1 = 0;
                }
            }

            if (blockDynamicLiquid.getMaterial() == Material.lava && l < 8 && j1 < 8 && j1 > l && random.nextInt(4) != 0) {
                i1 *= 4;
            }

            if (j1 == l) {
                if (flag) {
                    blockDynamicLiquid.func_149811_n(worldIn, x, y, z);
                }
            } else {
                l = j1;

                if (j1 < 0) {
                    worldIn.setBlockToAir(x, y, z);
                } else {
                    worldIn.setBlockMetadataWithNotify(x, y, z, j1, 2);
                    worldIn.scheduleBlockUpdate(x, y, z, blockDynamicLiquid, i1);
                    worldIn.notifyBlocksOfNeighborChange(x, y, z, blockDynamicLiquid);
                }
            }
        } else {
            blockDynamicLiquid.func_149811_n(worldIn, x, y, z);
        }

        if (blockDynamicLiquid.func_149809_q(worldIn, x, y - 1, z)) {
            if (blockDynamicLiquid.getMaterial() == Material.lava && worldIn.getBlock(x, y - 1, z).getMaterial() == Material.water) {
                worldIn.setBlock(x, y - 1, z, Blocks.stone);
                BlockHook.func_149799_m(worldIn, x, y - 1, z);
                return;
            }

            if (l >= 8) {
                blockDynamicLiquid.func_149813_h(worldIn, x, y - 1, z, l);
            } else {
                blockDynamicLiquid.func_149813_h(worldIn, x, y - 1, z, l + 8);
            }
        } else if (l >= 0 && (l == 0 || blockDynamicLiquid.func_149807_p(worldIn, x, y - 1, z))) {
            boolean[] aboolean = blockDynamicLiquid.func_149808_o(worldIn, x, y, z);
            j1 = l + b0;

            if (l >= 8) {
                j1 = 1;
            }

            if (j1 >= 8) {
                return;
            }

            if (aboolean[0]) {
                blockDynamicLiquid.func_149813_h(worldIn, x - 1, y, z, j1);
            }

            if (aboolean[1]) {
                blockDynamicLiquid.func_149813_h(worldIn, x + 1, y, z, j1);
            }

            if (aboolean[2]) {
                blockDynamicLiquid.func_149813_h(worldIn, x, y, z - 1, j1);
            }

            if (aboolean[3]) {
                blockDynamicLiquid.func_149813_h(worldIn, x, y, z + 1, j1);
            }
        }
    }

    public static int func_149804_e(BlockDynamicLiquid blockDynamicLiquid, World p_149804_1_, int p_149804_2_, int p_149804_3_, int p_149804_4_) {
        return p_149804_1_.getBlock(p_149804_2_, p_149804_3_, p_149804_4_).getMaterial() == blockDynamicLiquid.getMaterial() ? p_149804_1_.getBlockMetadata(p_149804_2_, p_149804_3_, p_149804_4_) : -1;
    }

    public static void func_149799_m(World p_149799_1_, int p_149799_2_, int p_149799_3_, int p_149799_4_) {
        p_149799_1_.playSoundEffect((float) p_149799_2_ + 0.5F, (float) p_149799_3_ + 0.5F, (float) p_149799_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8F);

        for (int l = 0; l < 8; ++l) {
            p_149799_1_.spawnParticle("largesmoke", (double) p_149799_2_ + Math.random(), (double) p_149799_3_ + 1.2D, (double) p_149799_4_ + Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Hook
    public static void updateTick(BlockTorch torch, World worldIn, int x, int y, int z, Random random) {
        if (torch == Blocks.torch) {
            worldIn.setBlock(x, y, z, BlocksZp.torch1, worldIn.getBlockMetadata(x, y, z), 2);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void playSoundWhenFallen(BlockFalling f, World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_) {
        if (p_149828_1_.getBlock(p_149828_2_, p_149828_3_ - 1, p_149828_4_) instanceof BlockPistonBase) {
            p_149828_1_.getBlock(p_149828_2_, p_149828_3_, p_149828_4_).dropBlockAsItem(p_149828_1_, p_149828_2_, p_149828_3_, p_149828_4_, 0, 1);
            p_149828_1_.setBlockToAir(p_149828_2_, p_149828_3_, p_149828_4_);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isFireSource(Block block, World world, int x, int y, int z, ForgeDirection side) {
        if (block == net.minecraft.init.Blocks.netherrack && side == UP && (world.provider instanceof WorldProviderHell)) {
            return true;
        }
        return (world.provider instanceof WorldProviderEnd) && block == net.minecraft.init.Blocks.bedrock && side == UP;
    }
}
