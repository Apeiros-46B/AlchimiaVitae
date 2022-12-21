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

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.DyeListener;
import me.apeiros.alchimiavitae.listeners.infusion.FishingRodListener;
import me.apeiros.alchimiavitae.listeners.infusion.HoeListener;
import me.apeiros.alchimiavitae.listeners.infusion.MeleeWeaponListener;
import me.apeiros.alchimiavitae.listeners.infusion.RangedWeaponListener;
import me.apeiros.alchimiavitae.listeners.infusion.TotemListener;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.MoltenMysteryMetal;
import me.apeiros.alchimiavitae.setup.items.general.SoulCollector;
import me.apeiros.alchimiavitae.setup.items.potions.BenevolentBrew;
import me.apeiros.alchimiavitae.setup.items.potions.MalevolentConcoction;
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfOsmosis;

/**
 * Sets up items, {@link Listener}s, and {@link Research}es
 */
public class Setup {

    // {{{ Main
    public static void setup(AlchimiaVitae instance) {
        // Register main item group
        AlchimiaUtils.ItemGroups.MAIN.register(instance);

        // Items
        setupItems(instance);

        // Listeners
        setupListeners(instance);

        // Researches
        setupResearches(instance);
    }
    // }}}

    // {{{ Items
    private static void setupItems(AlchimiaVitae instance) {
        // Items
        // {{{ Soul Collector & Condensed Soul
        new SoulCollector(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        CustomItemStack condensedSoulRecipeItem = new CustomItemStack(
                Material.DROWNED_SPAWN_EGG,
                "&bAny Mob",
                "&7Wither Skeletons and",
                "&7Withers have a chance",
                "&7to drop more souls...");

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.CONDENSED_SOUL, AlchimiaUtils.RecipeTypes.SOUL_COLLECTOR, new ItemStack[] {
                null, null, null,
                null, condensedSoulRecipeItem, null,
                null, null, null
        }).register(instance);
        // }}}

        // {{{ Plants
        // Machine
        new PlantInfusionChamber(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        // Plants
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.LIGHT_MAGIC_PLANT, AlchimiaUtils.RecipeTypes.PLANT_INFUSION_CHAMBER, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), SlimefunItems.MAGIC_LUMP_3, null,
                null, null, null,
                null, null, null
        }).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARK_MAGIC_PLANT, AlchimiaUtils.RecipeTypes.PLANT_INFUSION_CHAMBER, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), AlchimiaItems.CONDENSED_SOUL, null,
                null, null, null,
                null, null, null
        }).register(instance);

        // Essence
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.LIGHT_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                AlchimiaItems.LIGHT_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(AlchimiaItems.LIGHT_ESSENCE, 4)).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARK_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                AlchimiaItems.DARK_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(AlchimiaItems.DARK_ESSENCE, 4)).register(instance);
        // }}}

        // {{{ EXP Crystallizer
        new EXPCrystallizer(AlchimiaUtils.ItemGroups.GENERAL).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.EXP_CRYSTAL, AlchimiaUtils.RecipeTypes.EXP_CRYSTALLIZER, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 4), null, null,
                null, null, null,
                null, null, null
        }).register(instance);
        // }}}

        // {{{ Ingots
        // Illumium
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.ILLUMIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1, AlchimiaItems.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1,
                AlchimiaItems.LIGHT_ESSENCE, SlimefunItems.STEEL_INGOT, AlchimiaItems.LIGHT_ESSENCE,
                SlimefunItems.MAGIC_LUMP_1, AlchimiaItems.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1
        }, new SlimefunItemStack(AlchimiaItems.ILLUMIUM, 4)).register(instance);

        // Darksteel
        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.DARKSTEEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1, AlchimiaItems.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1,
                AlchimiaItems.DARK_ESSENCE, SlimefunItems.STEEL_INGOT, AlchimiaItems.DARK_ESSENCE,
                SlimefunItems.ENDER_LUMP_1, AlchimiaItems.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1
        }, new SlimefunItemStack(AlchimiaItems.DARKSTEEL, 4)).register(instance);
        // }}}

        // {{{ Divine Altar
        DivineAltar divineAltar = new DivineAltar(AlchimiaUtils.ItemGroups.GENERAL);
        divineAltar.register(instance);

        new MoltenMysteryMetal(AlchimiaUtils.ItemGroups.GENERAL, divineAltar).register(instance);

        new SlimefunItem(AlchimiaUtils.ItemGroups.GENERAL, AlchimiaItems.MYSTERY_METAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                AlchimiaItems.MOLTEN_MYSTERY_METAL, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(AlchimiaItems.MYSTERY_METAL, 16)).register(instance);
        // }}}

        // {{{ Cosmic Cauldron
        CosmicCauldron cauldron = new CosmicCauldron(AlchimiaUtils.ItemGroups.GENERAL, divineAltar);
        cauldron.register(instance);

        new PotionOfOsmosis(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        new BenevolentBrew(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        new MalevolentConcoction(AlchimiaUtils.ItemGroups.GENERAL, cauldron).register(instance);
        // }}}

        // {{{ Altar of Infusion
        new AltarOfInfusion(AlchimiaUtils.ItemGroups.INFUSIONS, divineAltar).register(instance);
        // }}}
    }
    // }}}

    // {{{ Listeners
    private static void setupListeners(AlchimiaVitae instance) {
        new DyeListener(instance);

        // Infusion listeners
        new MeleeWeaponListener(instance);
        new RangedWeaponListener(instance);
        new HoeListener(instance);
        new TotemListener(instance);
        new FishingRodListener(instance);
    }
    // }}}

    // {{{ Researches
    private static void setupResearches(AlchimiaVitae instance) {
        new Research(AbstractAddon.createKey("soul"), 131072,
                "Manipulation of life force", 15)
                .addItems(AlchimiaItems.CONDENSED_SOUL, AlchimiaItems.SOUL_COLLECTOR)
                .register();

        new Research(AbstractAddon.createKey("magic_plants"), 131073,
                "Powerful plants", 20)
                .addItems(AlchimiaItems.PLANT_INFUSION_CHAMBER, AlchimiaItems.LIGHT_MAGIC_PLANT, AlchimiaItems.DARK_MAGIC_PLANT)
                .register();

        new Research(AbstractAddon.createKey("magic_essence"), 131074,
                "Powerful powder", 10)
                .addItems(AlchimiaItems.LIGHT_ESSENCE, AlchimiaItems.DARK_ESSENCE)
                .register();

        new Research(AbstractAddon.createKey("exp_crystals"), 131075,
                "Pure crystalline energy", 12)
                .addItems(AlchimiaItems.EXP_CRYSTALLIZER, AlchimiaItems.EXP_CRYSTAL)
                .register();

        new Research(AbstractAddon.createKey("magic_steel"), 131076,
                "Mystical metals", 16)
                .addItems(AlchimiaItems.DARKSTEEL, AlchimiaItems.ILLUMIUM)
                .register();

        new Research(AbstractAddon.createKey("divine_altar"), 131077,
                "The Ancient Altar's lost cousin", 36)
                .addItems(AlchimiaItems.DIVINE_ALTAR)
                .register();

        new Research(AbstractAddon.createKey("metal_amalgamation"), 131078,
                "Amalgam", 19)
                .addItems(AlchimiaItems.MOLTEN_MYSTERY_METAL, AlchimiaItems.MYSTERY_METAL)
                .register();

        new Research(AbstractAddon.createKey("cosmic_cauldron"), 131079,
                "Advanced brewery", 36)
                .addItems(AlchimiaItems.COSMIC_CAULDRON)
                .register();

        new Research(AbstractAddon.createKey("potion_of_osmosis"), 131080,
                "Absorbing and reflecting", 30)
                .addItems(AlchimiaItems.POTION_OF_OSMOSIS)
                .register();

        new Research(AbstractAddon.createKey("benevolent_brew"), 131081,
                "A blessing from Gaia herself", 20)
                .addItems(AlchimiaItems.BENEVOLENT_BREW)
                .register();

        new Research(AbstractAddon.createKey("malevolent_concoction"), 131082,
                "A demonic liquid", 20)
                .addItems(AlchimiaItems.MALEVOLENT_CONCOCTION)
                .register();

        new Research(AbstractAddon.createKey("altar_of_infusion"), 131083,
                "Infusion", 36)
                .addItems(AlchimiaItems.ALTAR_OF_INFUSION)
                .register();
    }
    // }}}

}
