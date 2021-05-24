package me.apeiros.alchimiavitae.utils;

import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.NamespacedKey;

@UtilityClass
public class RecipeTypes {

    public static final RecipeType SOUL_COLLECTOR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "soul_collector_type"), Items.SOUL_COLLECTOR, "", "&bExtract using the Soul Collector");
    public static final RecipeType PLANT_INFUSION_CHAMBER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "plant_infusion_chamber_type"), Items.PLANT_INFUSION_CHAMBER, "", "&bInfuse using the Plant Infusion Chamber");
    public static final RecipeType EXP_CRYSTALLIZER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "exp_crystallizer_type"), Items.EXP_CRYSTALLIZER, "", "&bCrystallize using the Experience Crystallizer");

}
