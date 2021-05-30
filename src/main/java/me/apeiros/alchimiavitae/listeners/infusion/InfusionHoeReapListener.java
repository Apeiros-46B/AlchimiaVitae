package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class InfusionHoeReapListener implements Listener {

    private final NamespacedKey infusionAutoReplant = new NamespacedKey(AlchimiaVitae.i(), "infusion_autoreplant");

    @EventHandler(ignoreCancelled = true)
    public void onHarvestCrop(BlockBreakEvent e) {
        if (Tag.CROPS.isTagged(e.getBlock().getType())) {
            Player p = e.getPlayer();

            // Check if the tool has the Auto-Replant infusion
            if (p.getInventory().getItemInMainHand().getItemMeta().
                    getPersistentDataContainer().has(infusionAutoReplant, PersistentDataType.BYTE)) {
                // Spawn block
                e.getBlock().getWorld().spawnFallingBlock(e.getBlock().getLocation(), e.getBlock().getType().createBlockData());
                // Spawn particles
                e.getBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getBlock().getLocation(), 50, 1, 1, 1);
            }
        }
    }
}
