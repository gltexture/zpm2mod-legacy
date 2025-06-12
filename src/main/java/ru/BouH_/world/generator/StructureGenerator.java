package ru.BouH_.world.generator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.GoodPeople;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.world.biome.ICityBiome;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.CommonStructure;
import ru.BouH_.world.structures.building.NatureStructure;
import ru.BouH_.world.structures.building.SubmarineStructure;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureGenerator extends AGenerator { //TODO: Make new structure format
    public static StructureGenerator instance = new StructureGenerator();
    public final List<AStructure> natureStructureList = new ArrayList<>();
    public final List<AStructure> tentsStructureList = new ArrayList<>();
    public final List<AStructure> ruinsStructureList = new ArrayList<>();
    public final List<AStructure> commonStructureList = new ArrayList<>();
    public final List<AStructure> bigBuildingsStructureList = new ArrayList<>();
    public final List<AStructure> specialStructureList = new ArrayList<>();
    public final List<AStructure> militaryStructuresList = new ArrayList<>();
    public final List<AStructure> decorationStructuresList = new ArrayList<>();
    public final List<AStructure> portalsStructuresList = new ArrayList<>();
    public final List<AStructure> waterStructuresList = new ArrayList<>();

    protected StructureGenerator() {
        super(4);
    }

    public void loadStructures() {
        StructureHolder nature1 = StructureHolder.create("misc/nature1");
        StructureHolder nature2 = StructureHolder.create("misc/nature2");
        StructureHolder nature3 = StructureHolder.create("misc/nature3");
        StructureHolder nature4 = StructureHolder.create("misc/nature4");
        StructureHolder nature5 = StructureHolder.create("misc/nature5");
        StructureHolder nature6 = StructureHolder.create("misc/nature6");
        StructureHolder nature7 = StructureHolder.create("misc/nature7");
        StructureHolder nature8 = StructureHolder.create("misc/nature8");
        StructureHolder nature9 = StructureHolder.create("misc/nature9");
        StructureHolder nature10 = StructureHolder.create("misc/nature10");
        StructureHolder nature11 = StructureHolder.create("misc/nature11");
        StructureHolder nature12 = StructureHolder.create("misc/nature12");
        StructureHolder nature13 = StructureHolder.create("misc/nature13");

        StructureHolder tele1 = StructureHolder.create("misc/tele1");
        StructureHolder plane1 = StructureHolder.create("misc/plane1");

        StructureHolder tent1 = StructureHolder.create("misc/tent1");
        StructureHolder tent2 = StructureHolder.create("misc/tent2");
        StructureHolder tents1 = StructureHolder.create("misc/tents1");
        StructureHolder tents2 = StructureHolder.create("misc/tents2");

        StructureHolder airdrop1 = StructureHolder.create("misc/airdrop1");
        StructureHolder airdrop2 = StructureHolder.create("misc/airdrop2");
        StructureHolder airdrop3 = StructureHolder.create("misc/airdrop3");
        StructureHolder airdrop4 = StructureHolder.create("misc/airdrop4");

        StructureHolder desert_ruin1 = StructureHolder.create("misc/desert_ruin1");
        StructureHolder desert_ruin2 = StructureHolder.create("misc/desert_ruin2");
        StructureHolder desert_ruins1 = StructureHolder.create("misc/desert_ruins1");
        StructureHolder ruin1 = StructureHolder.create("misc/ruin1");
        StructureHolder ruin2 = StructureHolder.create("misc/ruin2");
        StructureHolder ruin3 = StructureHolder.create("misc/ruin3");
        StructureHolder ruin4 = StructureHolder.create("misc/ruin4");
        StructureHolder ruin5 = StructureHolder.create("misc/ruin5");

        StructureHolder desert_hunting1 = StructureHolder.create("misc/desert_hunting1");
        StructureHolder desert_hunting2 = StructureHolder.create("misc/desert_hunting2");
        StructureHolder hunting_1 = StructureHolder.create("misc/hunting1");
        StructureHolder hunting_2 = StructureHolder.create("misc/hunting2");
        StructureHolder mega_ruin1 = StructureHolder.create("misc/mega_ruin1");

        StructureHolder military_tent1 = StructureHolder.create("misc/military_tent1");
        StructureHolder military_tent2 = StructureHolder.create("misc/military_tent2");
        StructureHolder tank1 = StructureHolder.create("misc/tank1");
        StructureHolder heli1 = StructureHolder.create("misc/heli1");
        StructureHolder heli2 = StructureHolder.create("misc/heli2");
        StructureHolder heli3 = StructureHolder.create("misc/heli3");

        StructureHolder under_ground1 = StructureHolder.create("misc/under_ground1");
        StructureHolder farm1 = StructureHolder.create("misc/farm1");
        StructureHolder farm2 = StructureHolder.create("misc/farm2");
        StructureHolder house1 = StructureHolder.create("misc/house1");
        StructureHolder house2 = StructureHolder.create("misc/house2");
        StructureHolder house3 = StructureHolder.create("misc/house3");

        StructureHolder monument1 = StructureHolder.create("misc/monument1");
        StructureHolder monument2 = StructureHolder.create("misc/monument2");
        StructureHolder portal1 = StructureHolder.create("misc/portal1");
        StructureHolder portal2 = StructureHolder.create("misc/portal2");

        StructureHolder misc_tower1 = StructureHolder.create("misc/misc_tower1");
        StructureHolder misc_tower2 = StructureHolder.create("misc/misc_tower2");

        StructureHolder submarine1 = StructureHolder.create("misc/submarine1");
        StructureHolder submarine2 = StructureHolder.create("misc/submarine2");
        StructureHolder submarine3 = StructureHolder.create("misc/submarine3");

        BiomeGenBase[] allGenBiomes = new BiomeGenBase[]{BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.roofedForest, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.taigaHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHills, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.taiga, BiomeGenBase.taigaHills, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.icePlains};
        BiomeGenBase[] allGenBiomesAndRadiation = new BiomeGenBase[]{CommonProxy.biome_rad1, CommonProxy.biome_rad2, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.roofedForest, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.taigaHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHills, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.taiga, BiomeGenBase.taigaHills, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.icePlains};

        this.waterStructuresList.add(new SubmarineStructure(submarine1, 1, BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.frozenOcean));
        this.waterStructuresList.add(new SubmarineStructure(submarine2, 1, BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.frozenOcean));
        this.waterStructuresList.add(new SubmarineStructure(submarine3, 1, BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.frozenOcean));

        this.specialStructureList.add(new CommonStructure(airdrop1, 1, allGenBiomes));
        this.specialStructureList.add(new CommonStructure(airdrop2, 1, allGenBiomes));
        this.specialStructureList.add(new CommonStructure(airdrop3, 1, allGenBiomes));
        this.specialStructureList.add(new CommonStructure(airdrop4, 1, allGenBiomes));
        this.specialStructureList.add(new CommonStructure(tank1, 3, CommonProxy.biome_rad1, CommonProxy.biome_rad2));
        this.specialStructureList.add(new CommonStructure(heli1, 1, CommonProxy.biome_rad1, CommonProxy.biome_rad2));
        this.specialStructureList.add(new CommonStructure(heli2, 1, CommonProxy.biome_rad1, CommonProxy.biome_rad2));
        this.specialStructureList.add(new CommonStructure(heli3, 1, CommonProxy.biome_rad1, CommonProxy.biome_rad2));

        this.natureStructureList.add(new NatureStructure(nature1, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature2, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature3, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature4, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature5, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature6, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature7, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature8, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature9, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature10, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature11, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature12, 1, allGenBiomesAndRadiation));
        this.natureStructureList.add(new NatureStructure(nature13, 1, allGenBiomesAndRadiation));

        this.commonStructureList.add(new CommonStructure(farm1, 1, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.roofedForest, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forestHills, BiomeGenBase.extremeHills));
        this.commonStructureList.add(new CommonStructure(farm2, 1, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.roofedForest, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forestHills, BiomeGenBase.extremeHills));
        this.commonStructureList.add(new CommonStructure(under_ground1, 8, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(house1, 4, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(house2, 4, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(house3, 4, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(hunting_1, 1, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(hunting_2, 1, allGenBiomes));
        this.commonStructureList.add(new CommonStructure(military_tent1, 1, CommonProxy.biome_rad1, CommonProxy.biome_rad2));
        this.commonStructureList.add(new CommonStructure(military_tent2, 1, CommonProxy.biome_rad1, CommonProxy.biome_rad2));

        this.decorationStructuresList.add(new CommonStructure(monument1, 1, BiomeGenBase.jungle, BiomeGenBase.jungleEdge, BiomeGenBase.jungleHills));
        this.decorationStructuresList.add(new CommonStructure(monument2, 1, BiomeGenBase.jungle, BiomeGenBase.jungleEdge, BiomeGenBase.jungleHills));

        this.portalsStructuresList.add(new CommonStructure(portal1, 1, allGenBiomes));
        this.portalsStructuresList.add(new CommonStructure(portal2, 1, allGenBiomes));

        this.bigBuildingsStructureList.add(new CommonStructure(desert_hunting1, Blocks.sand, Blocks.sandstone, 1, BiomeGenBase.desert, BiomeGenBase.desertHills));
        this.bigBuildingsStructureList.add(new CommonStructure(desert_hunting2, Blocks.sand, Blocks.sandstone, 1, BiomeGenBase.desert, BiomeGenBase.desertHills));
        this.bigBuildingsStructureList.add(new CommonStructure(tele1, 1, allGenBiomesAndRadiation));
        this.bigBuildingsStructureList.add(new CommonStructure(plane1, 1, allGenBiomesAndRadiation));
        this.bigBuildingsStructureList.add(new CommonStructure(mega_ruin1, 1, allGenBiomesAndRadiation));

        this.militaryStructuresList.add(new CommonStructure(military_tent1, 2, allGenBiomes));
        this.militaryStructuresList.add(new CommonStructure(military_tent2, 2, allGenBiomes));
        this.militaryStructuresList.add(new CommonStructure(tank1, 1, allGenBiomesAndRadiation));
        this.militaryStructuresList.add(new CommonStructure(heli1, 1, allGenBiomesAndRadiation));
        this.militaryStructuresList.add(new CommonStructure(heli2, 1, allGenBiomesAndRadiation));
        this.militaryStructuresList.add(new CommonStructure(heli3, 1, allGenBiomesAndRadiation));

        this.tentsStructureList.add(new CommonStructure(tent1, 2, allGenBiomes));
        this.tentsStructureList.add(new CommonStructure(tent2, 2, allGenBiomes));
        this.tentsStructureList.add(new CommonStructure(tents1, 1, allGenBiomes));
        this.tentsStructureList.add(new CommonStructure(tents2, 1, allGenBiomes));

        this.ruinsStructureList.add(new CommonStructure(misc_tower1, 1, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(misc_tower2, 1, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(ruin1, 2, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(ruin2, 2, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(ruin3, 2, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(ruin4, 2, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(ruin5, 2, allGenBiomes));
        this.ruinsStructureList.add(new CommonStructure(desert_ruin1, Blocks.sand, Blocks.sandstone, 2, BiomeGenBase.desert, BiomeGenBase.desertHills));
        this.ruinsStructureList.add(new CommonStructure(desert_ruin2, Blocks.sand, Blocks.sandstone, 2, BiomeGenBase.desert, BiomeGenBase.desertHills));
        this.ruinsStructureList.add(new CommonStructure(desert_ruins1, Blocks.sand, Blocks.sandstone, 1, BiomeGenBase.desert, BiomeGenBase.desertHills));
    }

    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    private int findYUnderWater(World world, int x, int z) {
        int y = world.getPrecipitationHeight(x, z);
        while (y > 0 && world.getBlock(x, y, z).getMaterial().isReplaceable()) {
            y -= 1;
        }
        return y;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (SpecialGenerator.getTerType(world) instanceof WorldTypeZp) {
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;
            int y = this.findY(world, x, z);
            BiomeGenBase base = world.getBiomeGenForCoords(x, z);
            if (chunkX == 0 && chunkZ == 0) {
                world.setBlock(0, 5, 0, Blocks.chest);
                TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(0, 5, 0);
                ItemStack book = new ItemStack(Items.writable_book);
                book.setTagInfo(Main.MODID, new NBTTagCompound());
                NBTTagList bookPages = new NBTTagList();
                bookPages.appendTag(new NBTTagString("Thanks for playing ZPM 2. Good luck."));
                book.setTagInfo("author", new NBTTagString(GoodPeople.dungeonMaster));
                book.setTagInfo("title", new NBTTagString("sv_cheats 1"));
                book.setTagInfo("pages", bookPages);
                book.setItem(Items.written_book);
                tileEntityChest.setInventorySlotContents(0, new ItemStack(ItemsZp.sodagun));
                tileEntityChest.setInventorySlotContents(1, new ItemStack(ItemsZp.burn, 2));
                tileEntityChest.setInventorySlotContents(2, new ItemStack(ItemsZp.burn, 2));
                tileEntityChest.setInventorySlotContents(3, new ItemStack(ItemsZp.burn, 2));
                tileEntityChest.setInventorySlotContents(4, new ItemStack(ItemsZp.burn, 2));
                tileEntityChest.setInventorySlotContents(5, book);
            }
            for (int i = -4; i < 4; i++) {
                for (int j = -4; j < 4; j++) {
                    int cx = (chunkX + i) * 16;
                    int cz = (chunkZ + j) * 16;
                    if (world.getBiomeGenForCoords(cx, cz) instanceof ICityBiome) {
                        return;
                    }
                }
            }
            if (random.nextFloat() <= 0.825f) {
                float f1 = random.nextFloat();
                boolean flag;
                if (f1 <= 0.4f) {
                    flag = this.buildStructure(random, this.ruinsStructureList, base, world, x, y, z, random.nextInt(4));
                } else if (f1 <= 0.6f) {
                    flag = this.buildStructure(random, this.tentsStructureList, base, world, x, y, z, random.nextInt(4));
                } else if (f1 <= 0.68f) {
                    flag = this.buildStructure(random, this.specialStructureList, base, world, x, y, z, random.nextInt(4));
                } else if (f1 <= 0.72f) {
                    flag = this.buildStructure(random, this.militaryStructuresList, base, world, x, y, z, random.nextInt(4));
                } else if (f1 <= 0.95f) {
                    flag = this.buildStructure(random, this.commonStructureList, base, world, x, y, z, random.nextInt(4));
                } else {
                    flag = this.buildStructure(random, this.bigBuildingsStructureList, base, world, x, y, z, random.nextInt(4));
                }
                if (flag) {
                    return;
                }
            }
            if (random.nextFloat() <= 0.05f) {
                if (this.buildStructure(random, this.decorationStructuresList, base, world, x, y, z, random.nextInt(4))) {
                    return;
                }
            }
            if (random.nextFloat() <= 1.0e-3f) {
                if (this.buildStructure(random, this.portalsStructuresList, base, world, x, y, z, random.nextInt(4))) {
                    return;
                }
            }
            if (random.nextFloat() <= 0.15f) {
                this.buildStructure(random, this.natureStructureList, base, world, x, y, z, random.nextInt(4));
                return;
            }
            if (random.nextFloat() <= 0.03f) {
                y = this.findYUnderWater(world, x, z);
                this.buildStructure(random, this.waterStructuresList, base, world, x, y, z, random.nextInt(4));
            }
        }
    }
}
