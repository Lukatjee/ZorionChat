package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
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

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID senderUUID = player.getUniqueId();

        final HashMap<UUID, UUID> lastMessaged = MessageCommand.lastMessaged;
        final HashMap<UUID, String> socialSpyEnabled = SocialSpy.socialSpyEnabled;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final UUID value = lastMessaged.get(senderUUID);

        final String permission = configuration.getString("reply-permission");
        final String socialSpyPermission = configuration.getString("socialspy-permission");
        final String formattingPermission = configuration.getString("format-permission");
        final String noPermission = configuration.getString("no-permission");
        final String noArgumentsMessage = configuration.getString("incorrect-arguments");
        final String noRecentMessage = configuration.getString("no-recent-msg");
        final String socialSpyDisabled = configuration.getString("socialspy-disabled");

        if (player.hasPermission(permission)) {

            if (args.length == 0) {

                sender.sendMessage(formatting.format(noArgumentsMessage));

            } else {

                if (value != null) {

                    final Player receiver = Bukkit.getServer().getPlayer(value);
                    final UUID receiverUUID = receiver.getUniqueId();

                    final String formatSender = configuration.getString("msg-format-sender").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());
                    final String formatReceiver = configuration.getString("msg-format-receiver").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());

                    final String[] argsMessages = Arrays.copyOfRange(args, 0, args.length);
                    final String message = String.join(" ", argsMessages);

                    if (player.hasPermission(formattingPermission)) {

                        player.sendMessage(formatting.format(formatSender + message));
                        receiver.sendMessage(formatting.format(formatReceiver + message));

                    } else {

                        player.sendMessage(formatting.format(formatSender) + message);
                        receiver.sendMessage(formatting.format(formatReceiver) + message);

                    }

                    lastMessaged.put(senderUUID, receiverUUID);
                    lastMessaged.put(receiverUUID, senderUUID);

                    SocialSpy.socialSpyHandler(socialSpyPermission, player, receiver, message, socialSpyDisabled);

                } else {

                    player.sendMessage(formatting.format(noRecentMessage));

                }

            }

        } else {

            player.sendMessage(formatting.format(noPermission));

        }

        return false;
    }
}
