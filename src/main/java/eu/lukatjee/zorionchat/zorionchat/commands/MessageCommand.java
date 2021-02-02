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

public class MessageCommand implements CommandExecutor {

    // [0] Initial variables

    public static HashMap<UUID, UUID> lastMessaged = new HashMap<UUID, UUID>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID senderUUID = player.getUniqueId();

        final HashMap<UUID, String> socialSpyEnabled = SocialSpy.socialSpyEnabled;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String permission = configuration.getString("msg-permission");
        final String formattingPermission = configuration.getString("format-permission");

        final String noArgumentsMessage = configuration.getString("incorrect-arguments");
        final String cantFindPlayer = configuration.getString("invalid-player");
        final String noPermission = configuration.getString("no-permission");

        if (player.hasPermission(permission)) {

            if (args.length == 0 || args.length == 1) {

                sender.sendMessage(formatting.format(noArgumentsMessage));

            } else {

                if (Bukkit.getServer().getPlayer(args[0]) instanceof Player) {

                    final Player receiver = Bukkit.getServer().getPlayer(args[0]);
                    final UUID receiverUUID = receiver.getUniqueId();

                    final String formatSender = configuration.getString("msg-format-sender").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());
                    final String formatReceiver = configuration.getString("msg-format-receiver").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());
                    final String socialSpyDisabled = configuration.getString("socialspy-disabled");

                    final String socialSpyPermission = configuration.getString("socialspy-permission");

                    final String[] argsMessages = Arrays.copyOfRange(args, 1, args.length);
                    final String message = String.join(" ", argsMessages);

                    if (player.hasPermission(formattingPermission)) {

                        receiver.sendMessage(formatting.format(formatReceiver + message));
                        player.sendMessage(formatting.format(formatSender + message));

                    } else {

                        receiver.sendMessage(formatting.format(formatReceiver) + message);

                    }

                    lastMessaged.put(senderUUID, receiverUUID);
                    lastMessaged.put(receiverUUID, senderUUID);

                    SocialSpy.socialSpyHandler(socialSpyPermission, player, receiver, message, socialSpyDisabled);

                } else {

                    player.sendMessage(formatting.format(cantFindPlayer));

                }

            }

        } else {

            player.sendMessage(formatting.format(noPermission));

        }

        return false;

    }

}
