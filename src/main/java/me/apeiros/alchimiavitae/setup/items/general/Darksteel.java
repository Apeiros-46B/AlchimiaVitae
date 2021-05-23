package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Darksteel extends SlimefunItem {

    public Darksteel() {

        super(Categories.ALCHEMY, new SlimefunItemStack(AlchimiaVitaeItems.DARKSTEEL, 2), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1, AlchimiaVitaeItems.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1,
                AlchimiaVitaeItems.EVIL_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, AlchimiaVitaeItems.EVIL_MAGIC_PLANT,
                SlimefunItems.ENDER_LUMP_1, AlchimiaVitaeItems.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1
        });

    }

}
