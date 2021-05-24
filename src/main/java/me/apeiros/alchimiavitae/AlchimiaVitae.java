package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import lombok.SneakyThrows;
import me.apeiros.alchimiavitae.setup.Setup;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

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

    @Getter
    private static final Category category = new Category(new NamespacedKey(instance, "av_category"), new CustomItem(Material.TOTEM_OF_UNDYING, "&aAlchimia Vitae", "&8> &aClick to open"));

    @SneakyThrows
    @Override
    public void onEnable() {
        // Instance and super
        instance = this;
        super.onEnable();

        // Setup category, items, and listeners
        Setup.setup(instance);
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
