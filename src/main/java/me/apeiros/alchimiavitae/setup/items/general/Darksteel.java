package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Darksteel extends SlimefunItem {

    public Darksteel(Category c) {

        super(c, Items.DARKSTEEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1,
                Items.EVIL_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.EVIL_MAGIC_PLANT,
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1
        }, new SlimefunItemStack(Items.DARKSTEEL, 2));

    }

}
