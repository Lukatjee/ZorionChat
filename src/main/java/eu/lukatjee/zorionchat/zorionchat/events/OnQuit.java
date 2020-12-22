package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class OnQuit implements Listener {

    /*

        This class handles the PlayerQuitEvent
        This also adds support for vanish plugins.

     */

    private boolean isVanished(final Player player) {

        for (final MetadataValue meta : player.getMetadata("vanished")) {

            if (meta.asBoolean()) {

                return true;

            }

        }

        return false;

    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {

        FileConfiguration config = ZorionChat.plugin.getConfig();
        Player player = event.getPlayer();

        if (!this.isVanished(player)) {

            String message = config.getString("leave-message");

            if (message.equals("")) {

                event.setQuitMessage(null);

            } else {

                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message)));

            }

        } else {

            event.setQuitMessage(null);

        }

    }

}
