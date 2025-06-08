package ru.BouH_.entity.ieep;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ru.BouH_.ConfigZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketThirst;
import ru.BouH_.utils.EntityUtils;

public class Thirst implements IExtendedEntityProperties {
    private final EntityPlayer player;
    public float thirstSaturationLevel;
    public float thirstExhaustionLevel;
    public int thirstTime;
    private int thirst;
    private int prevThirst;

    public Thirst(EntityPlayer player) {
        this.player = player;
        this.thirst = 80;
        this.thirstSaturationLevel = 5.0f;
    }

    public static Thirst getThirst(EntityPlayer player) {
        return (Thirst) player.getExtendedProperties("Thirst");
    }

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("prevThirst", this.prevThirst);
        properties.setInteger("thirst", this.thirst);
        properties.setInteger("thirstTime", this.thirstTime);
        properties.setFloat("thirstSaturationLevel", this.thirstSaturationLevel);
        properties.setFloat("thirstExhaustionLevel", this.thirstExhaustionLevel);
        compound.setTag("Thirst", properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag("Thirst");
        if (properties != null) {
            this.prevThirst = properties.getInteger("prevThirst");
            this.thirst = properties.getInteger("thirst");
            this.thirstTime = properties.getInteger("thirstTime");
            this.thirstSaturationLevel = properties.getFloat("thirstSaturationLevel");
            this.thirstExhaustionLevel = properties.getFloat("thirstExhaustionLevel");
        }
    }

    public void addThirst(int amount, float mod) {
        int i1 = this.thirst + amount;
        this.thirst = (Math.min(i1, 100));
        this.thirstSaturationLevel = Math.min(this.thirstSaturationLevel + (float) amount * mod, 5.0f);
        if (i1 > 100) {
            this.player.getEntityData().setInteger("nausea", this.player.getEntityData().getInteger("nausea") + (i1 - 100));
        }
        this.packet();
    }

    public void removeThirst(int amount) {
        this.thirst = MathHelper.clamp_int(this.thirst - amount, 0, 100);
        this.thirstSaturationLevel = 0.0f;
        this.packet();
    }

    public void onUpdate() {
        EnumDifficulty enumdifficulty = this.player.worldObj.difficultySetting;
        this.prevThirst = this.thirst;
        int i1 = enumdifficulty == EnumDifficulty.EASY ? 40 : enumdifficulty == EnumDifficulty.NORMAL ? 50 : 60;
        float f1 = enumdifficulty == EnumDifficulty.EASY ? 1.1f : enumdifficulty == EnumDifficulty.NORMAL ? 1.0f : 0.9f;
        if (this.thirstExhaustionLevel > f1) {
            this.thirstExhaustionLevel -= f1;
            if (this.thirstSaturationLevel > 0.0F) {
                this.thirstSaturationLevel = Math.max(this.thirstSaturationLevel - f1, 0.0F);
            } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
                this.removeThirst(1);
            }
        }
        if (this.player.hurtTime > 0) {
            this.thirstTime = 0;
        }
        if (this.player.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.thirst > i1 && this.player.shouldHeal() && !this.player.isPotionActive(28)) {
            ++this.thirstTime;
            if (this.thirstTime >= 100) {
                this.player.heal(1.0F);
                this.addExhaustion(1.0F);
                this.thirstTime = 0;
            }
        } else if (this.thirst <= 0) {
            ++this.thirstTime;
            if (this.thirstTime >= 100) {
                if (enumdifficulty == EnumDifficulty.HARD || (this.player.getHealth() > 1.0F)) {
                    this.player.attackEntityFrom(DamageSource.starve, 1.0F);
                }
                this.thirstTime = 0;
            }
        } else {
            this.thirstTime = 0;
        }
    }

    public void addExhaustion(float p_75113_1_) {
        if (EntityUtils.isInBlock(this.player, Blocks.water)) {
            p_75113_1_ *= 0.75f;
        }
        this.thirstExhaustionLevel = Math.min(this.thirstExhaustionLevel + p_75113_1_, 50.0F) * ConfigZp.thirstExhaustionMultiplier;
    }

    public void setExhaustion(float p_75113_1_) {
        this.thirstExhaustionLevel = p_75113_1_;
    }

    public void setSaturation(float p_75113_1_) {
        this.thirstSaturationLevel = p_75113_1_;
    }

    public int getThirst() {
        return this.thirst;
    }

    @SideOnly(Side.CLIENT)
    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public int getPrevThirst() {
        return this.prevThirst;
    }

    @SideOnly(Side.CLIENT)
    public void setPrevThirst(int prevThirst) {
        this.prevThirst = prevThirst;
    }

    public void packet() {
        NetworkHandler.NETWORK.sendTo(new PacketThirst(this.getThirst()), (EntityPlayerMP) player);
    }
}
