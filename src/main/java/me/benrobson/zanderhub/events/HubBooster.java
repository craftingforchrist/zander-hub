package me.benrobson.zanderhub.events;

import me.benrobson.zanderhub.ZanderHubMain;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HubBooster implements Listener {
    ZanderHubMain plugin;

    public HubBooster(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    public class PlayerInteract implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();

            if (player.getLocation().getBlock().getType().equals(Material.STONE_PRESSURE_PLATE)) {
                player.sendMessage("Haha I go fly.");
            }
        }
    }
}