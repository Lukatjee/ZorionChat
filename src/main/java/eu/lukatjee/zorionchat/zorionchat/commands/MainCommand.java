package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*

            Initialize a few things here.

         */

        final FileConfiguration config = ZorionChat.plugin.getConfig();
        final Format formatConverter = new Format();

        /*

            Predefine a couple of values here.

         */

        final String type = "configFormat";
        final String prefix = formatConverter.formatConversion(type, config.getString("prefix"), (Player) sender);

        if (args.length > 0) {

            /*

                Execute this block when /zorionchat reload is executed.

             */

            if (args[0].equals("reload")) {

                if (sender instanceof Player) {

                    final Player player = (Player) sender;

                    if (player.hasPermission("zorionchat.command.reload")) {

                        String message = config.getString("successfully-reloaded-plugin");
                        ZorionChat.plugin.reloadConfig();
                        player.sendMessage(prefix + formatConverter.formatConversion(type, message, player));

                    } else {

                        String message = config.getString("no-permission");
                        ZorionChat.plugin.reloadConfig();
                        player.sendMessage(prefix + formatConverter.formatConversion(type, message, player));

                    }

                } else {

                    ZorionChat.plugin.reloadConfig();
                    sender.sendMessage("[ZorionChat] Successfully reloaded this plugin.");

                }

            }

        /*

            Execute this block when no arguments are given.

         */

        } else {

            String message = config.getString("help-command");
            sender.sendMessage(prefix + formatConverter.formatConversion(type, message, (Player) sender));

        }

        return false;

    }

}
