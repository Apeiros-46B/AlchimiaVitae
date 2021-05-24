package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CauldronOfVitality extends AbstractContainer {
    public CauldronOfVitality(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @NotNull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    protected void tick(@NotNull Block block) {

    }

    @Override
    protected void setupMenu(@NotNull BlockMenuPreset blockMenuPreset) {

    }
}
