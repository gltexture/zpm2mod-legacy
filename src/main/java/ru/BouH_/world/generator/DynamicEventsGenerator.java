package ru.BouH_.world.generator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.entity.projectile.EntityFlare2;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.sound.PacketSound;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.EventStructure;
import ru.BouH_.world.type.WorldTypeZp;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class DynamicEventsGenerator extends AGenerator {
    public static DynamicEventsGenerator instance = new DynamicEventsGenerator();
    public Set<Target> targets;
    private AStructure heliCrash;

    protected DynamicEventsGenerator() {
        super(3);
        this.targets = new HashSet<>();
    }

    @Override
    public void loadStructures() {
        StructureHolder dynamic_helicrash1 = StructureHolder.create("dynamic/dynamic_helicrash1");
        this.heliCrash = new EventStructure(dynamic_helicrash1, 1);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent ev) {
        if (ConfigZp.dynamicEvents && SpecialGenerator.getTerType(ev.world) instanceof WorldTypeZp) {
            if (ev.phase == TickEvent.Phase.END) {
                if (WorldManager.instance.serverWorldTickTime >= 2) {
                    int day = (int) (ev.world.getTotalWorldTime() / 24000);
                    if (day > 0) {
                        if (day % 3 == 0) {
                            if (ev.world.getWorldTime() % 24000 == 6800) {
                                for (Object o : ev.world.playerEntities) {
                                    EntityPlayer entityPlayer = (EntityPlayer) o;
                                    if (Main.rand.nextFloat() <= 0.15f) {
                                        this.tryGenHeliCrash(entityPlayer, ev.world);
                                    }
                                }
                                this.targets.removeIf(Target::isGenerated);
                            }
                        }
                        if (day % 5 == 0) {
                            if (ev.world.getWorldTime() % 24000 == 3200) {
                                for (Object o : ev.world.playerEntities) {
                                    EntityPlayer entityPlayer = (EntityPlayer) o;
                                    this.tryGenAirDrop(entityPlayer, ev.world);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void tryGenAirDrop(EntityPlayer entityPlayer, World world) {
        int x = MathHelper.floor_double(entityPlayer.posX) + 64 - Main.rand.nextInt(33);
        int z = MathHelper.floor_double(entityPlayer.posZ) + 64 - Main.rand.nextInt(33);
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
        NetworkHandler.NETWORK.sendTo(new PacketSound(3), entityPlayerMP);
        EntityFlare2 flare2 = new EntityFlare2(world, x + 0.5f, 256, z + 0.5f);
        world.spawnEntityInWorld(flare2);
    }

    private void tryGenHeliCrash(EntityPlayer entityPlayer, World world) {
        Iterator<Target> iterator = this.targets.iterator();
        for (Target target : this.targets) {
            if (target.getStructure() == this.heliCrash && target.isGenerated() && entityPlayer.getDistance(target.x, entityPlayer.posY, target.z) <= 400) {
                return;
            }
        }
        while (iterator.hasNext()) {
            Target target = iterator.next();
            if (!target.isGenerated()) {
                if (entityPlayer.getDistance(target.x, entityPlayer.posY, target.z) > 86 && entityPlayer.getDistance(target.x, entityPlayer.posY, target.z) <= 318) {
                    if (this.checkStructureRegion(world, target.getX(), target.getY(), target.getZ(), target.getX() + target.getStructure().getStructureHolder().getMaxX(), target.getY() + target.getStructure().getStructureHolder().getMaxY(), target.getZ() + target.getStructure().getStructureHolder().getMaxZ())) {
                        System.out.println(target.getX() + " " + target.getZ());
                        NetworkHandler.NETWORK.sendToAllAround(new PacketSound(0), new NetworkRegistry.TargetPoint(0, target.x, entityPlayer.posY, target.z, 324));
                        target.getStructure().runGenerator(world, target.getX(), target.getY(), target.getZ(), 0);
                        target.setGenerated(true);
                        break;
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int y = this.findY(world, x, z);
        AStructure aStructure = this.heliCrash;
        if (SpecialGenerator.getTerType(world) instanceof WorldTypeZp) {
            if (this.checkRegion(world, aStructure, x, y, z)) {
                this.targets.add(new Target(aStructure, x, y, z));
            }
        }
    }

    private boolean checkRegion(World world, AStructure aStructure, int x, int y, int z) {
        for (DynamicEventsGenerator.Target target : this.targets) {
            int deltaX = target.getX() - x;
            int deltaZ = target.getZ() - z;
            if (Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) < 86) {
                return false;
            }
        }
        return this.checkStructureRegion(world, x, y, z, x + aStructure.getStructureHolder().getMaxX(), y + aStructure.getStructureHolder().getMaxY(), z + aStructure.getStructureHolder().getMaxZ());
    }

    private boolean checkStructureRegion(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (world.getBlock(minX, minY + 1, minZ).getMaterial().isLiquid()) {
            return false;
        }
        for (int i = minX; i <= maxX; i++) {
            for (int k = minY; k <= maxY; k++) {
                for (int j = minZ; j <= maxZ; j++) {
                    Block block = world.getBlock(i, k, j);
                    if (k == minY) {
                        if (!block.isOpaqueCube()) {
                            return false;
                        }
                    } else {
                        if (!block.getMaterial().isReplaceable() && !(block instanceof BlockBush)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //-6295887901091295359
    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    public static class Target implements Serializable {
        private final int x;
        private final int y;
        private final int z;
        private final AStructure aStructure;
        private boolean isGenerated;

        public Target(AStructure aStructure, int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.isGenerated = false;
            this.aStructure = aStructure;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public boolean isGenerated() {
            return this.isGenerated;
        }

        public void setGenerated(boolean generated) {
            this.isGenerated = generated;
        }

        public AStructure getStructure() {
            return this.aStructure;
        }
    }
}
