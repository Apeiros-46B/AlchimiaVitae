package me.apeiros.alchimiavitae.listeners.infusion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InfusionFishingRodListener implements Listener {

    // Key
    private final NamespacedKey infusionSpikedHook = new NamespacedKey(AlchimiaVitae.i(), "infusion_spikedhook");
    private final NamespacedKey infusionKnockback = new NamespacedKey(AlchimiaVitae.i(), "infusion_knockback");

    // Constructor
    public InfusionFishingRodListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Fires when the hook hits an entity
    @EventHandler(ignoreCancelled = true)
    public void onHookHit(ProjectileHitEvent e) {
        // Check that the hit entity exists, the shooter exists, and the projectile is a FishHook
        if (e.getHitEntity() != null
                && e.getEntity().getShooter() != null
                && e.getEntity() instanceof FishHook) {
            // Variables
            Player p = (Player) e.getEntity().getShooter();
            FishHook proj = (FishHook) e.getEntity();
            Entity en = e.getHitEntity();

            // Check if the shooter has permission on the location
            if (SlimefunPlugin.getProtectionManager().hasPermission(p, en.getLocation(), ProtectableAction.ATTACK_ENTITY) ||
                SlimefunPlugin.getProtectionManager().hasPermission(p, en.getLocation(), ProtectableAction.ATTACK_PLAYER)) {

            }

            // Check if the spiked hook infusion exists on the projectile
            if (e.getEntity().getPersistentDataContainer().has(infusionSpikedHook, PersistentDataType.BYTE) && e.getHitEntity() instanceof LivingEntity) {
                // Variables
                LivingEntity len = (LivingEntity) en;
                double sum = Math.abs(proj.getVelocity().getX()) + Math.abs(proj.getVelocity().getY()) + Math.abs(proj.getVelocity().getZ());

                // Set health
                len.setHealth(len.getHealth() - Math.pow(sum * 1.1, 0.6));
            } else if (e.getEntity().getPersistentDataContainer().has(infusionKnockback, PersistentDataType.BYTE)) {
                // Deal knockback
                en.setVelocity(en.getVelocity()
                        .add(proj.getVelocity()
                        .multiply(2).setY(0))
                        .normalize());

                // Remove fishing hook and cancel event
                proj.setHookedEntity(null);
                proj.remove();
                e.setCancelled(true);
            }
        }
    }

    // Fires when the hook is thrown
    @EventHandler(ignoreCancelled = true)
    public void onHookThrow(ProjectileLaunchEvent e) {
        // Check if the projectile is a FishHook and if the shooter exists
        if (e.getEntity().getShooter() != null &&
                e.getEntity() instanceof FishHook) {
            // Variables
            Player p = (Player) e.getEntity().getShooter();
            FishHook f = (FishHook) e.getEntity();
            ItemStack rod = p.getInventory().getItemInMainHand();

            // Null check
            if (rod.getItemMeta() != null) {
                // Get PersistentDataContainer
                PersistentDataContainer pdc = rod.getItemMeta().getPersistentDataContainer();

                // Check if the fishing rod has infusion
                if (pdc.has(infusionSpikedHook, PersistentDataType.BYTE) || pdc.has(infusionKnockback, PersistentDataType.BYTE)) {
                    f.setVelocity(f.getVelocity().multiply(2));
                }
            }
        }
    }
}
