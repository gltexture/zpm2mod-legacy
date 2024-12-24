package ru.BouH_.items.gun.render.tracer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.utils.RenderUtils;

public class RenderTracer {
    public static final ConcurrentSet<RenderTracer> tracerSet = new ConcurrentSet<>();
    private double startX;
    private double startY;
    private double startZ;
    private double motionX;
    private double motionY;
    private double motionZ;
    private double motionXPrev;
    private double motionYPrev;
    private double motionZPrev;
    private Vec3 startVec;
    private Vec3 endVec;
    private float red;
    private float green;
    private float blue;
    private boolean shouldRender;

    public RenderTracer() {
    }

    public RenderTracer(EntityPlayer player, boolean shouldRender, double x, double y, double z) {
        Vec3 lookVec = player.getLookVec();
        this.startX = player.posX + lookVec.xCoord;
        this.startZ = player.posZ + lookVec.zCoord;
        this.shouldRender = shouldRender;
        if (player instanceof EntityPlayerSP) {
            ItemStack stack = player.getHeldItem();
            if (stack != null && stack.getItem() instanceof AGunBase) {
                if (stack.getItem() == ItemsZp.sodagun) {
                    this.red = 1.0f;
                    this.green = 0.1f;
                    this.blue = 1.0f;
                } else {
                    this.red = 1.0f;
                    this.green = 1.0f;
                    this.blue = 1.0f;
                }
                AGunBase aGunBase = (AGunBase) stack.getItem();
                if (!aGunBase.isPlayerInOpticScope(stack)) {
                    this.startX -= MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * 0.25f;
                    this.startZ -= MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * 0.25f;
                }
            }
        } else {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
        }
        this.startY = player.boundingBox.maxY - (GunItemRender.instance.isInScope() ? 0.35f : 0.325f) + lookVec.yCoord;
        this.startVec = Vec3.createVectorHelper(this.startX, this.startY, this.startZ);
        this.endVec = Vec3.createVectorHelper(x, y, z);
    }

    public static void register(RenderTracer tracer) {
        if (tracer.startVec.distanceTo(tracer.endVec) > 2.0f) {
            RenderTracer.tracerSet.add(tracer);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        RenderTracer.tracerSet.forEach(trace -> {
            double length = trace.startVec.distanceTo(trace.endVec);
            trace.motionXPrev = trace.motionX;
            trace.motionYPrev = trace.motionY;
            trace.motionZPrev = trace.motionZ;
            final float speed = 9.0f;
            trace.motionX += (trace.endVec.xCoord - trace.startVec.xCoord) / length * speed;
            trace.motionY += (trace.endVec.yCoord - trace.startVec.yCoord) / length * speed;
            trace.motionZ += (trace.endVec.zCoord - trace.startVec.zCoord) / length * speed;
        });
    }

    public boolean render(float partialTicks) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double length = this.startVec.distanceTo(this.endVec);

        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslated(-x, -y, -z);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(this.red, this.green, this.blue, 0.825f);

        Vec3 start = this.startVec;
        Vec3 newEnd = Vec3.createVectorHelper(this.startX + (this.endVec.xCoord - this.startVec.xCoord) / length * 4.0f, this.startY + (this.endVec.yCoord - this.startVec.yCoord) / length * 4.0f, this.startZ + (this.endVec.zCoord - this.startVec.zCoord) / length * 4.0f);

        double speedX = this.motionXPrev + (this.motionX - this.motionXPrev) * partialTicks;
        double speedY = this.motionYPrev + (this.motionY - this.motionYPrev) * partialTicks;
        double speedZ = this.motionZPrev + (this.motionZ - this.motionZPrev) * partialTicks;

        start = start.addVector(speedX, speedY, speedZ);
        newEnd = newEnd.addVector(speedX, speedY, speedZ);

        int blX = (int) start.xCoord;
        int blY = (int) start.yCoord;
        int blZ = (int) start.zCoord;
        Block block = player.worldObj.getBlock(blX, blY, blZ);

        if (block.getMaterial() == Material.water) {
            if (player.worldObj.getBlockMetadata(blX, blY, blZ) == 0 || player.worldObj.getBlockMetadata(blX, blY, blZ) == 9) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBubbleFX(player.worldObj, start.xCoord, start.yCoord, start.zCoord, 0, 0, 0));
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBubbleFX(player.worldObj, newEnd.xCoord, newEnd.yCoord, newEnd.zCoord, 0, 0, 0));
            }
        } else {
            if (this.shouldRender) {
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3d(start.xCoord, start.yCoord, start.zCoord);
                GL11.glVertex3d(newEnd.xCoord, newEnd.yCoord, newEnd.zCoord);
                GL11.glEnd();
                GL11.glLineWidth(2);
            }
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        return this.startVec.distanceTo(newEnd) > this.startVec.distanceTo(this.endVec);
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        RenderUtils.partialTicks = event.partialTicks;
        RenderTracer.tracerSet.removeIf(e -> e.render(event.partialTicks));
    }
}