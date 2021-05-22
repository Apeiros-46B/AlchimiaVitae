package me.apeiros.alchimiavitae.setup;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.SoulCollectorListener;
import me.apeiros.alchimiavitae.setup.items.electric.AdvancedBrewingChamber;
import me.apeiros.alchimiavitae.setup.items.general.CondensedSoul;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.utils.Categories;

public class AlchimiaVitaeSetup {

    public static void setup(AlchimiaVitae plugin) {
        // Category
        Categories.MAIN.register(plugin);

        // Items
        new SoulCollector().register(plugin);
        new CondensedSoul().register(plugin);
        new AdvancedBrewingChamber().setCapacity(512).setEnergyConsumption(64).register(plugin);

        // Listeners
        new SoulCollectorListener(plugin);
    }

}
