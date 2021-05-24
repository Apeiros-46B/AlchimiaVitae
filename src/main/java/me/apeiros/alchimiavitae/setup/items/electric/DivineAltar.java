package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class DivineAltar extends AbstractContainer {

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int STATUS_SLOT = 23;
    private static final int OUT_SLOT = 25;

    private static final int[] MAIN_BG = {0, 1, 2, 3, 4, 6, 8, 9, 13, 15, 17, 18, 22, 24, 26, 27, 31, 33, 35, 36, 37, 38, 39, 40, 42, 44};
    private static final int[] STATUS_BG = {5, 14, 32, 41};
    private static final int[] OUT_BG = {7, 16, 34, 43};

    public static final Map<MultiInput, ItemStack> RECIPES = new HashMap<>();

    public DivineAltar(Category c) {

        super(c, Items.PLANT_INFUSION_CHAMBER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.ELECTRIC_MOTOR, Items.EXP_CRYSTAL,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.HEATED_PRESSURE_CHAMBER_2, SlimefunItems.ANCIENT_PEDESTAL
        });

    }

    @NotNull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0]   ;
    }

    @Override
    protected void tick(@NotNull Block block) {

    }

    public void craft(@Nonnull Block b, @Nonnull BlockMenu inv, @Nonnull Player p) {
        int charge = getCharge(b.getLocation());

        if (charge < this.energy) { //not enough energy
            p.sendMessage( new String[] {
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + this.energy + " J"
            });
            return;
        }

        ItemStack output = RECIPES.get(new MultiInput(inv, IN_SLOTS));

        if (output == null) { //invalid
            p.sendMessage( ChatColor.RED + "Invalid Recipe!");
            return;
        }

        if (!inv.fits(output, OUTPUT_SLOTS)) { //not enough room
            p.sendMessage( ChatColor.GOLD + "Not enough room!");
            return;
        }

        for (int slot : INPUT_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot, 1);
            }
        }

        p.sendMessage( ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + output.getItemMeta().getDisplayName());

        inv.pucrapem(output.clone(), OUT_SLOT);
        setCharge(b.getLocation(), 0);

    }

    @Override
    protected void setupMenu(@NotNull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.addItem(STATUS_SLOT, new CustomItem(
                Material.RED_STAINED_GLASS_PANE,
                        "&cWaiting for items...", "&7Input your items on", "&7the left side of this GUI", "&7in the pattern shown in", "&7the Slimefun guide"),
                ChestMenuUtils.getEmptyClickHandler());

        for (int slot: STATUS_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.STATUS_BG);
        }

        for (int slot: OUT_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.OUT_BG);
        }

        for (int slot : MAIN_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.BG);
        }
    }

}
