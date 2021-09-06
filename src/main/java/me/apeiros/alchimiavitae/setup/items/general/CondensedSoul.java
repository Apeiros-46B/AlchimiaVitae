package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CondensedSoul extends SlimefunItem {

    public CondensedSoul(ItemGroup c) {

        super(c, Items.CONDENSED_SOUL, RecipeTypes.SOUL_COLLECTOR_TYPE, new ItemStack[] {
                null, null, null,
                null, new CustomItemStack(Material.DROWNED_SPAWN_EGG, "&bAny Mob"), null,
                null, null, null
        });

    }

}
