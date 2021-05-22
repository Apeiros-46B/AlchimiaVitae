package me.apeiros.alchimiavitae.utils;

import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.NamespacedKey;

@UtilityClass
public class AlchimiaVitaeRecipeTypes {

    public static final RecipeType SOUL_COLLECTOR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "soul_collector_type"), AlchimiaVitaeItems.SOUL_COLLECTOR, "", "&bExtract using the Soul Collector");
    public static final RecipeType ADVANCED_BREWING_CHAMBER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "advanced_brewing_chamber_type"), AlchimiaVitaeItems.ADVANCED_BREWING_CHAMBER, "", "&bBrew using the Advanced", "&bBrewing Chamber");

}
