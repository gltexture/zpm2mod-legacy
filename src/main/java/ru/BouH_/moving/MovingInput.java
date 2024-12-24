package ru.BouH_.moving;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.moving.PacketLying;

@SideOnly(Side.CLIENT)
public class MovingInput extends MovementInput {
    public static MovingInput instance = new MovingInput(Minecraft.getMinecraft().gameSettings);
    private final GameSettings gameSettings;
    public boolean lie;
    private boolean wasSprintPressed;
    private boolean wasSwimming;
    private int lieCd;
    private int nonGroundTicks;

    public float speed_rec;
    public int jumpTms;
    public int jumpCd;

    public MovingInput(GameSettings p_i1237_1_) {
        this.gameSettings = p_i1237_1_;
    }

    public void updatePlayerMoveState() {
        EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;
        boolean sprint = this.gameSettings.keyBindSprint.getIsKeyPressed();
        boolean lie = !this.wasSprintPressed && !this.jump && !pl.isOnLadder() && this.gameSettings.keyBindSneak.getIsKeyPressed() && sprint;
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.getIsKeyPressed()) {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.getIsKeyPressed()) {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.getIsKeyPressed()) {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.getIsKeyPressed()) {
            --this.moveStrafe;
        }

        this.sneak = !MovingUtils.isSwimming(pl) && !this.lie && this.gameSettings.keyBindSneak.getIsKeyPressed();

        if (this.sneak && pl.isOnLadder()) {
            pl.motionY = 0;
            this.sneak = false;
        }

        if (!this.lie && this.lieCd > 0) {
            this.lieCd -= 1;
        } else {
            if (this.wasSprintPressed) {
                if (MovingUtils.canSwim(pl)) {
                    this.lie = true;
                }
            } else if (!(!this.lie && pl.hurtTime > 0)) {
                this.lie = lie || (this.lie && sprint);
            }
        }
        this.wasSprintPressed = !this.lie && sprint;

        if (!pl.onGround && !pl.inWater) {
            this.nonGroundTicks += 1;
        } else {
            this.nonGroundTicks = 0;
        }

        if (!MovingUtils.isSwimming(pl)) {
            if (pl.isOnLadder() || Math.abs(pl.motionY) >= 0.5f || this.nonGroundTicks > 3) {
                this.lie = false;
            }
        } else {
            pl.setSprinting(true);
        }


        if ((!this.wasSwimming && !MovingUtils.isSwimming(pl) && Math.abs(pl.motionY) >= 0.15f) || (this.wasSwimming && !MovingUtils.isSwimming(pl) && this.lie) || pl.capabilities.isFlying) {
            this.lie = false;
        }

        if (!this.lie) {
            if (MovingUtils.forceLie(pl)) {
                this.lie = true;
            } else if (MovingUtils.forceSneak(pl)) {
                this.sneak = true;
            }
        }

        if (!this.lie) {
            this.jump = !MovingUtils.isSwimming(pl) && !(this.sneak && !pl.capabilities.isFlying) && this.gameSettings.keyBindJump.getIsKeyPressed();
        } else {
            this.lieCd = 30;
        }

        NetworkHandler.NETWORK.sendToServer(new PacketLying(this.lie));
        this.wasSwimming = MovingUtils.isSwimming(pl);
        Minecraft.getMinecraft().thePlayer.yOffset2 = this.lie ? 2.5f : this.sneak ? 0.8f : 0.0f;
        PlayerMiscData.getPlayerData(pl).setLying(this.lie);
        if (!MovingUtils.isSwimming(pl)) {
            if (this.lie) {
                if (!pl.handleWaterMovement()) {
                    this.moveStrafe = (float) ((double) this.moveStrafe * 0.2D);
                    this.moveForward = (float) ((double) this.moveForward * 0.2D);
                }
            } else if (this.sneak) {
                this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
                this.moveForward = (float) ((double) this.moveForward * 0.3D);
            }
        }
        if (this.stopPlayerRun(pl)) {
            this.moveForward = Math.min(this.moveForward, 0.7925f);
            this.moveStrafe = Math.min(this.moveStrafe, 0.7925f);
        }
        if (this.speed_rec > 0.0f) {
            this.moveForward *= (1.0f - this.speed_rec);
            this.moveStrafe *= (1.0f - this.speed_rec);
            this.speed_rec -= 0.1f;
        }
        if (MovingInput.instance.jumpCd-- <= 0) {
            MovingInput.instance.jumpTms = 0;
        }
    }

    private boolean stopPlayerRun(EntityPlayer pl) {
        return pl.isPotionActive(27) || Thirst.getThirst(pl).getThirst() <= 20 || Hunger.getHunger(pl).getHunger() <= 20;
    }
}
