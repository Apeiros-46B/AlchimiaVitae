package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.WeaponUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

public class SoulCollector extends SlimefunItem {

    public SoulCollector(ItemGroup c) {

        super(c, Items.SOUL_COLLECTOR, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.EARTH_RUNE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.AIR_RUNE,
                SlimefunItems.WATER_RUNE, SlimefunItems.NECROTIC_SKULL, SlimefunItems.FIRE_RUNE,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ESSENCE_OF_AFTERLIFE
        });

    }

    @Nonnull
    private WeaponUseHandler getWeaponUseHandler() {
        return (e, p, i) -> {
            if (e.getEntity() instanceof Player) {
                // The Soul Collector cannot be used on players
                e.setCancelled(true);
                p.sendMessage(Utils.legacySerialize("<red>You cannot hurt a player using the Soul Collector!"));
                p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
            }
        };
    }

    @Nonnull
    private EntityKillHandler getEntityKillHandler() {
        return (e, en, p, item) -> {
            // Collect souls and multiply dropped EXP by 3 (1 in the case of an Ender Dragon)
            ThreadLocalRandom r = ThreadLocalRandom.current();

            if (r.nextInt(2) == 0) {
                p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.4F, 1);
                int soulAmount = 1;
                int expMultiplier = 3;

                switch (e.getEntityType()) {
                    case WITHER -> soulAmount = r.nextInt(9);
                    case WITHER_SKELETON -> soulAmount = r.nextInt(4);
                    case ENDER_DRAGON -> expMultiplier = 1;
                    default -> {}
                }

                for (int i = 0; i < soulAmount; i++) {
                    e.getDrops().add(Items.CONDENSED_SOUL.clone());
                }

                e.setDroppedExp(e.getDroppedExp() * expMultiplier);
            }
        };
    }

    @Override
    public void preRegister() {
        this.addItemHandler(getWeaponUseHandler());
        this.addItemHandler(getEntityKillHandler());
    }

}
