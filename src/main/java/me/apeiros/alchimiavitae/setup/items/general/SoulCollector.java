package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SoulCollector extends SlimefunItem {

    public SoulCollector(ItemGroup c) {

        super(c, Items.SOUL_COLLECTOR, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.EARTH_RUNE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.AIR_RUNE,
                SlimefunItems.WATER_RUNE, SlimefunItems.NECROTIC_SKULL, SlimefunItems.FIRE_RUNE,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ESSENCE_OF_AFTERLIFE
        });

    }

}
