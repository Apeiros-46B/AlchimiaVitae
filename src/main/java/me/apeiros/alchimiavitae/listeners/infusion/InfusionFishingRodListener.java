package me.apeiros.alchimiavitae.listeners.infusion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InfusionFishingRodListener implements Listener {

    // Constructor
    public InfusionFishingRodListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Fires when the hook hits an entity
    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent e) {
        FishHook proj = e.getHook();
        Entity en = proj.getHookedEntity();

        if (e.getHook().getPersistentDataContainer().has(AltarOfInfusion.SPIKED_HOOK, PersistentDataType.BYTE) && en instanceof LivingEntity) {
            if (en instanceof Player) {
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), en.getLocation(), ProtectableAction.ATTACK_PLAYER)) {
                    // Variables
                    LivingEntity len = (LivingEntity) en;

                    // Set health
                    len.setHealth(len.getHealth() - Math.pow(2 * 1.1, 0.3));
                    Bukkit.getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(e.getPlayer(), en, EntityDamageEvent.DamageCause.CUSTOM, Math.pow(2 * 1.1, 0.3)));

                    // Remove fishing hook and cancel event
                    proj.setHookedEntity(null);
                    proj.remove();
                    e.setCancelled(true);
                }
            } else {
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), en.getLocation(), ProtectableAction.ATTACK_ENTITY)) {
                    // Variables
                    LivingEntity len = (LivingEntity) en;

                    // Set health
                    len.setHealth(len.getHealth() - Math.pow(2 * 1.1, 0.3));
                    Bukkit.getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(e.getPlayer(), en, EntityDamageEvent.DamageCause.CUSTOM, Math.pow(2 * 1.1, 0.3)));

                    // Remove fishing hook and cancel event
                    proj.setHookedEntity(null);
                    proj.remove();
                    e.setCancelled(true);
                }
            }
        } else if (proj.getPersistentDataContainer().has(AltarOfInfusion.KNOCKBACK, PersistentDataType.BYTE) && en != null) {
            if (en instanceof Player) {
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), en.getLocation(), ProtectableAction.ATTACK_PLAYER)) {
                    // Deal knockback
                    en.setVelocity(en.getVelocity().add(e.getPlayer().getEyeLocation().getDirection().setY(0).multiply(3).normalize()));

                    // Remove fishing hook and cancel event
                    proj.setHookedEntity(null);
                    proj.remove();
                    e.setCancelled(true);
                }
            } else {
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), en.getLocation(), ProtectableAction.ATTACK_ENTITY)) {
                    // Deal knockback
                    en.setVelocity(en.getVelocity().add(e.getPlayer().getEyeLocation().getDirection().setY(0).multiply(3).normalize()));

                    // Remove fishing hook and cancel event
                    proj.setHookedEntity(null);
                    proj.remove();
                    e.setCancelled(true);
                }
            }
        }
    }

    // Fires when the hook is thrown
    @EventHandler(ignoreCancelled = true)
    public void onHookThrow(ProjectileLaunchEvent e) {
        // Check if the projectile is a FishHook and if the shooter exists
        if (e.getEntity().getShooter() != null && e.getEntity() instanceof FishHook) {
            // Variables
            Player p = (Player) e.getEntity().getShooter();
            FishHook f = (FishHook) e.getEntity();
            ItemStack rod = p.getInventory().getItemInMainHand();

            // Null check
            if (rod.getItemMeta() != null) {
                // Get PersistentDataContainer
                PersistentDataContainer pdc = rod.getItemMeta().getPersistentDataContainer();

                // Check if the fishing rod has infusion
                if (pdc.has(AltarOfInfusion.SPIKED_HOOK, PersistentDataType.BYTE)) {
                    f.getPersistentDataContainer().set(AltarOfInfusion.SPIKED_HOOK, PersistentDataType.BYTE, (byte) 1);
                    f.setVelocity(f.getVelocity().multiply(2));
                } else if (pdc.has(AltarOfInfusion.KNOCKBACK, PersistentDataType.BYTE)) {
                    f.getPersistentDataContainer().set(AltarOfInfusion.KNOCKBACK, PersistentDataType.BYTE, (byte) 1);
                    f.setVelocity(f.getVelocity().multiply(2));
                }
            }
        }
    }
}
