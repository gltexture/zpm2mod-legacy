package ru.BouH_.audio;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockArmorGlass;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.world.type.WorldTypeZp;

public final class AmbientSounds {
    public static AmbientSounds instance = new AmbientSounds();
    public boolean init;
    public ALSoundZp menuMusic;
    public ALSoundZp jungle;
    public ALSoundZp swamp;
    public ALSoundZp ocean;
    public ALSoundZp valley_day;
    public ALSoundZp valley_night;
    public ALSoundZp wind;
    public ALSoundZp desert_wind;
    public ALSoundZp desert_wind2;
    public ALSoundZp cave;
    public ALSoundZp misc_ambient;
    public ALSoundZp oppression;
    public ALSoundZp oppression7n;
    public ALSoundZp nether;
    public ALSoundZp curse;
    public ALSoundZp water;
    public ALSoundZp rain;
    public ALSoundZp rain_outside;
    public ALSoundZp the_horror1;
    public ALSoundZp the_horror2;
    public ALSoundZp the_horror3;
    public ALSoundZp the_horror4;
    public ALSoundZp suspence;
    public ALSoundZp[] wizz = new ALSoundZp[4];
    public ALSoundZp[] spirit = new ALSoundZp[4];
    public ALSoundZp helicrash;
    public ALSoundZp seren;
    public boolean canPlayerHearWater = true;
    public boolean canPlayerHearAmbient = true;
    public boolean canPlayerHearAmbientBypassGlass = true;
    public boolean isInCave = false;
    public boolean canHearRain = false;
    public boolean smoothPlay;
    private ALSoundZp currentAmbient = null;

    public void loadSounds() {
        this.seren = new ALSoundZp("seren", SoundType.BACKGROUND_EVENT);
        this.menuMusic = new ALSoundZp("dark_ambience", SoundType.BACKGROUND_MUSIC);
        this.jungle = new ALSoundZp("jungle", SoundType.AMBIENT);
        this.swamp = new ALSoundZp("swamp", SoundType.AMBIENT);
        this.ocean = new ALSoundZp("ocean", SoundType.AMBIENT);
        this.valley_day = new ALSoundZp("valley_day", SoundType.AMBIENT);
        this.valley_night = new ALSoundZp("valley_night", SoundType.AMBIENT);
        this.wind = new ALSoundZp("wind", SoundType.AMBIENT);
        this.desert_wind = new ALSoundZp("desert_wind", SoundType.AMBIENT);
        this.desert_wind2 = new ALSoundZp("desert_wind2", SoundType.AMBIENT);
        this.cave = new ALSoundZp("cave", SoundType.AMBIENT);
        this.misc_ambient = new ALSoundZp("misc_ambient", SoundType.AMBIENT);
        this.oppression = new ALSoundZp("oppression", SoundType.AMBIENT);
        this.oppression7n = new ALSoundZp("oppression7n", SoundType.AMBIENT);

        this.nether = new ALSoundZp("nether", SoundType.AMBIENT);
        this.curse = new ALSoundZp("curse", SoundType.RELATIVE_LOOP);
        this.the_horror1 = new ALSoundZp("the_horror1", SoundType.EVENT);
        this.the_horror2 = new ALSoundZp("the_horror2", SoundType.EVENT);
        this.the_horror3 = new ALSoundZp("the_horror3", SoundType.EVENT);
        this.the_horror4 = new ALSoundZp("the_horror4", SoundType.EVENT);
        this.suspence = new ALSoundZp("suspence", SoundType.EVENT);
        this.wizz[0] = new ALSoundZp("wizz0", SoundType.EVENT);
        this.wizz[1] = new ALSoundZp("wizz1", SoundType.EVENT);
        this.wizz[2] = new ALSoundZp("wizz2", SoundType.EVENT);
        this.wizz[3] = new ALSoundZp("wizz3", SoundType.EVENT);
        this.spirit[0] = new ALSoundZp("spirit0", SoundType.EVENT);
        this.spirit[1] = new ALSoundZp("spirit1", SoundType.EVENT);
        this.spirit[2] = new ALSoundZp("spirit2", SoundType.EVENT);
        this.spirit[3] = new ALSoundZp("spirit3", SoundType.EVENT);
        this.helicrash = new ALSoundZp("helicrash", SoundType.EVENT);
        this.water = new ALSoundZp("water", SoundType.AMBIENT);
        this.rain = new ALSoundZp("rain", SoundType.AMBIENT);
        this.rain_outside = new ALSoundZp("rain_outside", SoundType.AMBIENT);
        this.currentAmbient = null;
        this.smoothPlay = true;
        this.init = true;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        //TODO: Remake this shit
        if (this.init) {
            this.updateSounds();
        }
    }

    public static boolean isClientDayTime(World world) {
        return (Minecraft.getMinecraft().theWorld.provider.dimensionId == 2) != Minecraft.getMinecraft().theWorld.isDaytime();
    }

    public void updateSounds() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        ALSoundZp.soundSetZp.forEach(ALSoundZp::updateSource);
        if (player == null) {
            ALSoundZp.soundSetZp.forEach(e -> {
                if (e.getSoundType() != SoundType.BACKGROUND_MUSIC) {
                    e.smoothStopSound();
                }
            });
            if (Main.settingsZp.musicVolume.isFlag()) {
                if (!this.menuMusic.isPlaying()) {
                    this.menuMusic.smoothStartSound();
                }
            } else {
                this.menuMusic.smoothStopSound();
            }
            if (this.currentAmbient != null) {
                this.currentAmbient = null;
            }
        } else if (player.worldObj.getChunkFromBlockCoords((int) player.posX, (int) player.posZ).isChunkLoaded) {
            this.menuMusic.smoothStopSound();
            if (Minecraft.getMinecraft().isGamePaused()) {
                if (this.currentAmbient != null) {
                    this.smoothPlay = false;
                }
                for (ALSoundZp alSoundZp : ALSoundZp.soundSetZp) {
                    if (alSoundZp.getSoundType() != SoundType.BACKGROUND_MUSIC && alSoundZp.getSoundType() != SoundType.BACKGROUND_EVENT) {
                        alSoundZp.pauseSound();
                    }
                }
            } else {
                if (player.worldObj.getWorldInfo().getTerrainType() instanceof WorldTypeZp && !this.curse.isPlaying()) {
                    this.curse.getSoundSourceData().setPos(0, 5, 0);
                    this.curse.playSound();
                }
                if (player.ticksExisted % 20 == 0) {
                    this.isInCave = this.checkPlayerIsInCave(player);
                    if (!this.isInCave) {
                        this.canPlayerHearAmbient = this.checkPlayerPosition(player, false);
                        this.canPlayerHearAmbientBypassGlass = this.checkPlayerPosition(player, true);
                    } else {
                        this.canPlayerHearAmbient = false;
                        this.canPlayerHearAmbientBypassGlass = false;
                    }
                    this.canHearRain = this.checkCanHearRain(player);
                }
                BiomeGenBase biomeGenBase = player.getEntityWorld().getBiomeGenForCoords((int) player.posX, (int) player.posZ);
                if (player.dimension == 0 || player.dimension == 2) {
                    if (Main.settingsZp.ambientVolume.isFlag()) {
                        if (player.isInsideOfMaterial(Material.water)) {
                            this.smoothPlay = false;
                            this.setNewAmbient(this.water);
                        } else {
                            boolean isDay = isClientDayTime(player.worldObj);
                            boolean isOppression = !isDay;
                            if (this.canHearRain) {
                                float f1 = Math.max(WeatherHandler.instance.getWeatherRain().currentRainStrength + 0.3f, 0.75f);
                                this.rain.setPitch(f1);
                                this.rain_outside.setPitch(f1);
                                if (this.canPlayerHearAmbient) {
                                    this.rain.smoothStartSound();
                                    this.rain_outside.smoothStopSound();
                                } else {
                                    this.rain.smoothStopSound();
                                    this.rain_outside.smoothStartSound();
                                }
                            } else {
                                this.rain.smoothStopSound();
                                this.rain_outside.smoothStopSound();
                            }
                            ALSoundZp oppression = (WorldManager.is7Night(player.worldObj)) ? this.oppression7n : this.oppression;
                            if (isOppression && !this.isInCave) {
                                if (!oppression.isPlaying()) {
                                    if (this.smoothPlay) {
                                        oppression.smoothStartSound();
                                    } else {
                                        oppression.playSound();
                                    }
                                }
                            } else {
                                this.oppression7n.smoothStopSound();
                                this.oppression.smoothStopSound();
                            }
                            if (this.isInCave || biomeGenBase == CommonProxy.biome_industry) {
                                this.setNewAmbient(this.cave);
                            } else {
                                if (!this.canPlayerHearAmbient) {
                                    this.setNewAmbient(this.misc_ambient);
                                } else {
                                    if (biomeGenBase == CommonProxy.biome_military || player.posY >= 101) {
                                        this.setNewAmbient(this.wind);
                                    } else {
                                        switch (biomeGenBase.getTempCategory()) {
                                            case COLD: {
                                                this.setNewAmbient(this.wind);
                                                break;
                                            }
                                            case WARM: {
                                                if (biomeGenBase == BiomeGenBase.savanna || biomeGenBase == BiomeGenBase.savannaPlateau) {
                                                    this.setNewAmbient(isDay ? this.valley_day : this.valley_night);
                                                } else {
                                                    this.setNewAmbient(player.worldObj.getWorldInfo().isRaining() ? this.desert_wind2 : this.desert_wind);
                                                }
                                                break;
                                            }
                                            case MEDIUM: {
                                                if (biomeGenBase == BiomeGenBase.swampland) {
                                                    this.setNewAmbient(this.swamp);
                                                } else if (biomeGenBase == BiomeGenBase.river) {
                                                    this.setNewAmbient(isDay ? this.valley_day : this.valley_night);
                                                } else if (biomeGenBase == BiomeGenBase.deepOcean || biomeGenBase == BiomeGenBase.ocean || biomeGenBase == BiomeGenBase.beach) {
                                                    this.setNewAmbient(this.ocean);
                                                } else if (biomeGenBase == BiomeGenBase.jungle || biomeGenBase == BiomeGenBase.jungleEdge || biomeGenBase == BiomeGenBase.jungleHills) {
                                                    this.setNewAmbient(this.jungle);
                                                } else {
                                                    this.setNewAmbient(isDay ? this.valley_day : this.valley_night);
                                                }
                                                break;
                                            }
                                            case OCEAN: {
                                                this.setNewAmbient(this.ocean);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        this.oppression.smoothStopSound();
                        this.oppression7n.smoothStopSound();
                        if (this.currentAmbient != null) {
                            this.currentAmbient.smoothStopSound();
                        }
                    }
                } else {
                    this.oppression.smoothStopSound();
                    this.oppression7n.smoothStopSound();
                    this.curse.stopSound();
                    this.setNewAmbient((player.dimension == 1 || player.dimension == -1) ? this.nether : this.misc_ambient);
                }
            }
        } else {
            this.smoothPlay = true;
            if (this.currentAmbient != null) {
                this.currentAmbient.smoothStopSound();
                this.currentAmbient = null;
            }
            this.oppression.smoothStopSound();
            this.oppression7n.smoothStopSound();
            this.curse.stopSound();
        }
    }

    private boolean checkBlock(World world, int x, int y, int z, boolean bypassGlass) {
        Block block = world.getBlock(x, y, z);
        if (block instanceof BlockLeaves || block.getMaterial().isReplaceable()) {
            return false;
        }
        if (!bypassGlass && (block instanceof BlockPane || block instanceof BlockArmorGlass || block instanceof BlockStainedGlass || block instanceof BlockGlass)) {
            return true;
        }
        if (block instanceof BlockEnchantmentTable || block instanceof BlockChest || block instanceof BlockSlab || block instanceof BlockStairs) {
            return true;
        }
        return block.renderAsNormalBlock();
    }

    private boolean checkDoor(World world, int rotation, int blockX, int blockY, int blockZ) {
        int meta = world.getBlockMetadata(blockX, blockY, blockZ);
        if (meta == 8) {
            meta = world.getBlockMetadata(blockX, blockY - 1, blockZ);
        }
        switch (meta) {
            case 4:
            case 1:
            case 3:
            case 6: {
                return rotation != 0;
            }
            case 0:
            case 2:
            case 7:
            case 5: {
                return rotation != 1;
            }
        }
        return true;
    }

    private boolean checkY(World world, int x, int y, int z, boolean bypassGlass) {
        if (y + 16 < world.getPrecipitationHeight(x, z)) {
            return false;
        }
        for (int i = y; i <= world.getPrecipitationHeight(x, z); i++) {
            if (this.checkBlock(world, x, i, z, bypassGlass)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkYStone(World world, int x, int y, int z) {
        for (int j = y + 1; j < y + 64; j++) {
            Block block = world.getBlock(x, j, z);
            if (block.getMaterial() == Material.rock) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCanHearRain(EntityPlayerSP entityPlayerSP) {
        if (WeatherHandler.instance.getWeatherRain().currentRainStrength < 0.2f) {
            return false;
        }
        int x = MathHelper.floor_double(entityPlayerSP.posX);
        int z = MathHelper.floor_double(entityPlayerSP.posZ);
        int y = (int) (entityPlayerSP.posY + entityPlayerSP.getEyeHeight());
        for (int j = y - 12; j < y + 12; j++) {
            for (int k = x - 8; k < x + 8; k++) {
                for (int o = z - 8; o < z + 8; o++) {
                    BiomeGenBase biomegenbase = entityPlayerSP.worldObj.getBiomeGenForCoords(k, o);
                    float f = biomegenbase.getFloatTemperature(k, j + 1, o);
                    if (f <= 0.15F || biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills) {
                        continue;
                    }
                    Block block = entityPlayerSP.worldObj.getBlock(k, j, o);
                    if (!block.getMaterial().isReplaceable() && entityPlayerSP.worldObj.canBlockSeeTheSky(k, j + 1, o)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkPlayerIsInCave(EntityPlayerSP entityPlayerSP) {
        int x = MathHelper.floor_double(entityPlayerSP.posX);
        int z = MathHelper.floor_double(entityPlayerSP.posZ);
        int y = (int) (entityPlayerSP.posY + entityPlayerSP.getEyeHeight());
        if (y > entityPlayerSP.worldObj.getHeightValue(x, z) - 24) {
            return false;
        }
        int num = 0;
        for (int i = x - 6; i < x + 6; i++) {
            for (int j = z - 6; j < z + 6; j++) {
                if (this.checkYStone(entityPlayerSP.worldObj, i, y, j)) {
                    num += 1;
                }
            }
        }
        return num >= 86;
    }

    private boolean checkPlayerPosition(EntityPlayerSP entityPlayerSP, boolean bypassGlass) {
        int x = MathHelper.floor_double(entityPlayerSP.posX);
        int z = MathHelper.floor_double(entityPlayerSP.posZ);
        int y = (int) (entityPlayerSP.posY) + 1;
        return this.checkDistance(entityPlayerSP.worldObj, x, y, z, bypassGlass) || this.checkDistance(entityPlayerSP.worldObj, x - 1, y, z, bypassGlass) || this.checkDistance(entityPlayerSP.worldObj, x, y, z - 1, bypassGlass) || this.checkDistance(entityPlayerSP.worldObj, x + 1, y, z, bypassGlass) || this.checkDistance(entityPlayerSP.worldObj, x, y, z + 1, bypassGlass);
    }

    private boolean checkDistance(World world, int x, int y, int z, boolean bypassGlass) {
        for (int k = y - 1; k < y + 2; k++) {
            if (this.checkBlock(world, x, k, z, bypassGlass)) {
                break;
            }

            for (int i = x; i < x + 8; i++) {
                Block block = world.getBlock(i, k, z);
                if (block instanceof BlockDoor) {
                    if (this.checkDoor(world, 0, i, k, z)) {
                        break;
                    }
                    continue;
                }
                if (this.checkBlock(world, i, k, z, bypassGlass)) {
                    break;
                }
                if (this.checkY(world, i, k, z, bypassGlass)) {
                    return true;
                }
            }

            for (int i = x - 1; i > x - 8; i--) {
                Block block = world.getBlock(i, k, z);
                if (block instanceof BlockDoor) {
                    if (this.checkDoor(world, 0, i, k, z)) {
                        break;
                    }
                    continue;
                }
                if (this.checkBlock(world, i, k, z, bypassGlass)) {
                    break;
                }
                if (this.checkY(world, i, k, z, bypassGlass)) {
                    return true;
                }
            }

            for (int o = z + 1; o < z + 8; o++) {
                Block block = world.getBlock(x, k, o);
                if (block instanceof BlockDoor) {
                    if (this.checkDoor(world, 1, x, k, o)) {
                        break;
                    }
                    continue;
                }
                if (this.checkBlock(world, x, k, o, bypassGlass)) {
                    break;
                }
                if (this.checkY(world, x, k, o, bypassGlass)) {
                    return true;
                }
            }

            for (int o = z - 1; o > z - 8; o--) {
                Block block = world.getBlock(x, k, o);
                if (block instanceof BlockDoor) {
                    if (this.checkDoor(world, 1, x, k, o)) {
                        break;
                    }
                    continue;
                }
                if (this.checkBlock(world, x, k, o, bypassGlass)) {
                    break;
                }
                if (this.checkY(world, x, k, o, bypassGlass)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setNewAmbient(ALSoundZp alSoundZp) {
        if (!alSoundZp.isPlaying()) {
            if (this.currentAmbient != null) {
                if (!this.smoothPlay) {
                    this.currentAmbient.pauseSound();
                } else {
                    this.currentAmbient.smoothStopSound();
                }
            }
            this.currentAmbient = alSoundZp;
            if (!this.smoothPlay) {
                this.currentAmbient.playSound();
            } else {
                this.currentAmbient.smoothStartSound();
            }
            this.smoothPlay = true;
        }
    }
}