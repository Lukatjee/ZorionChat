package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadUtil {

    public void ReloadCommand(CommandSender sender) {

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();
        final ZorionChat plugin = ZorionChat.plugin;

        if (sender instanceof Player) {

            final String player = sender.getName();

            sender.sendMessage(formatting.format(configuration.getString("reload-message")));
            System.out.println("[ZorionChat] Plugin was reloaded by " + player + ".");

        } else {

            System.out.println("[ZorionChat] Plugin was reloaded by console.");

        }

        plugin.reloadConfig();

    }

}
