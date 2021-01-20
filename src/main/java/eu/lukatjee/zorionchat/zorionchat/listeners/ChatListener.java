package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.PermissionCheck;
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

        final String value = currentChannel.get(playerUUID);
        final String globalFormat = configuration.getString("chat-format");
        final String staffFormat = configuration.getString("staffchat-format");
        final String staffchatDisabled = configuration.getString("staffchat-disabled");
        final String message = event.getMessage().replace("%", "%%");
        final String permissionFormat = configuration.getString("format-permission");
        final String permissionStaff = configuration.getString("staffchat-permission");

        final boolean hasFormatPermission = new PermissionCheck().permission(player, permissionFormat);
        final boolean hasStaffPermission = new PermissionCheck().permission(player, permissionStaff);

        // Converts the message for the chat.

        if (value != null) {

            if (value.equals("global")) {

                String format = formatting.format(PlaceholderAPI.setPlaceholders(player, globalFormat)).replace("%", "%%");

                if (hasFormatPermission) {

                    event.setFormat(format + formatting.format(message));

                } else {

                    event.setFormat(format + message);

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

}
