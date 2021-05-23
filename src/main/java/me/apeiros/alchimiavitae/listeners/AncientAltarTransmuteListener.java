package me.apeiros.alchimiavitae.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.AncientAltarCraftEvent;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class AncientAltarTransmuteListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onAncientAltarCraft(AncientAltarCraftEvent e) {
        if (e.getItem() == AlchimiaVitaeItems.MOLTEN_MYSTERY_METAL) {
            e.getAltarBlock().getWorld().strikeLightningEffect(e.getAltarBlock().getLocation());
            e.getAltarBlock().getWorld().playSound(e.getAltarBlock().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
            e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<green>Transmutation successful!")));
        }
    }
}
