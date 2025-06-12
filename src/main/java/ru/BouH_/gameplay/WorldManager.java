package ru.BouH_.gameplay;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.FuelBurnTimeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import ru.BouH_.ConfigZp;
import ru.BouH_.gameplay.client.ClientHandler;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.PacketDay;
import ru.BouH_.network.packets.misc.sound.PacketSound;
import ru.BouH_.network.packets.nbt.PacketMiscPlayerNbtInfo;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.type.WorldTypeHardcoreZp;

import java.util.List;
import java.util.Objects;

public class WorldManager {
    public static WorldManager instance = new WorldManager();
    public static int maxMonsterType;
    public long serverWorldTickTime;
    public long currentTime;

    public static boolean isSp() {
        return (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers.length > 0);
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load ev) {
        if (!ev.world.isRemote) {
            for (List<Entity> eList : ev.getChunk().entityLists) {
                for (Entity entity : eList) {
                    if (entity instanceof IProjectile) {
                        entity.setDead();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void trackEvent(PlayerEvent.StartTracking ev) {
        if (ev.target instanceof EntityPlayerMP) {
            NetworkHandler.NETWORK.sendTo(new PacketMiscPlayerNbtInfo(ev.target.getEntityId(), ev.target.getEntityData()), (EntityPlayerMP) ev.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load ev) {
        if (!ev.world.getGameRules().hasRule("changeChestsToLootCases")) {
            ev.world.getGameRules().addGameRule("changeChestsToLootCases", "false");
        }
        if (!ev.world.getGameRules().hasRule("showDeathMessages")) {
            ev.world.getGameRules().addGameRule("showDeathMessages", "true");
        }
        if (!ev.world.getGameRules().hasRule("doWeatherCycle")) {
            ev.world.getGameRules().addGameRule("doWeatherCycle", "true");
        }
        if (!ev.world.getGameRules().hasRule("drawTracersForAll")) {
            ev.world.getGameRules().addGameRule("drawTracersForAll", "false");
        }
        if (!ev.world.getGameRules().hasRule("doWeaponUnbreakable")) {
            ev.world.getGameRules().addGameRule("doWeaponUnbreakable", "false");
        }
        if (!ev.world.getGameRules().hasRule("canWeaponJam")) {
            ev.world.getGameRules().addGameRule("canWeaponJam", "true");
        }
        if (!ev.world.getGameRules().hasRule("disableNightSkipping")) {
            ev.world.getGameRules().addGameRule("disableNightSkipping", "false");
        }
        if (!ev.world.getGameRules().hasRule("zombiesCanMine")) {
            ev.world.getGameRules().addGameRule("zombiesCanMine", "true");
        }
        if (!ev.world.getGameRules().hasRule("zombiesCanEatFood")) {
            ev.world.getGameRules().addGameRule("zombiesCanEatFood", "true");
        }
        if (!ev.world.getGameRules().hasRule("zombiesCanPickItems")) {
            ev.world.getGameRules().addGameRule("zombiesCanPickItems", "true");
        }
        if (!ev.world.getGameRules().hasRule("zombiesSeekXRay")) {
            ev.world.getGameRules().addGameRule("zombiesSeekXRay", "true");
        }
        if (!ev.world.getGameRules().hasRule("achievementMessagesForAll")) {
            ev.world.getGameRules().addGameRule("achievementMessagesForAll", "true");
        }
        if (!ev.world.getGameRules().hasRule("keepSkillsAfterDeath")) {
            ev.world.getGameRules().addGameRule("keepSkillsAfterDeath", "false");
        }
        if (!ev.world.provider.hasNoSky) {
            WorldSaveDay.getStorage(ev.world);
        }
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save ev) {
        if (!ev.world.provider.hasNoSky) {
            Objects.requireNonNull(WorldSaveDay.getStorage(ev.world)).markDirty();
        }
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public void fuelEvent(FuelBurnTimeEvent ev) {
        ev.setResult(Event.Result.DENY);
        Item item = ev.fuel.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
            Block block = Block.getBlockFromItem(item);

            if (block == Blocks.wooden_slab) {
                ev.burnTime = 100;
            }

            if (block.getMaterial() == Material.wood) {
                ev.burnTime = 200;
            }

            if (block.getMaterial() == Material.web) {
                ev.burnTime = 40;
            }

            if (block.getMaterial() == Material.carpet) {
                ev.burnTime = 40;
            }

            if (block.getMaterial() == Material.cloth) {
                ev.burnTime = 80;
            }

            if (block == Blocks.coal_block) {
                ev.burnTime = 14400;
            }
        }

        if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) {
            ev.burnTime = 80;
        } else if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) {
            ev.burnTime = 80;
        } else if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) {
            ev.burnTime = 80;
        } else if (item == Items.stick) {
            ev.burnTime = 50;
        } else if (item == Items.coal) {
            ev.burnTime = 1600;
        } else if (item == Items.lava_bucket) {
            ev.burnTime = 12000;
        } else if (item == Item.getItemFromBlock(Blocks.sapling)) {
            ev.burnTime = 60;
        } else if (item == Items.blaze_rod) {
            ev.burnTime = Integer.MAX_VALUE;
        } else if (item == ItemsZp.uran_material) {
            ev.burnTime = 12000;
        } else if (item == Items.blaze_powder) {
            ev.burnTime = 600;
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent ev) {
        WorldServer world1 = DimensionManager.getWorld(0);
        WorldServer world2 = DimensionManager.getWorld(2);
        ConfigZp.skillsSystemCrafts = false;

        int nominalZombies = 0;
        switch (world1.difficultySetting) {
            case EASY: {
                nominalZombies = 120;
                break;
            }
            case NORMAL: {
                nominalZombies = 150;
                break;
            }
            case HARD: {
                nominalZombies = 180;
                break;
            }
            default: {
                nominalZombies = 120;
            }
        }

        if (SpecialGenerator.getTerType(world1) instanceof WorldTypeHardcoreZp) {
            world1.difficultySetting = EnumDifficulty.HARD;
        }

        if (WorldManager.is7Night(world1)) {
            nominalZombies += 32;
        }

        WorldManager.maxMonsterType = nominalZombies;
        WorldManager.maxMonsterType *= ConfigZp.zombieSpawnScale;

        if (ev.phase == TickEvent.Phase.START) {
            if (ConfigZp.Dim2Dim0Sync) {
                if (world1.getWorldTime() % 400 == 0) {
                    world2.setWorldTime(world1.getWorldTime());
                }
            }
            if (this.currentTime != 0) {
                if (world1.getGameRules().getGameRuleBooleanValue("disableNightSkipping")) {
                    MinecraftServer.getServer().worldServers[0].setWorldTime(WorldManager.instance.currentTime);
                }
                this.currentTime = 0;
            }
            if (world1.areAllPlayersAsleep()) {
                this.currentTime = world1.getWorldTime();
                this.serverWorldTickTime = 0;
            } else if (world1.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                if (this.serverWorldTickTime++ >= 2) {

                    final int nightT = 13500;
                    final int dayT = 23500;

                    if (world1.getWorldTime() % 24000 == dayT) {
                        NetworkHandler.NETWORK.sendToDimension(new PacketSound(4), 0);
                        WorldSaveDay saveDay = WorldSaveDay.getStorage(world1);
                        if (saveDay != null) {
                            saveDay.day += 1;
                            saveDay.markDirty();
                            NetworkHandler.NETWORK.sendToDimension(new PacketDay(WorldManager.is7NightEnabled(), saveDay.day), 0);
                        }
                    }
                    if (world2.getWorldTime() % 24000 == (isSp() ? nightT : dayT)) {
                        NetworkHandler.NETWORK.sendToDimension(new PacketSound(4), 2);
                        WorldSaveDay saveDay = WorldSaveDay.getStorage(world2);
                        if (saveDay != null) {
                            saveDay.day += 1;
                            saveDay.markDirty();
                            NetworkHandler.NETWORK.sendToDimension(new PacketDay(WorldManager.is7NightEnabled(), saveDay.day), 2);
                        }
                    }
                    if (world1.getWorldTime() % 24000 == nightT) {
                        if (WorldManager.is7Night(world1)) {
                            WeatherHandler.instance.getWeatherFog().startWeatherFogPacket7Night(WeatherHandler.instance.fogManagerMap.get(0));
                            NetworkHandler.NETWORK.sendToDimension(new PacketSound(5), 0);
                        } else {
                            NetworkHandler.NETWORK.sendToDimension(new PacketSound(1), 0);
                        }
                    }
                    if (world2.getWorldTime() % 24000 == (isSp() ? dayT : nightT)) {
                        if (WorldManager.is7Night(world2)) {
                            WeatherHandler.instance.getWeatherFog().startWeatherFogPacket7Night(WeatherHandler.instance.fogManagerMap.get(2));
                            NetworkHandler.NETWORK.sendToDimension(new PacketSound(5), 2);
                        } else {
                            NetworkHandler.NETWORK.sendToDimension(new PacketSound(1), 2);
                        }
                    }
                    this.serverWorldTickTime = 0;
                } else {
                    this.slowDownTime(world1);
                    this.slowDownTime(world2);
                }
            }
        }
        if (!world1.getGameRules().getGameRuleBooleanValue("disableNightSkipping")) {
            if (ev.phase == TickEvent.Phase.END) {
                if (world1.areAllPlayersAsleep()) {
                    world1.getWorldInfo().incrementTotalWorldTime(24000 - world1.getWorldTime());
                    world1.setWorldTime(0);
                }
            }
        }
    }

    public static boolean is7NightEnabled() {
        return ConfigZp.night7;
    }

    public static boolean is7Night(World world) {
        if (world.isRemote) {
            return ClientHandler.is7nightEnabled && (ClientHandler.day > 0 && ClientHandler.day % 7 == 0) || SpecialGenerator.getTerType(world) instanceof WorldTypeHardcoreZp;
        }
        if (world.provider.dimensionId == 0 || world.provider.dimensionId == 2) {
            if (SpecialGenerator.getTerType(world) instanceof WorldTypeHardcoreZp) {
                return true;
            }
            int dayZ = world.getWorldInfo().getNBTTagCompound().getInteger("dayZ");
            return WorldManager.is7NightEnabled() && (dayZ > 0 && dayZ % 7 == 0) && !world.provider.isDaytime();
        }
        return false;
    }

    public int getMidNightTime(World world) {
        return 18000;
    }

    private void slowDownTime(World world) {
        if (!(SpecialGenerator.getTerType(world) instanceof WorldTypeHardcoreZp) && ((ConfigZp.longDays && world.provider.isDaytime()) || (ConfigZp.longNights && !world.provider.isDaytime()))) {
            world.setWorldTime(world.getWorldTime() - 1);
            world.getWorldInfo().incrementTotalWorldTime(world.getTotalWorldTime() - 1);
        }
    }

    public static class WorldSaveDay extends WorldSavedData {
        public int day;

        public WorldSaveDay(String id) {
            super(id);
        }

        public static WorldSaveDay getStorage(World world) {
            if (world.mapStorage != null) {
                String ID = WorldSaveDay.class.getName() + "_" + world.provider.dimensionId;
                WorldSaveDay data = (WorldSaveDay) world.mapStorage.loadData(WorldSaveDay.class, ID);
                if (data != null) {
                    return data;
                } else {
                    data = new WorldSaveDay(ID);
                    data.markDirty();
                    world.mapStorage.setData(ID, data);
                }
            }
            return null;
        }

        @Override
        public void readFromNBT(NBTTagCompound p_76184_1_) {
            this.day = p_76184_1_.getInteger("dayZ");
        }

        @Override
        public void writeToNBT(NBTTagCompound p_76187_1_) {
            p_76187_1_.setInteger("dayZ", this.day);
        }
    }
}
