package me.apeiros.alchimiavitae.setup.items.electric;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public class PlantInfusionChamber extends AContainer implements RecipeDisplayItem {

    public PlantInfusionChamber(ItemGroup ig) {
        super(ig, AlchimiaItems.PLANT_INFUSION_CHAMBER, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.ELECTRIC_PRESS,
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HEATED_PRESSURE_CHAMBER_2, SlimefunItems.HARDENED_GLASS,
                SlimefunItems.HEATING_COIL, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.HEATING_COIL
        });

        this.setProcessingSpeed(1).setCapacity(128).setEnergyConsumption(32);
    }

    // {{{ Register recipes
    public void registerDefaultRecipes() {
        this.registerRecipe(60,
            new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING),
                SlimefunItems.MAGIC_LUMP_3
            },

            new ItemStack[] {
                AlchimiaItems.LIGHT_MAGIC_PLANT
            }
        );

        this.registerRecipe(60,
            new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING),
                AlchimiaItems.CONDENSED_SOUL
            },

            new ItemStack[] {
                AlchimiaItems.DARK_MAGIC_PLANT
            }
        );
    }
    // }}}

    // {{{ Other
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.GRASS);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "AV_PLANT_INFUSION_CHAMBER";
    }
    // }}}

}
