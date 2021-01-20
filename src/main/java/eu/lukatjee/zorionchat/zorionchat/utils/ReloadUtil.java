package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadUtil {

    final FileConfiguration configuration = ZorionChat.plugin.getConfig();
    final FormatterUtil formatting = new FormatterUtil();

    public void ReloadCommand(CommandSender sender) {

        if (sender instanceof Player) {

            final String player = sender.getName();
            final String permission = configuration.getString("reload-command");
            final boolean hasPermission = new PermissionCheck().permission(((Player) sender).getPlayer(), permission);

            if (hasPermission) {

                sender.sendMessage(formatting.format(configuration.getString("reload-message")));
                System.out.println("[ZorionChat] Plugin was reloaded by " + player + ".");
                ZorionChat.plugin.reloadConfig();

            } else { sender.sendMessage(formatting.format(configuration.getString("no-permission"))); }

        } else {

            System.out.println("[ZorionChat] Plugin was reloaded by console.");
            ZorionChat.plugin.reloadConfig();

        }

    }

}
