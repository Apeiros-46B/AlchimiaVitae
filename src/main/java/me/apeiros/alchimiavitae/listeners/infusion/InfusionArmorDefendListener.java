package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InfusionArmorDefendListener implements Listener {

    public InfusionArmorDefendListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            NamespacedKey nspk = new NamespacedKey(AlchimiaVitae.i(), "infused");
            boolean infused = false;
            for (ItemStack piece : p.getInventory().getArmorContents()) {
                if (piece.getItemMeta().getPersistentDataContainer().has(nspk, PersistentDataType.BYTE)) {
                    infused = true;
                } else {
                    infused = false;
                    break;
                }
            }

            if (infused) {
                // TODO: Add a function
            }
        }
    }
}
