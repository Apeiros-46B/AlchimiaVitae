package me.apeiros.alchimiavitae.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.inventory.PlayerInventory;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;

/**
 * {@link Listener} to prevent Condensed Souls from being used as sheep dye
 */
public class DyeListener implements Listener {

    public DyeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Handler to cancel dye (fires when a sheep is dyed)
    @EventHandler(ignoreCancelled = true)
    public void onDye(SheepDyeWoolEvent e) {
        PlayerInventory inventory = e.getPlayer().getInventory();

        // If player isn't holding a condensed soul in either hand, exit early
        if (!SlimefunUtils.isItemSimilar(inventory.getItemInMainHand(), AlchimiaItems.CONDENSED_SOUL, true))
            return;

        if (!SlimefunUtils.isItemSimilar(inventory.getItemInOffHand(), AlchimiaItems.CONDENSED_SOUL, true))
            return;

        // Cancel event
        e.setCancelled(true);
    }
    // }}}

}
