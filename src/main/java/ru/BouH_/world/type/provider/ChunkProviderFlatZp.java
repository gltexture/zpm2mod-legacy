package ru.BouH_.world.type.provider;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;

import java.util.List;
import java.util.Random;

public class ChunkProviderFlatZp implements IChunkProvider {
    private final World worldObj;
    private final Random random;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];
    private final boolean hasDecoration;
    private final WorldGenLakes waterLakeGenerator;
    private final WorldGenTallGrass worldGenTallGrass;
    private final WorldGenTrees worldGenTrees;

    @SuppressWarnings("all")
    public ChunkProviderFlatZp(World p_i2004_1_, long p_i2004_2_, boolean p_i2004_4_, String p_i2004_5_) {
        this.worldObj = p_i2004_1_;
        this.random = new Random(p_i2004_2_);
        FlatGeneratorInfo flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_i2004_5_);
        this.hasDecoration = flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
        this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        this.worldGenTallGrass = new WorldGenTallGrass(Blocks.tallgrass, 1);
        this.worldGenTrees = new WorldGenTrees(false);
        for (Object o : flatWorldGenInfo.getFlatLayers()) {
            FlatLayerInfo flatlayerinfo = (FlatLayerInfo) o;
            for (int j = flatlayerinfo.getMinY(); j < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); ++j) {
                this.cachedBlockIDs[j] = flatlayerinfo.func_151536_b();
                this.cachedBlockMetadata[j] = (byte) flatlayerinfo.getFillBlockMeta();
            }
        }
    }

    public Chunk loadChunk(int p_73158_1_, int p_73158_2_) {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }

    public Chunk provideChunk(int p_73154_1_, int p_73154_2_) {
        Chunk chunk = new Chunk(this.worldObj, p_73154_1_, p_73154_2_);
        int l;

        for (int k = 0; k < this.cachedBlockIDs.length; ++k) {
            Block block = this.cachedBlockIDs[k];

            if (block != null) {
                l = k >> 4;
                ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];

                if (extendedblockstorage == null) {
                    extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
                    chunk.getBlockStorageArray()[l] = extendedblockstorage;
                }

                for (int i1 = 0; i1 < 16; ++i1) {
                    for (int j1 = 0; j1 < 16; ++j1) {
                        extendedblockstorage.setExtBlockID(i1, k & 15, j1, block);
                        extendedblockstorage.setExtBlockMetadata(i1, k & 15, j1, this.cachedBlockMetadata[k]);
                    }
                }
            }
        }

        chunk.generateSkylightMap();
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (l = 0; l < abyte.length; ++l) {
            abyte[l] = (byte) abiomegenbase[l].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return true;
    }

    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long) p_73153_2_ * i1 + (long) p_73153_3_ * j1 ^ this.worldObj.getSeed());

        int l1;
        int i2;
        int j2;

        if (this.waterLakeGenerator != null && this.random.nextInt(8) == 0) {
            l1 = k + this.random.nextInt(16) + 8;
            i2 = this.random.nextInt(256);
            j2 = l + this.random.nextInt(16) + 8;
            this.waterLakeGenerator.generate(this.worldObj, this.random, l1, i2, j2);
        }

        if (this.worldGenTallGrass != null && this.random.nextInt(2) == 0) {
            l1 = k + this.random.nextInt(16) + 8;
            i2 = this.random.nextInt(256);
            j2 = l + this.random.nextInt(16) + 8;
            this.worldGenTallGrass.generate(this.worldObj, this.random, l1, i2, j2);
        }

        if (this.worldGenTallGrass != null && this.random.nextInt(12) == 0) {
            l1 = k + this.random.nextInt(16) + 8;
            i2 = this.random.nextInt(256);
            j2 = l + this.random.nextInt(16) + 8;
            this.worldGenTrees.generate(this.worldObj, this.random, l1, i2, j2);
        }

        if (this.hasDecoration) {
            biomegenbase.decorate(this.worldObj, this.random, k, l);
        }
    }

    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    public void saveExtraData() {
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "FlatLevelSource";
    }

    @SuppressWarnings("rawtypes")
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return biomegenbase.getSpawnableList(p_73155_1_);
    }

    public ChunkPosition findClosestStructure(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int p_82695_1_, int p_82695_2_) {
    }
}