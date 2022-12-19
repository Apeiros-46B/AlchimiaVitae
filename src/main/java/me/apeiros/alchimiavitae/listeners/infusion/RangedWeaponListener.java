package me.apeiros.alchimiavitae.listeners.infusion;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionManager;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;

/**
 * {@link Listener} for ranged weapon infusions
 */
public class RangedWeaponListener implements Listener {

    public RangedWeaponListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Main handler to spawn and add data to projectiles and play effects (fires when an entity shoots a bow)
    @EventHandler(ignoreCancelled = true)
    public void onBowShoot(EntityShootBowEvent e) {
        // Make sure shooter is a player
        if (!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();

        // Make sure bow exists and has meta
        if (e.getBow() == null || e.getBow().getItemMeta() == null)
            return;

        // Get PDC
        PersistentDataContainer pdc = e.getBow().getItemMeta().getPersistentDataContainer();

        // Needed for effects
        World w = p.getWorld();
        Location l = p.getLocation();

        // Check what infusion the bow has
        // {{{ True Aim infusion
        if (Infusion.TRUE_AIM.has(pdc)) {
            // No gravity
            e.getProjectile().setGravity(false);

            // Effects
            w.spawnParticle(Particle.END_ROD, l, 50);
            w.playSound(l, Sound.ENTITY_SHULKER_SHOOT, 1F, 1F);
        }
        // }}}

        // {{{ Volatility infusion
        else if (Infusion.VOLATILITY.has(pdc)) {
            // Calculate new velocity
            Fireball fb;
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

            // Prevent fire from spawning
            fb.setFireTicks(0);
            fb.setIsIncendiary(false);

            // Add infusion (for explosion event)
            fb.setShooter(p);
            Infusion.VOLATILITY.apply(fb.getPersistentDataContainer());

            // Remove the original projectile
            e.getProjectile().remove();

            // Effects
            w.spawnParticle(Particle.FLAME, l, 50);
            w.playSound(l, Sound.ITEM_FIRECHARGE_USE, 0.6F, 1F);
        }
        // }}}

        // {{{ Forceful infusion
        else if (Infusion.FORCEFUL.has(pdc)) {
            // Multiply velocity
            e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(2));

            // Effects
            w.spawnParticle(Particle.CRIT_MAGIC, l, 25);
            w.playSound(l, Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 1F, 1F);
        }
        // }}}

        // {{{ Healing infusion
        else if (Infusion.HEALING.has(pdc)) {
            // Add infusion
            Infusion.HEALING.apply(e.getProjectile().getPersistentDataContainer());
        }
        // }}}
    }
    // }}}

    // {{{ Handler for Volatility infusion effects (fires when an entity explodes)
    @EventHandler(ignoreCancelled = true)
    public void onFireballExplode(EntityExplodeEvent e) {
        // Make sure the exploding entity is a fireball
        if (!(e.getEntity() instanceof Fireball))
            return;

        Fireball fb = (Fireball) e.getEntity();

        // Make sure the shooter is a non-null player
        if (fb.getShooter() == null || !(fb.getShooter() instanceof Player))
            return;

        Player shooter = (Player) fb.getShooter();

        // Make sure fireball is from an infused bow
        if (!Infusion.VOLATILITY.has(fb.getPersistentDataContainer()))
            return;

        // Get the protection manager
        ProtectionManager pm = Slimefun.getProtectionManager();

        // Cancel event if no permission
        if (!pm.hasPermission(shooter, e.getLocation(), Interaction.ATTACK_PLAYER)
                && !pm.hasPermission(shooter, e.getLocation(), Interaction.ATTACK_ENTITY)) {
            e.setCancelled(true);
            return;
        }

        // Prevent block damage
        e.blockList().clear();
    }
    // }}}

    // {{{ Handler for Healing infusion effects (fires when a projectile hits)
    @EventHandler(ignoreCancelled = true)
    public void onArrowHit(ProjectileHitEvent e) {
        // Make sure the hit entity is a non-null living entity
        if (e.getHitEntity() == null || !(e.getHitEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity) e.getHitEntity();

        // Make sure projectile is an arrow
        if (!(e.getEntity() instanceof AbstractArrow))
            return;

        AbstractArrow arrow = (AbstractArrow) e.getEntity();

        // Make sure arrow is from an infused bow
        if (!Infusion.HEALING.has(arrow.getPersistentDataContainer()))
            return;

        // Cancel Handler to prevent damage
        e.setCancelled(true);

        // Heal entity
        entity.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, (int) Math.floor(arrow.getDamage() / 5)));
        entity.getWorld().spawnParticle(Particle.TOTEM, entity.getLocation(), 20, 1, 1, 1);

        // Remove arrow
        arrow.remove();
    }
    // }}}

}
