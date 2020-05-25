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

    Inventory compnav = Bukkit.createInventory(null, 9, "Server Selector");

    public void CompassNavGUI(Player player) {
        if (player == null) {
            return;
        }

        ItemStack survival = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta survivalMeta = survival.getItemMeta();
        survivalMeta.setDisplayName(ChatColor.WHITE + "Survival");
        survivalMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to join our Survival server."));
        survival.setItemMeta(survivalMeta);
        compnav.setItem(4, survival);

        ItemStack events = new ItemStack(Material.BEACON);
        ItemMeta eventsMeta = events.getItemMeta();
        eventsMeta.setDisplayName(ChatColor.DARK_PURPLE + "Events");
        eventsMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me to play our current Event!"));
        events.setItemMeta(eventsMeta);
        compnav.setItem(7, events);

        ItemStack revelation = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta revelationMeta = revelation.getItemMeta();
        revelationMeta.setDisplayName(ChatColor.WHITE + "Revelation");
        revelationMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me for more information."));
        revelation.setItemMeta(revelationMeta);
        compnav.setItem(1, revelation);

//        ItemStack rlcraft = new ItemStack(Material.ENDER_EYE);
//        ItemMeta rlcraftMeta = rlcraft.getItemMeta();
//        rlcraftMeta.setDisplayName(ChatColor.RED + "RLCraft");
//        rlcraftMeta.setLore(Arrays.asList(ChatColor.WHITE + "Click me for more information."));
//        rlcraft.setItemMeta(rlcraftMeta);
//        compnav.setItem(7, rlcraft);

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
                if (player.hasPermission("bungeecord.server.survival")) {
                    PluginMessageChannel.connect(player, "survival");
                } else  {
                    player.sendMessage(ChatColor.RED + "You do not have access to this server.");
                }
                break;

            // Events
            case BEACON:
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Sending you to Events..");
                if (player.hasPermission("bungeecord.server.events")) {
                    PluginMessageChannel.connect(player, "events");
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have access to this server.");
                }
                break;

            // Revelation
            case CRAFTING_TABLE:
                player.closeInventory();
                player.sendMessage("");
                player.sendMessage("Revelation is a Minecraft snapshot based Survival Server. Mojang has been dropping Snapshots weekly and so this Server allows the community to have fun and explore The Nether Update. We update the Server hours after it's release.");
                player.sendMessage("");
                player.sendMessage("This is a whitelist based Server. Join our Discord " + ChatColor.BLUE + "(/discord) " + ChatColor.RESET + "and type !request revelation USERNAME to gain access.");
                break;

            // RLCraft
//            case ENDER_EYE:
//                player.closeInventory();
//                player.sendMessage("");
//                player.sendMessage("RLCraft is a Minecraft Modpack consisting of 120 separate mods that has been bundled and tweaked by Shivaxi to create a challenging Minecraft fantasy world.");
//                player.sendMessage("This is a whitelist based Server. Join our Discord " + ChatColor.BLUE + "(/discord) " + ChatColor.RESET + " and type !request rlcraft USERNAME to gain access.");
//                player.sendMessage("");
//                break;

            default:
                break;
        }
    }
}
