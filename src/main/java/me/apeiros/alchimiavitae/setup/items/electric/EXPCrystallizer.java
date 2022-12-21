package me.apeiros.alchimiavitae.setup.items.electric;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public class EXPCrystallizer extends AContainer implements RecipeDisplayItem {

    public EXPCrystallizer(ItemGroup ig) {
        super(ig, AlchimiaItems.EXP_CRYSTALLIZER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SlimefunItems.TALISMAN_WISE, SlimefunItems.MAGICAL_GLASS, SlimefunItems.TALISMAN_WISE,
                SlimefunItems.HARDENED_GLASS, SlimefunItems.EXP_COLLECTOR, SlimefunItems.HARDENED_GLASS,
                SlimefunItems.HEATING_COIL, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.HEATING_COIL
        });

        this.setProcessingSpeed(1).setCapacity(64).setEnergyConsumption(16);
    }

    // {{{ Register recipes
    public void registerDefaultRecipes() {
        this.registerRecipe(10,
            new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 2)
            },

            new ItemStack[] {
                new SlimefunItemStack(AlchimiaItems.EXP_CRYSTAL, 1),
                new SlimefunItemStack(SlimefunItems.FLASK_OF_KNOWLEDGE, 2)
            }
        );
    }
    // }}}

    // {{{ Other
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.EMERALD);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "AV_EXP_CRYSTALLIZER";
    }
    // }}}

}
