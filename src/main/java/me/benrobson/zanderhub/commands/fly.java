package me.benrobson.zanderhub.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class fly implements CommandExecutor {
    public static List<Player> flying = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (sender.hasPermission("zanderhub.fly")) {
            Player player = (Player) sender;
            toggleUserFly(player);
        }
        return true;
    }

    private boolean toggleUserFly(Player player) {
        if (player == null) return false;

        boolean flyable = player.getAllowFlight();

        player.setAllowFlight(!flyable);
        player.setFlying(!flyable);
        player.sendMessage(ChatColor.GREEN + "You can " + (!flyable ? "now" : "no longer") + " fly.");

        return true;
    }
}
