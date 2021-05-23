package me.apeiros.alchimiavitae.setup;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.SoulCollectorListener;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.*;
import me.apeiros.alchimiavitae.setup.items.plants.evil.EvilMagicPlant;
import me.apeiros.alchimiavitae.setup.items.plants.good.GoodMagicPlant;
import me.apeiros.alchimiavitae.utils.Categories;

public class AlchimiaVitaeSetup {

    public static void setup(AlchimiaVitae plugin) {
        // Category
        Categories.MAIN.register(plugin);

        // Items
        new SoulCollector().register(plugin);
        new CondensedSoul().register(plugin);
        new PlantInfusionChamber().register(plugin);
        new GoodMagicPlant().register(plugin);
        new EvilMagicPlant().register(plugin);
        new EXPCrystallizer().register(plugin);
        new EXPCrystal().register(plugin);
        new Illumium().register(plugin);
        new Darksteel().register(plugin);
        new MoltenMysteryMetal().register(plugin);
        new MysteryMetal().register(plugin);
        new AlchemicReinforced().register(plugin);

        // Listeners
        new SoulCollectorListener(plugin);
    }

}
