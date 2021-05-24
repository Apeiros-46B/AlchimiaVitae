package me.apeiros.alchimiavitae.setup;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.listeners.EntityDeathListener;
import me.apeiros.alchimiavitae.setup.items.electric.DivineAltar;
import me.apeiros.alchimiavitae.setup.items.electric.EXPCrystallizer;
import me.apeiros.alchimiavitae.setup.items.electric.PlantInfusionChamber;
import me.apeiros.alchimiavitae.setup.items.general.*;
import me.apeiros.alchimiavitae.setup.items.plants.EvilMagicPlant;
import me.apeiros.alchimiavitae.setup.items.plants.GoodMagicPlant;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Setup {

    public static void setup(AlchimiaVitae plugin, Category c) {
        // Items
        new SoulCollector(c).register(plugin);
        new CondensedSoul(c).register(plugin);
        new PlantInfusionChamber(c).register(plugin);
        new GoodMagicPlant(c).register(plugin);
        new EvilMagicPlant(c).register(plugin);
        new EXPCrystallizer(c).register(plugin);
        new EXPCrystal(c).register(plugin);
        new Illumium(c).register(plugin);
        new Darksteel(c).register(plugin);
        new MoltenMysteryMetal(c).register(plugin);
        new MysteryMetal(c).register(plugin);

        // Divine Altar
        DivineAltar.RECIPES.put(new MultiInput(new ItemStack[]{
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
        }), new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2));

        new DivineAltar(c).register(plugin);

        // Items cont.d

        // Listeners
        new EntityDeathListener(plugin);
    }

}
