package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class InfusionAxeListener implements Listener {

    // Constructor
    public InfusionAxeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Main event
    @EventHandler(ignoreCancelled = true)
    public void onAxeHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p) {
            // Store the attacker and a ThreadLocalRandom in 3 variables
            ThreadLocalRandom r = ThreadLocalRandom.current();

            // Null check
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                // Assign value
                PersistentDataContainer container = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();

                // Check what infusion the axe has
            if (container.has(AltarOfInfusion.DESTRUCTIVE_CRITS, PersistentDataType.BYTE) && e.getEntity() instanceof Player victim && e.getEntity().getFallDistance() > 0) {
                    // Store the victim of the attack in a variable

                    // Damage armor
                    for (ItemStack d : victim.getInventory().getArmorContents()) {
                        if (d.getItemMeta() instanceof Damageable) {
                            ((Damageable) d.getItemMeta()).setDamage(r.nextInt(5));
                        }
                    }

                    // 1/5 chance to add slowness
                    if (r.nextInt(5) == 0) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1));
                    }

                    // 1/5 chance to add weakness
                    if (r.nextInt(5) == 0) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15, 1));
                    }

                    // 1/20 Chance to add brief mining fatigue
                    if (r.nextInt(20) == 0) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8, 3));
                    }
                } else if (container.has(AltarOfInfusion.PHANTOM_CRITS, PersistentDataType.BYTE) && e.getEntity() instanceof LivingEntity victim && e.getEntity().getFallDistance() > 0) {
                    // Store victim in a variable

                    // Set health
                    if (r.nextInt(4) == 0) {
                        victim.setHealth(victim.getHealth() - Math.pow(e.getFinalDamage(), 1.15) * 5 / 8);
                    }
                }
            }
        }
    }
}
