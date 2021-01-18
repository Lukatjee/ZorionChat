package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.PermissionCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final String message = event.getMessage().replace("%", "%%");
        final String chatFormat = ZorionChat.plugin.getConfig().getString("chat-format");
        final boolean hasPermission = new PermissionCheck().permission(player, "chat-format");

        final FormatterUtil formatting = new FormatterUtil();

        // Converts the message for the chat.
        String format = formatting.format(PlaceholderAPI.setPlaceholders(player, chatFormat)).replace("%", "%%");

        // Sets the format for the chat.
        if (hasPermission) { event.setFormat(format + formatting.format(message)); } else { event.setFormat(format + message); }

    }

}
