package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.ChatLock;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.ReloadUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        final Player player = Bukkit.getServer().getPlayer(sender.getName());

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();
        final ReloadUtil reload = new ReloadUtil();
        final ChatLock chatlock = new ChatLock();

        final String permission = configuration.getString("reload-permission");
        final String lockPermission = configuration.getString("chatlock-permission");
        final String clearPermission = configuration.getString("chatclear-permission");

        final String noPermission = configuration.getString("no-permission");
        final String chatCleared = configuration.getString("chat-cleared");

        if (args.length > 0) {

            if (args[0].equals("reload")) {

                if (player.hasPermission(permission)) {

                    reload.ReloadCommand(sender);

                } else {

                    sender.sendMessage(formatting.format(noPermission));

                }
            } else if (args[0].equals("lock")) {

                if (sender instanceof Player) {

                    if (player.hasPermission(lockPermission)) {

                        String message = chatlock.chatLock(player);
                        Bukkit.broadcastMessage(message);

                    } else {

                        player.sendMessage(formatting.format(noPermission));

                    }

                } else {

                    String message = chatlock.chatLock(player);
                    Bukkit.broadcastMessage(message);

                }

            } else if (args[0].equals("clear")) {

                if (player.hasPermission(clearPermission)) {

                    for (Player tempPlayer : Bukkit.getOnlinePlayers())

                        for (int i = 0; i < 100; i++) {

                            player.sendMessage(" ");

                        }

                    Bukkit.broadcastMessage(formatting.format(chatCleared));

                } else {

                    player.sendMessage(formatting.format(noPermission));

                }

            }

        } else {

            sender.sendMessage(formatting.format("&6This feature is being worked on."));

        }

        return false;

    }

    public static HashMap<String, String> chatFlow = new HashMap<String, String>();

}
