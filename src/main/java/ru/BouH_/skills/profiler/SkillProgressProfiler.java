package ru.BouH_.skills.profiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.nbt.PacketSkillProgressData;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;

import java.io.Serializable;

public class SkillProgressProfiler implements Serializable {
    private final Progress fisherProgress;
    private final Progress hunterProgress;
    private final Progress survivorProgress;
    private final Progress gunSmithProgress;
    private final Progress minerProgress;
    private final Progress farmerProgress;

    private static final long serialVersionUID = 13372L;

    public SkillProgressProfiler() {
        this.fisherProgress = new Progress();
        this.hunterProgress = new Progress();
        this.survivorProgress = new Progress();
        this.gunSmithProgress = new Progress();
        this.minerProgress = new Progress();
        this.farmerProgress = new Progress();
    }

    public void setProgress(SkillZp skillZp, EntityPlayer entityPlayer, float points) {
        Progress progress = null;
        if (skillZp == SkillManager.instance.hunter) {
            progress = this.hunterProgress;
        } else if (skillZp == SkillManager.instance.fisher) {
            progress = this.fisherProgress;
        } else if (skillZp == SkillManager.instance.miner) {
            progress = this.minerProgress;
        } else if (skillZp == SkillManager.instance.gunSmith) {
            progress = this.gunSmithProgress;
        } else if (skillZp == SkillManager.instance.survivor) {
            progress = this.survivorProgress;
        } else if (skillZp == SkillManager.instance.farmer) {
            progress = this.farmerProgress;
        }
        if (progress != null) {
            progress.setProgress(skillZp, entityPlayer, points);
        }
    }

    public void addProgress(SkillZp skillZp, EntityPlayer entityPlayer, float points) {
        if (points <= 0.0f) {
            return;
        }
        if (!SkillManager.instance.isMaxSkill(skillZp, entityPlayer)) {
            Progress progress = null;

            if (skillZp == SkillManager.instance.hunter) {
                progress = this.hunterProgress;
            } else if (skillZp == SkillManager.instance.fisher) {
                progress = this.fisherProgress;
            } else if (skillZp == SkillManager.instance.miner) {
                progress = this.minerProgress;
            } else if (skillZp == SkillManager.instance.gunSmith) {
                progress = this.gunSmithProgress;
            } else if (skillZp == SkillManager.instance.survivor) {
                progress = this.survivorProgress;
            } else if (skillZp == SkillManager.instance.farmer) {
                progress = this.farmerProgress;
            }

            if (progress != null) {
                progress.addProgress(skillZp, entityPlayer, points);
            }
        }
    }

    public float getProgress(SkillZp skillZp) {
        if (skillZp == SkillManager.instance.hunter) {
            return this.checkIfNullAndCreate(this.hunterProgress).getProgress();
        } else if (skillZp == SkillManager.instance.fisher) {
            return this.checkIfNullAndCreate(this.fisherProgress).getProgress();
        } else if (skillZp == SkillManager.instance.miner) {
            return this.checkIfNullAndCreate(this.minerProgress).getProgress();
        } else if (skillZp == SkillManager.instance.gunSmith) {
            return this.checkIfNullAndCreate(this.gunSmithProgress).getProgress();
        } else if (skillZp == SkillManager.instance.survivor) {
            return this.checkIfNullAndCreate(this.survivorProgress).getProgress();
        } else if (skillZp == SkillManager.instance.farmer) {
            return this.checkIfNullAndCreate(this.farmerProgress).getProgress();
        }
        return -1.0f;
    }

    private Progress checkIfNullAndCreate(Progress progress) {
        if (progress == null) {
            progress = new Progress();
        }
        return progress;
    }

    public static final class Progress implements Serializable {
        private float progress;
        private static final long serialVersionUID = 13373L;

        public float getProgress() {
            return this.progress;
        }

        public void addProgress(SkillZp skillZp, EntityPlayer entityPlayer, float points) {
            int currLvl = SkillManager.instance.getSkillPoints(skillZp, entityPlayer);
            float f1 = 1.0f - (currLvl * 0.025f);
            this.progress += points * f1;
            if (this.getProgress() > 1.0f) {
                this.progress = 0.0f;
                SkillManager.instance.addSkillPoints(skillZp, entityPlayer, 1);
                entityPlayer.addExperience(15);
            }
            if (!entityPlayer.worldObj.isRemote) {
                NetworkHandler.NETWORK.sendTo(new PacketSkillProgressData(entityPlayer.getEntityId(), skillZp.getId(), this.getProgress()), (EntityPlayerMP) entityPlayer);
            }
        }

        public void setProgress(SkillZp skillZp, EntityPlayer entityPlayer, float points) {
            this.progress = MathHelper.clamp_float(points, -1.0f, 1.0f);
        }
    }
}
