package me.apeiros.alchimiavitae.utils;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

/**
 * This class holds {@link ItemStack}s
 * that are frequently used in inventories
 */
@UtilityClass
public class ChestMenuItems {

    public static final ItemStack BG = ChestMenuUtils.getBackground();

    public static final ItemStack STATUS_BG = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#ff3b3b:#ff5c74>Status</gradient>")));

    public static final ItemStack OUT_BG = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#39f792:#5c95ff>Output</gradient>")));

    public static final ItemStack OUT_BG_ALT = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#39f792:#5c95ff>Output</gradient>")));

    public static final ItemStack OUT_BG_CLICK_CRAFT = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#39f792:#5c95ff>Output</gradient>")),
            "&aClick to craft");

    public static final ItemStack IN_BG = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#ff68fc:#ff9a5c>Input</gradient>")));

    public static final ItemStack IN_BG_CLICK_CRAFT = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#ff68fc:#ff9a5c>Input</gradient>")),
            "&aClick to craft");

    public static final ItemStack CRAFT_BTN = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#39f792:#5c95ff>Craft</gradient>")),
            "&aClick to craft");

    public static final ItemStack CRAFT_BG = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(MM.parse("<gradient:#39f792:#5c95ff>Craft</gradient>")));

}
