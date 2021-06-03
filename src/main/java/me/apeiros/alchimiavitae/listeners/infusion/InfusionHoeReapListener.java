package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class InfusionHoeReapListener implements Listener {

    // Key
    private final NamespacedKey infusionAutoReplant = new NamespacedKey(AlchimiaVitae.i(), "infusion_autoreplant");

    // Constructor
    public InfusionHoeReapListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onHarvestCrop(BlockBreakEvent e) {
        // Check if the block broken is a crop
        if (Tag.CROPS.isTagged(e.getBlock().getType()) && e.getBlock().getBlockData() instanceof Ageable) {
            Player p = e.getPlayer();
            Ageable a = (Ageable) e.getBlock().getBlockData();

            // Check if the tool has the Auto-Replant infusion
            if (p.getInventory().getItemInMainHand().getItemMeta().
                    getPersistentDataContainer().has(infusionAutoReplant, PersistentDataType.BYTE)) {

                // Check if the crop is at maximum age
                if (a.getAge() == a.getMaximumAge()) {
                    // Spawn block
                    e.getBlock().setType(e.getBlock().getType());
                    e.getBlock().setBlockData(e.getBlock().getType().createBlockData());

                    // Spawn particles
                    e.getBlock().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getBlock().getLocation(), 50, 1, 1, 1);
                }
            }
        }
    }
}
