package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class InfusionHoeReapListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onHarvestCrop(BlockBreakEvent e) {
        if (Tag.CROPS.isTagged(e.getBlock().getType())) {
            Player p = e.getPlayer();

            NamespacedKey nspk = new NamespacedKey(AlchimiaVitae.i(), "infused");

            if (p.getInventory().getItemInMainHand().getItemMeta().
                    getPersistentDataContainer().has(nspk, PersistentDataType.BYTE)) {
                // TODO: Add a function
            }
        }
    }
}
