package me.apeiros.alchimiavitae.setup;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
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
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
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
        setupDivineAltarRecipes();
        new DivineAltar(Categories.GENERAL).register(p);

        // Items cont.d
        new MoltenMysteryMetal(Categories.GENERAL).register(p);
        new MysteryMetal(Categories.GENERAL).register(p);
        new PotionOfOsmosis(Categories.GENERAL).register(p);

        // Ornate Cauldron
        setupOrnateCauldronRecipes();
        new OrnateCauldron(Categories.GENERAL).register(p);

        // Items cont.d
        new BenevolentBrew(Categories.GENERAL, p).register(p);


        // Listeners
        new EntityDeathListener(p);
    }

    private static void setupDivineAltarRecipes() {
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
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }), Items.MOLTEN_MYSTERY_METAL);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        }), Items.POTION_OF_OSMOSIS);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, new ItemStack(Material.LILAC), Items.EXP_CRYSTAL,
                Items.GOOD_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), Items.GOOD_ESSENCE,
                Items.ILLUMIUM, new ItemStack(Material.CORNFLOWER), Items.ILLUMIUM
        }), Items.BENEVOLENT_BREW);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        }), Items.ORNATE_CAULDRON);
    }

    private static void setupOrnateCauldronRecipes() {
        OrnateCauldron.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
                Items.GOOD_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
                Items.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY)
        }), Items.BENEVOLENT_BREW);
    }
}
