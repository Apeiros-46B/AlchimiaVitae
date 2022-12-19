package me.apeiros.alchimiavitae.listeners.infusion;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
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
    public void onHookThrow(ProjectileLaunchEvent e) {
        Projectile en = e.getEntity();

        // Make sure the shooter is a non-null player
        if (en.getShooter() == null || !(en.getShooter() instanceof Player))
            return;

        // Make sure the projectile is a fish hook
        if (!(en instanceof FishHook))
            return;

        FishHook h = (FishHook) en;
        Player p = (Player) en.getShooter();
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
        Infusion.KNOCKBACK.apply(pdc);

        // Make the hook fly further
        h.setVelocity(h.getVelocity().multiply(2));
    }
    // }}}

    // {{{ Handler to deal knockback (fires when a hook is retracted)
    @EventHandler(ignoreCancelled = true)
    public void onHookRetract(PlayerFishEvent e) {
        FishHook h = e.getHook();
        Entity en = h.getHookedEntity();

        // Make sure there is a hooked entity
        if (en == null)
            return;

        // Make sure the hook was thrown by a Knockback-infused fishing rod
        if (!Infusion.KNOCKBACK.has(h.getPersistentDataContainer()))
            return;

        // Get protection interaction and player
        Interaction interaction = (en instanceof Player ? Interaction.ATTACK_PLAYER : Interaction.ATTACK_ENTITY);
        Player p = e.getPlayer();

        // Make sure player has permission
        if (!Slimefun.getProtectionManager().hasPermission(p, en.getLocation(), interaction))
            return;

        // Deal knockback
        en.setVelocity(en.getVelocity().add(p.getEyeLocation().getDirection().setY(0).multiply(3).normalize()));

        // Remove hook
        h.setHookedEntity(null); // TODO: test if this is necessary
        h.remove();

        // Cancel event
        e.setCancelled(true);
    }
    // }}}

}
