package me.apeiros.alchimiavitae.utils;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

/**
 * This class holds ItemStacks that are frequently used in ChestMenus
 * @author Apeiros
 */
@UtilityClass
public class ChestMenuItems {

    public static final ItemStack BG = ChestMenuUtils.getBackground();

    public static final ItemStack STATUS_BG = new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#ff3b3b:#ff5c74>Status</gradient>")));

    public static final ItemStack OUT_BG = new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#39f792:#5c95ff>Output</gradient>")));

    public static final ItemStack OUT_BG_ALT = new CustomItem(Material.BLUE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#39f792:#5c95ff>Output</gradient>")));

    public static final ItemStack OUT_BG_CLICK_CRAFT = new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#39f792:#5c95ff>Output</gradient>")),
            "&aClick to craft");

    public static final ItemStack IN_BG = new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#ff68fc:#ff9a5c>Input</gradient>")));

    public static final ItemStack IN_BG_CLICK_CRAFT = new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#ff68fc:#ff9a5c>Input</gradient>")),
            "&aClick to craft");

    public static final ItemStack CRAFT_BTN = new CustomItem(Material.LIME_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#39f792:#5c95ff>Craft</gradient>")),
            "&aClick to craft");

    public static final ItemStack CRAFT_BG = new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
            BukkitComponentSerializer.legacy().serialize(mm.parse("<gradient:#39f792:#5c95ff>Craft</gradient>")));

}
