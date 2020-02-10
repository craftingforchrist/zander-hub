package me.benrobson.zanderhub.events;

import me.benrobson.zanderhub.ConfigurationManager;
import me.benrobson.zanderhub.ZanderHubMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Arrays;
import java.util.List;

public class HubPlayerJoin implements Listener {
    ZanderHubMain plugin;
    public HubPlayerJoin(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HubPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Teleport Player to Hub spawn point
        if (ConfigurationManager.getHubLocation() != null) {
            player.teleport(ConfigurationManager.getHubLocation());
        }

        event.setJoinMessage(null); // disable join message

        // Dispatch MOTD to user
        List<String> motd = plugin.configurationManager.getmotd().getStringList("motd");
        for (String s : motd) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
        event.getPlayer().sendMessage(" "); // Seperate between messages

        if (!player.hasPlayedBefore()) {
            // New user Joins for first time celebratory firework
            Firework firework = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
            FireworkMeta fireworkmeta = firework.getFireworkMeta();
            fireworkmeta.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(true)
                    .with(FireworkEffect.Type.CREEPER)
                    .withColor(Color.GREEN)
                    .withFade(Color.BLUE)
                    .build());
            fireworkmeta.setPower(3);
            firework.setFireworkMeta(fireworkmeta);

            // Send new user a MOTD seperate to the main MOTD
            List<String> newplayermotd = plugin.configurationManager.getmotd().getStringList("newplayermotd");
            for (String s : newplayermotd) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
            event.getPlayer().sendMessage(" "); // Seperate between messages

            // Add +1 to the unique players in config.yml
            final int unique = plugin.getConfig().getInt("uniqueplayers");
            plugin.getConfig().set("uniqueplayers", unique + 1);
            plugin.saveConfig();
            // Broadcast new Unique player message to hub.
            Bukkit.broadcastMessage(ChatColor.YELLOW + event.getPlayer().getDisplayName() + ChatColor.BLUE + " joined for the first time!" + ChatColor.GRAY + " (" + ChatColor.BLUE + unique + ChatColor.GRAY + ")");
            event.getPlayer().sendMessage(" "); // Seperate between messages
        }

        //player.setGameMode(GameMode.ADVENTURE); // Set users gamemode to Adventure
        player.playSound(player.getLocation(), Arrays.asList(Sound.values()).get((int) (Math.random() * (Sound.values().length - 1))), 1f,1f); // Play random sound

        // Disable player collision.
//        LivingEntity livingentity = (LivingEntity) player;
        player.setCollidable(true);

        // If user has the fly permission, enable flight
        if (player.hasPermission("zanderhub.fly")) {
            player.setAllowFlight(true);
        }
    }
}
