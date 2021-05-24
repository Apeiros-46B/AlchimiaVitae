package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SoulCollector extends SlimefunItem {

    public SoulCollector(Category c) {

        super(c, Items.SOUL_COLLECTOR, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.EARTH_RUNE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.AIR_RUNE,
                SlimefunItems.WATER_RUNE, SlimefunItems.NECROTIC_SKULL, SlimefunItems.FIRE_RUNE,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ESSENCE_OF_AFTERLIFE
        });

    }

}
