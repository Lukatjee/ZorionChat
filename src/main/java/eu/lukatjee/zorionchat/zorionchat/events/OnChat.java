package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();
        FileConfiguration config = ZorionChat.plugin.getConfig();

        /*

            Format the chatformat entered in config.yml.

         */

        String loadedFormat = config.getString("chat-format");
        String coloredFormat = ChatColor.translateAlternateColorCodes('&', loadedFormat);
        String format = PlaceholderAPI.setPlaceholders(player, coloredFormat);

        /*

            This block replaces the default minecraft chatformat.

         */

        if (!player.hasPermission("zorionchat.chat.color")) {

            event.setFormat(format + message);

        } else {

            event.setFormat(format + ChatColor.translateAlternateColorCodes('&', message));

        }

    }

}
