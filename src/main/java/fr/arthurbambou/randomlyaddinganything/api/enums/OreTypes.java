package fr.arthurbambou.randomlyaddinganything.api.enums;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public enum OreTypes {
    METAL,
    GEM;
    public static List<Identifier> METAL_TEXTURES = new ArrayList<>();
    public static List<Identifier> METAL_BLOCK_TEXTURES = new ArrayList<>();
    public static List<Identifier> METAL_ITEM_TEXTURES = new ArrayList<>();
    public static List<Identifier> GEM_TEXTURES = new ArrayList<>();
    public static List<Identifier> GEM_BLOCK_TEXTURES = new ArrayList<>();
    public static List<Identifier> GEM_ITEM_TEXTURES = new ArrayList<>();
}
