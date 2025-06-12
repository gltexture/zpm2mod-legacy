package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import ru.BouH_.Main;
import ru.BouH_.audio.AmbientSounds;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleLeaf;
import ru.BouH_.entity.zombie.EntityModZombie;
import ru.BouH_.entity.zombie.EntityModZombie2;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.FluidsZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.gun.base.fun.ALauncherBase;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.ClientUtils;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.SoundUtils;
import ru.BouH_.utils.TraceUtils;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.managers.WeatherRainManager;

@SideOnly(Side.CLIENT)
public class ClientHandler {
    public static int day;
    public static boolean is7nightEnabled;

    public static ClientHandler instance = new ClientHandler();
    public static long clientWorldTickTime;
    public int scaryTimer;
    public EntityModZombie horror;
    public int horrorTimer;
    public int seeTimer;
    public boolean wasHorrorSeen;
    public float nightBrightConstant = 0.0f;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        //TODO: Remake this shit
        if (player != null && !mc.isGamePaused()) {
            if (ev.phase == TickEvent.Phase.START) {
                float f2 = 0.0f;
                if (!AmbientSounds.isClientDayTime(player.worldObj) && AmbientSounds.instance.canPlayerHearAmbientBypassGlass) {
                    int curr = (int) player.worldObj.getWorldTime() % 24000;
                    int i1 = WorldManager.instance.getMidNightTime(player.worldObj);
                    f2 = MathHelper.clamp_float(1.0f - Math.abs(curr - i1) / 4500.0f, 0.0f, 1.0f);
                }
                float funN = (float) Math.pow(f2, 2.0f);
                funN = Math.min(funN, 0.7f);

                if (this.nightBrightConstant >= f2) {
                    this.nightBrightConstant = Math.max(this.nightBrightConstant - 0.025f, funN);
                } else {
                    this.nightBrightConstant = Math.min(this.nightBrightConstant + 0.025f, funN);
                }

                WeatherRainManager weatherRainManager = (WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(player.worldObj.provider.dimensionId);
                if (weatherRainManager != null) {
                    this.nightBrightConstant *= (1.0f - weatherRainManager.getRainStrength());
                }

                float f1 = player.isSneaking() ? 1.2e-2f : PlayerMiscData.getPlayerData(player).isLying() ? 1.0e-3f : 2.0e-2f;
                player.cameraPitch += MathHelper.sin(player.ticksExisted * 0.05f) * f1;
                player.rotationPitch -= MathHelper.sin(player.ticksExisted * 0.05f) * f1;
                player.rotationYaw -= (float) (Math.sin(2.0f + player.ticksExisted * 0.1f) * f1);
                ItemChecker.updateItemPickingUp();
                int x = MathHelper.floor_double(player.posX);
                int y = (int) player.posY;
                int z = MathHelper.floor_double(player.posZ);
                if (player.inventory.hasItem(ItemsZp.dosimeter)) {
                    int multiRad = EntityUtils.isEntityInLowRadiation(player) ? 4 : EntityUtils.isEntityInHighRadiation(player) ? 2 : 0;
                    if (multiRad > 0) {
                        if (player.ticksExisted % multiRad == 0) {
                            SoundUtils.playClientSound(player, Main.MODID + ":geiger" + (Main.rand.nextInt(3) + 1), 1.5F, 1.0f + Main.rand.nextFloat() * (multiRad * 0.1f));
                        }
                    }
                }
                if (this.getClosestPlayerExcluding(player.worldObj, x, y, z, 32) == null) {
                    if (this.scaryTimer++ >= 600) {
                        if (!Minecraft.getMinecraft().isGamePaused()) {
                            if (Main.rand.nextFloat() <= 0.01f) {
                                if (player.worldObj.getBlockLightValue(x, y, z) < 10 && !player.worldObj.canBlockSeeTheSky(x, y, z) && !player.isPotionActive(16) && !(ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope()) && !player.capabilities.isCreativeMode) {
                                    AmbientSounds.instance.spirit[Main.rand.nextInt(4)].playSound();
                                }
                            }
                            this.scaryTimer = 0;
                        }
                    }

                    if (this.scaryTimer > 0) {
                        this.scaryTimer--;
                    }
                    if (this.wasHorrorSeen) {
                        this.seeTimer += 1;
                    }
                    if (this.horror != null) {
                        if (this.seeTimer >= 40 || this.horror.ticksExisted > 1200) {
                            this.horror.setDead();
                            this.horror = null;
                            this.seeTimer = 0;
                            this.wasHorrorSeen = false;
                        }
                    } else {
                        if (this.horrorTimer++ >= 12000) {
                            if (Main.rand.nextFloat() <= 0.25f) {
                                int i1 = x + Main.rand.nextInt(201) - 100;
                                int k1 = z + Main.rand.nextInt(201) - 100;
                                int j1 = this.findY(player.worldObj, i1, (int) (player.posY + 16), k1) + 1;
                                if (player.getDistance(i1, j1, k1) >= 48) {
                                    if (this.canSpawnTarget(i1, k1 + 1, k1)) {
                                        this.horror = Main.rand.nextBoolean() ? new EntityModZombie(player.worldObj) : new EntityModZombie2(player.worldObj);
                                        this.horror.setLocationAndAngles(i1, j1, k1, Main.rand.nextInt(361) - 180, Main.rand.nextInt(181) - 90);
                                        player.worldObj.spawnEntityInWorld(this.horror);
                                    }
                                }
                            }
                            this.horrorTimer = 0;
                        }
                    }
                }
                mc.gameSettings.heldItemTooltips = false;
                if (player.getHeldItem() != null) {
                    if (player.getHeldItem().getItem() instanceof ALauncherBase) {
                        Entity entity = ((ALauncherBase) player.getHeldItem().getItem()).chooseTarget();
                        if (entity != null && player.getHeldItem().getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                            if (entity.ticksExisted % 7 == 0) {
                                SoundUtils.playMonoSound(Main.MODID + ":radar");
                            }
                        }
                    }
                }
                this.spawnParticles(mc);
            } else {
                if (!player.capabilities.isCreativeMode) {
                    if (mc.gameSettings.hideGUI) {
                        RenderManager.hideHud = !RenderManager.hideHud;
                        mc.gameSettings.hideGUI = false;
                    }
                } else {
                    if (mc.gameSettings.hideGUI) {
                        Minecraft.getMinecraft().entityRenderer.camRoll = 0;
                    }
                    RenderManager.hideHud = false;
                }
            }
            if (player.worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                if (ClientHandler.clientWorldTickTime++ >= 2) {
                    player.worldObj.setWorldTime(player.worldObj.getWorldTime() - 1);
                    ClientHandler.clientWorldTickTime = 0;
                }
            }
        } else {
            if (this.horror != null) {
                this.horror.setDead();
            }
            this.horror = null;
            this.scaryTimer = 0;
            this.horrorTimer = 0;
            this.seeTimer = 0;
            this.wasHorrorSeen = false;
        }
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClosestPlayerExcluding(World world, double x, double y, double z, double distance) {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer entityplayer1 = (EntityPlayer) world.playerEntities.get(i);
            if (entityplayer1 != Minecraft.getMinecraft().thePlayer) {
                double d5 = entityplayer1.getDistanceSq(x, y, z);
                if ((distance < 0.0D || d5 < distance * distance) && (d4 == -1.0D || d5 < d4)) {
                    d4 = d5;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }

    private int findY(World world, int x, int y, int z) {
        while (y > 0 && (!world.getBlock(x, y, z).isOpaqueCube() || world.getBlock(x, y, z).getMaterial().isLiquid())) {
            y -= 1;
        }
        return y;
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (this.horror != null) {
            double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.partialTicks;
            double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.partialTicks;
            double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.partialTicks;
            Frustrum frustrum = new Frustrum();
            frustrum.setPosition(d0, d1, d2);
            if (frustrum.isBoundingBoxInFrustum(this.horror.boundingBox)) {
                this.wasHorrorSeen = true;
            }
        }
    }

    public boolean canSpawnTarget(int x, int y, int z) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        double d0 = player.posX;
        double d1 = player.posY;
        double d2 = player.posZ;
        Frustrum frustrum = new Frustrum();
        frustrum.setPosition(d0, d1, d2);
        if (!frustrum.isBoundingBoxInFrustum(AxisAlignedBB.getBoundingBox(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2))) {
            return true;
        }
        Vec3 vecTarget = Vec3.createVectorHelper(d0, d1 + player.getEyeHeight(), d2);
        Vec3 vecBlock = Vec3.createVectorHelper(x, y, z);
        return TraceUtils.rayTraceBlocks(player.worldObj, vecTarget, vecBlock, false, true, false) != null;
    }

    private void spawnParticles(Minecraft mc) {
        byte b0 = 16;
        World world = mc.theWorld;
        for (int l = 0; l < 1000; ++l) {
            int i1 = (int) (mc.thePlayer.posX + Main.rand.nextInt(b0) - Main.rand.nextInt(b0));
            int j1 = (int) (mc.thePlayer.posY + Main.rand.nextInt(b0) - Main.rand.nextInt(b0));
            int k1 = (int) (mc.thePlayer.posZ + Main.rand.nextInt(b0) - Main.rand.nextInt(b0));
            Block block = world.getBlock(i1, j1, k1);

            if (block.getMaterial() == Material.air) {
                if (Main.rand.nextInt(16) + mc.thePlayer.posY - 8 > j1) {
                    if (world.getBiomeGenForCoords(i1, k1) == CommonProxy.biome_gas) {
                        if (Main.rand.nextFloat() <= (mc.gameSettings.fancyGraphics ? 0.01f : 0.005f)) {
                            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(world, (float) i1 + Main.rand.nextFloat(), (float) j1 + Main.rand.nextFloat(), (float) k1 + Main.rand.nextFloat(), 0, 0.1f, 0, new float[]{0.3f, 0.8f, 0.3f}, 1.2f - Main.rand.nextFloat() * 0.4f));
                        }
                    } else if (world.getBiomeGenForCoords(i1, k1) == CommonProxy.biome_acid) {
                        if (Main.rand.nextFloat() <= (mc.gameSettings.fancyGraphics ? 0.01f : 0.005f)) {
                            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(world, (float) i1 + Main.rand.nextFloat(), (float) j1 + Main.rand.nextFloat(), (float) k1 + Main.rand.nextFloat(), 0, 0, 0, new float[]{0.85f, 1.0f, 0.85f}, 1.2f - Main.rand.nextFloat() * 0.4f));
                        }
                        if (Main.rand.nextFloat() < 0.001f) {
                            SoundUtils.playClientSound(i1, j1, k1, "random.fizz", 1.0f, Main.rand.nextFloat() * 0.1F + 1.4F);
                        }
                    }
                }
            }
            if (block.getMaterial() == Material.water) {
                if (block == FluidsZp.acidblock) {
                    if (Main.rand.nextFloat() <= (mc.gameSettings.fancyGraphics ? 0.01f : 0.005f)) {
                        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(world, (float) i1 + Main.rand.nextFloat(), (float) j1 + Main.rand.nextFloat(), (float) k1 + Main.rand.nextFloat(), 0, 0.05f, 0, new float[]{0.85f, 1.0f, 0.85f}, 1.2f - Main.rand.nextFloat() * 0.4f));
                    }
                    if (Main.rand.nextFloat() < 0.0005f) {
                        SoundUtils.playClientSound(i1, j1, k1, "random.fizz", 1.0f, Main.rand.nextFloat() * 0.1F + 1.4F);
                    }
                } else if (block == FluidsZp.toxicwater_block) {
                    if (Main.rand.nextFloat() <= 0.005f) {
                        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(world, (float) i1 + Main.rand.nextFloat(), (float) j1 + Main.rand.nextFloat(), (float) k1 + Main.rand.nextFloat(), 0, 0.05f, 0, new float[]{0.8f, 0.7f, 0.5f}, 1.2f - Main.rand.nextFloat() * 0.4f));
                    }
                }
                if (EntityUtils.isBoilingWater(world, i1, j1, k1) || EntityUtils.isBoilingWater(world, i1, j1 - 1, k1)) {
                    if (Main.rand.nextFloat() <= 0.1f) {
                        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityExplodeFX(world, (float) i1 + Main.rand.nextFloat(), (float) j1 + Main.rand.nextFloat(), (float) k1 + Main.rand.nextFloat(), 0, Main.rand.nextFloat() * 0.25f, 0));
                    }
                    if (Main.rand.nextFloat() <= 0.05f) {
                        SoundUtils.playClientSound(i1, j1, k1, "random.fizz", 0.5f, Main.rand.nextFloat() * 0.1F + 0.65F);
                    }
                }
            }
            if (Main.settingsZp.fancyLeaf.isFlag()) {
                if (block instanceof BlockLeaves) {
                    if (world.getBlockMetadata(i1, j1, k1) != 1 && world.isAirBlock(i1, j1 - 1, k1)) {
                        if (Main.rand.nextFloat() <= 0.025f) {
                            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleLeaf(world, (float) i1 + Main.rand.nextFloat(), (float) j1 - 0.1f, (float) k1 + Main.rand.nextFloat(), 0, 0, 0));
                        }
                    }
                }
            }
        }
    }
}
