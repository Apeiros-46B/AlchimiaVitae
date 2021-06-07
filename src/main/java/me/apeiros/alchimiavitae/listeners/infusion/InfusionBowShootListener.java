package me.apeiros.alchimiavitae.listeners.infusion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class InfusionBowShootListener implements Listener {

    // Keys
    private final NamespacedKey infusionTrueAim = new NamespacedKey(AlchimiaVitae.i(), "infusion_trueaim");
    private final NamespacedKey infusionVolatile = new NamespacedKey(AlchimiaVitae.i(), "infusion_volatile");
    private final NamespacedKey infusionForceful = new NamespacedKey(AlchimiaVitae.i(), "infusion_forceful");
    private final NamespacedKey infusionHealing = new NamespacedKey(AlchimiaVitae.i(), "infusion_healing");

    // Timer variables for projectile trails
    private int trueAimTimer = 0;
    private int volatileTimer = 0;
    private int forcefulTimer = 0;
    private int healingTimer = 0;

    // Constructor
    public InfusionBowShootListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Main event
    @EventHandler(ignoreCancelled = true)
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            // Null check
            if (e.getBow() != null) {
                // Null check
                if (e.getBow().getItemMeta() != null) {
                    // Set container
                    PersistentDataContainer container = e.getBow().getItemMeta().getPersistentDataContainer();

                    // True aim infusion
                    if (container.has(infusionTrueAim, PersistentDataType.BYTE)) {
                        // Set gravity to false
                        e.getProjectile().setGravity(false);

                        // Shulker particle trail
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Cancel if timer variable is 50
                                if (trueAimTimer == 50) {
                                    this.cancel();
                                }

                                // Spawn particle
                                e.getProjectile().getWorld().spawnParticle(Particle.END_ROD, e.getProjectile().getLocation(), 1, 0, 0, 0);
                                // Increment timer variable
                                trueAimTimer++;
                            }
                        }.run();
                    }

                    // Volatility infusion
                    if (container.has(infusionVolatile, PersistentDataType.BYTE)) {
                        // Declare fb
                        Fireball fb;

                        // Random
                        ThreadLocalRandom r = ThreadLocalRandom.current();
                        int rNum = r.nextInt(6);

                        // Randomize fireball type and yield
                        if (rNum == 0) {
                            fb = p.launchProjectile(LargeFireball.class, e.getProjectile().getVelocity());
                            fb.setYield(4F);
                        } else {
                            fb = p.launchProjectile(Fireball.class, e.getProjectile().getVelocity());
                            fb.setYield(1F);
                        }

                        // Set other things such as velocity and shooter
                        fb.setVelocity(e.getProjectile().getVelocity().multiply(5).normalize());
                        fb.setFireTicks(0);
                        fb.setIsIncendiary(false);
                        fb.setShooter(p);

                        // Add data
                        fb.getPersistentDataContainer().set(infusionVolatile, PersistentDataType.BYTE, (byte) 1);

                        // Flame particle trail
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Cancel if timer variable is 50
                                if (volatileTimer == 50) {
                                    this.cancel();
                                }

                                // Spawn particle
                                e.getProjectile().getWorld().spawnParticle(Particle.FLAME, e.getProjectile().getLocation(), 1, 0, 0, 0);
                                // Increment timer variable
                                volatileTimer++;
                            }
                        }.run();
                    }

                    // Forceful infusion
                    if (container.has(infusionForceful, PersistentDataType.BYTE)) {
                        e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(2));

                        // Crit magic particle trail
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Cancel if timer variable is 50
                                if (forcefulTimer == 50) {
                                    this.cancel();
                                }

                                // Spawn particle
                                e.getProjectile().getWorld().spawnParticle(Particle.CRIT_MAGIC, e.getProjectile().getLocation(), 1, 0, 0, 0);
                                // Increment timer variable
                                forcefulTimer++;
                            }
                        }.run();
                    }

                    // Healing infusion
                    if (container.has(infusionHealing, PersistentDataType.BYTE)) {
                        // Add data
                        e.getProjectile().getPersistentDataContainer().set(infusionHealing, PersistentDataType.BYTE, (byte) 1);

                        // Totem particle trail
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Cancel if timer variable is 50
                                if (healingTimer == 50) {
                                    this.cancel();
                                }

                                // Spawn particle
                                e.getProjectile().getWorld().spawnParticle(Particle.TOTEM, e.getProjectile().getLocation(), 1, 0, 0, 0);
                                // Increment timer variable
                                healingTimer++;
                            }
                        }.run();
                    }
                }
            }
        }
    }

    // Event for volatility infusion
    @EventHandler(ignoreCancelled = true)
    public void onFireballExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Fireball || e.getEntity() instanceof LargeFireball) {
            if (((Fireball) e.getEntity()).getShooter() instanceof Player
                    && ((Fireball) e.getEntity()).getShooter() != null &&
                    e.getEntity().getPersistentDataContainer().has
                    (infusionVolatile, PersistentDataType.BYTE)) {
                Player shooter = (Player) ((Fireball) e.getEntity()).getShooter();
                ProtectionManager pm = SlimefunPlugin.getProtectionManager();

                if (pm.hasPermission(shooter, e.getLocation(), ProtectableAction.ATTACK_ENTITY) ||
                        pm.hasPermission(shooter, e.getLocation(), ProtectableAction.ATTACK_PLAYER)) {
                    // Remove explosion damage
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
                e.getHitEntity() instanceof LivingEntity &&
                e.getEntity() instanceof AbstractArrow &&
                e.getEntity().getPersistentDataContainer().has(
                        infusionHealing, PersistentDataType.BYTE)) {
            // Variables
            AbstractArrow arrow = (AbstractArrow) e.getEntity();
            LivingEntity entity = (LivingEntity) e.getHitEntity();

            // Heal entity
            e.setCancelled(true);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, (int) Math.floor(arrow.getDamage() / 5)));
            entity.getWorld().spawnParticle(Particle.TOTEM, entity.getLocation(), 20, 1, 1, 1);

            // Remove arrow
            arrow.remove();
        }
    }
}
