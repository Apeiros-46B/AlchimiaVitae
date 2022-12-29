package me.apeiros.alchimiavitae.listeners.infusion;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionManager;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;

/**
 * {@link Listener} for melee weapon infusions
 */
public class MeleeWeaponListener implements Listener {

    public MeleeWeaponListener(AlchimiaVitae p) {
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
        if (e.getEntity() instanceof LivingEntity victim && Infusion.DESTRUCTIVE_CRITS.has(pdc)) {
            // Make sure attacker has permission
            if (!pm.hasPermission(p, victim.getLocation(), Interaction.ATTACK_PLAYER))
                return;

            // Damage armor
            if (victim instanceof Player victimPlayer) {
                for (ItemStack armor : victimPlayer.getInventory().getArmorContents()) {
                    // Make sure the worn item is damageable
                    if (!(armor.getItemMeta() instanceof Damageable))
                        continue;

                    Damageable d = (Damageable) armor.getItemMeta();

                    // 1-5 damage
                    d.setDamage(d.getDamage() + rand.nextInt(1, 6));
                }
            }

            // Variables for effects
            World w = victim.getWorld();
            Location l = victim.getLocation();

            // 1/4 chance to add slowness
            if (rand.nextInt(4) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1));

                w.spawnParticle(Particle.BLOCK_CRACK, l, 100, Material.STONE.createBlockData());
                w.playSound(l, Sound.BLOCK_STONE_BREAK, 1, 1);
            }

            // 1/4 chance to add weakness
            if (rand.nextInt(4) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10, 1));

                w.spawnParticle(Particle.BLOCK_CRACK, l, 100, Material.DEEPSLATE.createBlockData());
                w.playSound(l, Sound.BLOCK_DEEPSLATE_BREAK, 1, 1);
            }

            // 1/8 Chance to add brief mining fatigue
            if (rand.nextInt(8) == 0) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 3));

                w.spawnParticle(Particle.BLOCK_CRACK, l, 100, Material.REDSTONE_BLOCK.createBlockData());
                w.playSound(l, Sound.BLOCK_METAL_BREAK, 1, 1);
            }
        }
        // }}}

        // {{{ Phantom Criticals
        else if (e.getEntity() instanceof LivingEntity victim && Infusion.PHANTOM_CRITS.has(pdc)) {
            // Make sure attacker has permission
            if (!pm.hasPermission(p, victim.getLocation(), victim instanceof Player ? Interaction.ATTACK_PLAYER : Interaction.ATTACK_ENTITY))
                return;

            // Reduce victim's health
            if (rand.nextInt(4) == 0) {
                victim.setHealth(victim.getHealth() - Math.pow(e.getFinalDamage(), 1.15) * 5 / 8);

                // Effect
                victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_PHANTOM_BITE, 0.7F, 1);
            }
        }
        // }}}
    }
    // }}}

}
