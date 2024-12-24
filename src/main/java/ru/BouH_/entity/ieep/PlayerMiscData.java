package ru.BouH_.entity.ieep;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ru.BouH_.ConfigZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketMiscInfo;
import ru.BouH_.skills.profiler.SkillProgressProfiler;

import java.io.*;
import java.util.LinkedList;

public class PlayerMiscData implements IExtendedEntityProperties {
    private final EntityPlayer player;
    private final LinkedList<AxisAlignedBB> AABBHistory = new LinkedList<>();
    private int playersKilled;
    private int zombiesKilled;
    private int playerDeaths;
    private int bloodInitializedId;
    private boolean pickUpOnF;
    private float gunInaccuracy;
    private float gunInaccuracyTimer;
    private boolean isLying;
    private int ping;
    private int playerLoudness;
    private int minLvl;
    private SkillProgressProfiler skillProgressProfiler;

    public PlayerMiscData(EntityPlayer player) {
        this.player = player;
        this.ping = -1;
        this.bloodInitializedId = -1;
        this.skillProgressProfiler = new SkillProgressProfiler();
    }

    public static PlayerMiscData getPlayerData(EntityPlayer player) {
        return (PlayerMiscData) player.getExtendedProperties("Miscellaneous");
    }

    public void copy(PlayerMiscData playerMiscData) {
        this.setPlayersKilled(playerMiscData.getPlayersKilled());
        this.setZombiesKilled(playerMiscData.getZombiesKilled());
        this.setPlayerDeaths(playerMiscData.getPlayerDeaths());
        this.setMinLvl(playerMiscData.getMinLvl());
    }

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("playersKilled", this.playersKilled);
        properties.setInteger("zombiesKilled", this.zombiesKilled);
        properties.setInteger("playerDeaths", this.playerDeaths);
        properties.setInteger("bloodInitializedId", this.bloodInitializedId);
        properties.setInteger("minLvl", this.minLvl);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(this.getSkillProgressProfiler());
            }
            properties.setByteArray("skill_prof", byteArrayOutputStream.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        compound.setTag("Miscellaneous", properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag("Miscellaneous");
        if (properties != null) {
            this.playersKilled = properties.getInteger("playersKilled");
            this.zombiesKilled = properties.getInteger("zombiesKilled");
            this.playerDeaths = properties.getInteger("playerDeaths");
            this.minLvl = properties.getInteger("minLvl");
            this.bloodInitializedId = properties.getInteger("bloodInitializedId");
            if (properties.hasKey("skill_prof")) {
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(properties.getByteArray("skill_prof"))) {
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                        this.skillProgressProfiler = (SkillProgressProfiler) objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public SkillProgressProfiler getSkillProgressProfiler() {
        return this.skillProgressProfiler;
    }

    public void addDeath() {
        this.lvlEvent(-this.playerDeaths++);
        this.packet();
    }

    private void lvlEvent(int minLvl) {
        this.setMinLvl(minLvl);
    }

    public void addPlayer() {
        this.playersKilled++;
        this.packet();
    }

    public void onUpdate() {
        int i1 = (int) (this.playerDeaths / 2.0f);
        int i2 = (int) (this.zombiesKilled / 50.0f);
        int lvl = MathHelper.clamp_int(-(i1 - i2), -20, 0);
        this.setMinLvl(!ConfigZp.negativeLevel ? 0 : lvl);
        if (this.getGunInaccuracyTimer() == 0) {
            if (this.player.ticksExisted % 5 == 0) {
                if (this.playerLoudness > 0) {
                    this.playerLoudness -= 1;
                }
            }
        }
    }

    public void addPlayerLoudness(int loud) {
        this.playerLoudness = Math.min(this.playerLoudness + loud, 32);
    }

    public int getPlayerLoudness() {
        return this.playerLoudness;
    }

    public float getGunInaccuracy() {
        return this.gunInaccuracy;
    }

    public void setGunInaccuracy(float gunInaccuracy) {
        this.gunInaccuracy = gunInaccuracy;
    }

    public float getGunInaccuracyTimer() {
        return this.gunInaccuracyTimer;
    }

    public void setGunInaccuracyTimer(float gunInaccuracyTimer) {
        this.gunInaccuracyTimer = gunInaccuracyTimer;
    }

    public boolean isPickUpOnF() {
        return this.pickUpOnF;
    }

    public void setPickUpOnF(boolean pickUpOnF) {
        this.pickUpOnF = pickUpOnF;
    }

    public boolean isLying() {
        return this.isLying;
    }

    public void setLying(boolean lying) {
        this.isLying = lying;
    }

    public int getPing() {
        return this.ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public void updateAABBHistory() {
        this.getAABBHistory().addFirst(this.player.boundingBox.copy());
        if (this.getAABBHistory().size() > 20) {
            this.getAABBHistory().remove(20);
        }
    }

    public LinkedList<AxisAlignedBB> getAABBHistory() {
        return this.AABBHistory;
    }

    public void addZombie(EntityLivingBase entityLivingBase) {
        if (!entityLivingBase.worldObj.isRemote) {
            this.zombiesKilled++;
            if (this.getZombiesKilled() > 0) {
                if (this.getZombiesKilled() % 500 == 0) {
                    entityLivingBase.dropItemWithOffset(ItemsZp.deagle_gold, 1, 1.0f);
                }
            }
            this.packet();
        }
    }

    public int getZombiesKilled() {
        return this.zombiesKilled;
    }

    public void setZombiesKilled(int zombiesKilled) {
        this.zombiesKilled = zombiesKilled;
    }

    public int getPlayersKilled() {
        return this.playersKilled;
    }

    public void setPlayersKilled(int playersKilled) {
        this.playersKilled = playersKilled;
    }

    public int getPlayerDeaths() {
        return this.playerDeaths;
    }

    public void setPlayerDeaths(int playerDeaths) {
        this.playerDeaths = playerDeaths;
    }

    public int getMinLvl() {
        return this.minLvl;
    }

    public void setMinLvl(int minLvl) {
        this.minLvl = minLvl;
    }

    public void packet() {
        NetworkHandler.NETWORK.sendTo(new PacketMiscInfo(this.getPlayersKilled(), this.getZombiesKilled(), this.getPlayerDeaths()), (EntityPlayerMP) this.player);
    }

    public int getBloodInitializedId() {
        return bloodInitializedId;
    }

    public void setBloodInitializedId(int bloodInitializedId) {
        this.bloodInitializedId = bloodInitializedId;
    }

    public void clearBloodInitializedId() {
        this.bloodInitializedId = -1;
    }
}
