package io.github.vampirestudios.raa.generation.surface;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.DoubleFunction;

//Code kindly taken from Terrestria. Thank you, coderbot, Prospector, and Valoeghese!
public class GlacierSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static SimplexNoiseSampler noiseGenerator;
    protected final BlockState WATER = Blocks.WATER.getDefaultState();
    protected final BlockState SAND = Blocks.SAND.getDefaultState();
    protected final BlockState pICE = Blocks.PACKED_ICE.getDefaultState();
    protected final BlockState bICE = Blocks.BLUE_ICE.getDefaultState();
    private long currentSeed = 0L;
    private DoubleFunction<TernarySurfaceConfig> configProvider;

    public GlacierSurfaceBuilder(DoubleFunction<TernarySurfaceConfig> config) {
        super(TernarySurfaceConfig::deserialize);
        configProvider = config;
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int worldHeight, double noiseVal, BlockState var9, BlockState var10, int var11, long seed, TernarySurfaceConfig configToIgnore) {
        TernarySurfaceConfig config = configProvider.apply(noiseVal);

        if (noiseGenerator == null || seed != currentSeed) {
            noiseGenerator = new SimplexNoiseSampler(new Random(seed));
            currentSeed = seed;
        }

        double noise = noiseGenerator.sample((double) x / 260D, (double) z / 260D);
        int glacierDifference = (int) ((noise > 0.1D && noise < 0.5D) ? (1171875 * Math.pow(noise - 0.26, 4)) - 3 : 0);

        int localX = x & 15;
        int localZ = z & 15;

        BlockPos.Mutable pos = new BlockPos.Mutable(localX, 255, localZ);

        if (glacierDifference != 0) {
            pos.setY(worldHeight);
            chunk.setBlockState(pos, AIR, false);

            pos.setY(worldHeight - 1);
            chunk.setBlockState(pos, AIR, false);

            worldHeight -= 2;
        }

        for (int y = worldHeight; y >= 0; --y) {
            pos.setY(y);
            BlockState toSet = STONE;

            if (y - worldHeight > glacierDifference) {
                toSet = (y - worldHeight == -1) ? bICE : pICE;
            } else {
                if (y < 255) {
                    BlockState upState = chunk.getBlockState(pos.up());
                    if (upState == AIR) {
                        toSet = config.getTopMaterial();
                    } else if (upState == WATER) {
                        toSet = config.getUnderwaterMaterial();
                    } else {
                        if (y < 253) {
                            if (chunk.getBlockState(pos.up(3)) == AIR || chunk.getBlockState(pos.up(2)) == AIR) {
                                toSet = config.getUnderMaterial();
                            }
                        }
                    }
                }
            }

            chunk.setBlockState(pos, toSet, false);
        }
    }
}