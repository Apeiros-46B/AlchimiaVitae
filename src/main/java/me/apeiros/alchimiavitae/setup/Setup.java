package me.apeiros.alchimiavitae.setup;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.SheepDyeListener;
import me.apeiros.alchimiavitae.listeners.infusion.InfusionAxeListener;
import me.apeiros.alchimiavitae.listeners.infusion.InfusionBowListener;
import me.apeiros.alchimiavitae.listeners.infusion.InfusionFishingRodListener;
import me.apeiros.alchimiavitae.listeners.infusion.InfusionHoeListener;
import me.apeiros.alchimiavitae.listeners.infusion.InfusionTotemListener;
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
import org.bukkit.NamespacedKey;

public class Setup {

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
        new SheepDyeListener(p);
        
        // Infusion listeners
        new InfusionAxeListener(p);
        new InfusionBowListener(p);
        new InfusionHoeListener(p);
        new InfusionTotemListener(p);
        new InfusionFishingRodListener(p);

        // Researches
        setupResearches(p);
    }

    public static void setupResearches(AlchimiaVitae p) {
        new Research(new NamespacedKey(p, "soul"), 131072,
                "Breaking the cycle of life and death", 25)
                .addItems(Items.CONDENSED_SOUL, Items.SOUL_COLLECTOR)
                .register();

        new Research(new NamespacedKey(p, "magic_plants"), 131073,
                "Two polar opposites", 30)
                .addItems(Items.PLANT_INFUSION_CHAMBER, Items.GOOD_MAGIC_PLANT, Items.EVIL_MAGIC_PLANT)
                .register();

        new Research(new NamespacedKey(p, "magic_essence"), 131074,
                "Grinding it down", 10)
                .addItems(Items.GOOD_ESSENCE, Items.EVIL_ESSENCE)
                .register();

        new Research(new NamespacedKey(p, "exp_crystals"), 131075,
                "Crystalline experience", 21)
                .addItems(Items.EXP_CRYSTALLIZER, Items.EXP_CRYSTAL)
                .register();

        new Research(new NamespacedKey(p, "magic_steel"), 131076,
                "Mystical metals", 16)
                .addItems(Items.DARKSTEEL, Items.ILLUMIUM)
                .register();

        new Research(new NamespacedKey(p, "divine_altar"), 131077,
                "The long-lost cousin of the Ancient Altar", 45)
                .addItems(Items.DIVINE_ALTAR)
                .register();

        new Research(new NamespacedKey(p, "metal_amalgamation"), 131078,
                "An amalgamation of metallic substances", 19)
                .addItems(Items.MOLTEN_MYSTERY_METAL, Items.MYSTERY_METAL)
                .register();

        new Research(new NamespacedKey(p, "ornate_cauldron"), 131079,
                "A contraption to brew advanced potions", 35)
                .addItems(Items.ORNATE_CAULDRON)
                .register();

        new Research(new NamespacedKey(p, "potion_of_osmosis"), 131080,
                "Absorbing and reflecting", 30)
                .addItems(Items.POTION_OF_OSMOSIS)
                .register();

        new Research(new NamespacedKey(p, "benevolent_brew"), 131081,
                "A blessing from Gaia herself", 35)
                .addItems(Items.BENEVOLENT_BREW)
                .register();

        new Research(new NamespacedKey(p, "malevolent_concoction"), 131082,
                "A substance with a slightly corrupted tinge", 35)
                .addItems(Items.MALEVOLENT_CONCOCTION)
                .register();

        new Research(new NamespacedKey(p, "altar_of_infusion"), 131083,
                "The ultimate altar to energize your items", 30)
                .addItems(Items.ALTAR_OF_INFUSION)
                .register();
    }
}
