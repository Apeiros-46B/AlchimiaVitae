package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import lombok.SneakyThrows;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;

import javax.annotation.Nullable;

public class AlchimiaVitae extends AbstractAddon implements SlimefunAddon {

    @Getter private static AlchimiaVitae instance;

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
        instance = this;
        super.onEnable();

        // Read something from your config.yml
        Config cfg = new Config(instance);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }


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
    protected String getGithubPath() {
        return "https://github.com/Apeiros-46B/AddonJam2021Entry";
    }

}
