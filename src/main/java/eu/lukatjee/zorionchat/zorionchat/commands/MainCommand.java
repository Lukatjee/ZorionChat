package eu.lukatjee.zorionchat.zorionchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equals("reload")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("zorionchat.command.reload")) {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9ZorionChat&8]&a Successfully reloaded this plugin."));

                } else {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9ZorionChat&8]&c It appears that you don't have the permission to execute this command."));

                }

            } else {

                sender.sendMessage("[ZorionChat] Successfully reloaded this plugin.");

            }

        }

        return false;

    }

}
