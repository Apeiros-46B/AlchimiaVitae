package me.apeiros.alchimiavitae.setup.items.general;

import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class MysteryMetal extends SlimefunItem {

    public MysteryMetal() {

        super(Categories.ALCHEMY, new SlimefunItemStack(AlchimiaVitaeItems.MYSTERY_METAL, 16), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                AlchimiaVitaeItems.MOLTEN_MYSTERY_METAL, null, null,
                null, null, null,
                null, null, null
        });

    }

}
