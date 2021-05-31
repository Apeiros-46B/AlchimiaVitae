package me.apeiros.alchimiavitae.utils;

import io.github.mooy1.infinitylib.categories.MultiCategory;
import io.github.mooy1.infinitylib.categories.SubCategory;
import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * Holds the two subcategories and the main category for this addon
 */
@UtilityClass
public class Categories {

    public static final Category GENERAL = new SubCategory(
            new NamespacedKey(AlchimiaVitae.i(), "av_general"),
            new CustomItem(Material.ENCHANTED_BOOK, "&6Alchimia Vitae &7- &2General")
    );

    public static final Category ALTAR_RECIPES = new SubCategory(
            new NamespacedKey(AlchimiaVitae.i(), "av_altar_recipes"),
            new CustomItem(Material.ENCHANTING_TABLE, "&6Alchimia Vitae &7- &5Altar Recipes")
    );

    public static final Category INFUSIONS = new SubCategory(
            new NamespacedKey(AlchimiaVitae.i(), "av_infusions"),
            new CustomItem(Material.LODESTONE, "&6Alchimia Vitae &7- &dInfusions")
    );

    public static final Category MAIN = new MultiCategory(
            new NamespacedKey(AlchimiaVitae.i(), "alchimia_vitae"),
            new CustomItem(Material.TOTEM_OF_UNDYING, "&6Alchimia Vitae"),
            GENERAL, ALTAR_RECIPES, INFUSIONS
    );

}
