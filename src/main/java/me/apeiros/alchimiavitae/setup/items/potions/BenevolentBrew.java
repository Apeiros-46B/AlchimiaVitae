package me.apeiros.alchimiavitae.setup.items.potions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;

public class BenevolentBrew extends AbstractListenerPotion {

    public BenevolentBrew(ItemGroup ig, CosmicCauldron cauldron) {
        super(ig, AlchimiaItems.BENEVOLENT_BREW, AlchimiaUtils.RecipeTypes.COSMIC_CAULDRON, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
                AlchimiaItems.LIGHT_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
                AlchimiaItems.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY)
        }, cauldron);
    }

    // {{{ Handle potion drink
    @EventHandler(ignoreCancelled = true)
    public void onDrink(PlayerItemConsumeEvent e) {
        // Make sure the potion is similar to this one
        if (!this.isSimilar(e.getItem()))
            return;

        // Set player's absorption amount
        Player p = e.getPlayer();
        p.setAbsorptionAmount(p.getAbsorptionAmount() + AlchimiaVitae.i().getConfig()
                .getDouble("options.potions.benevolent-brew.absorption-halfhearts"));

        // Effects
        World w = p.getWorld();
        Location l = p.getLocation();

        w.playSound(l, Sound.ITEM_BOTTLE_EMPTY, 1, 1);
        w.playSound(l, Sound.ITEM_TOTEM_USE, 0.3F, 1);
        w.playSound(l, Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.5F, 1);
        w.spawnParticle(Particle.BLOCK_CRACK, l, 200, 3, 3, 3, Material.EMERALD_BLOCK.createBlockData());
        w.spawnParticle(Particle.TOTEM, l, 200, 1, 1, 1);
    }
    // }}}

}
