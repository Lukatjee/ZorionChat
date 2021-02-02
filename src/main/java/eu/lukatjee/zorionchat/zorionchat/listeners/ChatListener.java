package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.commands.MainCommand;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();

        final HashMap<UUID, String> currentChannel = JoinEvent.currentChannel;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();
        final HashMap<String, String> chatLock = MainCommand.chatFlow;

        final String value = currentChannel.get(playerUUID);

        final String permissionFormat = configuration.getString("format-permission");
        final String permissionStaff = configuration.getString("staffchat-permission");
        final String chatLockPermission = configuration.getString("chatlock-permission");

        final String globalFormat = configuration.getString("chat-format");
        final String staffFormat = configuration.getString("staffchat-format");
        final String staffchatDisabled = configuration.getString("staffchat-disabled");
        final String message = event.getMessage().replace("%", "%%");

        if (value.equals("global")) {

            String format = formatting.format(PlaceholderAPI.setPlaceholders(player, globalFormat)).replace("%", "%%");

            if (chatLock.get("chat") == null || chatLock.get("chat").equals("unlocked")) {

                event.setCancelled(true);

                for (Player tempPlayer : Bukkit.getOnlinePlayers()) {

                    if (player.hasPermission(permissionFormat)) {

                        tempPlayer.sendMessage(format + formatting.format(message));

                    } else {

                        tempPlayer.sendMessage(format + message);

                    }

                }

            } else if (player.hasPermission(chatLockPermission)) {

                for (Player tempPlayer : Bukkit.getOnlinePlayers()) {

                    if (player.hasPermission(permissionFormat)) {

                        tempPlayer.sendMessage(format + formatting.format(message));

                    } else {

                        tempPlayer.sendMessage(format + message);

                    }

                }

            } else {

                event.setCancelled(true);

            }

        } else if (value.equals("staff")) {

            String format = formatting.format(PlaceholderAPI.setPlaceholders(player, staffFormat));
            event.setCancelled(true);

            if (player.hasPermission(permissionStaff)) {

                Bukkit.broadcast(format + formatting.format(message), permissionStaff);

            } else {

                currentChannel.replace(playerUUID, "staff", "global");
                player.sendMessage(formatting.format(staffchatDisabled));

            }

        }

    }

}
