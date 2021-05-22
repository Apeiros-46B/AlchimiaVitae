package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeSetup;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
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

        // Config and auto-updates
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            if (this.getDescription().getVersion().startsWith("DEV - ")) {
                (new GitHubBuildsUpdater(this, this.getFile(), this.getGithubPath())).start();
            }
        } else {
            this.runSync(() -> {
                this.log("#######################################", "Auto Updates have been disabled for " + this.getName(), "You will receive no support for bugs", "Until you update to the latest version!", "#######################################");
            });
        }

        // Setup
        AlchimiaVitaeSetup.setup(this);
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
