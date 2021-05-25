package me.apeiros.alchimiavitae.setup;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.EntityDeathListener;
import me.apeiros.alchimiavitae.setup.items.electric.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.*;
import me.apeiros.alchimiavitae.setup.items.plants.EvilEssence;
import me.apeiros.alchimiavitae.setup.items.plants.EvilMagicPlant;
import me.apeiros.alchimiavitae.setup.items.plants.GoodEssence;
import me.apeiros.alchimiavitae.setup.items.plants.GoodMagicPlant;
import me.apeiros.alchimiavitae.setup.items.potions.PotionOfExchange;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Setup {

    public static void setup(AlchimiaVitae p, Category c) {
        // Items
        new SoulCollector(c).register(p);
        new CondensedSoul(c).register(p);
        new PlantInfusionChamber(c).register(p);
        new GoodMagicPlant(c).register(p);
        new EvilMagicPlant(c).register(p);
        new GoodEssence(c).register(p);
        new EvilEssence(c).register(p);
        new EXPCrystallizer(c).register(p);
        new EXPCrystal(c).register(p);
        new Illumium(c).register(p);
        new Darksteel(c).register(p);

        setupDivineAltarRecipes();
        new DivineAltar(c).register(p);

        new MoltenMysteryMetal(c).register(p);
        new MysteryMetal(c).register(p);
        new PotionOfExchange(c, p).register(p);

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

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }), Items.MOLTEN_MYSTERY_METAL);

        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[] {
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }), Items.POTION_OF_EXCHANGE);
    }

}
