package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class InfusionSwordListener implements Listener {

    // Key

    // Constructor
    public InfusionSwordListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof HumanEntity) {
            // Generate a random number
            ThreadLocalRandom r = ThreadLocalRandom.current();
            int rNum = r.nextInt(4);

            // Conditions for disabling shield
            if (((HumanEntity) e.getEntity()).isBlocking() && ((Player) e.getDamager()).isSprinting() && rNum <= 2) {
                // Disable shield
                ((HumanEntity) e.getEntity()).setCooldown(Material.SHIELD, 100);
            } else if (((HumanEntity) e.getEntity()).isBlocking() && rNum == 0) {
                // Disable shield
                ((HumanEntity) e.getEntity()).setCooldown(Material.SHIELD, 100);
            }
        }
    }
}
