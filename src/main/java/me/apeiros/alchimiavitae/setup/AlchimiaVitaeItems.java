package me.apeiros.alchimiavitae.setup;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Material;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class AlchimiaVitaeItems {

    public static final SlimefunItemStack SOUL_COLLECTOR = new SlimefunItemStack("AV_SOUL_COLLECTOR",
            Material.DIAMOND_SWORD, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#1cbbff:#6692f2>Soul Collector</gradient>")),
            "&bCollects Souls", "&bKill any mob with this", "&bto extract its Soul.");

    public static final SlimefunItemStack CONDENSED_SOUL = new SlimefunItemStack("AV_CONDENSED_SOUL",
            Material.LIGHT_BLUE_DYE, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#6baefa:#7145b0>Condensed Soul</gradient>")),
            "&9An Ancient one, condensed into an orb.", "&9&oPerhaps there is a way", "&9&oto manipulate its &apower&9...");

    public static final SlimefunItemStack ADVANCED_BREWING_CHAMBER = new SlimefunItemStack("AV_ADVANCED_BREWING_CHAMBER",
            Material.BLACK_STAINED_GLASS, BukkitComponentSerializer.legacy().serialize
            (mm.parse("<gradient:#5555ff:#9300ff>Advanced Brewing Chamber</gradient>")),
            "&2Can be used to create", "&2advanced potions.", "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.powerBuffer(512),
            LoreBuilder.powerPerSecond(128));

}
