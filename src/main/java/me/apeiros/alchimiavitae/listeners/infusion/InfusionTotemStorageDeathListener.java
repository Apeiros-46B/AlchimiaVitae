package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.EntityEffect;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfusionTotemStorageDeathListener implements Listener {

    // Key
    private final NamespacedKey infusionTotemStorage = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");

    // Constructor
    public InfusionTotemStorageDeathListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID && e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (p.getInventory().getChestplate() != null) {
                PersistentDataContainer container = p.getInventory().getChestplate().getItemMeta().getPersistentDataContainer();

                if (container.has(infusionTotemStorage, PersistentDataType.INTEGER)) {
                    if (container.get(infusionTotemStorage, PersistentDataType.INTEGER) > 0) {
                        if (p.getHealth() - e.getFinalDamage() <= 0) {
                            // The amount of totems stored in the chestplate
                            int totemsStored = container.get(infusionTotemStorage, PersistentDataType.INTEGER);

                            // Cancel the damage event
                            e.setCancelled(true);

                            // Decrement the totemsStored variable and set it to the container
                            totemsStored--;
                            container.set(infusionTotemStorage, PersistentDataType.INTEGER, totemsStored);

                            // Add potion effects and heal by half a heart
                            p.setHealth(p.getHealth() + 1);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 5, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 45, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1));

                            // Totem visual effects
                            p.playEffect(EntityEffect.TOTEM_RESURRECT);
                        }
                    }
                }
            }
        }
    }
}
