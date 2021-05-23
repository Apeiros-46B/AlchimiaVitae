package me.apeiros.alchimiavitae.utils;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import io.github.mooy1.infinitylib.categories.MultiCategory;
import io.github.mooy1.infinitylib.categories.SubCategory;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Categories {

    public static final Category GENERAL = new SubCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimiavitae_general"),
            new CustomItem(Material.TOTEM_OF_UNDYING, "&aAlchimia Vitae &7- &aGeneral", "", "&a⇨ Click to open")
    );

    public static final Category PLANTS = new SubCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimiavitae_plants"),
            new CustomItem(Material.JUNGLE_SAPLING, "&aAlchimia Vitae &7- &2Plants", "", "&d⇨ Click to open")
    );

    public static final Category SPELLS = new SubCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimiavitae_spells"),
            new CustomItem(Material.ENCHANTED_BOOK, "&aAlchimia Vitae &7- &5Spells", "", "&a⇨ Click to open")
    );

    public static final Category BREWS = new SubCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimiavitae_brews"),
            new CustomItem(Material.DRAGON_BREATH, "&aAlchimia Vitae &7- &dBrewing", "", "&e⇨ Click to open")
    );

    public static final Category ALCHEMY = new SubCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimivitae_transmutation"),
            new CustomItem(Material.PISTON, "&aAlchimia Vitae &7- &6Transmutation", "", "&e⇨ Click to open")
    );

    public static final Category MAIN = new MultiCategory(
            new NamespacedKey(AlchimiaVitae.inst(), "alchimivitae"),
            new CustomItem(Material.BREWING_STAND, "&aAlchimia Vitae", "", "&5⇨ Click to open"),
            GENERAL, PLANTS, SPELLS, BREWS, ALCHEMY
    );
}