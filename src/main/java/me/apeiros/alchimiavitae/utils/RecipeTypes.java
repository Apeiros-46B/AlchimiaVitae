package me.apeiros.alchimiavitae.utils;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.NamespacedKey;

/**
 * Holds this addon's {@link RecipeType}s
 */
@UtilityClass
public class RecipeTypes {

    public static final RecipeType SOUL_COLLECTOR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "soul_collector_type"), Items.SOUL_COLLECTOR, "", "&b&oExtract using the Soul Collector");
    public static final RecipeType PLANT_INFUSION_CHAMBER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "plant_infusion_chamber_type"), Items.PLANT_INFUSION_CHAMBER, "", "&b&oInfuse using the Plant Infusion Chamber");
    public static final RecipeType EXP_CRYSTALLIZER_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "exp_crystallizer_type"), Items.EXP_CRYSTALLIZER, "", "&b&oCrystallize using the Experience Crystallizer");
    public static final RecipeType DIVINE_ALTAR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "divine_altar_type"), Items.DIVINE_ALTAR, "", "&b&oFabricate using the Divine Altar");
    public static final RecipeType ORNATE_CAULDRON_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "ornate_cauldron_type"), Items.ORNATE_CAULDRON, "", "&b&oBrew using the Ornate Cauldron");
    public static final RecipeType INFUSION_ALTAR_TYPE = new RecipeType(new NamespacedKey(AlchimiaVitae.i(), "infusion_altar_type"), Items.ALTAR_OF_INFUSION, "", "&b&oInfuse using the Altar of Infusion");

}
