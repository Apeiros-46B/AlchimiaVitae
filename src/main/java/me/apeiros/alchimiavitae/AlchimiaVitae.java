package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.apeiros.alchimiavitae.listeners.SoulCollectorListener;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeSetup;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlchimiaVitae extends AbstractAddon implements SlimefunAddon {

    private static AlchimiaVitae instance;

    public static final MiniMessage mm = MiniMessage.builder()
            .removeDefaultTransformations()
            .transformation(TransformationType.COLOR)
            .transformation(TransformationType.DECORATION)
            .transformation(TransformationType.GRADIENT)
            .markdown()
            .markdownFlavor(DiscordFlavor.get())
            .build();

    @SneakyThrows
    @Override
    public void onEnable() {
        // Instance and super
        instance = this;
        super.onEnable();

        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        // Setup category, items, and listeners
        AlchimiaVitaeSetup.setup(instance);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    @Nullable
    protected Metrics setupMetrics() {
        return null;
    }

    @Override
    @Nonnull
    protected String getGithubPath() {
        return "https://github.com/Apeiros-46B/AddonJam2021Entry";
    }

    public static AlchimiaVitae inst() {
        return instance;
    }

}
