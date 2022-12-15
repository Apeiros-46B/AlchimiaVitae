package me.apeiros.alchimiavitae.setup;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.DyeListener;
import me.apeiros.alchimiavitae.listeners.infusion.AxeListener;
import me.apeiros.alchimiavitae.listeners.infusion.BowListener;
import me.apeiros.alchimiavitae.listeners.infusion.HoeListener;
import me.apeiros.alchimiavitae.listeners.infusion.RodListener;
import me.apeiros.alchimiavitae.listeners.infusion.TotemListener;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.MoltenMysteryMetal;
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
        // Register main item group
        Utils.ItemGroups.MAIN.register(p);

        // Items
        setupItems(p);

        // Listeners
        setupListeners(p);

        // Researches
        setupResearches(p);
    }
    // }}}

    // {{{ Items
    private static void setupItems(AlchimiaVitae p) {
        // Items
        // {{{ Soul Collector & Condensed Soul
        new SoulCollector(Utils.ItemGroups.GENERAL).register(p);

        CustomItemStack condensedSoulRecipeItem = new CustomItemStack(
                Material.DROWNED_SPAWN_EGG,
                "&bAny Mob",
                "&7Wither Skeletons and",
                "&7Withers have a chance",
                "&7to drop more souls...");

        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.CONDENSED_SOUL, Utils.RecipeTypes.SOUL_COLLECTOR_TYPE, new ItemStack[] {
                null, null, null,
                null, condensedSoulRecipeItem, null,
                null, null, null
        }).register(p);
        // }}}

        // {{{ Plants
        // Machine
        new PlantInfusionChamber(Utils.ItemGroups.GENERAL).register(p);

        // Plants
        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.LIGHT_MAGIC_PLANT, Utils.RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), SlimefunItems.MAGIC_LUMP_3, null,
                null, null, null,
                null, null, null
        }).register(p);

        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.DARK_MAGIC_PLANT, Utils.RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), Items.CONDENSED_SOUL, null,
                null, null, null,
                null, null, null
        }).register(p);

        // Essence
        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.LIGHT_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                Items.LIGHT_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.LIGHT_ESSENCE, 4)).register(p);

        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.DARK_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                Items.DARK_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.DARK_ESSENCE, 4)).register(p);
        // }}}

        // {{{ EXP Crystallizer
        new EXPCrystallizer(Utils.ItemGroups.GENERAL).register(p);

        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.EXP_CRYSTAL, Utils.RecipeTypes.EXP_CRYSTALLIZER_TYPE, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 4), null, null,
                null, null, null,
                null, null, null
        }).register(p);
        // }}}

        // {{{ Ingots
        // Illumium
        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.ILLUMIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1,
                Items.LIGHT_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.LIGHT_MAGIC_PLANT,
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1
        }, new SlimefunItemStack(Items.ILLUMIUM, 4)).register(p);

        // Darksteel
        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.DARKSTEEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1,
                Items.DARK_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.DARK_MAGIC_PLANT,
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1
        }, new SlimefunItemStack(Items.DARKSTEEL, 4)).register(p);
        // }}}

        // {{{ Divine Altar
        new DivineAltar(Utils.ItemGroups.GENERAL).register(p);
        new MoltenMysteryMetal(Utils.ItemGroups.GENERAL).register(p);

        new SlimefunItem(Utils.ItemGroups.GENERAL, Items.MYSTERY_METAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MOLTEN_MYSTERY_METAL, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.MYSTERY_METAL, 16)).register(p);
        // }}}

        // {{{ Cosmic Cauldron
        new CosmicCauldron(Utils.ItemGroups.GENERAL).register(p);
        new PotionOfOsmosis(Utils.ItemGroups.GENERAL).register(p);
        new BenevolentBrew(Utils.ItemGroups.GENERAL).register(p);
        new MalevolentConcoction(Utils.ItemGroups.GENERAL).register(p);
        // }}}

        // {{{ Altar of Infusion
        new AltarOfInfusion(Utils.ItemGroups.INFUSIONS).register(p);
        // }}}
    }
    // }}}

    // {{{ Listeners
    private static void setupListeners(AlchimiaVitae p) {
        new DyeListener(p);

        // Infusion listeners
        new AxeListener(p);
        new BowListener(p);
        new HoeListener(p);
        new TotemListener(p);
        new RodListener(p);
    }
    // }}}

    // {{{ Researches
    private static void setupResearches(AlchimiaVitae p) {
        new Research(AbstractAddon.createKey("soul"), 131072,
                "Manipulation of life force", 15)
                .addItems(Items.CONDENSED_SOUL, Items.SOUL_COLLECTOR)
                .register();

        new Research(AbstractAddon.createKey("magic_plants"), 131073,
                "Powerful plants", 20)
                .addItems(Items.PLANT_INFUSION_CHAMBER, Items.LIGHT_MAGIC_PLANT, Items.DARK_MAGIC_PLANT)
                .register();

        new Research(AbstractAddon.createKey("magic_essence"), 131074,
                "Powerful powder", 10)
                .addItems(Items.LIGHT_ESSENCE, Items.DARK_ESSENCE)
                .register();

        new Research(AbstractAddon.createKey("exp_crystals"), 131075,
                "Pure crystalline energy", 12)
                .addItems(Items.EXP_CRYSTALLIZER, Items.EXP_CRYSTAL)
                .register();

        new Research(AbstractAddon.createKey("magic_steel"), 131076,
                "Mystical metals", 16)
                .addItems(Items.DARKSTEEL, Items.ILLUMIUM)
                .register();

        new Research(AbstractAddon.createKey("divine_altar"), 131077,
                "The Ancient Altar's lost cousin", 36)
                .addItems(Items.DIVINE_ALTAR)
                .register();

        new Research(AbstractAddon.createKey("metal_amalgamation"), 131078,
                "Amalgam", 19)
                .addItems(Items.MOLTEN_MYSTERY_METAL, Items.MYSTERY_METAL)
                .register();

        new Research(AbstractAddon.createKey("cosmic_cauldron"), 131079,
                "Advanced brewery", 36)
                .addItems(Items.COSMIC_CAULDRON)
                .register();

        new Research(AbstractAddon.createKey("potion_of_osmosis"), 131080,
                "Absorbing and reflecting", 30)
                .addItems(Items.POTION_OF_OSMOSIS)
                .register();

        new Research(AbstractAddon.createKey("benevolent_brew"), 131081,
                "A blessing from Gaia herself", 20)
                .addItems(Items.BENEVOLENT_BREW)
                .register();

        new Research(AbstractAddon.createKey("malevolent_concoction"), 131082,
                "A demonic liquid", 20)
                .addItems(Items.MALEVOLENT_CONCOCTION)
                .register();

        new Research(AbstractAddon.createKey("altar_of_infusion"), 131083,
                "Infusion", 36)
                .addItems(Items.ALTAR_OF_INFUSION)
                .register();
    }
    // }}}

}
