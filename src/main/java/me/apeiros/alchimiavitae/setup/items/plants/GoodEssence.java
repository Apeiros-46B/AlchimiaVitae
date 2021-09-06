package me.apeiros.alchimiavitae.setup.items.plants;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.inventory.ItemStack;

public class GoodEssence extends SlimefunItem {

    public GoodEssence(ItemGroup c) {

        super(c, Items.GOOD_ESSENCE, RecipeType.GRIND_STONE, new ItemStack[] {
                Items.GOOD_MAGIC_PLANT, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.GOOD_ESSENCE, 4));

    }

}
