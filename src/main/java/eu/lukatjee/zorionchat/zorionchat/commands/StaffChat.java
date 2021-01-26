package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.listeners.JoinEvent;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.PermissionCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class StaffChat implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // [0] Initial variables

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID playerUUID = Bukkit.getServer().getPlayer(sender.getName()).getUniqueId();

        final HashMap<UUID, String> currentChannel = JoinEvent.currentChannel;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String value = currentChannel.get(playerUUID);

        final String staffChatEnabled = configuration.getString("staffchat-enabled");
        final String staffChatDisabled = configuration.getString("staffchat-disabled");
        final String noPermission = configuration.getString("no-permission");
        final String permission = configuration.getString("staffchat-permission");

        final boolean hasPermission = new PermissionCheck().permission(player, permission);

        // [1] Permission check

        if (hasPermission) {

            // [2] Checks whether the command has arguments or not

            if (args.length == 0) {

                // [3] Checks the players current channel and changes according to the current channel

                if (value.equals("global")) {

                    currentChannel.replace(playerUUID, "global", "staff");
                    sender.sendMessage(formatting.format(staffChatEnabled));

                } else {

                    currentChannel.replace(playerUUID, "staff", "global");
                    sender.sendMessage(formatting.format(staffChatDisabled));

                }

            // [2] When the command has multiple arguments the code below will be executed

            } else {

                final String format = PlaceholderAPI.setPlaceholders(player, formatting.format(configuration.getString("staffchat-format")));

                final String[] argsMessages = Arrays.copyOfRange(args, 0, args.length);
                final String message = String.join(" ", argsMessages);

                Bukkit.broadcast(formatting.format(format + message), permission);

            }

        // [0] Returns error when the player doesn't have permission

        } else {

            sender.sendMessage(formatting.format(noPermission));

        }

        return false;

    }
}
