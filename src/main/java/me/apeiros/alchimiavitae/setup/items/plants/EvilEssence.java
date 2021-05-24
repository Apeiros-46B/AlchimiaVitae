package me.apeiros.alchimiavitae.setup.items.plants;

import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class EvilEssence extends SlimefunItem {

    public EvilEssence(Category c) {

        super(c, Items.EVIL_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                Items.EVIL_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.EVIL_ESSENCE, 4));

    }

}
