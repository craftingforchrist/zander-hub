package me.benrobson.zanderhub.events;

import me.benrobson.zanderhub.ZanderHubMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HubPlayerJoinChristmas implements Listener {
    ZanderHubMain plugin;
    public HubPlayerJoinChristmas(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HubPlayerJoinChristmas(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Send player a title to their screen.
        player.sendTitle(ChatColor.GREEN + "Merry " + ChatColor.RED + "Christmas!", player.getDisplayName() + " have a very Merry Christmas!", 10, 60, 10);

        // Send player a message to their chat.
        player.sendMessage(ChatColor.BOLD + ChatColor.WHITE.toString() + "============= " + ChatColor.GREEN + "Merry " + ChatColor.RED + "Christmas" + ChatColor.WHITE + " =============");
        player.sendMessage("Merry Christmas " + ChatColor.GREEN + player.getDisplayName() + "!");
        player.sendMessage("Have a wonderful day with all your friends and family. Remember the reason for the season.");
        player.sendMessage("For to us a child is born, to us a son is given, and the government will be on his shoulders. And he will be called Wonderful Counselor, Mighty God, Everlasting Father, Prince of Peace.");
        player.sendMessage(ChatColor.AQUA + "Isaiah 9:6 // New International Version (NIV)");
    }
}
