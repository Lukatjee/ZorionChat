package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.PermissionCheck;
import eu.lukatjee.zorionchat.zorionchat.utils.SocialSpy;
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

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // [0] Initial variables

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID senderUUID = player.getUniqueId();

        final HashMap<UUID, UUID> lastMessaged = MessageCommand.lastMessaged;
        final HashMap<UUID, String> socialSpyEnabled = SocialSpy.socialSpyEnabled;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final UUID value = lastMessaged.get(senderUUID);

        final String permission = configuration.getString("reply-permission");
        final String noPermission = configuration.getString("no-permission");
        final String socialSpyPermission = configuration.getString("socialspy-permission");
        final String formattingPermission = configuration.getString("format-permission");
        final String noArgumentsMessage = configuration.getString("incorrect-arguments");
        final String noRecentMessage = configuration.getString("no-recent-msg");
        final String socialSpyDisabled = configuration.getString("socialspy-disabled");

        final boolean hasReplyPermission = new PermissionCheck().permission(player, permission);
        final boolean hasFormattingPermission = new PermissionCheck().permission(player, formattingPermission);

        // [1] Permission check

        if (hasReplyPermission) {

            // [2] Checks if enough arguments are given

            if (args.length == 0) {

                sender.sendMessage(formatting.format(noArgumentsMessage));

            // [2] Executed when 2 or more arguments have been entered

            } else {

                // [3] Check whether the sender has a hashmap value

                if (value != null) {

                    // [0] Initial variables

                    final Player receiver = Bukkit.getServer().getPlayer(value);
                    final UUID receiverUUID = receiver.getUniqueId();
                    final String formatSender = configuration.getString("msg-format-sender").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());
                    final String formatReceiver = configuration.getString("msg-format-receiver").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());
                    final String[] argsMessages = Arrays.copyOfRange(args, 0, args.length);
                    final String message = String.join(" ", argsMessages);

                    // [4] Checks if sender has permission to format their messages in general

                    if (hasFormattingPermission) {

                        player.sendMessage(formatting.format(formatSender + message));
                        receiver.sendMessage(formatting.format(formatReceiver + message));

                    // [4] Returns non-formatted chatmessage except for the general chatformat set in config.yml

                    } else {

                        player.sendMessage(formatting.format(formatSender) + message);
                        receiver.sendMessage(formatting.format(formatReceiver) + message);

                    }

                    // [5] Puts the values in hashmaps so players can from then on use /reply or /r and sends message to staff members who enabled socialspy

                    lastMessaged.put(senderUUID, receiverUUID);
                    lastMessaged.put(receiverUUID, senderUUID);

                    SocialSpy.socialSpyHandler(socialSpyPermission, player, receiver, message, socialSpyDisabled);

                // [3] Returns error when the player hasn't messaged anyone recently

                } else {

                    player.sendMessage(formatting.format(noRecentMessage));

                }

            }

        // [1] Returns error when the player doesn't have permission

        } else {

            player.sendMessage(formatting.format(noPermission));

        }

        return false;
    }
}
