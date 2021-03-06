package io.github.vampirestudios.raa.utils;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.config.BetterCavesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Set;

/**
 * Utility functions for BetterCaves. These functions are mostly refactored versions of methods found in
 * {@code net.minecraft.world.gen.MapGenCaves}.
 * This class may not be instantiated - all members are {@code public} and {@code static},
 * and as such may be accessed freely.
 */
public class BetterCaveUtil {

    /* Common IBlockStates used in this class */
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    private static final BlockState SAND = Blocks.SAND.getDefaultState();
    private static final BlockState RED_SAND = Blocks.RED_SAND.getDefaultState();
    private static final BlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
    private static final BlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
    // Set of carvable blocks provided by vanilla 1.14
    public static Set<Block> carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW, Blocks.PACKED_ICE);
    private BetterCaveUtil() {
    } // Private constructor prevents instantiation

    /**
     * Determine if the block at the specified location is the designated top block for the biome.
     *
     * @param chunkIn the chunk containing the block
     * @param localX  the block's chunk-local x-coordinate
     * @param y       the block's chunk-local y-coordinate (same as real y-coordinate)
     * @param localZ  the block's chunk-local z-coordinate
     * @return true if this block is the same type as the biome's designated top block
     */
    public static boolean isTopBlock(Chunk chunkIn, int localX, int y, int localZ) {
        BlockPos blockPos = new BlockPos(localX, y, localZ);
        Biome biome = chunkIn.getBiomeArray().getBiomeForNoiseGen(localX, y, localZ);
        BlockState blockState = chunkIn.getBlockState(blockPos);

        return blockState == biome.getSurfaceConfig().getTopMaterial();
    }

    /**
     * Digs out the current block, default implementation removes stone, filler, and top block.
     * Sets the block to lavaBlockState if y is less then the lavaDepth in the Config, and air other wise.
     * If setting to air, it also checks to see if we've broken the surface, and if so,
     * tries to make the floor the biome's top block.
     *
     * @param chunkIn        the chunk containing the block
     * @param lavaBlockState the BlockState to use as lava. If you want regular lava, you can either specify it, or
     *                       use the wrapper function without this param
     * @param localX         the block's chunk-local x coordinate
     * @param y              the block's chunk-local y coordinate (same as real y-coordinate)
     * @param localZ         the block's chunk-local z coordinate
     * @param chunkX         the chunk's x coordinate
     * @param chunkZ         the chunk's z coordinate
     */
    public static void digBlock(Chunk chunkIn, BlockState lavaBlockState, int localX, int y, int localZ, int chunkX, int chunkZ) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(localX, y, localZ);

        BlockPos blockPos = new BlockPos(mutableBlockPos.getX(), mutableBlockPos.getY(), mutableBlockPos.getZ());
        BlockPos blockPosAbove = mutableBlockPos.setOffset(Direction.UP);
        BlockPos blockPosBelow = mutableBlockPos.setOffset(Direction.DOWN).setOffset(Direction.DOWN);

        BlockState blockState = chunkIn.getBlockState(blockPos);
        BlockState blockStateAbove = chunkIn.getBlockState(blockPosAbove);

        Biome biome = chunkIn.getBiomeArray().getBiomeForNoiseGen(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        BlockState biomeTopBlockState = biome.getSurfaceConfig().getTopMaterial();
        BlockState biomeFillerBlockState = biome.getSurfaceConfig().getUnderMaterial();

        // Only continue if the block is replaceable
        if (canReplaceBlock(blockState, blockStateAbove) || blockState == biomeTopBlockState || blockState == biomeFillerBlockState) {
            if (y <= BetterCavesConfig.lavaDepth) { // Replace any block below the lava depth with the lava block passed in
                chunkIn.setBlockState(blockPos, lavaBlockState, false);
            } else {
                // Adjust block below if block removed is biome top block
                if (isTopBlock(chunkIn, localX, y, localZ)
                        && canReplaceBlock(chunkIn.getBlockState(blockPosBelow), AIR))
                    chunkIn.setBlockState(blockPosBelow, biomeTopBlockState, false);

                // Replace this block with air, effectively "digging" it out
                chunkIn.setBlockState(blockPos, CAVE_AIR, false);

                // If we caused floating sand to form, replace it with sandstone
                if (blockStateAbove == SAND)
                    chunkIn.setBlockState(blockPosAbove, SANDSTONE, false);
                else if (blockStateAbove == RED_SAND)
                    chunkIn.setBlockState(blockPosAbove, RED_SANDSTONE, false);
            }
        }
    }

    /**
     * Determines if the Block of a given IBlockState is suitable to be replaced during cave generation.
     * Basically returns true for most common worldgen blocks (e.g. stone, dirt, sand), false if the block is air.
     *
     * @param blockState      the block's IBlockState
     * @param blockStateAbove the IBlockState of the block above this one
     * @return true if the blockState can be replaced
     */
    public static boolean canReplaceBlock(BlockState blockState, BlockState blockStateAbove) {
        Block block = blockState.getBlock();

        // Avoid damaging trees
        if (blockState.getMaterial() == Material.LEAVES
                || blockState.getMaterial() == Material.WOOD)
            return false;

        // Avoid digging out under trees
        if (blockStateAbove.getMaterial() == Material.WOOD)
            return false;

        // This should hopefully avoid damaging villages
        if (block == Blocks.FARMLAND
                || block == Blocks.GRASS_PATH) {
            return false;
        }

        // Accept stone-like blocks added from other mods
        if (blockState.getMaterial() == Material.STONE)
            return true;

        // List of carvable blocks provided by vanilla
        if (carvableBlocks.contains(block))
            return true;

        // Only accept gravel and sand if water is not directly above it
        return (block == Blocks.SAND || block == Blocks.GRAVEL)
                && !blockStateAbove.getFluidState().matches(FluidTags.WATER);
    }

    /**
     * Tests 8 edge points and center of chunk to approximate max surface altitude (y-coordinate) of the chunk.
     * Note that water blocks also count as the surface.
     *
     * @param chunkIn chunk
     * @return y-coordinate of the approximate highest surface altitude in the chunk
     */
    public static int getMaxSurfaceAltitudeChunk(Chunk chunkIn) {
        int maxHeight = 0;
        int[] testCoords = {0, 7, 15}; // chunk-local x/z coordinates to test for max height

        for (int x : testCoords)
            for (int z : testCoords)
                maxHeight = Math.max(maxHeight, getSurfaceAltitudeForColumn(chunkIn, x, z));

        return maxHeight;
    }

    /**
     * Tests 8 edge points and center of chunk to approximate min surface altitude (y-coordinate) of the chunk.
     * Note that water blocks also count as the surface.
     *
     * @param chunkIn chunk
     * @return y-coordinate of the approximate lowest surface altitude in the chunk
     */
    public static int getMinSurfaceAltitudeChunk(Chunk chunkIn) {
        int minHeight = 256;
        int[] testCoords = {0, 7, 15}; // chunk-local x/z coordinates to test for max height

        for (int x : testCoords)
            for (int z : testCoords)
                minHeight = Math.min(minHeight, getSurfaceAltitudeForColumn(chunkIn, x, z));

        return minHeight;
    }

    /**
     * Tests every block in a 2x2 "sub-chunk" to get the max surface altitude (y-coordinate) of the sub-chunk.
     * Note that water blocks also count as the surface.
     *
     * @param chunkIn chunk
     * @param subX    The x-coordinate of the sub-chunk. Note that this is regular chunk-local x-coordinate divided
     *                by 2. E.g. If you want the last 2 blocks on the x-axis in the chunk (blocks 14 and 15), use subX = 7.
     * @param subZ    The z-coordinate of the sub-chunk. Note that this is regular chunk-local z-coordinate divided
     *                by 2. E.g. If you want the last 2 blocks on the z-axis in the chunk (blocks 14 and 15), use subZ = 7.
     * @return Max surface height of the sub-chunk
     */
    public static int getMaxSurfaceAltitudeSubChunk(Chunk chunkIn, int subX, int subZ) {
        int maxHeight = 0;
        int[] testCoords = {0, 1}; // chunk-local x/z coordinates to test for max height

        for (int x : testCoords)
            for (int z : testCoords)
                maxHeight = Math.max(maxHeight, getSurfaceAltitudeForColumn(chunkIn, (subX * 2) + x, (subZ * 2) + z));

        return maxHeight;
    }

    /**
     * Returns the y-coordinate of the surface block for a given local block coordinate for a given chunk.
     * Note that water blocks also count as the surface.
     *
     * @param chunkIn chunk
     * @param localX  The block's chunk-local x-coordinate
     * @param localZ  The block's chunk-local z-coordinate
     * @return The y-coordinate of the surface block
     */
    private static int getSurfaceAltitudeForColumn(Chunk chunkIn, int localX, int localZ) {
        return searchSurfaceAltitudeInRangeForColumn(chunkIn, localX, localZ, 255, 0);
    }

    /**
     * Searches for the y-coordinate of the surface block for a given local block coordinate for a given chunk in a
     * specific range of y-coordinates.
     * Note that water blocks also count as the surface.
     *
     * @param chunkIn chunk
     * @param localX  The block's chunk-local x-coordinate
     * @param localZ  The block's chunk-local z-coordinate
     * @param topY    The top y-coordinate to stop searching at
     * @param bottomY The bottom y-coordinate to start searching at
     * @return The y-coordinate of the surface block
     */
    public static int searchSurfaceAltitudeInRangeForColumn(Chunk chunkIn, int localX, int localZ, int topY, int bottomY) {
        BlockPos.Mutable blockPos = new BlockPos.Mutable(localX, bottomY, localZ);

        // Edge case: blocks go all the way up to build height
        if (topY == 255) {
            BlockPos topPos = new BlockPos(localX, topY, localZ);
            if (chunkIn.getBlockState(topPos) != Blocks.AIR.getDefaultState() && chunkIn.getBlockState(topPos).getMaterial() != Material.WATER)
                return 255;
        }

        for (int y = bottomY; y <= topY; y++) {
            if (chunkIn.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || chunkIn.getBlockState(blockPos).getMaterial() == Material.WATER)
                return y;
            blockPos.offset(Direction.UP);
        }

        return -1; // Surface somehow not found
    }
}