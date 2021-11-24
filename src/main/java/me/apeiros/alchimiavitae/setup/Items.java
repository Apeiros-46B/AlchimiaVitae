package me.apeiros.alchimiavitae.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.utils.PotionUtils;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class Items {

    public static final SlimefunItemStack SOUL_COLLECTOR = new SlimefunItemStack("AV_SOUL_COLLECTOR",
            Material.DIAMOND_SWORD, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#6baefa:#7145b0>Soul Collector</gradient>")),
            "&bCollects Souls", "&bKill any mob with this", "&bto extract its Soul");

    private static final Configuration cfg = AlchimiaVitae.i().getConfig();

    static {
        ItemMeta meta = SOUL_COLLECTOR.getItemMeta();
        meta.setUnbreakable(true);

        SOUL_COLLECTOR.setItemMeta(meta);
        SOUL_COLLECTOR.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
    }

    public static final SlimefunItemStack CONDENSED_SOUL = new SlimefunItemStack("AV_CONDENSED_SOUL",
            Material.LIGHT_BLUE_DYE, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#6baefa:#7145b0>Condensed Soul</gradient>")),
            "&bA Soul, condensed into an orb", "&9&oPerhaps there is a way", "&9&oto manipulate its power...");

    static {
        ItemMeta meta = CONDENSED_SOUL.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        CONDENSED_SOUL.setItemMeta(meta);
        CONDENSED_SOUL.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack PLANT_INFUSION_CHAMBER = new SlimefunItemStack("AV_PLANT_INFUSION_CHAMBER",
            Material.LIME_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#549c64:#1de078>Plant Infusion Chamber</gradient>")),
            "&bCan infuse plants with dark", "&bor light energy using", "&bSouls and Magical Lumps", "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(64));

    public static final SlimefunItemStack GOOD_MAGIC_PLANT = new SlimefunItemStack("AV_GOOD_MAGIC_PLANT",
            Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#2ddae0:#31f876>Plant of Light Magic</gradient>")),
            "&7Light Magic I", "&aRadiates with an empyreal", "&aglow like no other...");

    static {
        ItemMeta meta = GOOD_MAGIC_PLANT.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        GOOD_MAGIC_PLANT.setItemMeta(meta);
        GOOD_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack EVIL_MAGIC_PLANT = new SlimefunItemStack("AV_EVIL_MAGIC_PLANT",
            Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff5555:#ffa012>Plant of Dark Magic</gradient>")),
            "&7Dark Magic I", "&cContains untold amounts of dark", "&cmagic locked away in its xylems...");

    static {
        ItemMeta meta = EVIL_MAGIC_PLANT.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        EVIL_MAGIC_PLANT.setItemMeta(meta);
        EVIL_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack GOOD_ESSENCE = new SlimefunItemStack("AV_GOOD_ESSENCE",
            Material.SUGAR, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#2ddae0:#31f876>Light Essence</gradient>")),
            "&7Light Magic II", "&aLife. Illumination. ");

    public static final SlimefunItemStack EVIL_ESSENCE = new SlimefunItemStack("AV_EVIL_ESSENCE",
            Material.GUNPOWDER, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff5555:#ffa012>Dark Essence</gradient>")),
            "&7Dark Magic II", "&cDeath. Darkness.");

    public static final SlimefunItemStack EXP_CRYSTALLIZER = new SlimefunItemStack("AV_EXP_CRYSTALLIZER",
            Material.LIME_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#50fa75:#3dd2ff>Experience Crystallizer</gradient>")),
            "&aForms EXP orbs into a crystalline,", "&adurable, and energetic form.",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(32));

    public static final SlimefunItemStack EXP_CRYSTAL = new SlimefunItemStack("AV_EXP_CRYSTAL",
            Material.EMERALD, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#50fa75:#3dd2ff>Experience Crystal</gradient>")),
            "&aA chunk of crystalline experience");

    static {
        ItemMeta meta = EXP_CRYSTAL.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        EXP_CRYSTAL.setItemMeta(meta);
        EXP_CRYSTAL.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack ILLUMIUM = new SlimefunItemStack("AV_ILLUMIUM",
            Material.IRON_INGOT, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#2ddae0:#31f876>Illumium Ingot</gradient>")),
            "&aIt energetically effuses light in your hand");

    static {
        ItemMeta meta = ILLUMIUM.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        ILLUMIUM.setItemMeta(meta);
        ILLUMIUM.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack DARKSTEEL = new SlimefunItemStack("AV_DARKSTEEL",
            Material.NETHERITE_INGOT, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff5555:#ffa012>Darksteel Ingot</gradient>")),
            "&cIt radiates... with darkness?");

    static {
        ItemMeta meta = DARKSTEEL.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        DARKSTEEL.setItemMeta(meta);
        DARKSTEEL.addUnsafeEnchantment(Enchantment.LUCK, 1);
    }

    public static final SlimefunItemStack DIVINE_ALTAR = new SlimefunItemStack("AV_DIVINE_ALTAR",
            Material.ENCHANTING_TABLE, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff5555:#ff6cfd>Divine Altar</gradient>")),
            "&5A sacred apparatus for the", "&5performance of ancient rituals");

    public static final SlimefunItemStack MOLTEN_MYSTERY_METAL = new SlimefunItemStack("AV_MOLTEN_MYSTERY_METAL",
            Material.LAVA_BUCKET, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff6745:#ff5555>Molten Mystery Metal</gradient>")),
            "&6A conglomerate of different metals");

    public static final SlimefunItemStack MYSTERY_METAL = new SlimefunItemStack("AV_MYSTERY_METAL",
            Material.IRON_INGOT, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#ff6745:#ff5555>Mystery Metal Ingot</gradient>")),
            "&6Contains many metals");

    public static final SlimefunItemStack POTION_OF_OSMOSIS = new SlimefunItemStack("AV_POTION_OF_OSMOSIS",
            Material.DRAGON_BREATH, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#6274e7:#8752a3>Potion of Osmosis</gradient>")),
            "&6Absorbs your potion effects", "&6and stores them in a bottle on &eRight Click");

    public static final SlimefunItemStack ORNATE_CAULDRON = new SlimefunItemStack("AV_ORNATE_CAULDRON",
            Material.CAULDRON, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#57ebbe:#f6fa2a>Ornate Cauldron</gradient>")),
            "&2An advanced cauldron for the brewing of potions");

    // Make sure to make the int pair at the end {time in seconds * 20, level}
    private static final Map<PotionEffectType, int[]> potEffectsMap = new HashMap<>();

    static {
        // Add effects
        potEffectsMap.put(PotionEffectType.DAMAGE_RESISTANCE, new int[]{
                cfg.getInt("options.potions.benevolent-brew.resistance.ticks"),
                cfg.getInt("options.potions.benevolent-brew.resistance.level") - 1
        });
        potEffectsMap.put(PotionEffectType.FAST_DIGGING, new int[]{
                cfg.getInt("options.potions.benevolent-brew.haste.ticks"),
                cfg.getInt("options.potions.benevolent-brew.haste.level") - 1
        });
        potEffectsMap.put(PotionEffectType.REGENERATION, new int[]{
                cfg.getInt("options.potions.benevolent-brew.regen.ticks"),
                cfg.getInt("options.potions.benevolent-brew.regen.level") - 1
        });
        potEffectsMap.put(PotionEffectType.SPEED, new int[]{
                cfg.getInt("options.potions.benevolent-brew.speed.ticks"),
                cfg.getInt("options.potions.benevolent-brew.speed.level") - 1
        });
        potEffectsMap.put(PotionEffectType.JUMP, new int[]{
                cfg.getInt("options.potions.benevolent-brew.jump.ticks"),
                cfg.getInt("options.potions.benevolent-brew.jump.level") - 1
        });
    }

    public static final SlimefunItemStack BENEVOLENT_BREW = PotionUtils.makePotion(MM.parse(
            "<gradient:#2ddae0:#31f876>Benevolent Brew</gradient>"), Color.LIME, potEffectsMap);

    static {
        // Clear the map from the previous usage
        potEffectsMap.clear();

        // Add effects
        potEffectsMap.put(PotionEffectType.WEAKNESS, new int[]{
                cfg.getInt("options.potions.malevolent-concoction.weakness.ticks"),
                cfg.getInt("options.potions.malevolent-concoction.weakness.level") - 1
        });
        potEffectsMap.put(PotionEffectType.SLOW, new int[]{
                cfg.getInt("options.potions.malevolent-concoction.slowness.ticks"),
                cfg.getInt("options.potions.malevolent-concoction.slowness.level") - 1
        });
        potEffectsMap.put(PotionEffectType.POISON, new int[]{
                cfg.getInt("options.potions.malevolent-concoction.poison.ticks"),
                cfg.getInt("options.potions.malevolent-concoction.poison.level") - 1
        });
        potEffectsMap.put(PotionEffectType.BLINDNESS, new int[]{
                cfg.getInt("options.potions.malevolent-concoction.blindness.ticks"),
                cfg.getInt("options.potions.malevolent-concoction.blindness.level") - 1
        });
        potEffectsMap.put(PotionEffectType.HUNGER, new int[]{
                cfg.getInt("options.potions.malevolent-concoction.hunger.ticks"),
                cfg.getInt("options.potions.malevolent-concoction.hunger.level") - 1
        });
    }

    public static final SlimefunItemStack MALEVOLENT_CONCOCTION = PotionUtils.makeSplashPotion(MM.parse(
            "<gradient:#ff5555:#ffa012>Malevolent Concoction</gradient>"), Color.MAROON, potEffectsMap);

    public static final SlimefunItemStack ALTAR_OF_INFUSION = new SlimefunItemStack("AV_ALTAR_OF_INFUSION",
            Material.LODESTONE, BukkitComponentSerializer.legacy().serialize
            (MM.parse("<gradient:#f78770:#ff607b>Altar of Infusion</gradient>")),
            "&5An altar that combines technology", "&5and witchcraft to infuse items with", "&5powerful properties",
            "&dCan only infuse gold, iron, diamond, and netherite gear,", "&das well as fishing rods, bows, and crossbows");

}
