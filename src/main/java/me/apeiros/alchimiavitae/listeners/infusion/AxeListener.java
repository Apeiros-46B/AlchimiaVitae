package me.apeiros.alchimiavitae.listeners.infusion;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionManager;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;

/**
 * {@link Listener} for axe infusions
 */
public class AxeListener implements Listener {

    public AxeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Handler to apply effects (fires when an entity damages another)
    @EventHandler(ignoreCancelled = true)
    public void onAxeHit(EntityDamageByEntityEvent e) {
        // Make sure damager is a player
        if (!(e.getDamager() instanceof Player))
            return;

        Player p = (Player) e.getDamager();

        // Make sure the damager was falling (it was a critical hit)
        if (e.getEntity().getFallDistance() <= 0)
            return;

        // Make sure the damager's main hand item has meta
        ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();

        if (meta == null)
            return;

        // Get the item's PDC, a random number generator, and the protection manager
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        ProtectionManager pm = Slimefun.getProtectionManager();

        // Check what infusion the axe has
        // {{{ Destructive Criticals
        if (e.getEntity() instanceof Player victim && pdc.has(AltarOfInfusion.DESTRUCTIVE_CRITS, PersistentDataType.BYTE)) {
            // Make sure attacker has permission
            if (!pm.hasPermission(p, victim.getLocation(), Interaction.ATTACK_PLAYER))
                return;

            // Damage armor
            for (ItemStack armor : victim.getInventory().getArmorContents()) {
                if (!(armor.getItemMeta() instanceof Damageable))
                    continue;

                Damageable d = (Damageable) armor.getItemMeta();
                int current = d.getDamage();

                // 0-5 damage
                d.setDamage(current + rand.nextInt(6));
            }

            // 1/5 chance to add slowness
            if (rand.nextInt(5) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1));
            }

            // 1/5 chance to add weakness
            if (rand.nextInt(5) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15, 1));
            }

            // 1/20 Chance to add brief mining fatigue
            if (rand.nextInt(20) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8, 3));
            }
        }
        // }}}

        // {{{ Phantom Criticals
        else if (e.getEntity() instanceof LivingEntity victim && pdc.has(AltarOfInfusion.PHANTOM_CRITS, PersistentDataType.BYTE)) {
            // Make sure attacker has permission
            if (!pm.hasPermission(p, victim.getLocation(), victim instanceof Player ? Interaction.ATTACK_PLAYER : Interaction.ATTACK_ENTITY))
                return;

            // Reduce victim's health
            if (rand.nextInt(4) == 0) {
                victim.setHealth(victim.getHealth() - Math.pow(e.getFinalDamage(), 1.15) * 5 / 8);
            }
        }
        // }}}
    }
    // }}}

}
