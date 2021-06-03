package me.apeiros.alchimiavitae.setup;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.EntityDeathListener;
import me.apeiros.alchimiavitae.listeners.infusion.*;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.crafters.OrnateCauldron;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.*;
import me.apeiros.alchimiavitae.setup.items.plants.EvilEssence;
import me.apeiros.alchimiavitae.setup.items.plants.EvilMagicPlant;
import me.apeiros.alchimiavitae.setup.items.plants.GoodEssence;
import me.apeiros.alchimiavitae.setup.items.plants.GoodMagicPlant;
import me.apeiros.alchimiavitae.setup.items.potions.BenevolentBrew;
import me.apeiros.alchimiavitae.setup.items.potions.MalevolentConcoction;
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfOsmosis;
import me.apeiros.alchimiavitae.utils.Categories;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Setup {

    private static final NamespacedKey axeInfusionDestructiveCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_destructivecrits");
    private static final NamespacedKey axeInfusionPhantomCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_phantomcrits");
    private static final NamespacedKey chestplateInfusionTotemBattery = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");
    private static final NamespacedKey bowInfusionTrueAim = new NamespacedKey(AlchimiaVitae.i(), "infusion_trueaim");
    private static final NamespacedKey bowInfusionForceful = new NamespacedKey(AlchimiaVitae.i(), "infusion_forceful");
    private static final NamespacedKey bowInfusionVolatile = new NamespacedKey(AlchimiaVitae.i(), "infusion_volatile");
    private static final NamespacedKey bowInfusionHealing = new NamespacedKey(AlchimiaVitae.i(), "infusion_healing");
    private static final NamespacedKey hoeInfusionAutoReplant = new NamespacedKey(AlchimiaVitae.i(), "infusion_autoreplant");

    public static void setup(AlchimiaVitae p) {
        // Category
        Categories.MAIN.register(p);

        // Items
        new SoulCollector(Categories.GENERAL).register(p);
        new CondensedSoul(Categories.GENERAL).register(p);
        new PlantInfusionChamber(Categories.GENERAL).register(p);
        new GoodMagicPlant(Categories.GENERAL).register(p);
        new EvilMagicPlant(Categories.GENERAL).register(p);
        new GoodEssence(Categories.GENERAL).register(p);
        new EvilEssence(Categories.GENERAL).register(p);
        new EXPCrystallizer(Categories.GENERAL).register(p);
        new EXPCrystal(Categories.GENERAL).register(p);
        new Illumium(Categories.GENERAL).register(p);
        new Darksteel(Categories.GENERAL).register(p);

        // Divine Altar
        new DivineAltar(Categories.GENERAL).register(p);
        setupDivineAltar(p);

        // Items cont.d
        new MoltenMysteryMetal(Categories.GENERAL).register(p);
        new MysteryMetal(Categories.GENERAL).register(p);

        // Ornate Cauldron
        new OrnateCauldron(Categories.GENERAL).register(p);
        setupOrnateCauldron();

        // Items cont.d
        new PotionOfOsmosis(Categories.GENERAL).register(p);
        new BenevolentBrew(Categories.GENERAL, p).register(p);
        new MalevolentConcoction(Categories.GENERAL, p).register(p);

        // Altar of Infusion
        new AltarOfInfusion(Categories.INFUSIONS).register(p);
        setupInfusionAltar(p);

        // Listeners
        new EntityDeathListener(p);
        new InfusionAxeAttackListener(p);
        new InfusionBowShootListener(p);
        new InfusionHoeReapListener(p);
        new InfusionTotemRightClickEvent(p);
        new InfusionTotemStorageDeathListener(p);

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
                "Osmosis and absorption", 30)
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

    private static void setupDivineAltar(AlchimiaVitae p) {
        // Add recipes to recipe map
        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
        }), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                null, SlimefunItems.STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.STEEL_INGOT, null
        }), new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 2));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.CARBON, null
        }), new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 8));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.COMPRESSED_CARBON, null
        }), new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 8));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
        }), SlimefunItems.COMPRESSED_CARBON);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }), Items.MOLTEN_MYSTERY_METAL);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        }), Items.ORNATE_CAULDRON);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, Items.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, Items.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
        }), Items.ALTAR_OF_INFUSION);

        // Setup recipes for already existent slimefun items in altar recipes category
        SlimefunItemStack item = new SlimefunItemStack("AV_REINFORCED_ALLOY_INGOT", Material.IRON_INGOT, "&b&lReinforced Alloy Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
        }, new SlimefunItemStack(item, 2)).register(p);

        item = new SlimefunItemStack("AV_HARDENED_METAL_INGOT", Material.IRON_INGOT, "&b&lHardened Metal");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                null, SlimefunItems.STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.STEEL_INGOT, null
        }, new SlimefunItemStack(item, 2)).register(p);

        item = new SlimefunItemStack("AV_STEEL_INGOT", Material.IRON_INGOT, "&bSteel Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.CARBON, null
        }, new SlimefunItemStack(item, 8)).register(p);

        item = new SlimefunItemStack("AV_DAMASCUS_STEEL_INGOT", Material.IRON_INGOT, "&bDamascus Steel Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.COMPRESSED_CARBON, null
        }, new SlimefunItemStack(item, 8)).register(p);

        item = new SlimefunItemStack("AV_COMPRESSED_CARBON", HeadTexture.COMPRESSED_CARBON, "&cCompressed Carbon");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
        }, item).register(p);

    }

    private static void setupOrnateCauldron() {
        // Add recipes to recipe map
        OrnateCauldron.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        }), Items.POTION_OF_OSMOSIS);

        OrnateCauldron.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
                Items.GOOD_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
                Items.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY)
        }), Items.BENEVOLENT_BREW);
    }

    private static void setupInfusionAltar(AlchimiaVitae p) {
        // Add recipes to recipe map
        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                Items.DARKSTEEL, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
        }), axeInfusionDestructiveCrits);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                Items.DARKSTEEL, SlimefunItems.HARDENED_GLASS,
                new ItemStack(Material.PHANTOM_MEMBRANE), Items.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
        }), axeInfusionPhantomCrits);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                SlimefunItems.NECROTIC_SKULL, Items.CONDENSED_SOUL, Items.BENEVOLENT_BREW,
                Items.ILLUMIUM, Items.EXP_CRYSTAL,
                new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
        }), chestplateInfusionTotemBattery);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                Items.DARKSTEEL, Items.EXP_CRYSTAL,
                new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.REINFORCED_ALLOY_JETPACK
        }), bowInfusionTrueAim);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                SlimefunItems.INFUSED_MAGNET, SlimefunItems.REINFORCED_ALLOY_JETBOOTS,
                SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
        }), bowInfusionForceful);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                Items.DARKSTEEL, SlimefunItems.LAVA_GENERATOR_2,
                new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
        }), bowInfusionVolatile);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                Items.ILLUMIUM, new ItemStack(Material.TOTEM_OF_UNDYING),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
        }), bowInfusionHealing);

        AltarOfInfusion.RECIPES.put(new MultiInput(new ItemStack[] {
                new ItemStack(Material.COMPOSTER), Items.GOOD_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                Items.ILLUMIUM, SlimefunItems.FLUID_PUMP,
                new ItemStack(Material.BONE_BLOCK), Items.GOOD_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
        }), hoeInfusionAutoReplant);

        // Setup display recipes for infusions
        CustomItem validInfuseAxe = new CustomItem(Material.DIAMOND_AXE, "&b&lA valid axe to infuse", "&aa &6gold&a, &firon&a, &bdiamond&a,", "&aor &cnetherite &aaxe will do");
        CustomItem validInfuseChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&b&lA valid chestplate to infuse", "&aa &6gold&a, &firon&a, &bdiamond&a,", "&aor &cnetherite &achestplate will do");
        CustomItem validInfuseBow = new CustomItem(Material.BOW, "&b&lA valid bow to infuse", "&aA bow or crossbow will do");
        CustomItem validInfuseHoe = new CustomItem(Material.DIAMOND_HOE, "&b&lA valid hoe to infuse", "&aa &6gold&a, &firon&a, &bdiamond&a,", "&aor &cnetherite &ahoe will do");

        SlimefunItemStack item = new SlimefunItemStack("AV_DESTRUCTIVE_CRITS_INFUSION", Material.TNT, "&c&lDestructive Criticals",
                "&41/20 chance to give opponent Mining Fatigue III for 8 seconds on crit",
                "&41/5 chance to give opponent Slowness I for 15 seconds on crit",
                "&41/5 chance to give opponent Weakness I for 15 seconds on crit",
                "&4Deals 0-5 extra damage to opponent's armor on crit");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                Items.DARKSTEEL, validInfuseAxe, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
        }, item).register(p);

        item = new SlimefunItemStack("AV_PHANTOM_CRITS_INFUSION", Material.PHANTOM_MEMBRANE, "&bPhantom Criticals",
                "&a1/4 chance to deal (your attack damage to the power of 1.15",
                "&amultiplied by 5/8) extra damage on a crit, bypassing armor");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                Items.DARKSTEEL, validInfuseAxe, SlimefunItems.HARDENED_GLASS,
                new ItemStack(Material.PHANTOM_MEMBRANE), Items.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
        }, item).register(p);

        item = new SlimefunItemStack("AV_TOTEM_BATTERY_INFUSION", Material.TOTEM_OF_UNDYING, "&6&lTotem Battery",
                "&eA built-in pocket dimension that holds the energy", "&eof up to 8 Totems of Undying",
                "&6Store a Totem in this apparatus", "&6by &e&lShift-Right-Clicking &6with a Totem in the hand",
                "&6while the infused chestplate is worn");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                SlimefunItems.NECROTIC_SKULL, Items.CONDENSED_SOUL, Items.BENEVOLENT_BREW,
                Items.ILLUMIUM, validInfuseChestplate, Items.EXP_CRYSTAL,
                new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
        }, item).register(p);

        item = new SlimefunItemStack("AV_TRUE_AIM_INFUSION", Material.SHULKER_SHELL, "&dTrue Aim",
                "&5Partially using the levitation charm", "&5Shulkers use to terminate their victims,",
                "&5a bow infused with this magic can fire", "&5arrows that are not affected by gravity");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                Items.DARKSTEEL, validInfuseBow, Items.EXP_CRYSTAL,
                new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.REINFORCED_ALLOY_JETPACK
        }, item).register(p);

        item = new SlimefunItemStack("AV_FORCEFUL_INFUSION", Material.PISTON, "&2Forceful",
                "&aThis infusion uses mechanical", "&adevices and electromagnets to accelerate",
                "&aprojectiles to blistering speeds", "&aArrows will travel 2x farther and faster");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                SlimefunItems.INFUSED_MAGNET, validInfuseBow, SlimefunItems.REINFORCED_ALLOY_JETBOOTS,
                SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
        }, item).register(p);

        item = new SlimefunItemStack("AV_VOLATILE_INFUSION", Material.FIRE_CHARGE, "&4&lVolatility",
                "&cThis extremely dangerous infusion creates", "&cspheres made of pure superheated lava,",
                "&cdelivering a mini-inferno to the target", "&41/7 chance to fire a large fireball",
                "&46/7 chance to fire a small fireball");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                Items.DARKSTEEL, validInfuseBow, SlimefunItems.LAVA_GENERATOR_2,
                new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
        }, item).register(p);

        item = new SlimefunItemStack("AV_HEALING_INFUSION", Material.REDSTONE, "&cHealing",
                "&cThis infusion will heal hit entities", " &cand recover their &4health", "" +
                "&aHeals for the same amount that a bow shot would damage");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                Items.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                Items.ILLUMIUM, validInfuseBow, new ItemStack(Material.TOTEM_OF_UNDYING),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
        }, item).register(p);

        item = new SlimefunItemStack("AV_AUTO_REPLANT_INFUSION", Material.WHEAT, "&aAutomatic Re-plant",
                "&2Any fully-grown crops broken",
                "&2with a hoe infused with this", "&2will &aautomatically &2be replanted");

        new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                new ItemStack(Material.COMPOSTER), Items.GOOD_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                Items.ILLUMIUM, validInfuseHoe, SlimefunItems.FLUID_PUMP,
                new ItemStack(Material.BONE_BLOCK), Items.GOOD_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
        }, item).register(p);
    }
}
