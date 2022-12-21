package me.apeiros.alchimiavitae.listeners.infusion;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;

/**
 * {@link Listener} for Knockback (fishing rod) infusion
 */
public class FishingRodListener implements Listener {

    public FishingRodListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Main handler to increase the range of and add data to fish hooks (fires when a hook is thrown)
    @EventHandler(ignoreCancelled = true)
    public void onHookLaunch(ProjectileLaunchEvent e) {
        Projectile en = e.getEntity();

        // Make sure the shooter is a non-null player
        if (en.getShooter() == null || !(en.getShooter() instanceof Player))
            return;

        Player p = (Player) en.getShooter();

        // Make sure the projectile is a fish hook
        if (!(en instanceof FishHook))
            return;

        FishHook h = (FishHook) en;
        ItemStack rod = p.getInventory().getItemInMainHand();

        // Make sure the rod has meta
        if (rod.getItemMeta() == null)
            return;

        // Get PDC
        PersistentDataContainer pdc = rod.getItemMeta().getPersistentDataContainer();

        // Make sure the rod has the infusion
        if (!Infusion.KNOCKBACK.has(pdc))
            return;

        // Add the infusion data to the hook
        Infusion.KNOCKBACK.apply(h.getPersistentDataContainer());
    }
    // }}}

    // {{{ Handler to deal knockback (fires when a hook hits an entity)
    @EventHandler(ignoreCancelled = true)
    public void onHookHit(ProjectileHitEvent e) {
        // Make sure the hook hit an entity
        if (e.getHitEntity() == null)
            return;

        Projectile en = e.getEntity();

        // Make sure the shooter is a non-null player
        if (en.getShooter() == null || !(en.getShooter() instanceof Player))
            return;

        // Make sure the projectile is a fish hook
        if (!(en instanceof FishHook))
            return;

        FishHook h = (FishHook) en;
        Player p = (Player) en.getShooter();
        Entity victim = e.getHitEntity();

        // Make sure the hook was launched by a Knockback-infused fishing rod
        if (!Infusion.KNOCKBACK.has(h.getPersistentDataContainer()))
            return;

        // Make sure player has permission
        Interaction interaction = (victim instanceof Player ? Interaction.ATTACK_PLAYER : Interaction.ATTACK_ENTITY);
        if (!Slimefun.getProtectionManager().hasPermission(p, victim.getLocation(), interaction))
            return;

        // Cause a damage tick if the entity is damagable
        // (This deals knockback the same way Minecraft 1.8 fishing rods did)
        if (victim instanceof Damageable d)
            d.damage(0, p);

        // Remove hook
        h.remove();
    }
    // }}}

}
