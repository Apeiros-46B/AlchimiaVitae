package me.apeiros.alchimiavitae.setup;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.SoulCollectorListener;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.CondensedSoul;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.setup.items.plants.evil.EvilMagicalPlant;
import me.apeiros.alchimiavitae.setup.items.plants.good.GoodMagicalPlant;
import me.apeiros.alchimiavitae.utils.Categories;

public class AlchimiaVitaeSetup {

    public static void setup(AlchimiaVitae plugin) {
        // Category
        Categories.MAIN.register(plugin);

        // Items
        new SoulCollector().register(plugin);
        new CondensedSoul().register(plugin);
        new PlantInfusionChamber().register(plugin);
        new GoodMagicalPlant().register(plugin);
        new EvilMagicalPlant().register(plugin);

        // Listeners
        new SoulCollectorListener(plugin);
    }

}
