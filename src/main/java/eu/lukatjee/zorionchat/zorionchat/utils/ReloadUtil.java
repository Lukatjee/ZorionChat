package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadUtil {

    public void ReloadCommand(CommandSender sender) {

        // [0] Initial variables

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();
        final ZorionChat plugin = ZorionChat.plugin;

        // [1] Checks whether the sender is a player or not

        if (sender instanceof Player) {

            // [0] Initial variables

            final String player = sender.getName();

            sender.sendMessage(formatting.format(configuration.getString("reload-message")));
            System.out.println("[ZorionChat] Plugin was reloaded by " + player + ".");
            plugin.reloadConfig();

        // [1] Handles the reload command for the console

        } else {

            System.out.println("[ZorionChat] Plugin was reloaded by console.");
            plugin.reloadConfig();

        }

    }

}
