package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import me.apeiros.alchimiavitae.setup.Setup;

public class AlchimiaVitae extends AbstractAddon {

    private static AlchimiaVitae i;

    public AlchimiaVitae() {
        super("Apeiros-46B", "AlchimiaVitae", "master", "options.auto-update");
    }

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

    public static AlchimiaVitae i() {
        return i;
    }

}
