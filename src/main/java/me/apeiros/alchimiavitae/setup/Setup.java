package me.apeiros.alchimiavitae.setup;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.EntityDeathListener;
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
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfOsmosis;
import me.apeiros.alchimiavitae.utils.Categories;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Setup {

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
        setupDivineAltar(p);
        new DivineAltar(Categories.GENERAL).register(p);

        // Items cont.d
        new MoltenMysteryMetal(Categories.GENERAL).register(p);
        new MysteryMetal(Categories.GENERAL).register(p);

        // Ornate Cauldron
        setupOrnateCauldronRecipes();
        new OrnateCauldron(Categories.GENERAL).register(p);

        // Items cont.d
        new PotionOfOsmosis(Categories.GENERAL).register(p);
        new BenevolentBrew(Categories.GENERAL, p).register(p);

        // Listeners
        new EntityDeathListener(p);

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

        new Research(new NamespacedKey(p, "potion_of_osmosis"), 131079,
                "Osmosis and absorption", 30)
                .addItems(Items.POTION_OF_OSMOSIS)
                .register();

        new Research(new NamespacedKey(p, "benevolent_brew"), 131080,
                "A weak blessing from Gaia herself", 35)
                .addItems(Items.BENEVOLENT_BREW)
                .register();
    }

    private static void setupDivineAltar(AlchimiaVitae p) {
        // Add recipes to recipe map
        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
        }), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                null, SlimefunItems.STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.STEEL_INGOT, null
        }), new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 2));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.CARBON, null
        }), new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 8));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.COMPRESSED_CARBON, null
        }), new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 8));

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
        }), SlimefunItems.COMPRESSED_CARBON);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }), Items.MOLTEN_MYSTERY_METAL);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        }), Items.ORNATE_CAULDRON);

        // Setup recipes for already existent slimefun items in altar recipes category
        SlimefunItemStack item = new SlimefunItemStack("AV_REINFORCED_ALLOY_INGOT", Material.IRON_INGOT, "&b&lReinforced Alloy Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
        }, new SlimefunItemStack(item, 2)).register(p);

        item = new SlimefunItemStack("AV_HARDENED_METAL_INGOT", Material.IRON_INGOT, "&b&lHardened Metal");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                null, SlimefunItems.STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.STEEL_INGOT, null
        }, new SlimefunItemStack(item, 2)).register(p);

        item = new SlimefunItemStack("AV_STEEL_INGOT", Material.IRON_INGOT, "&bSteel Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.CARBON, null
        }, new SlimefunItemStack(item, 8)).register(p);

        item = new SlimefunItemStack("AV_DAMASCUS_STEEL_INGOT", Material.IRON_INGOT, "&bDamascus Steel Ingot");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                null, new ItemStack(Material.IRON_BLOCK), null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.COMPRESSED_CARBON, null
        }, new SlimefunItemStack(item, 8)).register(p);

        item = new SlimefunItemStack("AV_COMPRESSED_CARBON", HeadTexture.COMPRESSED_CARBON, "&cCompressed Carbon");

        new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
        }, item).register(p);

    }

    private static void setupOrnateCauldronRecipes() {
        // Add recipes to recipe map
        OrnateCauldron.RECIPES.put(new MultiInput(new ItemStack[]{
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
}
