package me.benrobson.zanderhub.gui;

import me.benrobson.zanderhub.ZanderHubMain;
import me.benrobson.zanderhub.events.PluginMessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HubCompass implements Listener {
    ZanderHubMain plugin;
    public void navigationgui(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void navigationgui(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() ==  Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                CompassNavGUI(player);
            }
        }
    }

    Inventory compnav = Bukkit.createInventory(null, 27, "Server Selector");

    public void CompassNavGUI(Player player) {
        if (player == null) {
            return;
        }

        ItemStack survival = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta survivalMeta = survival.getItemMeta();
        survivalMeta.setDisplayName(ChatColor.WHITE + "Survival");
        survivalMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to join our Survival server."));
        survival.setItemMeta(survivalMeta);
        compnav.setItem(13, survival);

        ItemStack events = new ItemStack(Material.BEACON);
        ItemMeta eventsMeta = survival.getItemMeta();
        eventsMeta.setDisplayName(ChatColor.DARK_PURPLE + "Events");
        eventsMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to play our current Event!"));
        events.setItemMeta(eventsMeta);
        compnav.setItem(10, events);

        ItemStack build = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta buildMeta = survival.getItemMeta();
        buildMeta.setDisplayName(ChatColor.AQUA + "Build");
        buildMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to join the Build Server."));
        build.setItemMeta(buildMeta);
        compnav.setItem(16, build);

        ItemStack revelation = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta revelationMeta = revelation.getItemMeta();
        revelationMeta.setDisplayName(ChatColor.WHITE + "Revelation");
        revelationMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me for more information."));
        revelation.setItemMeta(revelationMeta);
        compnav.setItem(20, revelation);

        ItemStack rlcraft = new ItemStack(Material.ENDER_EYE);
        ItemMeta rlcraftMeta = rlcraft.getItemMeta();
        rlcraftMeta.setDisplayName(ChatColor.RED + "RLCraft");
        rlcraftMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me for more information."));
        rlcraft.setItemMeta(rlcraftMeta);
        compnav.setItem(24, rlcraft);

        player.openInventory(compnav);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Server Selector")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            // Survival
            case IRON_PICKAXE:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Survival..");
                PluginMessageChannel.connect(player, "survival");
                break;

            // Events
            case BEACON:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Events..");
                PluginMessageChannel.connect(player, "events");
                break;

            // Build
            case GRASS_BLOCK:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Build..");
                PluginMessageChannel.connect(player, "build");
                break;

            // Revelation
            case CRAFTING_TABLE:
                player.closeInventory();
                player.sendMessage("\n");
                player.sendMessage("Revelation is a Minecraft Snapshot Server.\nYou can not be connected to this Server via the Hub.\nPlease connect using " + ChatColor.YELLOW + ChatColor.BOLD + "revelation.craftingforchrist.net");
                break;

            // RLCraft
            case ENDER_EYE:
                player.closeInventory();
                player.sendMessage("\n");
                player.sendMessage("RLCraft is a mod pack Server.\nYou can not be connected to this Server via the Hub. Please join our Discord (use " + ChatColor.BLUE + "/discord " + ChatColor.RESET + "to join) and type !request to access the RLCraft Server.");
                break;

            default:
                break;
        }
    }
}
