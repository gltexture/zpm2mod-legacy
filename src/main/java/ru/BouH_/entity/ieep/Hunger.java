package ru.BouH_.entity.ieep;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ru.BouH_.ConfigZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketHunger;

public class Hunger implements IExtendedEntityProperties {
    private final EntityPlayer player;
    public float hungerSaturationLevel;
    public float hungerExhaustionLevel;
    public int hungerTime;
    private int hunger;
    private int prevHunger;

    public Hunger(EntityPlayer player) {
        this.player = player;
        this.hunger = 80;
        this.hungerSaturationLevel = 5.0f;
    }

    public static Hunger getHunger(EntityPlayer player) {
        return (Hunger) player.getExtendedProperties("Hunger");
    }

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("prevHunger", this.prevHunger);
        properties.setInteger("hunger", this.hunger);
        properties.setInteger("hungerTime", this.hungerTime);
        properties.setFloat("hungerSaturationLevel", this.hungerSaturationLevel);
        properties.setFloat("hungerExhaustionLevel", this.hungerExhaustionLevel);
        compound.setTag("Hunger", properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag("Hunger");
        if (properties != null) {
            this.prevHunger = properties.getInteger("prevHunger");
            this.hunger = properties.getInteger("hunger");
            this.hungerTime = properties.getInteger("hungerTime");
            this.hungerSaturationLevel = properties.getFloat("hungerSaturationLevel");
            this.hungerExhaustionLevel = properties.getFloat("hungerExhaustionLevel");
        }
    }

    public void addHunger(int amount, float mod) {
        int i1 = this.hunger + amount;
        this.hunger = (Math.min(i1, 100));
        this.hungerSaturationLevel = Math.min(this.hungerSaturationLevel + (float) amount * mod, 5.0f);
        if (i1 > 100) {
            this.player.getEntityData().setInteger("nausea", this.player.getEntityData().getInteger("nausea") + (i1 - 100));
        }
        this.packet();
    }

    public void removeHunger(int amount) {
        this.hunger = MathHelper.clamp_int(this.hunger - amount, 0, 100);
        this.hungerSaturationLevel = 0.0f;
        this.packet();
    }

    public void onUpdate() {
        EnumDifficulty enumdifficulty = this.player.worldObj.difficultySetting;
        this.prevHunger = this.hunger;
        int i1 = enumdifficulty == EnumDifficulty.EASY ? 40 : enumdifficulty == EnumDifficulty.NORMAL ? 50 : 60;
        float f1 = enumdifficulty == EnumDifficulty.EASY ? 1.1f : enumdifficulty == EnumDifficulty.NORMAL ? 1.0f : 0.9f;
        if (this.hungerExhaustionLevel > f1) {
            this.hungerExhaustionLevel -= f1;
            if (this.hungerSaturationLevel > 0.0F) {
                this.hungerSaturationLevel = Math.max(this.hungerSaturationLevel - f1, 0.0F);
            } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
                this.removeHunger(1);
            }
        }
        if (this.player.hurtTime > 0) {
            this.hungerTime = 0;
        }
        if (this.player.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.hunger > i1 && this.player.shouldHeal() && !this.player.isPotionActive(28)) {
            ++this.hungerTime;
            if (this.hungerTime >= 100) {
                this.player.heal(1.0F);
                this.addExhaustion(1.0F);
                this.hungerTime = 0;
            }
        } else if (this.hunger <= 0) {
            ++this.hungerTime;
            if (this.hungerTime >= 100) {
                if (enumdifficulty == EnumDifficulty.HARD || (this.player.getHealth() > 1.0F)) {
                    this.player.attackEntityFrom(DamageSource.starve, 1.0F);
                }
                this.hungerTime = 0;
            }
        } else {
            this.hungerTime = 0;
        }
    }

    public void addExhaustion(float p_75113_1_) {
        this.hungerExhaustionLevel = Math.min(this.hungerExhaustionLevel + p_75113_1_, 50.0F) * ConfigZp.hungerExhaustionMultiplier;
    }

    public void setExhaustion(float p_75113_1_) {
        this.hungerExhaustionLevel = p_75113_1_;
    }

    public void setSaturation(float p_75113_1_) {
        this.hungerSaturationLevel = p_75113_1_;
    }

    public int getHunger() {
        return this.hunger;
    }

    @SideOnly(Side.CLIENT)
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getPrevHunger() {
        return this.prevHunger;
    }

    @SideOnly(Side.CLIENT)
    public void setPrevHunger(int prevHunger) {
        this.prevHunger = prevHunger;
    }

    public void packet() {
        NetworkHandler.NETWORK.sendTo(new PacketHunger(this.getHunger()), (EntityPlayerMP) player);
    }
}
