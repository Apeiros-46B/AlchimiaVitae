package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;

import me.apeiros.alchimiavitae.setup.Setup;

/**
 * Main class
 */
public class AlchimiaVitae extends AbstractAddon {

    private static AlchimiaVitae i;

    public AlchimiaVitae() {
        super("Apeiros-46B", "AlchimiaVitae", "master", "options.auto-update");
    }

    @Override
    public void enable() {
        // set instance
        i = this;

        // setup items and listeners
        Setup.setup(this);

        // bStats
        new Metrics(this, 15139);
    }

    @Override
    public void disable() {
        // set instance to null
        i = null;
    }

    public static AlchimiaVitae i() {
        return i;
    }

}
