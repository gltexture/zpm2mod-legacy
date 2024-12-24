package ru.BouH_.items.gun.base;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.particle.EntityParticleShell;
import ru.BouH_.entity.particle.EntityParticleSpark;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.crossbow.ItemCrossbow;
import ru.BouH_.items.gun.modules.ItemFlashSuppressor;
import ru.BouH_.items.gun.modules.ItemScope;
import ru.BouH_.items.gun.modules.ItemSilencer;
import ru.BouH_.items.gun.modules.base.ALauncherModuleBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.items.gun.render.Shell;
import ru.BouH_.moving.MovingUtils;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.gun.PacketReloadFinishC;
import ru.BouH_.network.packets.gun.PacketReloading;
import ru.BouH_.network.packets.gun.PacketResetGun;
import ru.BouH_.network.packets.gun.PacketShooting;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.utils.SoundUtils;
import ru.BouH_.zpm_recipes.LubricantGunRecipe;
import ru.BouH_.zpm_recipes.WeaponRepairRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AGunBase extends Item {
    protected final Map<Integer, ModuleInfo> supportedModsMap = new HashMap<>();
    protected final List<EnumModule> supportedModifiers = new ArrayList<>();
    protected final int ammo;
    protected final int cdReload;
    protected final int cdShoot;
    protected final int damage;
    protected final int effectiveDistance;
    protected final float recoilVertical;
    protected final float recoilHorizontal;
    protected final float inaccuracy;
    protected final float inaccuracyInAim;
    protected final float jamChanceMultiplier;
    protected final Shell shell;
    protected final boolean shouldDropShell;
    protected final boolean isAutomatic;
    protected final AAmmo ammoItem;
    protected final String shootSound;
    protected final String reloadSound;
    private final int maxDistance;
    private final float shootVolume;
    private final float addInaccuracyStep;
    private final float maxAddInaccuracy;
    public List<ItemModule> scopeListToRender = new ArrayList<>();

    public List<ItemModule> barrelListToRender = new ArrayList<>();

    public List<ItemModule> underBarrelListToRender = new ArrayList<>();
    protected boolean hasShutterAnim;
    protected String shutterSound;
    protected GunType renderType;
    @SideOnly(Side.CLIENT)
    protected boolean isMuzzleFlashDisabled;
    @SideOnly(Side.CLIENT)
    protected boolean triggerShell;

    protected AGunBase(String weaponName, String shootSound, String reloadSound, GunType renderType, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChanceMultiplier, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        if (FMLLaunchHandler.side().isClient()) {
            MinecraftForgeClient.registerItemRenderer(this, GunItemRender.instance);
        }
        this.setUnlocalizedName(weaponName);

        GameRegistry.addSmelting(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ItemsZp.scrap_material, 9), 0.25f);
        this.addRepair();

        this.setMaxStackSize(1);
        this.setCreativeTab(ItemsZp.weap);
        this.shootSound = shootSound;
        this.reloadSound = reloadSound;
        this.renderType = renderType;
        this.ammo = ammo;
        this.cdReload = cdReload;
        this.jamChanceMultiplier = jamChanceMultiplier;
        this.damage = damage;
        this.cdShoot = cdShoot;
        this.effectiveDistance = effectiveDistance;
        this.maxDistance = maxDistance;
        this.recoilVertical = recoilVertical;
        this.recoilHorizontal = recoilHorizontal;
        this.inaccuracy = inaccuracy;
        this.inaccuracyInAim = inaccuracyInAim;
        this.addInaccuracyStep = addInaccuracyStep;
        this.maxAddInaccuracy = maxAddInaccuracy;
        this.shell = ammoItem.getShell();
        this.shouldDropShell = shouldDropShell;
        this.ammoItem = ammoItem;
        this.shootVolume = ammoItem.getVolume();
        this.isAutomatic = isAutomatic;
        this.setNoRepair();
    }

    public static void resetPartiallyGun(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            NetworkHandler.NETWORK.sendToAllAround(new PacketResetGun(player.getEntityId()), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
        }
        player.getEntityData().setBoolean("isReloading", false);
        player.getEntityData().setBoolean("isUnReloading", false);
        player.getEntityData().setBoolean("interruptReloading", false);
        player.getEntityData().setInteger("cdReload", 0);
        player.getEntityData().setInteger("cdShoot", 0);
        player.getEntityData().setInteger("cdUse", player.worldObj.isRemote && player instanceof EntityPlayerSP ? 6 : 5);
        player.getEntityData().setInteger("cdFlash", 0);
    }

    protected void addRepair() {
        WeaponRepairRecipe.addRepairRecipe(CraftingManager.getInstance(), new ItemStack(this), new ItemStack(this.repairItem()));
        LubricantGunRecipe.addRepairRecipe(CraftingManager.getInstance(), new ItemStack(this));
    }

    public Item repairItem() {
        return ItemsZp.repair;
    }

    @SideOnly(Side.CLIENT)
    public void shootPacket(ItemStack stack, EntityPlayer player) {
        if (this.isReadyToShoot(stack, player)) {
            if (Main.settingsZp.autoReload.isFlag()) {
                if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
                    if (!this.canShoot(stack, player)) {
                        if (this.canStartReloading(player, stack, false)) {
                            NetworkHandler.NETWORK.sendToServer(new PacketReloading(false));
                            this.reloadStart(stack, player, false);
                            return;
                        }
                    }
                }
            }
            NetworkHandler.NETWORK.sendToServer(new PacketShooting(player.rotationPitch, player.rotationYaw, GunItemRender.instance.isInScope()));
            this.shoot(player, player.getHeldItem(), player.rotationYaw, player.rotationPitch, GunItemRender.instance.isInScope());
        }
    }

    @SideOnly(Side.CLIENT)
    public void reloadPacket(ItemStack stack, EntityPlayer player, boolean flag) {
        if (this.canStartReloading(player, stack, flag)) {
            NetworkHandler.NETWORK.sendToServer(new PacketReloading(flag));
            this.reloadStart(stack, player, flag);
        }
    }

    public boolean reload(ItemStack stack, EntityPlayer player, boolean unReloading) {
        if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
            ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) this.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod();
            return aLauncherModuleBase.reload(this, stack, player, unReloading);
        } else {
            return this.makeReload(stack, player, unReloading);
        }
    }

    public void reloadFinish(ItemStack stack, EntityPlayer player, boolean shouldResetJam) {
        if (!player.worldObj.isRemote) {
            NetworkHandler.NETWORK.sendToAllAround(new PacketReloadFinishC(player.getEntityId(), stack), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
        }
        this.onReloadPost(stack, player);
        AGunBase.resetPartiallyGun(player);
        if (shouldResetJam) {
            stack.getTagCompound().getCompoundTag(Main.MODID).setBoolean("isJammed", false);
        }
    }

    public void reloadStart(ItemStack stack, EntityPlayer player, boolean unReload) {
        this.onReloadPre(stack, player);
        AGunBase aGunBase = (AGunBase) stack.getItem();
        if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
            ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) this.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod();
            aLauncherModuleBase.playReloadSound(player);
            player.getEntityData().setInteger("cdReload", aLauncherModuleBase.getReloadCd());
        } else {
            if (player.worldObj.isRemote) {
                this.playReloadSound(player);
            }
            player.getEntityData().setInteger("cdReload", stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed") ? Math.max(aGunBase.getCdReload(), 40) : aGunBase.getCdReload());
        }
        if (unReload) {
            player.getEntityData().setBoolean("isUnReloading", true);
        } else {
            player.getEntityData().setBoolean("isReloading", true);
        }
    }

    public void shoot(EntityPlayer player, ItemStack stack, float rotationPitch, float rotationYaw, boolean scoping) {
        World world = player.worldObj;
        if (this.canShoot(stack, player)) {
            this.onShootPre(stack, player);
            if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
                ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) this.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod();
                aLauncherModuleBase.shoot(this, stack, player, rotationPitch, rotationYaw, scoping);
                if (!world.isRemote) {
                    this.damageWeapon(player, stack);
                }
                stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("metaAmmo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") - 1);
            } else {
                player.getEntityData().setInteger("cdFlash", 2);
                if (!world.isRemote) {
                    this.makeShot(player, rotationPitch, rotationYaw, scoping);
                    this.triggerAchievement(player);
                    if (player.worldObj.getGameRules().getGameRuleBooleanValue("canWeaponJam")) {
                        if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("reserveShots") == 0) {
                            this.damageWeapon(player, stack);
                            double i1 = stack.getCurrentDurability() / (double) stack.getMaxDurability();
                            if (Math.pow(i1, Math.E / this.jamChanceMultiplier) >= Main.rand.nextFloat()) {
                                stack.getTagCompound().getCompoundTag(Main.MODID).setBoolean("isJammed", true);
                            }
                        } else {
                            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("reserveShots", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("reserveShots") - 1);
                        }
                    }
                } else {
                    if (player instanceof EntityPlayerSP) {
                        this.addRecoil(this.getRecoilHorizontal(), this.getRecoilVertical());
                    }
                    this.gunFX(stack, player, this.shouldDropShell);
                    this.playShootSound(stack, player);
                }
                PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
                playerMiscData.setGunInaccuracyTimer(Math.max(6 - SkillManager.instance.getSkillPoints(SkillManager.instance.gunSmith, player) / 3, 0));
                float f1 = this.maxAddInaccuracy * (1.0f - SkillManager.instance.getSkillBonus(SkillManager.instance.gunSmith, player, 0.03f));
                if (playerMiscData.getGunInaccuracy() < f1) {
                    playerMiscData.setGunInaccuracy(Math.min(playerMiscData.getGunInaccuracy() + this.addInaccuracyStep, f1));
                }
                stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") - 1);
                if (!this.hasShutterAnim && this.getCdShoot() < 20 && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == 0) {
                    player.getEntityData().setInteger("cdShoot", MathHelper.clamp_int(this.getCdShoot(), 8, 20));
                } else {
                    player.getEntityData().setInteger("cdShoot", this.getCdShoot());
                }
            }
        } else {
            if (world.isRemote) {
                SoundUtils.playClientSound(player, (Main.MODID + ":holster"), 2.5f, this.getScaledPitch(player));
                if (player instanceof EntityPlayerSP) {
                    GunItemRender.instance.addRecoilAnimation(0.5f);
                }
            }
            player.getEntityData().setInteger("cdShoot", 8);
        }
        this.onShootPost(stack, player);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.hasTagCompound()) {
            stack.setTagInfo(Main.MODID, new NBTTagCompound());
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.SCOPE.toString(), -1);
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.BARREL.toString(), -1);
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.UNDERBARREL.toString(), -1);
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("fireMode", this.getStandardFireMode().getId());
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("reserveShots", 0);
        }
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            if (world.isRemote) {
                if (isSelected) {
                    if (this.hasShutterAnim) {
                        if (player.getEntityData().getInteger("cdShoot") == this.getCdShoot() * 0.5f) {
                            if (this.triggerShell) {
                                this.playShutterAnim(player, stack);
                            }
                        }
                    }
                    this.updateSuitableModifiersStack(player);
                } else if (this.triggerShell) {
                    if (stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isEquipped")) {
                        this.playShutterAnim(player, stack);
                    }
                }
            }
            stack.getTagCompound().getCompoundTag(Main.MODID).setBoolean("isEquipped", isSelected);
        }
    }

    public void damageWeapon(EntityPlayer player, ItemStack stack) {
        if (!player.capabilities.isCreativeMode) {
            if (!player.worldObj.getGameRules().getGameRuleBooleanValue("doWeaponUnbreakable")) {
                if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("shootCount") == 0) {
                    stack.damageItem(1, player);
                    if (stack.stackSize == 0) {
                        this.clearAllModifiers(player, stack);
                        player.worldObj.playSoundAtEntity(player, "random.break", 1.0f, 0.6f + Main.rand.nextFloat() * 0.2f);
                        player.attackEntityFrom(DamageSource.generic, 1);
                        player.setCurrentItemOrArmor(0, null);
                    }
                    int shootCount = this.getCdShoot() <= 3 ? 2 : 1;
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("shootCount", shootCount);
                } else {
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("shootCount", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("shootCount") - 1);
                }
            }
        }
    }

    protected void triggerAchievement(EntityPlayer entityPlayer) {
        AchievementManager.instance.triggerAchievement(AchievementManager.instance.firstShot, entityPlayer);
    }

    public abstract void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping);

    public abstract boolean makeReload(ItemStack stack, EntityPlayer player, boolean unReloading);

    @SideOnly(Side.CLIENT)
    public abstract List<String> weaponDescriptionText(ItemStack stack);

    public float collectModuleInaccuracy(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().inaccuracyModifier;
            }
        }
        return mod;
    }

    public float collectModuleDistance(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().distanceModifier;
            }
        }
        return mod;
    }

    public float collectModuleStability(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().stabilityModifier;
            }
        }
        return mod;
    }

    public float collectModuleHorizontalRecoil(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().recoilHorizontalModifier;
            }
        }
        return mod;
    }

    public float collectModuleVerticalRecoil(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().recoilVerticalModifier;
            }
        }
        return mod;
    }

    public float collectModuleDamage(ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().damageModifier;
            }
        }
        return mod;
    }

    public void onShootPre(ItemStack stack, EntityPlayer player) {
    }

    public void onShootPost(ItemStack stack, EntityPlayer player) {
    }

    public void onReloadPre(ItemStack stack, EntityPlayer player) {
    }

    public void onReloadPost(ItemStack stack, EntityPlayer player) {
    }


    public boolean shouldDropShell(ItemStack stack) {
        return this.shouldDropShell && this.shell != null && !stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed");
    }

    @SideOnly(Side.CLIENT)
    public boolean isPlayerInOpticScope(ItemStack stack) {
        boolean flag = false;
        if (stack != null && this.getCurrentFireMode(stack) != EnumFireModes.SAFE && this.getCurrentFireMode(stack) != EnumFireModes.LAUNCHER) {
            if (GunItemRender.instance.isInScope()) {
                ModuleInfo modScope = this.getCurrentModule(stack, EnumModule.SCOPE);
                if (modScope != null) {
                    ItemScope itemScope = (ItemScope) modScope.getMod();
                    if (itemScope.getFov() > 0) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    @SideOnly(Side.CLIENT)
    public void sparksFX(EntityPlayer player, ItemStack stack) {
        double i1 = (double) stack.getCurrentDurability() / (double) stack.getMaxDurability();
        if (i1 >= 0.2f && Main.rand.nextFloat() <= i1) {
            int max = Main.rand.nextInt((int) (i1 * 10)) + (int) (i1 * 5) + 1;
            Vec3 lookVec = player.getLookVec();
            float f1 = this.particleSmokePosMultiplier();
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
                if (!this.isPlayerInOpticScope(stack)) {
                    posX -= MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.125f;
                    posZ -= MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.125f;
                }
            }
            for (int i = 0; i < max; i++) {
                if (!player.isInsideOfMaterial(Material.water)) {
                    float f = 0.2f + i * 0.2f;
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleSpark(player.worldObj, posX, posY, posZ, lookVec.xCoord * 0.1f * f, lookVec.yCoord * 0.1f * f, lookVec.zCoord * 0.1f * f));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void gunFX(ItemStack stack, EntityPlayer player, boolean dropShell) {
        if (stack.hasTagCompound() && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("reserveShots") == 0) {
            this.sparksFX(player, stack);
        }
        if (!this.disableSmoke(player.getHeldItem())) {
            this.gunSmokeFX(player, stack);
        }
        if (dropShell) {
            if (player instanceof EntityPlayerSP && this.hasShutterAnim) {
                if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                    this.triggerShell = true;
                }
            } else {
                this.gunShellFX(player, stack);
            }
        }
        if (player instanceof EntityPlayerSP) {
            GunItemRender.instance.muzzleFlashTimer = 1;
            GunItemRender.instance.muzzleFlashId = Main.rand.nextInt(6);
        }
    }

    @SideOnly(Side.CLIENT)
    public float particleShellPosMultiplier() {
        return this.renderType == GunType.PISTOL ? 0.8f : 0.55f;
    }

    @SideOnly(Side.CLIENT)
    public float particleSmokePosMultiplier() {
        return this.renderType == GunType.PISTOL ? 1.0f : 1.25f;
    }

    @SideOnly(Side.CLIENT)
    public int particleSmokeCount() {
        return Math.max(this.damage / 10, 1);
    }

    @SideOnly(Side.CLIENT)
    public void gunSmokeFX(EntityPlayer player, ItemStack stack) {
        this.ammoItem.generateSmoke(player, stack, this, this.particleSmokeCount());
    }

    @SideOnly(Side.CLIENT)
    public void gunShellFX(EntityPlayer player, ItemStack stack) {
        Vec3 lookVec = player.getLookVec();
        Vec3 vec31 = Vec3.createVectorHelper(-0.35f, -0.2f, -0.1f);
        double yConst = -MathHelper.sin(0.5f * (player.rotationPitch + 360) / 360.0F * (float) Math.PI);

        vec31.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0F);
        vec31.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);
        vec31 = vec31.addVector(player.posX, player.boundingBox.maxY - 0.1f + yConst * 0.15f, player.posZ);

        Vec3 vec3 = player.getLookVec().addVector(512, -256, 0);
        vec3.rotateAroundY((float) -Math.PI);
        vec3.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);

        double xPos = lookVec.xCoord;
        double yPos = lookVec.yCoord;
        double zPos = lookVec.zCoord;

        double f1;
        if (player instanceof EntityPlayerSP) {
            f1 = this.particleShellPosMultiplier() + GunItemRender.instance.scopingProgress() * 0.005d;
            f1 += (70 - Minecraft.getMinecraft().gameSettings.fovSetting) * 0.007d;
        } else {
            f1 = this.particleShellPosMultiplier();
        }
        xPos *= f1;
        yPos *= f1;
        zPos *= f1;

        double posX = vec31.xCoord + xPos;
        double posY = vec31.yCoord + yPos;
        double posZ = vec31.zCoord + zPos;

        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleShell(player.worldObj, this.shell, posX, posY, posZ, vec3.xCoord + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 200.0f, -vec3.yCoord + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 200.0f, vec3.zCoord + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 200.0f));
    }

    @SideOnly(Side.CLIENT)
    public void addRecoil(float strengthHorizontal, float strengthVertical) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        float mix = Math.max(1.0f - PlayerMiscData.getPlayerData(player).getGunInaccuracy() * 0.2f, 0.25f);
        strengthHorizontal *= this.recoilHorizontalModifier(player, player.getHeldItem());
        strengthVertical *= this.recoilVerticalModifier(player, player.getHeldItem());
        player.rotationYaw += (strengthHorizontal * mix) * ((Main.rand.nextFloat() * 2.0f) - 1.0f);
        player.rotationPitch -= (strengthVertical * mix);
        player.cameraPitch += (strengthVertical * mix) * 0.25f;
        float strength = Math.max(strengthHorizontal, strengthVertical) * 0.8f;
        GunItemRender.instance.addRecoilAnimation(Math.min(strength, 4.0f));
        GunItemRender.instance.addShake(strength);
    }

    @SideOnly(Side.CLIENT)
    public GunType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(GunType type) {
        this.renderType = type;
    }

    public boolean isInReloadingAnim(EntityPlayer player) {
        return this.isInReloading(player) || this.isInUnReloading(player);
    }

    public boolean isInReloading(EntityPlayer player) {
        return player.getEntityData().getBoolean("isReloading");
    }

    public boolean isInUnReloading(EntityPlayer player) {
        return player.getEntityData().getBoolean("isUnReloading");
    }

    @SideOnly(Side.CLIENT)
    public void disableMuzzleFlash() {
        this.isMuzzleFlashDisabled = true;
    }

    @SideOnly(Side.CLIENT)
    public boolean disableSmoke(ItemStack stack) {
        return this.getCurrentModule(stack, EnumModule.BARREL) != null && this.getCurrentModule(stack, EnumModule.BARREL).getMod() instanceof ItemFlashSuppressor;
    }

    @SideOnly(Side.CLIENT)
    public boolean addTranslateMuzzle1st(EntityPlayer player, ItemStack stack) {
        boolean flag = this.getCurrentModule(stack, EnumModule.BARREL) != null;
        return !this.isMuzzleFlashDisabled && !this.isSilenced(stack) && !flag;
    }

    @SideOnly(Side.CLIENT)
    public boolean addTranslateMuzzle3d(EntityPlayer player, ItemStack stack) {
        boolean flag = this.getCurrentModule(stack, EnumModule.BARREL) != null;
        return !this.isMuzzleFlashDisabled && !this.isSilenced(stack) && !flag && !this.isInReloadingAnim(player);
    }

    public boolean isOnLadder(EntityPlayer player) {
        return this.renderType != GunType.PISTOL2 && this.renderType != GunType.PISTOL && player.isOnLadder() && !player.onGround;
    }

    public float getScaledPitch(EntityPlayer player) {
        return player.isInsideOfMaterial(Material.water) || player.isInsideOfMaterial(Material.lava) ? 0.0f : Main.rand.nextFloat() * 0.2f + 1.0f;
    }

    public void switchFireMode(ItemStack stack) {
        int current = this.availableFireModes(stack).indexOf(this.getCurrentFireMode(stack));
        if (current == this.availableFireModes(stack).size() - 1) {
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("fireMode", EnumFireModes.SAFE.getId());
        } else {
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("fireMode", this.availableFireModes(stack).get(current + 1).getId());
        }
    }

    public void setFireMode(ItemStack stack, EnumFireModes enumFireModes) {
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("fireMode", enumFireModes.getId());
    }

    public void onModuleRemoved(EntityPlayer player, ItemStack stack, ItemStack itemModule) {
        if (itemModule.getItem() instanceof ItemModule) {
            if (itemModule.getItem() instanceof ALauncherModuleBase) {
                ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) itemModule.getItem();
                if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
                    this.setFireMode(stack, this.getStandardFireMode());
                }
                if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") > 0) {
                    ItemStack stack1 = new ItemStack(aLauncherModuleBase.getAmmo(), stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo"));
                    if (!player.inventory.addItemStackToInventory(stack1)) {
                        player.dropItemWithOffset(stack1.getItem(), stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo"), 1.0f);
                    }
                }
                stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("metaAmmo", 0);
            }
        }
    }

    public boolean canInterruptReloading(EntityPlayer player, ItemStack stack) {
        return false;
    }

    public void onModuleAdded(EntityPlayer player, ItemStack stack, ItemStack itemModule) {
    }

    public EnumFireModes getStandardFireMode() {
        return this.isAuto() ? EnumFireModes.AUTO : EnumFireModes.SEMI;
    }

    public EnumFireModes getCurrentFireMode(ItemStack stack) {
        return EnumFireModes.getEnumFireMode(stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("fireMode"));
    }

    public List<EnumFireModes> availableFireModes(ItemStack stack) {
        List<EnumFireModes> list = new ArrayList<>();
        list.add(EnumFireModes.SAFE);
        list.add(EnumFireModes.SEMI);
        if (this.isAuto()) {
            list.add(EnumFireModes.AUTO);
        }
        ModuleInfo modUnderBarrel = this.getCurrentModule(stack, EnumModule.UNDERBARREL);
        if (modUnderBarrel != null && modUnderBarrel.getMod() instanceof ALauncherModuleBase) {
            list.add(EnumFireModes.LAUNCHER);
        }
        return list;
    }

    public void playReloadSound(EntityPlayer player) {
        SoundUtils.playClientSound(player, Main.MODID + ":" + this.getReloadSound(), 2.5f, this.getScaledPitch(player));
    }

    public void playShootSound(ItemStack stack, EntityPlayer player) {
        float f1 = this.getShootVolume(stack);
        if (this.isSilenced(stack)) {
            SoundUtils.playClientSound(player, Main.MODID + ":silenced_s", f1, this.getScaledPitch(player) * (8.0f / this.shootVolume));
        } else {
            String snd = this.getShootSound();
            if (this == ItemsZp.mp5 && this.getCurrentModule(stack, EnumModule.UNDERBARREL) != null && this.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod() instanceof ALauncherModuleBase) {
                snd = "mp5_hl_s";
            }
            SoundUtils.playClientSound(player, Main.MODID + ":" + snd, f1, this.getScaledPitch(player));
        }
        if (!(player instanceof EntityPlayerSP)) {
            if (!(this instanceof ItemCrossbow) && Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(player) >= f1 * 16) {
                SoundUtils.playClientSound(player, Main.MODID + ":far_s", Math.min(f1 * 2.0f, 16), this.getScaledPitch(player));
            }
        }
    }

    public float getShootVolume(ItemStack stack) {
        float volume = Math.min(this.shootVolume, 16);
        if (this.isSilenced(stack)) {
            volume *= 0.3f;
        }
        return volume;
    }

    public boolean isReadyToShoot(ItemStack stack, EntityPlayer player) {
        return player.getEntityData().getInteger("cdUse") == 0 && !this.isInReloadingAnim(player) && player.getEntityData().getInteger("cdShoot") == 0 && !this.isOnLadder(player);
    }

    public boolean canShoot(ItemStack stack, EntityPlayer player) {
        if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
            return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") > 0;
        } else {
            return !stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed") && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean canActivateCross(ItemStack stack, EntityPlayer player) {
        return player.getEntityData().getInteger("cdUse") == 0 && !this.isInReloadingAnim(player) && !this.isOnLadder(player) && this.getCurrentFireMode(stack) != EnumFireModes.SAFE;
    }

    public boolean canStartReloading(EntityPlayer player, ItemStack stack, boolean unReload) {
        if (this.isInReloadingAnim(player) || player.getEntityData().getInteger("cdUse") > 0) {
            return false;
        }
        if (this.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
            ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) this.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod();
            if (unReload) {
                return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") > 0;
            } else {
                return player.inventory.hasItem(aLauncherModuleBase.getAmmo()) && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") < aLauncherModuleBase.getMaxAmmo();
            }
        } else {
            if (stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
                return true;
            }
            if (unReload) {
                return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0;
            } else {
                return player.inventory.hasItem(this.getAmmo()) && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") < this.getMaxAmmo();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void playShutterAnim(EntityPlayer player, ItemStack stack) {
        SoundUtils.playClientSound(player, (Main.MODID + ":" + this.shutterSound), 1.5f, 1.0f);
        this.triggerShell = false;
        this.gunShellFX(player, stack);
        GunItemRender.instance.addShutterAnimation();
    }

    public void enableShutterAnim(String sound) {
        this.hasShutterAnim = true;
        this.shutterSound = sound;
    }

    public void addModule(ModuleInfo mod) {
        this.getAllSupportedModsMap().put(mod.getMod().getId(), mod);
        if (!this.supportedModifiers.contains(mod.getMod().getEnumModule())) {
            this.supportedModifiers.add(mod.getMod().getEnumModule());
        }
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if (stack.hasTagCompound() && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("reserveShots") > 0) {
            tooltip.add(EnumChatFormatting.DARK_PURPLE + I18n.format("weapon.misc.lubricate"));
        }
        tooltip.add(String.format("%s: %s", I18n.format("weapon.gui.desc.ammoType") + EnumChatFormatting.GRAY, new ItemStack(this.getAmmo()).getDisplayName()));
        tooltip.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.magCapacity") + EnumChatFormatting.GRAY, this.getMaxAmmo(), I18n.format("weapon.gui.desc.ammo")));
    }

    public List<ModuleInfo> getAllModules(ItemStack stack) {
        List<ModuleInfo> modifiers = new ArrayList<>();
        ModuleInfo modBarrel = this.getCurrentModule(stack, EnumModule.BARREL);
        ModuleInfo modScope = this.getCurrentModule(stack, EnumModule.SCOPE);
        ModuleInfo modUnderBarrel = this.getCurrentModule(stack, EnumModule.UNDERBARREL);
        if (modBarrel != null) {
            modifiers.add(modBarrel);
        }
        if (modScope != null) {
            modifiers.add(modScope);
        }
        if (modUnderBarrel != null) {
            modifiers.add(modUnderBarrel);
        }
        return modifiers;
    }

    public ModuleInfo getCurrentModule(ItemStack stack, EnumModule enumMod) {
        if (stack.getTagCompound().getCompoundTag(Main.MODID).hasKey(enumMod.toString())) {
            ModuleInfo moduleInfo = this.getAllSupportedModsMap().get(stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(enumMod.toString()));
            if (moduleInfo == null || enumMod != moduleInfo.getMod().getEnumModule()) {
                return null;
            }
            return moduleInfo;
        }
        return null;
    }

    public float distanceModifier(EntityPlayer player, ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().getModifiedDistance(player, false);
            }
        }
        return mod;
    }

    public float damageModifier(EntityPlayer player, ItemStack stack) {
        float mod = 1.0f;
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().getModifiedDamage(player, false);
            }
        }
        return mod;
    }

    public float inaccuracyModifier(EntityPlayer player, ItemStack stack, boolean scoping) {
        float mod = 1.0f;
        float addMod = 1.0f;
        PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
        if (!this.getAllModules(stack).isEmpty()) {
            for (ModuleInfo modifier : this.getAllModules(stack)) {
                mod += modifier.getMod().getModifiedInaccuracy(player, scoping);
                addMod -= modifier.getMod().getModifiedStability(player, scoping);
            }
        }
        mod += playerMiscData.getGunInaccuracy() * addMod;
        if (MovingUtils.isSwimming(player)) {
            mod = 3.0f;
        } else {
            if (!player.onGround) {
                mod += 1.0f;
            }
            if (player.isSprinting()) {
                mod += 0.5f;
            }
            if (PlayerMiscData.getPlayerData(player).isLying()) {
                mod -= 0.1f;
            } else if (player.isSneaking()) {
                mod -= 0.05f;
            }
            double f1 = player.posX - player.lastTickPosX;
            double f2 = player.posZ - player.lastTickPosZ;
            mod += MathHelper.sqrt_double(f1 * f1 + f2 * f2);
        }
        return mod;
    }

    public float recoilHorizontalModifier(EntityPlayer player, ItemStack stack) {
        float mod = 1.0f;
        if (MovingUtils.isSwimming(player)) {
            mod = 3.0f;
        } else {
            if (!this.getAllModules(stack).isEmpty()) {
                for (ModuleInfo modifier : this.getAllModules(stack)) {
                    mod += modifier.getMod().getModifiedHorizontalRecoil(player, GunItemRender.instance.isInScope());
                }
            }
            if (!player.onGround) {
                mod += 0.3f;
            }
            if (player.isSprinting()) {
                mod += 0.2f;
            }
            if (PlayerMiscData.getPlayerData(player).isLying()) {
                mod -= 0.15f;
            } else if (player.isSneaking()) {
                mod -= 0.05f;
            }
            if (GunItemRender.instance.isInScope()) {
                mod -= 0.1f;
            }
            mod += MathHelper.sqrt_double(player.motionX * player.motionX + player.motionZ * player.motionZ);
        }
        return Math.max(mod, 0.05f);
    }

    public float recoilVerticalModifier(EntityPlayer player, ItemStack stack) {
        float mod = 1.0f;
        if (MovingUtils.isSwimming(player)) {
            mod = 3.0f;
        } else {
            if (!this.getAllModules(stack).isEmpty()) {
                for (ModuleInfo modifier : this.getAllModules(stack)) {
                    mod += modifier.getMod().getModifiedVerticalRecoil(player, GunItemRender.instance.isInScope());
                }
            }
            if (!player.onGround) {
                mod += 0.3f;
            }
            if (player.isSprinting()) {
                mod += 0.2f;
            }
            if (PlayerMiscData.getPlayerData(player).isLying()) {
                mod -= 0.15f;
            } else if (player.isSneaking()) {
                mod -= 0.05f;
            }
            if (GunItemRender.instance.isInScope()) {
                mod -= 0.1f;
            }
            mod += MathHelper.sqrt_double(player.motionX * player.motionX + player.motionZ * player.motionZ);
        }
        return Math.max(mod, 0.05f);
    }

    public List<ItemModule> getListToRender(EnumModule enumModifier) {
        if (enumModifier == EnumModule.BARREL) {
            return this.barrelListToRender;
        } else if (enumModifier == EnumModule.UNDERBARREL) {
            return this.underBarrelListToRender;
        } else if (enumModifier == EnumModule.SCOPE) {
            return this.scopeListToRender;
        }
        return null;
    }

    public Map<Integer, ModuleInfo> getAllSupportedModsMap() {
        return this.supportedModsMap;
    }

    public List<ModuleInfo> getSupportedModsList(EnumModule enumModule) {
        List<ModuleInfo> list = new ArrayList<>();
        for (ModuleInfo modifier : this.getAllSupportedModsMap().values()) {
            if (modifier.getMod().getEnumModule().equals(enumModule)) {
                list.add(modifier);
            }
        }
        return list;
    }

    public List<EnumModule> getSupportedModifiers() {
        return this.supportedModifiers;
    }

    public boolean isSilenced(ItemStack stack) {
        return this.getCurrentModule(stack, EnumModule.BARREL) != null && this.getCurrentModule(stack, EnumModule.BARREL).getMod() instanceof ItemSilencer;
    }

    public int getCurrentScopeId(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.SCOPE.toString());
    }

    public int getCurrentBarrelId(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.BARREL.toString());
    }

    public int getCurrentUnderBarrelId(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.UNDERBARREL.toString());
    }

    public void setModifier(ItemStack stack, int id) {
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(ItemModule.getModById(id).getEnumModule().toString(), id);
    }

    public void setScope(ItemStack stack, int id) {
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.SCOPE.toString(), id);
    }

    public void setBarrel(ItemStack stack, int id) {
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.BARREL.toString(), id);
    }

    public void setUnderBarrel(ItemStack stack, int id) {
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.UNDERBARREL.toString(), id);
    }

    public void clearAllModifiers(EntityPlayer player, ItemStack stack) {
        if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.UNDERBARREL.toString()) >= 0) {
            this.clearUnderBarrel(player, stack);
        }
        if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.BARREL.toString()) >= 0) {
            this.clearBarrel(player, stack);
        }
        if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.SCOPE.toString()) >= 0) {
            this.clearScope(player, stack);
        }
    }

    public void clearModifier(EntityPlayer player, ItemStack stack, EnumModule enumModifier) {
        if (enumModifier == EnumModule.UNDERBARREL) {
            this.clearUnderBarrel(player, stack);
        } else if (enumModifier == EnumModule.BARREL) {
            this.clearBarrel(player, stack);
        } else if (enumModifier == EnumModule.SCOPE) {
            this.clearScope(player, stack);
        }
    }

    public void clearScope(EntityPlayer player, ItemStack stack) {
        int currentId = stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.SCOPE.toString());
        if (currentId >= 0) {
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.SCOPE.toString(), -1);
        }
        if (!player.worldObj.isRemote) {
            if (currentId >= 0) {
                ItemStack stack1 = new ItemStack(ItemModule.getModById(currentId));
                this.onModuleRemoved(player, stack, stack1);
                if (!player.inventory.addItemStackToInventory(stack1)) {
                    player.dropItemWithOffset(stack1.getItem(), 1, 1.0f);
                }
            }
        }
    }

    public void clearBarrel(EntityPlayer player, ItemStack stack) {
        int currentId = stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.BARREL.toString());
        if (currentId >= 0) {
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.BARREL.toString(), -1);
        }
        if (!player.worldObj.isRemote) {
            if (currentId >= 0) {
                ItemStack stack1 = new ItemStack(ItemModule.getModById(currentId));
                this.onModuleRemoved(player, stack, stack1);
                if (!player.inventory.addItemStackToInventory(stack1)) {
                    player.dropItemWithOffset(stack1.getItem(), 1, 1.0f);
                }
            }
        }
    }

    public void clearUnderBarrel(EntityPlayer player, ItemStack stack) {
        int currentId = stack.getTagCompound().getCompoundTag(Main.MODID).getInteger(EnumModule.UNDERBARREL.toString());
        if (currentId >= 0) {
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.UNDERBARREL.toString(), -1);
        }
        if (!player.worldObj.isRemote) {
            if (currentId >= 0) {
                ItemStack stack1 = new ItemStack(ItemModule.getModById(currentId));
                this.onModuleRemoved(player, stack, stack1);
                if (!player.inventory.addItemStackToInventory(stack1)) {
                    player.dropItemWithOffset(stack1.getItem(), 1, 1.0f);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void updateSuitableModifiersStack(EntityPlayer pl) {
        for (ModuleInfo modifier : this.getAllSupportedModsMap().values()) {
            ItemModule mod = modifier.getMod();
            if (pl.inventory.hasItem(mod)) {
                if (modifier.getMod().getEnumModule() == EnumModule.SCOPE && !this.scopeListToRender.contains(mod)) {
                    this.scopeListToRender.add(mod);
                }
                if (modifier.getMod().getEnumModule() == EnumModule.BARREL && !this.barrelListToRender.contains(mod)) {
                    this.barrelListToRender.add(mod);
                }
                if (modifier.getMod().getEnumModule() == EnumModule.UNDERBARREL && !this.underBarrelListToRender.contains(mod)) {
                    this.underBarrelListToRender.add(mod);
                }
            }
        }
        this.scopeListToRender.removeIf(e -> !pl.inventory.hasItem(e));
        this.barrelListToRender.removeIf(e -> !pl.inventory.hasItem(e));
        this.underBarrelListToRender.removeIf(e -> !pl.inventory.hasItem(e));
    }

    public String getShootSound() {
        return this.shootSound;
    }

    public String getReloadSound() {
        return this.reloadSound;
    }

    public boolean isAuto() {
        return this.isAutomatic;
    }

    public int getEffectiveDistance() {
        return this.effectiveDistance;
    }

    public int getMaxDistance() {
        return Math.max(this.maxDistance, this.getEffectiveDistance());
    }

    public int getMaxAmmo() {
        return this.ammo;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getCdReload() {
        return this.cdReload;
    }

    public int getCdShoot() {
        return Math.min(this.cdShoot, this.cdReload);
    }

    public int getFireRate() {
        return this.getMaxAmmo() > 1 ? this.getCdShoot() : this.getCdReload();
    }

    public AAmmo getAAmmo() {
        return this.ammoItem;
    }

    public Item getAmmo() {
        return this.ammoItem.getItem();
    }

    public float getInaccuracy() {
        return this.inaccuracy;
    }

    public float getInaccuracyInAim() {
        return this.inaccuracyInAim;
    }

    public float getRecoilHorizontal() {
        return this.recoilHorizontal;
    }

    public float getRecoilVertical() {
        return this.recoilVertical;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return true;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        return true;
    }

    public enum GunType {
        PISTOL,
        PISTOL2,
        RIFLE,
        RIFLE2,
        CROSSBOW,
        RPG
    }
}
