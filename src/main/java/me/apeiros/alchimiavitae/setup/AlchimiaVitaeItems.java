package me.apeiros.alchimiavitae.setup;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class AlchimiaVitaeItems {

    public static final SlimefunItemStack SOUL_COLLECTOR = new SlimefunItemStack("AV_SOUL_COLLECTOR",
        Material.DIAMOND_SWORD, BukkitComponentSerializer.legacy().serialize
        (mm.parse("<gradient:#6BAEFA:#7145B0>Soul Collector</gradient>")),
        "&bCollects Souls", "&bKill any mob with this", "&bto extract its Soul.");

    static {
        ItemMeta meta = SOUL_COLLECTOR.getItemMeta();
        meta.setUnbreakable(true);

        SOUL_COLLECTOR.setItemMeta(meta);
        SOUL_COLLECTOR.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
    }

    public static final SlimefunItemStack CONDENSED_SOUL = new SlimefunItemStack("AV_CONDENSED_SOUL",
        Material.LIGHT_BLUE_DYE, BukkitComponentSerializer.legacy().serialize
        (mm.parse("<gradient:#6BAEFA:#7145B0>Condensed Soul</gradient>")),
        "&9A Soul, condensed into an orb.", "&9&oPerhaps there is a way", "&9&oto manipulate its power...");

    static {
        CONDENSED_SOUL.addUnsafeEnchantment(Enchantment.LUCK, 1);
        CONDENSED_SOUL.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack PLANT_INFUSION_CHAMBER = new SlimefunItemStack("AV_PLANT_INFUSION_CHAMBER",
            Material.LIME_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#549C64:#1DE078>Plant Infusion Chamber</gradient>")),
            "&bCan infuse plants with dark", "&bor light energy using", "&bSouls and Magical Lumps", "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(64));

    public static final SlimefunItemStack GOOD_MAGIC_PLANT = new SlimefunItemStack("AV_GOOD_MAGIC_PLANT",
        Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
        (mm.parse("<gradient:#2DDAE0:#31F876>Benevolent Magical Plant</gradient>")),
        "&7Light Magic III", "", "&aRadiates with an empyreal", "&aglow like no other...");

    static {
        GOOD_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
        GOOD_MAGIC_PLANT.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public static final SlimefunItemStack EVIL_MAGIC_PLANT = new SlimefunItemStack("AV_EVIL_MAGIC_PLANT",
        Material.OAK_SAPLING, BukkitComponentSerializer.legacy().serialize
        (mm.parse("<gradient:#5555FF:#9300FF>Malevolent Magical Plant</gradient>")),
        "&7Dark Magic III", "", "&cContains untold amounts of dark", "&cenergy locked away in its xylems...");

    static {
        EVIL_MAGIC_PLANT.addUnsafeEnchantment(Enchantment.LUCK, 1);
        EVIL_MAGIC_PLANT.addFlags(ItemFlag.HIDE_ENCHANTS);
    }

}
