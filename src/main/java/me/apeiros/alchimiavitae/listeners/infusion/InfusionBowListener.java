package me.apeiros.alchimiavitae.listeners.infusion;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionManager;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class InfusionBowListener implements Listener {

    // Constructor
    public InfusionBowListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Main event
    @EventHandler(ignoreCancelled = true)
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player p) {

            // Null check
            if (e.getBow() != null) {
                // Null check
                if (e.getBow().getItemMeta() != null) {
                    // Set container
                    PersistentDataContainer container = e.getBow().getItemMeta().getPersistentDataContainer();

                    // For effects
                    World w = p.getWorld();
                    Location l = p.getLocation();

                    // True aim infusion
                    if (container.has(AltarOfInfusion.TRUE_AIM, PersistentDataType.BYTE)) {
                        // Set gravity to false
                        e.getProjectile().setGravity(false);

                        // Effects
                        w.spawnParticle(Particle.END_ROD, l, 50);
                        w.playSound(l, Sound.ENTITY_SHULKER_SHOOT, 1F, 1F);
                    }

                    // Volatility infusion
                    if (container.has(AltarOfInfusion.VOLATILE, PersistentDataType.BYTE)) {
                        // Declare fireball
                        Fireball fb;

                        // Calculate fireball's velocity
                        Vector newVelocity = e.getProjectile().getVelocity().multiply(5).normalize();

                        // Determine fireball type and explosion yield based on force of the original projectile
                        float force = e.getForce();
                        if (force >= 0.95) {
                            fb = p.launchProjectile(LargeFireball.class, newVelocity);
                            fb.setYield(force * 3);
                        } else if (force >= 0.3) {
                            fb = p.launchProjectile(Fireball.class, newVelocity);
                            fb.setYield(force * 3);
                        } else {
                            fb = p.launchProjectile(SmallFireball.class, newVelocity);
                            fb.setYield(force * 2);
                        }

                        // Prevent fire
                        fb.setFireTicks(0);
                        fb.setIsIncendiary(false);

                        // Set shooter to the player who shot the bow
                        fb.setShooter(p);

                        // Add data (for explosion event)
                        fb.getPersistentDataContainer().set(AltarOfInfusion.VOLATILE, PersistentDataType.BYTE, (byte) 1);

                        // Remove the original projectile
                        e.getProjectile().remove();

                        // Play visual effects
                        w.spawnParticle(Particle.FLAME, l, 50);
                        w.playSound(l, Sound.ITEM_FIRECHARGE_USE, 0.6F, 1F);
                    }

                    // Forceful infusion
                    if (container.has(AltarOfInfusion.FORCEFUL, PersistentDataType.BYTE)) {
                        // Increase velocity of arrow
                        e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(2));

                        // Effects
                        w.spawnParticle(Particle.CRIT_MAGIC, l, 25);
                        w.playSound(l, Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 1F, 1F);
                    }

                    // Healing infusion
                    if (container.has(AltarOfInfusion.HEALING, PersistentDataType.BYTE)) {
                        // Add data
                        e.getProjectile().getPersistentDataContainer().set(AltarOfInfusion.HEALING, PersistentDataType.BYTE, (byte) 1);
                    }
                }
            }
        }
    }

    // Event for volatility infusion
    @EventHandler(ignoreCancelled = true)
    public void onFireballExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Fireball fireball) {
            if (((Fireball) e.getEntity()).getShooter() instanceof Player shooter
                    && ((Fireball) e.getEntity()).getShooter() != null &&
                    e.getEntity().getPersistentDataContainer().has
                    (AltarOfInfusion.VOLATILE, PersistentDataType.BYTE)) {
                ProtectionManager pm = Slimefun.getProtectionManager();

                if (pm.hasPermission(shooter, e.getLocation(), Interaction.ATTACK_ENTITY) ||
                        pm.hasPermission(shooter, e.getLocation(), Interaction.ATTACK_PLAYER)) {
                    // Prevent block damage
                    e.blockList().clear();
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

    // Event for healing infusion
    @EventHandler(ignoreCancelled = true)
    public void onArrowHit(ProjectileHitEvent e) {
        if (e.getHitEntity() != null &&
                e.getHitEntity() instanceof LivingEntity entity &&
                e.getEntity() instanceof AbstractArrow arrow &&
                e.getEntity().getPersistentDataContainer().has(
                AltarOfInfusion.HEALING, PersistentDataType.BYTE)) {

            // Heal entity
            e.setCancelled(true);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, (int) Math.floor(arrow.getDamage() / 5)));
            entity.getWorld().spawnParticle(Particle.TOTEM, entity.getLocation(), 20, 1, 1, 1);

            // Remove arrow
            arrow.remove();
        }
    }
}
