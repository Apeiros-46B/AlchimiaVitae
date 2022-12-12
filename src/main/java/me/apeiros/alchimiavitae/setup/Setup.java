package me.apeiros.alchimiavitae.setup;

import org.bukkit.event.Listener;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.DyeListener;
import me.apeiros.alchimiavitae.listeners.infusion.AxeListener;
import me.apeiros.alchimiavitae.listeners.infusion.BowListener;
import me.apeiros.alchimiavitae.listeners.infusion.HoeListener;
import me.apeiros.alchimiavitae.listeners.infusion.RodListener;
import me.apeiros.alchimiavitae.listeners.infusion.TotemListener;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.crafters.OrnateCauldron;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.CondensedSoul;
import me.apeiros.alchimiavitae.setup.items.general.Darksteel;
import me.apeiros.alchimiavitae.setup.items.general.EXPCrystal;
import me.apeiros.alchimiavitae.setup.items.general.Illumium;
import me.apeiros.alchimiavitae.setup.items.general.MoltenMysteryMetal;
import me.apeiros.alchimiavitae.setup.items.general.MysteryMetal;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.setup.items.plants.EvilEssence;
import me.apeiros.alchimiavitae.setup.items.plants.EvilMagicPlant;
import me.apeiros.alchimiavitae.setup.items.plants.GoodEssence;
import me.apeiros.alchimiavitae.setup.items.plants.GoodMagicPlant;
import me.apeiros.alchimiavitae.setup.items.potions.BenevolentBrew;
import me.apeiros.alchimiavitae.setup.items.potions.MalevolentConcoction;
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfOsmosis;
import me.apeiros.alchimiavitae.utils.Utils;

/**
 * Sets up items, {@link Listener}s, and {@link Research}es
 */
public class Setup {

    // {{{ Main
    public static void setup(AlchimiaVitae p) {
        // Category
        Utils.ItemGroups.MAIN.register(p);

        // Items
        new SoulCollector(Utils.ItemGroups.GENERAL).register(p);
        new CondensedSoul(Utils.ItemGroups.GENERAL).register(p);
        new PlantInfusionChamber(Utils.ItemGroups.GENERAL).register(p);
        new GoodMagicPlant(Utils.ItemGroups.GENERAL).register(p);
        new EvilMagicPlant(Utils.ItemGroups.GENERAL).register(p);
        new GoodEssence(Utils.ItemGroups.GENERAL).register(p);
        new EvilEssence(Utils.ItemGroups.GENERAL).register(p);
        new EXPCrystallizer(Utils.ItemGroups.GENERAL).register(p);
        new EXPCrystal(Utils.ItemGroups.GENERAL).register(p);
        new Illumium(Utils.ItemGroups.GENERAL).register(p);
        new Darksteel(Utils.ItemGroups.GENERAL).register(p);
        new DivineAltar(Utils.ItemGroups.GENERAL).register(p);
        new MoltenMysteryMetal(Utils.ItemGroups.GENERAL).register(p);
        new MysteryMetal(Utils.ItemGroups.GENERAL).register(p);
        new OrnateCauldron(Utils.ItemGroups.GENERAL).register(p);
        new PotionOfOsmosis(Utils.ItemGroups.GENERAL).register(p);
        new BenevolentBrew(Utils.ItemGroups.GENERAL).register(p);
        new MalevolentConcoction(Utils.ItemGroups.GENERAL).register(p);
        new AltarOfInfusion(Utils.ItemGroups.INFUSIONS).register(p);

        // Listeners
        new DyeListener(p);

        // Infusion listeners
        new AxeListener(p);
        new BowListener(p);
        new HoeListener(p);
        new TotemListener(p);
        new RodListener(p);

        // Researches
        setupResearches(p);
    }
    // }}}

    // {{{ Researches
    public static void setupResearches(AlchimiaVitae p) {
        new Research(AbstractAddon.createKey("soul"), 131072,
                "Extraction of life", 25)
                .addItems(Items.CONDENSED_SOUL, Items.SOUL_COLLECTOR)
                .register();

        new Research(AbstractAddon.createKey("magic_plants"), 131073,
                "Two polar opposites", 30) // TODO: Make a word which is like photosynthesis but moon instead of sun
                .addItems(Items.PLANT_INFUSION_CHAMBER, Items.GOOD_MAGIC_PLANT, Items.EVIL_MAGIC_PLANT)
                .register();

        new Research(AbstractAddon.createKey("magic_essence"), 131074,
                "Powerful powder", 10)
                .addItems(Items.GOOD_ESSENCE, Items.EVIL_ESSENCE)
                .register();

        new Research(AbstractAddon.createKey("exp_crystals"), 131075,
                "Pure crystalline energy", 21)
                .addItems(Items.EXP_CRYSTALLIZER, Items.EXP_CRYSTAL)
                .register();

        new Research(AbstractAddon.createKey("magic_steel"), 131076,
                "Mystical metals", 16)
                .addItems(Items.DARKSTEEL, Items.ILLUMIUM)
                .register();

        new Research(AbstractAddon.createKey("divine_altar"), 131077,
                "The Ancient Altar's lost cousin", 45)
                .addItems(Items.DIVINE_ALTAR)
                .register();

        new Research(AbstractAddon.createKey("metal_amalgamation"), 131078,
                "Amalgam", 19)
                .addItems(Items.MOLTEN_MYSTERY_METAL, Items.MYSTERY_METAL)
                .register();

        new Research(AbstractAddon.createKey("ornate_cauldron"), 131079,
                "Advanced brewery", 35)
                .addItems(Items.ORNATE_CAULDRON)
                .register();

        new Research(AbstractAddon.createKey("potion_of_osmosis"), 131080,
                "Absorbing and reflecting", 30)
                .addItems(Items.POTION_OF_OSMOSIS)
                .register();

        new Research(AbstractAddon.createKey("benevolent_brew"), 131081,
                "A blessing from Gaia herself", 35)
                .addItems(Items.BENEVOLENT_BREW)
                .register();

        new Research(AbstractAddon.createKey("malevolent_concoction"), 131082,
                "A red liquid form the underworld", 35)
                .addItems(Items.MALEVOLENT_CONCOCTION)
                .register();

        new Research(AbstractAddon.createKey("altar_of_infusion"), 131083,
                "Infusion", 30)
                .addItems(Items.ALTAR_OF_INFUSION)
                .register();
    }
    // }}}

}
