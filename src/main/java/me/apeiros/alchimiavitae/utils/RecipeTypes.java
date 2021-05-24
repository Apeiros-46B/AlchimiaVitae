package me.apeiros.alchimiavitae.utils;

import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.NamespacedKey;

@UtilityClass
public class RecipeTypes {

    public static final RecipeType SOUL_COLLECTOR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "soul_collector_type"), Items.SOUL_COLLECTOR, "", "&b&oExtract using the Soul Collector");
    public static final RecipeType PLANT_INFUSION_CHAMBER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "plant_infusion_chamber_type"), Items.PLANT_INFUSION_CHAMBER, "", "&b&oInfuse using the Plant Infusion Chamber");
    public static final RecipeType EXP_CRYSTALLIZER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "exp_crystallizer_type"), Items.EXP_CRYSTALLIZER, "", "&b&oCrystallize using the Experience Crystallizer");
    public static final RecipeType DIVINE_ALTAR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.inst(), "divine_altar_type"), Items.DIVINE_ALTAR, "", "&b&oFabricate using the Divine Altar");

}
