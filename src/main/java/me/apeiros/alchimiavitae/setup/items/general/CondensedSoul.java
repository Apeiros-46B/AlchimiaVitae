package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CondensedSoul extends SimpleSlimefunItem<ItemUseHandler> {

    public CondensedSoul(ItemGroup c) {

        super(c, Items.CONDENSED_SOUL, Utils.RecipeTypes.SOUL_COLLECTOR_TYPE, new ItemStack[] {
                null, null, null,
                null, new CustomItemStack(Material.DROWNED_SPAWN_EGG, "&bAny Mob", "&7Wither Skeletons and", "&7Withers have a chance", "&7to drop more souls..."), null,
                null, null, null
        });

    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        // TODO: is this doing the work or is DyeListener.java doing it
        return PlayerRightClickEvent::cancel;
    }
}
