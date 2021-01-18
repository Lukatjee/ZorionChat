package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadUtil {

    final FormatterUtil formatting = new FormatterUtil();
    final FileConfiguration configuration = ZorionChat.plugin.getConfig();

    public void ReloadCommand(CommandSender sender) {

        if (sender instanceof Player) {

            final String playerName = sender.getName();

            if (sender.hasPermission("zorionchat.reload")) {

                sender.sendMessage(formatting.format(configuration.getString("reload-message")));
                System.out.println("[ZorionChat] Plugin was reloaded by " + playerName + ".");
                ZorionChat.plugin.reloadConfig();

            } else {

                sender.sendMessage(formatting.format(configuration.getString("no-permission")));

            }

        } else {

            System.out.println("[ZorionChat] Plugin was reloaded by console.");
            ZorionChat.plugin.reloadConfig();

        }

    }

}
