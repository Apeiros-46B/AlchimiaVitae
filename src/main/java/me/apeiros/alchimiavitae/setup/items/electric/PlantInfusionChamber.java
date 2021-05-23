package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class PlantInfusionChamber extends AContainer implements RecipeDisplayItem {

    public PlantInfusionChamber() {

        super(Categories.PLANTS, AlchimiaVitaeItems.PLANT_INFUSION_CHAMBER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.ELECTRIC_PRESS,
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HEATED_PRESSURE_CHAMBER_2, SlimefunItems.HARDENED_GLASS,
                SlimefunItems.HEATING_COIL, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.HEATING_COIL
        });

        this.setProcessingSpeed(1).setCapacity(128).setEnergyConsumption(32);

    }

    public void registerDefaultRecipes() {

        this.registerRecipe(300,
                new ItemStack[]{
                        new ItemStack(Material.OAK_SAPLING),
                        SlimefunItems.MAGIC_LUMP_3},
                new ItemStack[]{
                        AlchimiaVitaeItems.GOOD_MAGIC_PLANT});

        this.registerRecipe(300,
                new ItemStack[]{
                        new ItemStack(Material.OAK_SAPLING),
                        AlchimiaVitaeItems.CONDENSED_SOUL},
                new ItemStack[]{
                        AlchimiaVitaeItems.EVIL_MAGIC_PLANT});

    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.GRASS);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "AV_PLANT_INFUSION_CHAMBER";
    }
}