package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.listeners.JoinEvent;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
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

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID playerUUID = Bukkit.getServer().getPlayer(sender.getName()).getUniqueId();

        final HashMap<UUID, String> currentChannel = JoinEvent.currentChannel;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String value = currentChannel.get(playerUUID);

        final String permission = configuration.getString("staffchat-permission");

        final String staffChatEnabled = configuration.getString("staffchat-enabled");
        final String staffChatDisabled = configuration.getString("staffchat-disabled");
        final String noPermission = configuration.getString("no-permission");

        if (player.hasPermission(permission)) {

            if (args.length == 0) {

                if (value.equals("global")) {

                    currentChannel.replace(playerUUID, "global", "staff");
                    sender.sendMessage(formatting.format(staffChatEnabled));

                } else {

                    currentChannel.replace(playerUUID, "staff", "global");
                    sender.sendMessage(formatting.format(staffChatDisabled));

                }

            } else {

                final String format = PlaceholderAPI.setPlaceholders(player, formatting.format(configuration.getString("staffchat-format")));

                final String[] argsMessages = Arrays.copyOfRange(args, 0, args.length);
                final String message = String.join(" ", argsMessages);

                Bukkit.broadcast(formatting.format(format + message), permission);

            }

        } else {

            sender.sendMessage(formatting.format(noPermission));

        }

        return false;

    }
}
