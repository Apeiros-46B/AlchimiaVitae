package me.apeiros.alchimiavitae.utils;

import io.github.mooy1.infinitylib.groups.MultiGroup;
import io.github.mooy1.infinitylib.groups.SubGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Map;

/**
 * Holds utility methods and constants
 */
@UtilityClass
public class Utils {

    // Misc util methods

    public static NamespacedKey key(String s) {
        return new NamespacedKey(AlchimiaVitae.i(), s);
    }

    // Minimessage

    private static final MiniMessage MM = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.reset())
                    .build()
            )
            .build();

    private static final LegacyComponentSerializer LCS = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static final PlainTextComponentSerializer PTCS = PlainTextComponentSerializer.plainText();

    public static Component parse(String s) {
        return MM.deserialize(s);
    }

    public static String parseLegacy(String s) {
        return LCS.serialize(parse(s));
    }

    private static String parsePlain(Component c) {
        return PTCS.serialize(c);
    }

    // Methods for making potions

    public static SlimefunItemStack makePotion(Component name, Color color, Map<PotionEffectType, int[]> effects) {
        name = name.decoration(TextDecoration.ITALIC, false);

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.displayName(name);
        potionMeta.setColor(color);

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            potionMeta.addCustomEffect(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true), true);
        }

        String id = "AV_" + parsePlain(name).toUpperCase().replace(" ", "_") + "_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }

    public static SlimefunItemStack makePotion(Component name, Color color, Collection<PotionEffect> effects) {
        name = name.decoration(TextDecoration.ITALIC, false);

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.displayName(name);
        potionMeta.setColor(color);

        for (PotionEffect e : effects) {
            potionMeta.addCustomEffect(e, true);
        }

        String id = "AV_" + parsePlain(name).toUpperCase().replace(" ", "_") + "_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }

    public static SlimefunItemStack makeSplashPotion(Component name, Color color, Map<PotionEffectType, int[]> effects) {
        name = name.decoration(TextDecoration.ITALIC, false);

        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.displayName(name);
        potionMeta.setColor(color);

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            potionMeta.addCustomEffect(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true), true);
        }

        String id = "AV_" + parsePlain(name).toUpperCase().replace(" ", "_") + "_SPLASH_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }

    /**
     * Holds this addon's {@link RecipeType}s
     */
    @UtilityClass
    public class RecipeTypes {

        public static final RecipeType SOUL_COLLECTOR_TYPE = new RecipeType(Utils.key("soul_collector_type"), Items.SOUL_COLLECTOR, "", "&b&oExtract using the Soul Collector");
        public static final RecipeType PLANT_INFUSION_CHAMBER_TYPE = new RecipeType(Utils.key("plant_infusion_chamber_type"), Items.PLANT_INFUSION_CHAMBER, "", "&b&oInfuse using the Plant Infusion Chamber");
        public static final RecipeType EXP_CRYSTALLIZER_TYPE = new RecipeType(Utils.key("exp_crystallizer_type"), Items.EXP_CRYSTALLIZER, "", "&b&oCrystallize using the Experience Crystallizer");
        public static final RecipeType DIVINE_ALTAR_TYPE = new RecipeType(Utils.key("divine_altar_type"), Items.DIVINE_ALTAR, "", "&b&oFabricate using the Divine Altar");
        public static final RecipeType ORNATE_CAULDRON_TYPE = new RecipeType(Utils.key("ornate_cauldron_type"), Items.ORNATE_CAULDRON, "", "&b&oBrew using the Ornate Cauldron");
        public static final RecipeType INFUSION_ALTAR_TYPE = new RecipeType(Utils.key("infusion_altar_type"), Items.ALTAR_OF_INFUSION, "", "&b&oInfuse using the Altar of Infusion");

    }

    /**
     * Holds the {@link ItemGroup}s of this addon
     */
    @UtilityClass
    public class ItemGroups {

        public static final ItemGroup GENERAL = new SubGroup(
                "av_general",
                new CustomItemStack(Material.ENCHANTED_BOOK, "&6Alchimia Vitae &7- &2General")
        );

        public static final ItemGroup ALTAR_RECIPES = new SubGroup(
                "av_altar_recipes",
                new CustomItemStack(Material.ENCHANTING_TABLE, "&6Alchimia Vitae &7- &5Transmutation")
        );

        public static final ItemGroup INFUSIONS = new SubGroup(
                "av_infusions",
                new CustomItemStack(Material.LODESTONE, "&6Alchimia Vitae &7- &dInfusion")
        );

        public static final ItemGroup MAIN = new MultiGroup(
                "alchimia_vitae",
                new CustomItemStack(Material.TOTEM_OF_UNDYING, "&6Alchimia Vitae"),
                GENERAL, ALTAR_RECIPES, INFUSIONS
        );

    }
}
