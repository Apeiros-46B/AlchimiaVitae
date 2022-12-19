package me.apeiros.alchimiavitae.setup.items.potions;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;

/**
 * Shared superclass which {@link AlchimiaVitae}'s
 * custom potions inherit from
 */
abstract class AbstractListenerPotion extends SlimefunItem implements Listener {

    public AbstractListenerPotion(ItemGroup ig, SlimefunItemStack item, RecipeType rt, ItemStack[] recipe, CosmicCauldron cauldron) {
        super(ig, item, rt, recipe);

        // Register handler
        AlchimiaVitae instance = AlchimiaVitae.i();
        instance.getServer().getPluginManager().registerEvents(this, instance);

		// Add recipe to Cosmic Cauldron
		cauldron.newRecipe(item, recipe);
    }

    // Check if an item is similar to this item
    protected boolean isSimilar(ItemStack item) {
        return SlimefunUtils.isItemSimilar(this.getItem(), item, true, false);
    }

}
