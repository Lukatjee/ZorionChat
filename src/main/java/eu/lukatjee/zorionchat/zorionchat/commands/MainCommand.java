package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.PermissionCheck;
import eu.lukatjee.zorionchat.zorionchat.utils.ReloadUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        // Initial variables

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final ReloadUtil reload = new ReloadUtil();
        final FormatterUtil formatting = new FormatterUtil();
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final String permission = configuration.getString("reload-permission");
        final boolean hasPermission = new PermissionCheck().permission(player, permission);

        // [0] Check if arguments are given

        if (args.length > 0) {

            // [1] Executes the reload class, permission check is not handled here

            if (args[0].equals("reload")) {

                // [2] Checks if player has permission to execute the reload command

                if (hasPermission) {

                    reload.ReloadCommand(sender);

                // [2] Returns error message when the player doesn't have permission

                } else {

                    sender.sendMessage(formatting.format(configuration.getString("no-permission")));

                }
            }

        // [0] Returns list of commands when no arguments are given

        } else {

            sender.sendMessage(formatting.format("&6This feature is being worked on."));

        }

        return false;

    }

}
