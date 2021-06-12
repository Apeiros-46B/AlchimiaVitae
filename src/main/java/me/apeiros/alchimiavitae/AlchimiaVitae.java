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

    private static AlchimiaVitae instance;

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
    public void onEnable() {
        // Instance and super
        instance = this;
        super.onEnable();

        // Setup items and listeners
        Setup.setup(instance);
    }

    @Override
    public void onDisable() {
        // Set instance to null
        instance = null;
    }

    @Override
    @Nullable
    protected Metrics setupMetrics() {
        return null;
    }

    @Override
    @Nonnull
    protected String getGithubPath() {
        return "Apeiros-46B/AlchimiaVitae";
    }

    public static AlchimiaVitae i() {
        return instance;
    }

}
