package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class OnJoin implements Listener {

    /*

        This class handles the PlayerJoinEvent
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
    public void onJoin(PlayerJoinEvent event) {

        FileConfiguration config = ZorionChat.plugin.getConfig();
        Player player = event.getPlayer();

        /*

            Execute this block when player has joined before and is not vanished.

         */

        if (player.hasPlayedBefore() && !this.isVanished(player)) {

            String message = config.getString("join-message");

            if (message.equals("")) {

                event.setJoinMessage(null);

            } else {

                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message)));

            }

        /*

            Execute this block when player hasn't joined before.

         */

        } else if (!player.hasPlayedBefore()) {

            String message = config.getString("first-join-message");

            if (message.equals("")) {

                event.setJoinMessage("");

            } else {

                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message)));

            }

        /*

            Execute this block when player is vanished.

         */

        } else if (isVanished(player)) {

            event.setJoinMessage(null);

        }

    }

}
