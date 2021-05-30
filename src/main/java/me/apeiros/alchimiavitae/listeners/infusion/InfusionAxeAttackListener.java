package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

public class InfusionAxeAttackListener implements Listener {

    // TODO: Destructive Criticals Infusion (Extra armor and shield damage on crits)
    // TODO: Phantom Criticals Infusion (Chance to partially bypass shield or armor on a crit)

    // Keys
    private final NamespacedKey infusionDestructiveCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_destructivecrits");
    private final NamespacedKey infusionPhantomCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_phantomcrits");

    // Constructor
    public InfusionAxeAttackListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Main event
    @EventHandler(ignoreCancelled = true)
    public void onAxeHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            // Store the attacker, the weapon's PDC, and a ThreadLocalRandom in 3 variables
            Player p = (Player) e.getDamager();
            PersistentDataContainer container = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            ThreadLocalRandom r = ThreadLocalRandom.current();

            // Check what infusion the axe has
            if (container.has(infusionDestructiveCrits, PersistentDataType.BYTE) && e.getEntity() instanceof Player) {
                // Store the victim of the attack in a variable
                Player victim = (Player) e.getEntity();

                // Damage armor
                for (ItemStack d : victim.getInventory().getArmorContents()) {
                    if (d.getItemMeta() instanceof Damageable) {
                        ((Damageable) d.getItemMeta()).setDamage(r.nextInt(5));
                    }
                }

                // Damage shield
                if (victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                    ((Damageable) victim.getInventory().getItemInOffHand()).setDamage(r.nextInt(5));
                }

                // 1/5 chance to add slowness
                if (r.nextInt(4) == 0) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1));
                }

                // 1/5 chance to add weakness
                if (r.nextInt(4) == 0) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15, 1));
                }

                // 1/20 Chance to add brief mining fatigue
                if (r.nextInt(19) == 0) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8, 3));
                }
            } else if (container.has(infusionPhantomCrits, PersistentDataType.BYTE) && e.getEntity() instanceof LivingEntity) {
                // Store victim in a variable
                LivingEntity victim = (LivingEntity) e.getEntity();

                // Set health
                if (r.nextInt(4) == 0) {
                    victim.setHealth(victim.getHealth() - Math.pow(e.getFinalDamage(), 1.15) * 5 / 8);
                }
            }
        }
    }
}
