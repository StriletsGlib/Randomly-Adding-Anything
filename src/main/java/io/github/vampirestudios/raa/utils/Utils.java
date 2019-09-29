package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class Utils {

    public static String toTitleCase(String lowerCase) {
        return "" + Character.toUpperCase(lowerCase.charAt(0))+lowerCase.substring(1);
    }

    public static SurfaceBuilder<TernarySurfaceConfig> random(int chance) {
        if (chance == 10) {
            return SurfaceBuilder.BADLANDS;
        } else if(chance == 5) {
            return SurfaceBuilder.GIANT_TREE_TAIGA;
        } else if(chance == 30) {
            return SurfaceBuilder.SHATTERED_SAVANNA;
        } else if(chance == 2) {
            return SurfaceBuilder.MOUNTAIN;
        } else if(chance == 40) {
            return SurfaceBuilder.WOODED_BADLANDS;
        } else {
            return RandomlyAddingAnything.SURFACE_BUILDER;
        }
    }

    public static SurfaceChunkGenerator randomCG(int chance, World world, BiomeSource biomeSource) {
        if (chance == 22) {
            return new CavesChunkGenerator(world, biomeSource, new CavesChunkGeneratorConfig());
        } else if(chance == 44) {
            return new FloatingIslandsChunkGenerator(world, biomeSource, new FloatingIslandsChunkGeneratorConfig());
        } else {
            return new OverworldChunkGenerator(world, biomeSource, new OverworldChunkGeneratorConfig());
        }
    }

}
