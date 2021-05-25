package me.apeiros.alchimiavitae.setup;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.apeiros.alchimiavitae.utils.PotionUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class Items {

    public static final SlimefunItemStack SOUL_COLLECTOR = new SlimefunItemStack("AV_SOUL_COLLECTOR",
            Material.DIAMOND_SWORD, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#6baefa:#7145b0>Soul Collector</gradient>")),
            "&bCollects Souls", "&bKill any mob with this", "&bto extract its Soul");

    static {
        ItemMeta meta = SOUL_COLLECTOR.getItemMeta();
        meta.setUnbreakable(true);

        SOUL_COLLECTOR.setItemMeta(meta);
        SOUL_COLLECTOR.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
    }

    public static final SlimefunItemStack CONDENSED_SOUL = new SlimefunItemStack("AV_CONDENSED_SOUL",
            Material.LIGHT_BLUE_DYE, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#6baefa:#7145b0>Condensed Soul</gradient>")),
            "&bA Soul, condensed into an orb", "&9&oPerhaps there is a way", "&9&oto manipulate its power...");

    static {
        CONDENSED_SOUL.addUnsafeEnchantment(Enchantment.LUCK, 1);
        CONDENSED_SOUL.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack PLANT_INFUSION_CHAMBER = new SlimefunItemStack("AV_PLANT_INFUSION_CHAMBER",
            Material.LIME_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#549c64:#1de078>Plant Infusion Chamber</gradient>")),
            "&bCan infuse plants with dark", "&bor light energy using", "&bSouls and Magical Lumps", "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(64));

    public static final SlimefunItemStack GOOD_MAGIC_PLANT = new SlimefunItemStack("AV_GOOD_MAGIC_PLANT",
            Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#2ddae0:#31f876>Plant of Light Magic</gradient>")),
            "&7Light Magic III", "&aRadiates with an empyreal", "&aglow like no other...");

    static {
        GOOD_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
        GOOD_MAGIC_PLANT.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack EVIL_MAGIC_PLANT = new SlimefunItemStack("AV_EVIL_MAGIC_PLANT",
            Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff5555:#ffa012>Plant of Dark Magic</gradient>")),
            "&7Dark Magic III", "&cContains untold amounts of dark", "&cmagic locked away in its xylems...");

    static {
        EVIL_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
        EVIL_MAGIC_PLANT.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack GOOD_ESSENCE = new SlimefunItemStack("AV_GOOD_ESSENCE",
            Material.GLOWSTONE_DUST, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#2ddae0:#31f876>Light Essence</gradient>")),
            "&7Light Magic III", "&aLife. Illumination. ");

    public static final SlimefunItemStack EVIL_ESSENCE = new SlimefunItemStack("AV_EVIL_ESSENCE",
            Material.GUNPOWDER, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff5555:#ffa012>Dark Essence</gradient>")),
            "&7Dark Magic III", "&cDeath. Darkness.");

    public static final SlimefunItemStack EXP_CRYSTALLIZER = new SlimefunItemStack("AV_EXP_CRYSTALLIZER",
            Material.LIME_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#50fa75:#3dd2ff>Experience Crystallizer</gradient>")),
            "&aForms EXP orbs into a crystalline,", "&adurable, and energetic form.",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(32));

    public static final SlimefunItemStack EXP_CRYSTAL = new SlimefunItemStack("AV_EXP_CRYSTAL",
            Material.EMERALD, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#50fa75:#3dd2ff>Experience Crystal</gradient>")),
            "&aA chunk of crystalline experience");

    static {
        EXP_CRYSTAL.addUnsafeEnchantment(Enchantment.LUCK, 1);
        EXP_CRYSTAL.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack ILLUMIUM = new SlimefunItemStack("AV_ILLUMIUM",
            Material.IRON_INGOT, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#2ddae0:#31f876>Illumium Ingot</gradient>")),
            "&aIt energetically effuses light in your hand");

    static {
        ILLUMIUM.addUnsafeEnchantment(Enchantment.LUCK, 1);
        ILLUMIUM.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack DARKSTEEL = new SlimefunItemStack("AV_DARKSTEEL",
            Material.NETHERITE_INGOT, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff5555:#ffa012>Darksteel Ingot</gradient>")),
            "&cIt radiates... with darkness?");

    static {
        DARKSTEEL.addUnsafeEnchantment(Enchantment.LUCK, 1);
        DARKSTEEL.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack DIVINE_ALTAR = new SlimefunItemStack("AV_DIVINE_ALTAR",
            Material.ENCHANTING_TABLE, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff5555:#ff6cfd>Divine Altar</gradient>")),
            "&5A sacred apparatus for the", "&5performance of ancient rituals");

    public static final SlimefunItemStack MOLTEN_MYSTERY_METAL = new SlimefunItemStack("AV_MOLTEN_MYSTERY_METAL",
            Material.LAVA_BUCKET, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff6745:#ff5555>Molten Mystery Metal</gradient>")),
            "&6A conglomerate of different metals");

    public static final SlimefunItemStack MYSTERY_METAL = new SlimefunItemStack("AV_MYSTERY_METAL",
            Material.IRON_INGOT, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#ff6745:#ff5555>Mystery Metal Ingot</gradient>")),
            "&6Contains many metals");

    public static final SlimefunItemStack POTION_OF_EXCHANGE = PotionUtils.makeExchangePotion(new PotionEffect[]{},
            BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#6274e7:#8752a3>Potion of Exchange</gradient>")), "", Color.FUCHSIA);

}
