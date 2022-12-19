package me.apeiros.alchimiavitae.setup.items.potions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;

public class MalevolentConcoction extends AbstractListenerPotion {

    public MalevolentConcoction(ItemGroup ig, CosmicCauldron cauldron) {
        super(ig, AlchimiaItems.MALEVOLENT_CONCOCTION, AlchimiaUtils.RecipeTypes.COSMIC_CAULDRON, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.BONE_BLOCK),
                AlchimiaItems.DARK_ESSENCE, new ItemStack(Material.DRAGON_BREATH), new ItemStack(Material.LAVA_BUCKET),
                AlchimiaItems.DARKSTEEL, AlchimiaItems.CONDENSED_SOUL, new ItemStack(Material.ROTTEN_FLESH)
        }, cauldron);
    }

    // {{{ Handle potion throw
    @EventHandler(ignoreCancelled = true)
    public void onThrow(ProjectileLaunchEvent e) {
        Entity en = e.getEntity();

        // Make sure the projectile is a potion
        if (!(en instanceof ThrownPotion))
            return;

        ThrownPotion potion = (ThrownPotion) en;

        // Make sure the potion is similar to this one
        if (!this.isSimilar(potion.getItem()))
            return;

        // Effects
        World w = potion.getWorld();
        Location l = potion.getLocation();

        w.spawnParticle(Particle.SPELL_WITCH, l, 75, 1, 1, 1);
        w.playSound(l, Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.4F, 1);
        w.playSound(l, Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
    }
    // }}}

    // {{{ Handle potion splash
    @EventHandler(ignoreCancelled = true)
    public void onSplash(PotionSplashEvent e) {
        ThrownPotion potion = e.getEntity();

        // Make sure the potion is similar to this one
        if (!this.isSimilar(potion.getItem()))
            return;

        // Effects
        World w = potion.getWorld();
        Location l = potion.getLocation();

        w.spawnParticle(Particle.SPELL_WITCH, l, 75, 1, 1, 1);
        w.spawnParticle(Particle.WARPED_SPORE, l, 200, 3, 1, 3);
        w.playSound(l, Sound.ENTITY_EVOKER_PREPARE_ATTACK, 1, 1);
        w.playSound(l, Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
    }
    // }}}

}
