package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.RandomlyAddingAnything;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class Textures {

    public static void init() {
        ores();
    }

    private static void ores() {
        /*OreTypes.METAL_TEXTURES.add(new Identifier("item/melon_seeds"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/prismarine_crystals"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/cocoa_beans"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/blaze_powder"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/iron_ingot"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/gold_ingot"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/gold_nugget"));
        OreTypes.METAL_TEXTURES.add(new Identifier("item/iron_nugget"));

        OreTypes.GEM_TEXTURES.add(new Identifier("item/ghast_tear"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/coal"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/diamond"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/glowstone_dust"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/gunpowder"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/lapis_lazuli"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/prismarine_shard"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/quartz"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/redstone"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/ruby"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/sugar"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/emerald"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/ender_pearl"));
        OreTypes.GEM_TEXTURES.add(new Identifier("item/charcoal"));*/


        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_1"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_2"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_3"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_4"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_5"));
        OreTypes.METAL_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_6"));

        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_1"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_2"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_3"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_4"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_5"));
        OreTypes.GEM_ITEM_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "item/thing_6"));


        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_1"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_2"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_3"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_4"));
        OreTypes.METAL_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_5"));

        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_1"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_2"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_3"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_4"));
        OreTypes.GEM_BLOCK_TEXTURES.add(new Identifier(RandomlyAddingAnything.MOD_ID, "block/ore_5"));
    }
}
