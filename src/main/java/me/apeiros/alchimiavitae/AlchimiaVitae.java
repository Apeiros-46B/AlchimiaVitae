package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.apeiros.alchimiavitae.setup.Setup;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlchimiaVitae extends AbstractAddon implements SlimefunAddon {

    private static AlchimiaVitae i;

    public static final MiniMessage MM = MiniMessage.builder()
            .removeDefaultTransformations()
            .transformation(TransformationType.COLOR)
            .transformation(TransformationType.DECORATION)
            .transformation(TransformationType.GRADIENT)
            .markdown()
            .markdownFlavor(DiscordFlavor.get())
            .build();

    @SneakyThrows
    @Override
    public void enable() {
        // Instance and super
        i = this;

        // Setup items and listeners
        Setup.setup(this);
    }

    @Override
    public void disable() {
        // Set instance to null
        i = null;
    }

    @Override
    @Nullable
    protected Metrics setupMetrics() {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getAutoUpdatePath() {
        return null;
    }

    @Override
    @Nonnull
    protected String getGithubPath() {
        return "Apeiros-46B/AlchimiaVitae/master";
    }

    public static AlchimiaVitae i() {
        return i;
    }

}
