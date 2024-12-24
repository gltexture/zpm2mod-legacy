package ru.BouH_.gameplay;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.achievements.AchievementZp;
import ru.BouH_.blocks.BlockMine;
import ru.BouH_.blocks.BlockOreZp;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.init.LootCasesZp;
import ru.BouH_.items.food.FoodWaterFish;
import ru.BouH_.items.food.FoodZp;
import ru.BouH_.items.misc.ItemRocket;
import ru.BouH_.items.tools.ItemMetalScissors;
import ru.BouH_.items.tools.ItemWrench;
import ru.BouH_.misc.DamageSourceZp;
import ru.BouH_.moving.MovingUtils;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketBrightnessInfo;
import ru.BouH_.network.packets.misc.ping.PacketPingClient;
import ru.BouH_.network.packets.nbt.PacketAchSkillData;
import ru.BouH_.network.packets.nbt.PacketMiscPlayerNbtInfo;
import ru.BouH_.network.packets.nbt.PacketSkillProgressData;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.type.WorldTypeCrazyZp;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    public static final Map<Item, Integer> foodAdditionalSaturationMap = new HashMap<>();
    public static final Map<Item, Float> meleeRangeBonus = new HashMap<>();
    public static final Map<Item, Integer> meleeSpeedBonus = new HashMap<>();

    public static PlayerManager instance = new PlayerManager();

    static {
        foodAdditionalSaturationMap.put(Items.apple, 3);
        foodAdditionalSaturationMap.put(Items.golden_apple, 3);
        foodAdditionalSaturationMap.put(Items.carrot, 1);
        foodAdditionalSaturationMap.put(Items.golden_carrot, 1);
        foodAdditionalSaturationMap.put(Items.melon, 8);
        foodAdditionalSaturationMap.put(ItemsZp.jam, 2);
        foodAdditionalSaturationMap.put(ItemsZp.pea, 4);
        foodAdditionalSaturationMap.put(ItemsZp.ananas, 6);
        foodAdditionalSaturationMap.put(ItemsZp.orange, 3);

        meleeRangeBonus.put(ItemsZp.cleaver, -0.2f);
        meleeRangeBonus.put(ItemsZp.golf_club, 0.2f);
        meleeRangeBonus.put(ItemsZp.iron_club, 0.1f);
        meleeRangeBonus.put(ItemsZp.mjolnir, 0.1f);
        meleeRangeBonus.put(ItemsZp.inferno, 0.15f);
        meleeRangeBonus.put(ItemsZp.lucille, 0.08f);
        meleeRangeBonus.put(ItemsZp.ripper, 0.05f);
        meleeRangeBonus.put(ItemsZp.pipe, 0.3f);
        meleeRangeBonus.put(ItemsZp.copper_spear, 0.4f);
        meleeRangeBonus.put(ItemsZp.steel_spear, 0.4f);
        meleeRangeBonus.put(ItemsZp.police_club, 0.15f);
        meleeRangeBonus.put(ItemsZp.screwdriver, -0.3f);
        meleeRangeBonus.put(ItemsZp.hammer, -0.3f);
        meleeRangeBonus.put(ItemsZp.bone_knife, -0.35f);
        meleeRangeBonus.put(ItemsZp.katana, 0.15f);

        meleeSpeedBonus.put(ItemsZp.bone_knife, 12);
        meleeSpeedBonus.put(ItemsZp.hammer, 16);
        meleeSpeedBonus.put(ItemsZp.screwdriver, 16);
        meleeSpeedBonus.put(ItemsZp.cleaver, 18);
    }

    @SubscribeEvent
    public void onCraft(cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent ev) {
        if (!ev.player.worldObj.isRemote) {
            if (ev.crafting.getItem() != null) {
                if (ev.crafting.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.workbench, ev.player);
                } else if (ev.crafting.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.furnace, ev.player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent ev) {
        if (ev.phase == TickEvent.Phase.END) {
            if (!ev.player.worldObj.isRemote) {
                PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(ev.player);
                if (ev.player.inventory.hasItem(ItemsZp.uran_material) || ev.player.inventory.hasItem(Item.getItemFromBlock(BlocksZp.uranium))) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.uranium, ev.player);
                }
                if (ev.player.inventory.hasItem(Items.iron_ingot)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.iron, ev.player);
                }
                if (ev.player.inventory.hasItem(Items.diamond)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.diamond, ev.player);
                }
                if (ev.player.inventory.hasItem(ItemsZp.steel_ingot)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.steel, ev.player);
                }
                if (SpecialGenerator.instance.isPointInsideCityRange(null, MathHelper.floor_double(ev.player.posX), MathHelper.floor_double(ev.player.posZ), 312)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.city, ev.player);
                }
                if (playerMiscData.getZombiesKilled() >= 500) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.antiZombie, ev.player);
                }
                if (ev.player.inventory.armorInventory[0] != null && ev.player.inventory.armorInventory[1] != null && ev.player.inventory.armorInventory[2] != null && ev.player.inventory.armorInventory[3] != null) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.armored, ev.player);
                }
                if (ev.player.worldObj.getWorldInfo().getTerrainType() instanceof WorldTypeZp || ev.player.worldObj.getWorldInfo().getTerrainType() instanceof WorldTypeCrazyZp) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.enterWorld, ev.player);
                    if (ev.player.getDistance(0, 5, 0) <= 6) {
                        AchievementManager.instance.triggerAchievement(AchievementManager.instance.secretLoc, ev.player);
                    }
                }
                if (!ev.player.capabilities.isCreativeMode) {
                    this.updateStats(ev.player);
                }
            }
        }
    }

    private void updateStats(EntityPlayer pl) {
        Thirst thirst = Thirst.getThirst(pl);
        Hunger hunger = Hunger.getHunger(pl);
        PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(pl);
        thirst.onUpdate();
        hunger.onUpdate();
        playerMiscData.onUpdate();
        hunger.addExhaustion(1.0e-6f);
        thirst.addExhaustion(1.0e-6f);

        if (pl.isPotionActive(17)) {
            hunger.addExhaustion(2.5e-2f * (float) (pl.getActivePotionEffect(Potion.hunger).getAmplifier() + 1));
        }
        if (pl.isPotionActive(23)) {
            hunger.addHunger(pl.getActivePotionEffect(Potion.saturation).getAmplifier() + 1, 1.0F);
        }
        if (pl.isPotionActive(31)) {
            thirst.addExhaustion(2.5e-2f * (float) (pl.getActivePotionEffect(CommonProxy.dehydration).getAmplifier() + 1));
        }

        if (pl.posX != pl.lastTickPosX || pl.posZ != pl.lastTickPosZ) {
            hunger.addExhaustion(Main.rand.nextFloat() * 5.0e-4f);
            thirst.addExhaustion(Main.rand.nextFloat() * 5.0e-4f);
        }

        if (MovingUtils.isSwimming(pl)) {
            hunger.addExhaustion(4.0e-3f);
            thirst.addExhaustion(1.0e-3f);
        } else if (pl.isSprinting()) {
            hunger.addExhaustion(1.0e-3f);
            thirst.addExhaustion(2.0e-3f);
        }

        if (pl.isPotionActive(29)) {
            thirst.addExhaustion(1.0e-2f);
        }

        if (!pl.isPotionActive(12)) {
            if (pl.isBurning() || pl.handleLavaMovement()) {
                thirst.addExhaustion(2.5e-2f);
            }
        }

        if (pl.ticksExisted % 20 == 0) {
            float i1 = pl.worldObj.getBiomeGenForCoords(MathHelper.floor_double(pl.posX), MathHelper.floor_double(pl.posZ)).temperature;
            if (i1 <= 0.5f && pl.getCurrentArmor(1) == null) {
                hunger.addExhaustion(5.0e-2f);
            } else if (i1 > 1.0f && pl.getCurrentArmor(0) == null) {
                if (pl.worldObj.canBlockSeeTheSky(MathHelper.floor_double(pl.posX), MathHelper.floor_double(pl.posY), MathHelper.floor_double(pl.posZ))) {
                    thirst.addExhaustion(5.0e-2f);
                }
            }
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent ev) {
        if (!ev.entityLiving.worldObj.isRemote) {
            if (ev.entityLiving instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer) ev.entityLiving;
                if (!entityPlayer.capabilities.isCreativeMode) {
                    Hunger hunger = Hunger.getHunger(entityPlayer);
                    Thirst thirst = Thirst.getThirst(entityPlayer);
                    hunger.addExhaustion(0.1f);
                    thirst.addExhaustion(0.1f);
                }
            }
        }
    }

    @SubscribeEvent
    public void onConstruct(EntityEvent.EntityConstructing ev) {
        if (ev.entity instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) ev.entity;
            if (Hunger.getHunger(pl) == null) {
                pl.registerExtendedProperties("Hunger", new Hunger(pl));
            }
            if (Thirst.getThirst(pl) == null) {
                pl.registerExtendedProperties("Thirst", new Thirst(pl));
            }
            if (PlayerMiscData.getPlayerData(pl) == null) {
                pl.registerExtendedProperties("Miscellaneous", new PlayerMiscData(pl));
            }
            AchievementManager.instance.createAchievementBase(pl);
            SkillManager.instance.createSkillBase(pl);
        }
    }

    @SubscribeEvent
    public void disconnect(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent ev) {
        NetworkHandler.NETWORK.sendToAll(new PacketPingClient(ev.player.getDisplayName(), -1));
    }

    @SubscribeEvent()
    public void onWakeUp(PlayerWakeUpEvent ev) {
        if (!ev.entityPlayer.worldObj.isRemote) {
            if (!ev.entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("disableNightSkipping")) {
                if (!ev.entityPlayer.capabilities.isCreativeMode) {
                    if (!ev.updateWorld) {
                        Thirst thirst = Thirst.getThirst(ev.entityPlayer);
                        Hunger hunger = Hunger.getHunger(ev.entityPlayer);
                        int toRemove = 40;
                        thirst.removeThirst(toRemove);
                        hunger.removeHunger(toRemove);
                        ev.entityPlayer.addExperienceLevel(-1);
                        if (ev.entityPlayer instanceof EntityPlayerMP) {
                            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) ev.entityPlayer;
                            entityPlayerMP.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(entityPlayerMP.experience, entityPlayerMP.experienceTotal, entityPlayerMP.experienceLevel));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent()
    public void onPickUp(EntityItemPickupEvent ev) {
        if (ev.entityLiving instanceof EntityPlayer) {
            boolean flag = PlayerMiscData.getPlayerData(ev.entityPlayer).isPickUpOnF();
            if (flag) {
                ev.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onToss(ItemTossEvent ev) {
        if (!ev.player.worldObj.isRemote) {
            ev.setCanceled(true);
            if (ev.entityItem.getEntityItem().getItem() instanceof ItemRocket) {
                if (!ev.entityItem.getEntityItem().hasTagCompound()) {
                    ev.entityItem.getEntityItem().setTagInfo(Main.MODID, new NBTTagCompound());
                    ev.entityItem.getEntityItem().getTagCompound().getCompoundTag(Main.MODID).setString("owner", ev.player.getDisplayName());
                }
            }
            Vec3 lookVec = ev.player.getLookVec();
            double vel = -MathHelper.sin(((ev.player.rotationPitch - 90) / 90) * 0.6f);
            EntityItem item = new EntityItem(ev.player.worldObj, ev.player.posX + lookVec.xCoord * 0.2f, ev.player.boundingBox.minY + (ev.player.boundingBox.maxY - ev.player.boundingBox.minY) * 0.5f, ev.player.posZ + lookVec.zCoord * 0.2f, ev.entityItem.getEntityItem());
            item.delayBeforeCanPickup = 10;
            item.motionX = lookVec.xCoord * vel;
            item.motionY = lookVec.yCoord * vel;
            item.motionZ = lookVec.zCoord * vel;
            ev.player.worldObj.spawnEntityInWorld(item);
        }
    }

    @SubscribeEvent
    public void connectEvent(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent ev) {
        if (ConfigZp.enableKitStart) {
            if (!ev.player.getEntityData().getBoolean("firstPlay")) {
                Item melee = null;
                switch (Main.rand.nextInt(6)) {
                    case 0: {
                        melee = ItemsZp.bat;
                        break;
                    }
                    case 1: {
                        melee = ItemsZp.cleaver;
                        break;
                    }
                    case 2: {
                        melee = ItemsZp.golf_club;
                        break;
                    }
                    case 3: {
                        melee = ItemsZp.pipe;
                        break;
                    }
                    case 4: {
                        melee = Items.stone_sword;
                        break;
                    }
                    case 5: {
                        melee = ItemsZp.police_club;
                        break;
                    }
                }
                ItemStack gun = null;
                ItemStack ammo = null;
                switch (Main.rand.nextInt(4)) {
                    case 0: {
                        gun = new ItemStack(ItemsZp.walther, 1, 25);
                        ammo = new ItemStack(ItemsZp._22lr, 32);
                        break;
                    }
                    case 1: {
                        gun = new ItemStack(ItemsZp.pm, 1, 75);
                        ammo = new ItemStack(ItemsZp._9mm, 32);
                        break;
                    }
                    case 2: {
                        gun = new ItemStack(ItemsZp.toz66_short, 1, 40);
                        ammo = new ItemStack(ItemsZp._12, 16);
                        break;
                    }
                    case 3: {
                        gun = new ItemStack(ItemsZp.m1895, 1, 70);
                        ammo = new ItemStack(ItemsZp._7_62x54R, 24);
                        break;
                    }
                }
                ev.player.inventory.addItemStackToInventory(new ItemStack(melee));
                ev.player.inventory.addItemStackToInventory(gun);
                ev.player.inventory.addItemStackToInventory(new ItemStack(ItemsZp.plate, 16));
                ev.player.inventory.addItemStackToInventory(ammo);
                if (ammo.getItem() == ItemsZp._22lr) {
                    ev.player.inventory.addItemStackToInventory(new ItemStack(ammo.getItem(), 32));
                }
                ItemStack food = null;
                ItemStack drink = null;
                switch (Main.rand.nextInt(5)) {
                    case 0: {
                        food = new ItemStack(ItemsZp.soup, 6);
                        break;
                    }
                    case 1: {
                        food = new ItemStack(ItemsZp.ananas, 8);
                        break;
                    }
                    case 2: {
                        food = new ItemStack(ItemsZp.jam, 10);
                        break;
                    }
                    case 3: {
                        food = new ItemStack(ItemsZp.donut, 12);
                        break;
                    }
                    case 4: {
                        food = new ItemStack(ItemsZp.pea, 8);
                        break;
                    }
                }
                switch (Main.rand.nextInt(3)) {
                    case 0: {
                        drink = new ItemStack(ItemsZp.water, 6);
                        break;
                    }
                    case 1: {
                        drink = new ItemStack(ItemsZp.cola, 10);
                        break;
                    }
                    case 2: {
                        drink = new ItemStack(ItemsZp.pepsi, 10);
                        break;
                    }
                }
                ev.player.inventory.addItemStackToInventory(food);
                ev.player.inventory.addItemStackToInventory(drink);
                ev.player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(Blocks.torch), 16));
                ev.player.inventory.addItemStackToInventory(new ItemStack(ItemsZp.bandage));
                switch (Main.rand.nextInt(3)) {
                    case 0: {
                        ev.player.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
                        break;
                    }
                    case 1: {
                        ev.player.setCurrentItemOrArmor(4, new ItemStack(Items.chainmail_helmet));
                        break;
                    }
                    case 2: {
                        ev.player.setCurrentItemOrArmor(1, new ItemStack(Items.iron_boots));
                        break;
                    }
                }
                ev.player.getEntityData().setBoolean("firstPlay", true);
            }
        }
        if (ev.player instanceof EntityPlayerMP) {
            PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(ev.player);
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) ev.player;
            boolean flag = false;
            if (MovingUtils.forceLie(entityPlayerMP)) {
                playerMiscData.setLying(true);
                flag = true;
            } else if (MovingUtils.forceSneak(entityPlayerMP)) {
                entityPlayerMP.setSneaking(true);
                flag = true;
            }
            if (flag) { //Maybe bugfix... or not
                entityPlayerMP.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(entityPlayerMP.posX, entityPlayerMP.posY + entityPlayerMP.getEyeHeight(), entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch, entityPlayerMP.onGround));
            }
        }
        this.sendSyncData(ev.player, ev.player.dimension);
    }

    public void sendSyncData(EntityPlayer player, int toDim) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
            NetworkHandler.NETWORK.sendTo(new PacketBrightnessInfo(ConfigZp.clientHigherBrightness), entityPlayerMP);
        }
        if (player.getEntityData() != null) {
            NetworkHandler.NETWORK.sendToAllAround(new PacketMiscPlayerNbtInfo(player.getEntityId(), player.getEntityData()), new NetworkRegistry.TargetPoint(toDim, player.posX, player.posY, player.posZ, 256));
        }
    }

    public void sendAchievementData(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
            NetworkHandler.NETWORK.sendTo(new PacketAchSkillData(player.getEntityId(), player.getEntityData(), ConfigZp.skillsSystem, ConfigZp.achievementsSystem), entityPlayerMP);
            for (SkillZp skillZp : SkillManager.instance.getSkillZpList()) {
                NetworkHandler.NETWORK.sendTo(new PacketSkillProgressData(player.getEntityId(), skillZp.getId(), PlayerMiscData.getPlayerData(player).getSkillProgressProfiler().getProgress(skillZp)), entityPlayerMP);
            }
        }
    }

    @SubscribeEvent
    public void onClonePlayer(PlayerEvent.Clone e) {
        PlayerMiscData.getPlayerData(e.entityPlayer).copy(PlayerMiscData.getPlayerData(e.original));
        if (!e.entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            e.entityPlayer.experienceLevel = PlayerMiscData.getPlayerData(e.original).getMinLvl();
        }
        for (AchievementZp achievementZp : AchievementManager.instance.getAchievementZpList()) {
            if (e.original.getEntityData().hasKey(achievementZp.getNBT())) {
                e.entityPlayer.getEntityData().setByte(achievementZp.getNBT(), e.original.getEntityData().getByte(achievementZp.getNBT()));
            }
        }
        for (SkillZp skillZp : SkillManager.instance.getSkillZpList()) {
            float f1 = PlayerMiscData.getPlayerData(e.original).getSkillProgressProfiler().getProgress(skillZp);
            e.entityPlayer.getEntityData().setInteger(skillZp.getNBT(), e.original.getEntityData().getByte(skillZp.getNBT()));
            PlayerMiscData.getPlayerData(e.entityPlayer).getSkillProgressProfiler().setProgress(skillZp, e.entityPlayer, f1 - 0.3f);
        }
        e.entityPlayer.getEntityData().setBoolean("firstPlay", e.original.getEntityData().getBoolean("firstPlay"));
        e.original.getEntityData().setInteger("radiation", 0);
        e.entityPlayer.getEntityData().setInteger("radiation", 0);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent ev) {
        ItemStack stack = ev.entityPlayer.getHeldItem();
        if (ev.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (ev.world.getBlock(ev.x, ev.y, ev.z) == Blocks.ender_chest) {
                if (!ev.world.isRemote) {
                    ev.world.playSoundAtEntity(ev.entityPlayer, "mob.endermen.portal", 5.0F, 0.8F + Main.rand.nextFloat() * 0.2f);
                    ev.useBlock = Event.Result.DENY;
                }
            }
            if (!ev.world.isRemote) {
                int day = (int) (ev.world.getTotalWorldTime() / 24000);
                if (ev.world.getBlock(ev.x, ev.y, ev.z) == Blocks.bed) {
                    if (ev.entityPlayer.experienceLevel < 0) {
                        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("misc.cantSleep.level");
                        ev.entityPlayer.addChatMessage(chatComponentTranslation);
                        ev.useBlock = Event.Result.DENY;
                    } else if (day > 0 && (day % 7) == 0) {
                        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("misc.cantSleep.noSleep");
                        ev.entityPlayer.addChatMessage(chatComponentTranslation);
                        ev.useBlock = Event.Result.DENY;
                    } else {
                        List<AZombieBase> list = ev.entityPlayer.worldObj.getEntitiesWithinAABB(AZombieBase.class, ev.entityPlayer.boundingBox.expand(16, 16, 16));
                        if (!list.isEmpty()) {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("tile.bed.notSafe");
                            ev.entityPlayer.addChatMessage(chatComponentTranslation);
                            ev.useBlock = Event.Result.DENY;
                        } else if (ev.world.provider.dimensionId != 0) {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("misc.cantSleep.dimension");
                            ev.entityPlayer.addChatMessage(chatComponentTranslation);
                            ev.useBlock = Event.Result.DENY;
                        } else if (ev.world.isDaytime()) {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("tile.bed.noSleep");
                            ev.entityPlayer.addChatMessage(chatComponentTranslation);
                            ev.useBlock = Event.Result.DENY;
                        }
                    }
                } else if (ev.world.getBlock(ev.x, ev.y, ev.z) == Blocks.cake) {
                    Hunger hunger = Hunger.getHunger(ev.entityPlayer);
                    hunger.addHunger(10, 0.1f);
                }
            }
        }
        if (stack != null) {
            if (ev.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                if (stack.getItem() instanceof ItemBucket) {
                    if (!ev.entityPlayer.capabilities.isCreativeMode && ev.entityPlayer.isInsideOfMaterial(Material.water)) {
                        if (!((EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && ev.entityPlayer.inventory.hasItem(ItemsZp.oxygen))) {
                            ev.setCanceled(true);
                        }
                    }
                }
            }
            if (ev.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (!ev.world.isRemote) {
                    if (stack.getItem() instanceof ItemHoe) {
                        if (ev.face != 0) {
                            Block block = ev.world.getBlock(ev.x, ev.y, ev.z);
                            if (ev.world.isAirBlock(ev.x, ev.y + 1, ev.z) && (block == Blocks.grass || block == Blocks.dirt)) {
                                if (Main.rand.nextFloat() <= 0.03f) {
                                    EntityItem entityitem = new EntityItem(ev.world, ev.x + 0.5f, ev.y + 0.5f, ev.z + 0.5f, new ItemStack(Items.wheat_seeds));
                                    entityitem.delayBeforeCanPickup = 10;
                                    ev.world.spawnEntityInWorld(entityitem);
                                }
                            }
                        }
                    }
                }
                int i = ev.x;
                int j = ev.y;
                int k = ev.z;
                switch (ev.face) {
                    case 0: {
                        --j;
                        break;
                    }
                    case 1: {
                        ++j;
                        break;
                    }
                    case 2: {
                        --k;
                        break;
                    }
                    case 3: {
                        ++k;
                        break;
                    }
                    case 4: {
                        --i;
                        break;
                    }
                    case 5: {
                        ++i;
                        break;
                    }
                }
                if (stack.getItem() instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) ev.entityPlayer.getHeldItem().getItem();
                    AxisAlignedBB axisAlignedBB = ev.entityPlayer.boundingBox;
                    double y = axisAlignedBB.minY;
                    float maxY = PlayerMiscData.getPlayerData(ev.entityPlayer).isLying() ? 0.8f : ev.entityPlayer.isSneaking() ? 1.5f : 1.8f;
                    AxisAlignedBB axisAlignedBB1 = AxisAlignedBB.getBoundingBox(axisAlignedBB.minX, y + 0.25f, axisAlignedBB.minZ, axisAlignedBB.maxX, y + maxY, axisAlignedBB.maxZ);
                    if (!EntityUtils.isBlockInAABB(ev.entityPlayer, i, j, k, itemBlock.blockInstance, axisAlignedBB1)) {
                        ev.useItem = Event.Result.DENY;
                    }
                }
                if (!ev.entityPlayer.capabilities.isCreativeMode && ev.entityPlayer.isInsideOfMaterial(Material.water)) {
                    if (!((EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && ev.entityPlayer.inventory.hasItem(ItemsZp.oxygen))) {
                        if (!(stack.getItem() instanceof ItemBlock) || (stack.getItem() instanceof ItemBlock && !((ItemBlock) stack.getItem()).blockInstance.isNormalCube())) {
                            if (ev.world.getBlock(i, j, k).getMaterial() == Material.water) {
                                ev.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
        if (ev.world.getGameRules().getGameRuleBooleanValue("changeChestsToLootCases") && ev.world.getBlock(ev.x, ev.y, ev.z) == Blocks.chest) {
            float r1 = Main.rand.nextFloat();
            Block block;
            if (r1 <= 0.6f) {
                float r2 = Main.rand.nextFloat();
                if (r2 <= 0.2f) {
                    block = LootCasesZp.tier1_chest;
                } else if (r2 <= 0.4f) {
                    block = LootCasesZp.tier2_chest;
                } else if (r2 <= 0.5f) {
                    block = LootCasesZp.tier3_chest;
                } else if (r2 <= 0.58f) {
                    block = LootCasesZp.tier17_chest;
                } else if (r2 <= 0.64f) {
                    block = LootCasesZp.tier15_chest;
                } else if (r2 <= 0.7f) {
                    block = LootCasesZp.tier16_chest;
                } else if (r2 <= 0.8f) {
                    block = LootCasesZp.tier12_chest;
                } else if (r2 <= 0.85f) {
                    block = LootCasesZp.tier10_chest;
                } else if (r2 <= 0.88f) {
                    block = LootCasesZp.tier11_chest;
                } else {
                    block = LootCasesZp.tier4_chest;
                }
            } else if (r1 <= 0.9f) {
                float r2 = Main.rand.nextFloat();
                if (r2 <= 0.9f) {
                    block = LootCasesZp.tier5_chest;
                } else if (r2 <= 0.98f) {
                    block = LootCasesZp.tier6_chest;
                } else {
                    block = LootCasesZp.tier18_chest;
                }
            } else {
                float r2 = Main.rand.nextFloat();
                if (r2 <= 0.5f) {
                    block = LootCasesZp.tier13_chest;
                } else if (r2 <= 0.65f) {
                    block = LootCasesZp.tier14_chest;
                } else if (r2 <= 0.8f) {
                    block = LootCasesZp.tier7_chest;
                } else if (r2 <= 0.95f) {
                    block = LootCasesZp.tier8_chest;
                } else {
                    block = LootCasesZp.tier9_chest;
                }
            }
            int m = ev.world.getBlockMetadata(ev.x, ev.y, ev.z);
            ev.world.setBlock(ev.x, ev.y, ev.z, block);
            ev.world.setBlockMetadataWithNotify(ev.x, ev.y, ev.z, m, 0);
        }
    }

    @SubscribeEvent()
    public void onBreak(PlayerEvent.BreakSpeed ev) {
        if (ev.entityPlayer.isInsideOfMaterial(Material.water)) {
            if (!((EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(ev.entityPlayer, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && ev.entityPlayer.inventory.hasItem(ItemsZp.oxygen))) {
                ev.newSpeed = ev.originalSpeed * 0.3f;
            }
        }
        if (PlayerMiscData.getPlayerData(ev.entityPlayer).isLying() || ev.entityPlayer.isInWeb || !ev.entityPlayer.onGround || Thirst.getThirst(ev.entityPlayer).getThirst() <= 10 || Hunger.getHunger(ev.entityPlayer).getHunger() <= 10) {
            ev.newSpeed = ev.originalSpeed * 0.3f;
        }
    }

    @SubscribeEvent()
    public void onHarvest(BlockEvent.HarvestDropsEvent ev) {
        if (!ev.world.isRemote) {
            if (ev.block instanceof BlockBed) {
                ev.drops.clear();
            }
            if (ev.harvester != null) {
                if (!(ev.harvester.getHeldItem() != null && ev.harvester.getHeldItem().getItem() instanceof ItemWrench)) {
                    if (ev.block instanceof BlockFurnace) {
                        ev.drops.clear();
                        ev.drops.add(new ItemStack(Main.rand.nextFloat() <= 0.2f ? Items.flint : Item.getItemFromBlock(BlocksZp.furnace_destroyed)));
                    } else if (ev.block instanceof BlockWorkbench) {
                        ev.drops.clear();
                        if (Main.rand.nextFloat() <= 0.2f) {
                            if (Main.rand.nextFloat() <= 0.5f) {
                                ev.drops.add(new ItemStack(ItemsZp.table));
                            } else if (Main.rand.nextFloat() <= 0.8f) {
                                ev.drops.add(new ItemStack(ItemsZp.shelves));
                            } else {
                                ev.drops.add(new ItemStack(ItemsZp.chisel));
                            }
                        } else {
                            ev.drops.add(new ItemStack(BlocksZp.workbench_destroyed));
                        }
                    }
                }
            }
            if (!ev.isSilkTouching) {
                if (ev.block instanceof BlockDoublePlant) {
                    if (ev.harvester == null) {
                        ev.drops.clear();
                    }
                }
                if (ev.block instanceof BlockTallGrass) {
                    if (ev.harvester != null && ev.harvester.getHeldItem() != null && ev.harvester.getHeldItem().getItem() instanceof ItemHoe) {
                        ev.harvester.getHeldItem().damageItem(1, ev.harvester);
                        if (Main.rand.nextFloat() >= 0.7f) {
                            ev.drops.clear();
                        }
                    } else {
                        ev.drops.clear();
                    }
                } else if (ev.block instanceof BlockRedstoneOre || ev.block instanceof BlockOre || ev.block instanceof BlockOreZp) {
                    ev.drops.clear();
                    Item itemToDrop = ev.block == Blocks.iron_ore ? ItemsZp.raw_iron : ev.block == Blocks.gold_ore ? ItemsZp.raw_gold : ev.block.getItemDropped(ev.blockMetadata, Main.rand, ev.fortuneLevel);
                    float f2 = ev.fortuneLevel * 0.25f;
                    if (ev.harvester != null) {
                        PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(ev.harvester);
                        float f1 = 0.01f;
                        if (ev.block == Blocks.iron_ore || ev.block == Blocks.gold_ore || ev.block == Blocks.lapis_ore) {
                            f1 = 0.025f;
                        } else if (ev.block == BlocksZp.copper_ore || ev.block == Blocks.redstone_ore) {
                            f1 = 0.015f;
                        } else if (ev.block == BlocksZp.titan_ore || ev.block == Blocks.emerald_ore || ev.block == Blocks.diamond_ore) {
                            f1 = 0.1f;
                        }
                        playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.miner, ev.harvester, f1);
                        f2 += SkillManager.instance.getSkillBonus(SkillManager.instance.miner, ev.harvester, 0.05f);
                    }
                    int i1 = Main.rand.nextFloat() <= f2 ? 2 : 1;
                    if (f2 > 1.0f && Main.rand.nextFloat() <= 0.1f) {
                        i1 = 3;
                    }
                    ev.drops.add(new ItemStack(itemToDrop, i1, ev.block == Blocks.lapis_ore ? 4 : 0));
                } else if (ev.harvester != null && ev.block == Blocks.melon_block) {
                    if (Main.rand.nextFloat() <= 0.3f) {
                        if (this.checkMelonStem(ev.world, ev.x - 1, ev.y, ev.z)) {
                            ev.world.setBlockToAir(ev.x - 1, ev.y, ev.z);
                        } else if (this.checkMelonStem(ev.world, ev.x + 1, ev.y, ev.z)) {
                            ev.world.setBlockToAir(ev.x + 1, ev.y, ev.z);
                        } else if (this.checkMelonStem(ev.world, ev.x, ev.y, ev.z - 1)) {
                            ev.world.setBlockToAir(ev.x, ev.y, ev.z - 1);
                        } else if (this.checkMelonStem(ev.world, ev.x, ev.y, ev.z + 1)) {
                            ev.world.setBlockToAir(ev.x, ev.y, ev.z + 1);
                        }
                    }
                    ev.drops.clear();
                    ev.drops.add(new ItemStack(Items.melon, Main.rand.nextInt(4) + 1));
                    PlayerMiscData.getPlayerData(ev.harvester).getSkillProgressProfiler().addProgress(SkillManager.instance.farmer, ev.harvester, 0.045f);
                } else if (ev.harvester != null && ev.block == Blocks.pumpkin) {
                    if (Main.rand.nextFloat() <= 0.3f) {
                        if (this.checkPumpkinStem(ev.world, ev.x - 1, ev.y, ev.z)) {
                            ev.world.setBlockToAir(ev.x - 1, ev.y, ev.z);
                        } else if (this.checkPumpkinStem(ev.world, ev.x + 1, ev.y, ev.z)) {
                            ev.world.setBlockToAir(ev.x + 1, ev.y, ev.z);
                        } else if (this.checkPumpkinStem(ev.world, ev.x, ev.y, ev.z - 1)) {
                            ev.world.setBlockToAir(ev.x, ev.y, ev.z - 1);
                        } else if (this.checkPumpkinStem(ev.world, ev.x, ev.y, ev.z + 1)) {
                            ev.world.setBlockToAir(ev.x, ev.y, ev.z + 1);
                        }
                    }
                    PlayerMiscData.getPlayerData(ev.harvester).getSkillProgressProfiler().addProgress(SkillManager.instance.farmer, ev.harvester, 0.045f);
                }
            }
            if (ev.block instanceof BlockBookshelf) {
                ev.drops.clear();
                if (Main.rand.nextFloat() <= 0.7f) {
                    ev.drops.add(new ItemStack(Items.paper, Main.rand.nextFloat() <= 0.3f * (ev.fortuneLevel + 1) ? 2 : 1));
                }
            } else if (ev.block instanceof BlockGlowstone) {
                ev.drops.clear();
                Item itemToDrop = ev.block.getItemDropped(ev.blockMetadata, Main.rand, ev.fortuneLevel);
                ev.drops.add(new ItemStack(itemToDrop, ev.fortuneLevel + (Main.rand.nextBoolean() ? 1 : 0)));
            } else if (ev.block instanceof BlockRedstoneLight) {
                ev.drops.clear();
                Item itemToDrop = Items.glowstone_dust;
                ev.drops.add(new ItemStack(itemToDrop, ev.fortuneLevel + (Main.rand.nextBoolean() ? 1 : 0)));
            } else if (ev.block instanceof BlockLeaves) {
                if (ev.blockMetadata == 4) {
                    ev.drops.clear();
                    if (Main.rand.nextFloat() <= 0.03f) {
                        if (Main.rand.nextFloat() <= 0.8f) {
                            ev.drops.add(new ItemStack(ItemsZp.rotten_apple, 1));
                        } else {
                            ev.drops.add(new ItemStack(Items.apple, 1));
                        }
                    }
                }
            } else if (ev.harvester != null && ev.block instanceof BlockCrops) {
                if (ev.blockMetadata == 7) {
                    ev.drops.clear();
                    float f1 = SkillManager.instance.getSkillBonus(SkillManager.instance.farmer, ev.harvester, 0.04f);
                    int bonus = f1 >= 0.2f ? 1 : 0;
                    if (ev.block == Blocks.carrots) {
                        ev.drops.add(new ItemStack(Items.carrot, (Main.rand.nextFloat() <= (0.7f - f1) ? 2 : 1) + bonus));
                    } else if (ev.block == Blocks.potatoes) {
                        if (Main.rand.nextFloat() <= 0.1f) {
                            ev.drops.add(new ItemStack(Items.poisonous_potato));
                        } else {
                            ev.drops.add(new ItemStack(Items.potato, (Main.rand.nextFloat() <= (0.05f + f1) ? 3 : Main.rand.nextFloat() <= (0.5f + f1) ? 2 : 1) + bonus));
                        }
                    } else if (ev.block == Blocks.wheat) {
                        ev.drops.add(new ItemStack(Items.wheat, (Main.rand.nextBoolean() ? 1 : 2) + bonus));
                        if (bonus == 1) {
                            ev.drops.add(new ItemStack(Items.wheat_seeds));
                        }
                    }
                    PlayerMiscData.getPlayerData(ev.harvester).getSkillProgressProfiler().addProgress(SkillManager.instance.farmer, ev.harvester, 0.0375f);
                }
            } else if (ev.block instanceof BlockStem) {
                ev.drops.clear();
            }
        }
    }

    private boolean checkMelonStem(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Blocks.melon_stem && world.getBlockMetadata(x, y, z) == 7;
    }

    private boolean checkPumpkinStem(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Blocks.pumpkin_stem && world.getBlockMetadata(x, y, z) == 7;
    }

    @SubscribeEvent()
    public void onBreak(BlockEvent.BreakEvent ev) {
        if (!ev.getPlayer().capabilities.isCreativeMode) {
            if (ev.block == Blocks.wool || ev.block == Blocks.web) {
                if (ev.getPlayer().getHeldItem() != null && ev.getPlayer().getHeldItem().getItem() instanceof ItemShears) {
                    ev.getPlayer().getHeldItem().damageItem(1, ev.getPlayer());
                }
            } else if (ev.block instanceof BlockMine) {
                if (ev.getPlayer().getHeldItem() != null && ev.getPlayer().getHeldItem().getItem() instanceof ItemMetalScissors) {
                    BlockMine blockMine = (BlockMine) ev.block;
                    blockMine.dropBlockAsItem(ev.world, ev.x, ev.y, ev.z);
                } else {
                    ev.world.createExplosion(null, ev.x, ev.y + 0.5f, ev.z, 3.0f, false);
                }
            } else if (ev.getPlayer().getHeldItem() == null && (ev.block == BlocksZp.lamp || ev.block == BlocksZp.lamp_off || ev.block.getMaterial() == Material.glass)) {
                if (!EntityUtils.isInArmor(ev.getPlayer(), null, ItemsZp.juggernaut_chestplate, null, null) && !EntityUtils.isInArmor(ev.getPlayer(), null, ItemsZp.aqualung_chestplate, null, null) && !EntityUtils.isInArmor(ev.getPlayer(), null, ItemsZp.indcostume_chestplate, null, null) && !EntityUtils.isInArmor(ev.getPlayer(), null, ItemsZp.rad_chestplate, null, null)) {
                    ev.getPlayer().attackEntityFrom(DamageSourceZp.blood, 2);
                    ev.getPlayer().addPotionEffect(new PotionEffect(28, 300 + Main.rand.nextInt(101)));
                }
            }
            Thirst thirst = Thirst.getThirst(ev.getPlayer());
            Hunger hunger = Hunger.getHunger(ev.getPlayer());
            thirst.addExhaustion(0.025f);
            hunger.addExhaustion(0.05f);
            PlayerMiscData.getPlayerData(ev.getPlayer()).addPlayerLoudness(1);
        }
    }

    @SubscribeEvent()
    public void onPlace(BlockEvent.PlaceEvent ev) {
        if (ev.placedBlock == Blocks.lit_pumpkin) {
            int l = MathHelper.floor_double((double) (ev.player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
            ev.world.setBlock(ev.x, ev.y, ev.z, BlocksZp.pumpkinNew, l, 2);
        }
        if (!ev.player.capabilities.isCreativeMode) {
            Thirst thirst = Thirst.getThirst(ev.player);
            Hunger hunger = Hunger.getHunger(ev.player);
            thirst.addExhaustion(0.05f);
            hunger.addExhaustion(0.025f);
            PlayerMiscData.getPlayerData(ev.player).addPlayerLoudness(1);
        }
    }

    @SubscribeEvent()
    public void onTeleport(EnderTeleportEvent ev) {
        if (ev.entityLiving instanceof EntityPlayer) {
            if (ev.entityLiving.getDistance(ev.targetX, ev.targetY, ev.targetZ) > 64) {
                ev.entityLiving.setHealth(1.0f);
                ev.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent ev) {
        if (ev.target != null) {
            if (!ev.entityPlayer.capabilities.isCreativeMode) {
                Thirst thirst = Thirst.getThirst(ev.entityPlayer);
                Hunger hunger = Hunger.getHunger(ev.entityPlayer);
                thirst.addExhaustion(Main.rand.nextFloat() * 0.02f + 0.01f);
                hunger.addExhaustion(Main.rand.nextFloat() * 0.04f + 0.01f);
            }
            float const1 = ev.entityPlayer.isPotionActive(29) ? 3.5f : 3.0f;
            if (ev.target instanceof EntityPlayer) {
                const1 += 2.2f;
            }
            ItemStack stack = ev.entityPlayer.getHeldItem();
            if (stack != null) {
                if (meleeRangeBonus.containsKey(stack.getItem())) {
                    const1 += meleeRangeBonus.get(stack.getItem());
                } else {
                    if (stack.getItem() instanceof ItemAxe) {
                        const1 += 0.1f;
                    }
                }
            }
            if (!ev.entityPlayer.onGround) {
                const1 -= 0.2f;
            }
            if (ev.entityPlayer.getDistanceToEntity(ev.target) > const1) {
                ev.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onInteractEntity(EntityInteractEvent ev) {
        ItemStack stack = ev.entityPlayer.getHeldItem();
        if (ev.target instanceof EntityVillager) {
            EntityVillager vil = (EntityVillager) ev.target;
            boolean flag = false;
            Entity detectEnemy = vil.worldObj.findNearestEntityWithinAABB(AZombieBase.class, vil.boundingBox.expand(32, 32, 32), vil);
            if (vil.posY >= ev.entityPlayer.worldObj.getWorldInfo().getTerrainType().getCloudHeight()) {
                flag = true;
                if (vil.worldObj.isRemote) {
                    ev.entityPlayer.addChatComponentMessage(new ChatComponentText(I18n.format("misc.villager.cantWork.high")));
                }
            } else if (detectEnemy != null) {
                if (ev.entityPlayer.worldObj.isRemote) {
                    ev.entityPlayer.addChatComponentMessage(new ChatComponentText(I18n.format("misc.villager.cantWork.zombie")));
                }
                flag = true;
            }
            if (flag) {
                ev.setCanceled(true);
            }
        } else if (ev.target instanceof EntityCow) {
            if (stack != null) {
                if (ev.target instanceof EntityMooshroom) {
                    if (stack.getItem() == Items.bowl) {
                        EntityMooshroom entityMooshroom = (EntityMooshroom) ev.target;
                        entityMooshroom.setDead();
                        if (!entityMooshroom.worldObj.isRemote) {
                            EntityCow entitycow = new EntityCow(entityMooshroom.worldObj);
                            entitycow.setLocationAndAngles(entityMooshroom.posX, entityMooshroom.posY, entityMooshroom.posZ, entityMooshroom.rotationYaw, entityMooshroom.rotationPitch);
                            entitycow.setHealth(entityMooshroom.getHealth());
                            entitycow.renderYawOffset = entityMooshroom.renderYawOffset;
                            entityMooshroom.worldObj.spawnEntityInWorld(entitycow);
                        }
                        entityMooshroom.worldObj.spawnParticle("largeexplode", entityMooshroom.posX, entityMooshroom.posY + (double) (entityMooshroom.height / 2.0F), entityMooshroom.posZ, 0.0D, 0.0D, 0.0D);
                        entityMooshroom.playSound("mob.sheep.shear", 1.0F, 1.0F);
                    }
                } else {
                    if (stack.getItem() == Items.bucket) {
                        if (ev.target.worldObj.isRemote) {
                            ev.setCanceled(true);
                        } else {
                            if (ev.target.getEntityData().getInteger("cowReloading") == 1200) {
                                ev.entityPlayer.inventory.setInventorySlotContents(ev.entityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
                                ev.target.getEntityData().setInteger("cowReloading", 0);
                                ev.target.getEntityData().setBoolean("canCowReload", false);
                            } else {
                                ev.setCanceled(true);
                            }
                        }
                    } else if (stack.getItem() == Items.wheat) {
                        if (!ev.target.worldObj.isRemote) {
                            ev.target.getEntityData().setBoolean("canCowReload", true);
                        }
                    }
                }
            }
        } else if (ev.target instanceof EntityChicken) {
            if (stack != null) {
                if (stack.getItem() == Items.wheat) {
                    if (!ev.target.worldObj.isRemote) {
                        ev.target.getEntityData().setBoolean("canChickenReload", true);
                    }
                }
            }
        } else if (ev.target instanceof EntitySheep) {
            if (!ev.target.worldObj.isRemote) {
                if (stack != null && stack.getItem() instanceof ItemShears) {
                    EntitySheep entitySheep = (EntitySheep) ev.target;
                    ev.setCanceled(true);
                    if (!entitySheep.getSheared() && !entitySheep.isChild()) {
                        ev.entityPlayer.getHeldItem().damageItem(3, ev.entityPlayer);
                        entitySheep.setSheared(true);
                        entitySheep.entityDropItem(new ItemStack(Blocks.wool, 1, entitySheep.getFleeceColor()), 1.0f);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onStartUsing(PlayerUseItemEvent.Start ev) {
        if (ev.item != null) {
            if (ev.entityPlayer.getEntityData().getInteger("itemUsed") > 0) {
                ev.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onUsed(PlayerUseItemEvent.Finish ev) {
        if (ev.item != null) {
            if (!ev.entityPlayer.worldObj.isRemote) {
                if (ev.item.getItemUseAction() == EnumAction.drink) {
                    if (ev.item.getItem() instanceof ItemPotion) {
                        Thirst.getThirst(ev.entityPlayer).addThirst(8, 0.25f);
                        if (ev.item.getMetadata() == 0) {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(17, 600));
                            ev.entityPlayer.addPotionEffect(new PotionEffect(31, 600));
                            ev.entityPlayer.getEntityData().setInteger("nausea", ev.entityPlayer.getEntityData().getInteger("nausea") + 25);
                        }
                    }
                } else if (ev.item.getItemUseAction() == EnumAction.eat) {
                    if (ev.item.getItem() instanceof ItemFood) {
                        ItemFood food = (ItemFood) ev.item.getItem();
                        if (ev.item.getItem() == Items.golden_carrot) {
                            Hunger.getHunger(ev.entityPlayer).addHunger(40, 5.0f);
                        } else if (!(ev.item.getItem() instanceof FoodZp)) {
                            Hunger.getHunger(ev.entityPlayer).addHunger((int) (food.getHealAmount(ev.item) * 4.5f), food.getSaturationModifier(ev.item));
                        }
                        if (PlayerManager.foodAdditionalSaturationMap.containsKey(food)) {
                            Thirst.getThirst(ev.entityPlayer).addThirst(PlayerManager.foodAdditionalSaturationMap.get(food), 0.5f);
                        }
                        if (food == Items.melon) {
                            if (Main.rand.nextFloat() <= 0.02f) {
                                ev.entityPlayer.dropItemWithOffset(Items.melon_seeds, 1, 1.0f);
                            }
                        }
                        if (food == Items.poisonous_potato) {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(19, 300, 1));
                        }
                    }
                }
                if (ev.item.hasTagCompound() && ev.item.getTagCompound().getCompoundTag(Main.MODID).getByte("poisonous") == 1) {
                    ev.entityPlayer.addPotionEffect(new PotionEffect(19, 900, 1));
                    ev.entityPlayer.addPotionEffect(new PotionEffect(20, 400));
                    ev.entityPlayer.addPotionEffect(new PotionEffect(9, 2400));
                }
                if (!(ev.entityPlayer.isPotionActive(26) && ev.entityPlayer.getActivePotionEffect(CommonProxy.zpm).getDuration() <= 12000)) {
                    if (ev.item.getItem() == Items.rotten_flesh) {
                        switch (Main.rand.nextInt(5)) {
                            case 0: {
                                ev.entityPlayer.addPotionEffect(new PotionEffect(19, 360));
                                break;
                            }
                            case 1: {
                                ev.entityPlayer.addPotionEffect(new PotionEffect(17, 1800));
                                break;
                            }
                            case 2: {
                                ev.entityPlayer.addPotionEffect(new PotionEffect(9, 1200));
                                break;
                            }
                            case 3: {
                                ev.entityPlayer.addPotionEffect(new PotionEffect(31, 1800));
                                break;
                            }
                        }
                        if (!ev.entityPlayer.isPotionActive(26) && Main.rand.nextFloat() <= 0.05f) {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(26, 21600));
                        }
                        ev.entityPlayer.getEntityData().setInteger("nausea", ev.entityPlayer.getEntityData().getInteger("nausea") + 60);
                    }
                }
                Item item = ev.item.getItem();
                if (item == ItemsZp.coke || item == ItemsZp.heroin || item == ItemsZp.meth) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.dope, ev.entityPlayer);
                }
                if (item == Items.chicken || item == Items.fish || item == Items.beef || item == Items.porkchop || (item instanceof FoodWaterFish && !((FoodWaterFish) item).cantPoisonPlayer(ev.item))) {
                    ev.entityPlayer.addPotionEffect((new PotionEffect(17, 2400)));
                    ev.entityPlayer.getEntityData().setInteger("nausea", ev.entityPlayer.getEntityData().getInteger("nausea") + 35);
                }
                ev.entityPlayer.getEntityData().setInteger("cdThrow", 20);
            }
            ev.entityPlayer.getEntityData().setInteger("itemUsed", 20);
        }
    }
}
