package ru.BouH_.init;

import com.google.common.collect.Iterators;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.EnumHelper;
import ru.BouH_.Main;
import ru.BouH_.entity.projectile.*;
import ru.BouH_.entity.projectile.dispencer.ProjectileCustomRocket;
import ru.BouH_.entity.projectile.dispencer.ProjectileThrowable;
import ru.BouH_.entity.zombie.*;
import ru.BouH_.entity.zombie.special.EntityZombieHalloween;
import ru.BouH_.entity.zombie.special.EntityZombieNewYear;
import ru.BouH_.fun.projectiles.*;
import ru.BouH_.fun.renders.*;
import ru.BouH_.fun.rockets.*;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.render.entity.*;
import ru.BouH_.render.mobs.*;
import ru.BouH_.render.player.RenderPlayerZp;
import ru.BouH_.render.tile.TileChestRenderer;

import java.util.*;

public class EntitiesZp {
    public static final EnumCreatureType waterZombie = EnumHelper.addCreatureType("waterZombie", IMob.class, 70, Material.water, false, false);

    public static final Map<Class<? extends AZombieBase>, Object> resourceMap = new HashMap<>();

    public static void registerEntitySpawn() {
        BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.getBiomeGenArray()), Objects::nonNull), BiomeGenBase.class);
        List<BiomeGenBase> biomesForZombies = new ArrayList<>(Arrays.asList(allBiomes));
        biomesForZombies.remove(BiomeGenBase.hell);
        biomesForZombies.remove(BiomeGenBase.sky);
        biomesForZombies.remove(CommonProxy.biome_antiZm);
        BiomeGenBase[] getAllBiomesForZombies = Iterators.toArray(biomesForZombies.iterator(), BiomeGenBase.class);

        EntityRegistry.removeSpawn(EntityEnderman.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntitySlime.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntityWitch.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster, allBiomes);
        EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, allBiomes);

        EntityRegistry.removeSpawn(EntityPig.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityCow.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityChicken.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntitySheep.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityMooshroom.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityHorse.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityWolf.class, EnumCreatureType.creature, allBiomes);
        EntityRegistry.removeSpawn(EntityBat.class, EnumCreatureType.ambient, allBiomes);

        EntityRegistry.addSpawn(EntityZombieCitizen.class, 100, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieMiner.class, 32, 1, 1, EnumCreatureType.monster, getAllBiomesForZombies);

        EntityRegistry.addSpawn(EntityZombieDrowned.class, 1, 1, 1, EnumCreatureType.waterCreature, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieStrong.class, 6, 1, 1, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieHunter.class, 6, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieTank.class, 6, 1, 1, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieSprinter.class, 10, 1, 3, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieMilitary.class, 6, 1, 1, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieSwamp.class, 6, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieBurned.class, 6, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieBurned.class, 30, 1, 2, EnumCreatureType.monster, BiomeGenBase.hell);
        EntityRegistry.addSpawn(EntityZombieWolf.class, 6, 2, 5, EnumCreatureType.monster, getAllBiomesForZombies);

        EntityRegistry.addSpawn(EntityWolf.class, 2, 1, 3, EnumCreatureType.creature, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.taigaHills, BiomeGenBase.taiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.coldTaiga, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills);
        EntityRegistry.addSpawn(EntitySheep.class, 6, 1, 2, EnumCreatureType.creature, BiomeGenBase.plains, BiomeGenBase.megaTaigaHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.iceMountains, BiomeGenBase.birchForestHills, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.taigaHills, BiomeGenBase.taiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.coldTaiga, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills);
        EntityRegistry.addSpawn(EntityPig.class, 6, 1, 2, EnumCreatureType.creature, BiomeGenBase.plains, BiomeGenBase.megaTaigaHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.iceMountains, BiomeGenBase.birchForestHills, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);
        EntityRegistry.addSpawn(EntityChicken.class, 8, 1, 2, EnumCreatureType.creature, BiomeGenBase.plains, BiomeGenBase.megaTaigaHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.iceMountains, BiomeGenBase.birchForestHills, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);
        EntityRegistry.addSpawn(EntityCow.class, 6, 1, 2, EnumCreatureType.creature, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.plains, BiomeGenBase.megaTaigaHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.iceMountains, BiomeGenBase.birchForestHills, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);

        EntityRegistry.addSpawn(EntityZombieHalloween.class, 8, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);
        EntityRegistry.addSpawn(EntityZombieNewYear.class, 8, 1, 2, EnumCreatureType.monster, getAllBiomesForZombies);

        EntityRegistry.addSpawn(EntityEnderman.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.sky);
        EntityRegistry.addSpawn(EntityMagmaCube.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.hell);
        EntityRegistry.addSpawn(EntityPigZombie.class, 12, 1, 3, EnumCreatureType.monster, BiomeGenBase.hell);
        EntityRegistry.addSpawn(EntitySlime.class, 2, 1, 2, EnumCreatureType.monster, BiomeGenBase.jungle);

        EntityRegistry.addSpawn(EntityMooshroom.class, 6, 1, 1, EnumCreatureType.creature, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore);
        EntityRegistry.addSpawn(EntityHorse.class, 1, 1, 1, EnumCreatureType.creature, BiomeGenBase.savannaPlateau);
    }

    public static void registerEntity() {
        EntitiesZp.addEntity(EntityAcid.class, "acid");
        EntitiesZp.addEntity(EntityHolywater.class, "holywater");
        EntitiesZp.addEntity(EntityRocket.class, "rocket");
        EntitiesZp.addEntity(EntityFlare.class, "flare");
        EntitiesZp.addEntity(EntityFlare2.class, "flare2");
        EntitiesZp.addEntity(EntityJavelinRocket.class, "javelin");
        EntitiesZp.addEntity(EntityIglaRocket.class, "igla");

        EntitiesZp.addEntity(EntityCustomRocket.class, "customrocket");
        EntitiesZp.addEntity(EntityGeran2.class, "geran2");
        EntitiesZp.addEntity(EntitySolncepekRocket.class, "solncepek_rocket");
        EntitiesZp.addEntity(EntityKatyushaRocket.class, "katyusha_rocket");
        EntitiesZp.addEntity(EntityGradRocket.class, "grad_rocket");
        EntitiesZp.addEntity(EntityKalibrRocket.class, "kalibr_rocket");
        EntitiesZp.addEntity(EntityKalibrGuidedRocket.class, "kalibr_guided_rocket");
        EntitiesZp.addEntity(EntityIskanderRocket.class, "iskander_rocket");
        EntitiesZp.addEntity(EntityC300Rocket.class, "c300_rocket");
        EntitiesZp.addEntity(EntityR27Rocket.class, "r27_rocket");
        EntitiesZp.addEntity(EntityOvodRocket.class, "ovod_rocket");
        EntitiesZp.addEntity(EntityX101Rocket.class, "x101_rocket");
        EntitiesZp.addEntity(EntityPancirRocket.class, "pancir_rocket");
        EntitiesZp.addEntity(EntityBastionRocket.class, "bastion_rocket");
        EntitiesZp.addEntity(EntityUraganRocket.class, "uragan_rocket");
        EntitiesZp.addEntity(EntityKinzhalRocket.class, "kinchal_rocket");

        EntitiesZp.addEntity(EntityGrenade40.class, "weaponGrenade");
        EntitiesZp.addEntity(EntityTnt.class, "tnt");
        EntitiesZp.addEntity(EntityPlate.class, "plate");
        EntitiesZp.addEntity(EntityPlateBait.class, "plate_meat");
        EntitiesZp.addEntity(EntityTrapGrenade.class, "trap_grenade");
        EntitiesZp.addEntity(EntityRock.class, "rock");
        EntitiesZp.addEntity(EntityGrenadeSmoke.class, "grenade_smoke");
        EntitiesZp.addEntity(EntityGrenadeGas.class, "grenade_gas");
        EntitiesZp.addEntity(EntityGlassBottle.class, "glass_bottle");
        EntitiesZp.addEntity(EntityTrashBrick.class, "trash1");
        EntitiesZp.addEntity(EntityTrashRottenFlesh.class, "trash2");
        EntitiesZp.addEntity(EntityTrashBone.class, "trash3");
        EntitiesZp.addEntity(EntityGrenade.class, "grenade");
        EntitiesZp.addEntity(EntityWog25.class, "wog25");

        EntitiesZp.addEntity(EntityRot.class, "rot");

        if (EntitiesZp.class.getResourceAsStream("/assets/" + Main.MODID + "/structures/misc/rbush1.struct") != null) {
            EntitiesZp.addEntity(EntityModZombie.class, "particle_1");
        }
        if (EntitiesZp.class.getResourceAsStream("/assets/" + Main.MODID + "/structures/misc/rbush2.struct") != null) {
            EntitiesZp.addEntity(EntityModZombie2.class, "particle_2");
        }

        EntitiesZp.addEntityZombieMob(EntityZombieCitizen.class, "citizen", 354, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieMiner.class, "miner", 6, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieDrowned.class, "drowned", 5,0x6B8E23, 0x8FBC8F);

        EntitiesZp.addEntityZombieMob(EntityZombieStrong.class, "strong", 5, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieHunter.class, "hunter", 4, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieTank.class, "tank", 4, 0x6B8E23, 0x8FBC8F);

        EntitiesZp.addEntityZombieMob(EntityZombieMilitary.class, "military", 3, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieSprinter.class, "sprinter", 4, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieSwamp.class, "swamp", 11, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieBurned.class, "burned", 2, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieWolf.class, "wolf", 4, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieHalloween.class, "halloween", 1, 0x6B8E23, 0x8FBC8F);
        EntitiesZp.addEntityZombieMob(EntityZombieNewYear.class, "new_year", 1,  0x6B8E23, 0x8FBC8F);
    }

    @SuppressWarnings("unchecked")
    public static void addEntityZombieMob(Class<? extends AZombieBase> enClass, String skinName, int skinCount, int color1, int color2) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        String s1 = skinName + "Zp";
        EntityRegistry.registerGlobalEntityID(enClass, s1, entityId);
        EntityRegistry.registerModEntity(enClass, s1, entityId, Main.instance, Integer.MAX_VALUE, 1, true);
        if (FMLLaunchHandler.side().isClient()) {
            ArrayList<ResourceLocation> resourceLocations = new ArrayList<>();
            String path = "textures/entity/" + skinName + "/";
            for (int i = 0; i < skinCount; i++) {
                resourceLocations.add(new ResourceLocation(Main.MODID + ":" + path + skinName + i + ".png"));
            }
            EntitiesZp.resourceMap.put(enClass, resourceLocations);
        } else {
            EntitiesZp.resourceMap.put(enClass, skinCount);
        }
        EntityList.entityEggs.put(entityId, new EntityList.EntityEggInfo(entityId, color1, color2));
    }

    @SuppressWarnings("unchecked")
    public static void addEntityMob(Class<? extends Entity> enClass, String id, int color1, int color2) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(enClass, id, entityId);
        EntityRegistry.registerModEntity(enClass, id, entityId, Main.instance, Integer.MAX_VALUE, 1, true);
        EntityList.entityEggs.put(entityId, new EntityList.EntityEggInfo(entityId, color1, color2));
    }

    public static void addEntity(Class<? extends Entity> enClass, String id) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(enClass, id, entityId);
        EntityRegistry.registerModEntity(enClass, id, entityId, Main.instance, Integer.MAX_VALUE, 32, true);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public static void addEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityItem.class, new RenderItemZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderProjectile("rocket"));
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomRocket.class, new RenderCustomRProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, new RenderFlareProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlare2.class, new RenderFlare2Projectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityJavelinRocket.class, new RenderJavelinProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityIglaRocket.class, new RenderIglaProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade40.class, new RenderProjectile("grenade"));
        RenderingRegistry.registerEntityRenderingHandler(EntityWog25.class, new RenderProjectile("wog25"));

        RenderingRegistry.registerEntityRenderingHandler(EntityGeran2.class, new RenderGeran2Projectile());
        RenderingRegistry.registerEntityRenderingHandler(EntitySolncepekRocket.class, new RenderSolncepekProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityKatyushaRocket.class, new RenderKatyshaProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityGradRocket.class, new RenderGradProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityKalibrRocket.class, new RenderKalibrProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityKalibrGuidedRocket.class, new RenderKalibrProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityIskanderRocket.class, new RenderIskanderProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityC300Rocket.class, new RenderC300Projectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityR27Rocket.class, new RenderR27Projectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityOvodRocket.class, new RenderOvodProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityX101Rocket.class, new RenderX101Projectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityPancirRocket.class, new RenderPancirProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityBastionRocket.class, new RenderBastionProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityUraganRocket.class, new RenderUraganProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityKinzhalRocket.class, new RenderKinzhalProjectile());

        TileEntityRendererDispatcher.instance.mapSpecialRenderers.replace(TileEntityChest.class, new TileChestRenderer());

        for (Object o : TileEntityRendererDispatcher.instance.mapSpecialRenderers.values()) {
            TileEntitySpecialRenderer tileentityspecialrenderer = (TileEntitySpecialRenderer) o;
            tileentityspecialrenderer.func_147497_a(TileEntityRendererDispatcher.instance);
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityClientPlayerMP.class, new RenderPlayerZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityOtherPlayerMP.class, new RenderPlayerZp());

        if (EntitiesZp.class.getResourceAsStream("/assets/" + Main.MODID + "/structures/misc/rbush1.struct") != null) {
            RenderingRegistry.registerEntityRenderingHandler(EntityModZombie.class, new RenderZombieZ());
        }
        if (EntitiesZp.class.getResourceAsStream("/assets/" + Main.MODID + "/structures/misc/rbush2.struct") != null) {
            RenderingRegistry.registerEntityRenderingHandler(EntityModZombie2.class, new RenderZombieZ2());
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityZombieCitizen.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieMiner.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieDrowned.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieBurned.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieMilitary.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieHalloween.class, new RenderZombieZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieNewYear.class, new RenderZombieZp());

        RenderingRegistry.registerEntityRenderingHandler(EntityZombieStrong.class, new RenderZombieStrongZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieHunter.class, new RenderZombieHunterZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieTank.class, new RenderZombieHunterZp());

        RenderingRegistry.registerEntityRenderingHandler(EntityZombieSwamp.class, new RenderZombieCrouchedZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieSprinter.class, new RenderZombieCrouchedZp());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieWolf.class, new RenderZombieWolfZp(new ModelWolfZombie(0.5f)));

        RenderingRegistry.registerEntityRenderingHandler(EntityRot.class, new RenderThrowable(ItemsZp.rot_mass));
        RenderingRegistry.registerEntityRenderingHandler(EntityTrapGrenade.class, new RenderThrowable(ItemsZp.trap_grenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityAcid.class, new RenderThrowable(ItemsZp.acid));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlateBait.class, new RenderThrowable(ItemsZp.plate_meat));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlate.class, new RenderThrowable(ItemsZp.plate));
        RenderingRegistry.registerEntityRenderingHandler(EntityRock.class, new RenderThrowable(ItemsZp.rock));
        RenderingRegistry.registerEntityRenderingHandler(EntityHolywater.class, new RenderThrowable(ItemsZp.holywater));

        RenderingRegistry.registerEntityRenderingHandler(EntityTnt.class, new RenderThrowable(ItemsZp.tnt));
        RenderingRegistry.registerEntityRenderingHandler(EntityGlassBottle.class, new RenderThrowable(Items.glass_bottle));
        RenderingRegistry.registerEntityRenderingHandler(EntityTrashBrick.class, new RenderThrowable(Items.brick));
        RenderingRegistry.registerEntityRenderingHandler(EntityTrashRottenFlesh.class, new RenderThrowable(Items.rotten_flesh));
        RenderingRegistry.registerEntityRenderingHandler(EntityTrashBone.class, new RenderThrowable(Items.bone));

        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderThrowable(ItemsZp.frag_grenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSmoke.class, new RenderThrowable(ItemsZp.smoke_grenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGas.class, new RenderThrowable(ItemsZp.gas_grenade));
    }

    public static void registerEntityDisplacer() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.acid, new ProjectileThrowable(EntityAcid.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.holywater, new ProjectileThrowable(EntityHolywater.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.plate_meat, new ProjectileThrowable(EntityPlate.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.trap_grenade, new ProjectileThrowable(EntityTrapGrenade.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.plate, new ProjectileThrowable(EntityPlate.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.rot_mass, new ProjectileThrowable(EntityRot.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.rock, new ProjectileThrowable(EntityRock.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.glass_bottle, new ProjectileThrowable(EntityGlassBottle.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.brick, new ProjectileThrowable(EntityTrashBrick.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bone, new ProjectileThrowable(EntityTrashBone.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.rotten_flesh, new ProjectileThrowable(EntityTrashRottenFlesh.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.frag_grenade, new ProjectileThrowable(EntityGrenade.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.gas_grenade, new ProjectileThrowable(EntityGrenadeGas.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.smoke_grenade, new ProjectileThrowable(EntityGrenadeSmoke.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.tnt, new ProjectileThrowable(EntityTnt.class));
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._customRocket, new ProjectileCustomRocket());

        //FUN
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._geran2, new ProjectileGeran2());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._solncepekRocket, new ProjectileSolncepekRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._gradRocket, new ProjectileGradRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._katyushaRocket, new ProjectileKatyushaRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._kalibrRocket, new ProjectileKalibrRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._iskanderRocket, new ProjectileIskanderRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._c300Rocket, new ProjectileC300Rocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._r27Rocket, new ProjectileR27Rocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._ovodRocket, new ProjectileOvodRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._x101Rocket, new ProjectileX101Rocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._pancirRocket, new ProjectilePancirRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._uraganRocket, new ProjectileUraganRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._bastionRocket, new ProjectileBastionRocket());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp._kinzhalRocket, new ProjectileKinzhalRocket());
    }
}
