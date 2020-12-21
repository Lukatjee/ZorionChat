package eu.lukatjee.zorionchat.zorionchat.events;

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
    public void onJoin(final PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        final String username = player.getName();

        if (player.hasPlayedBefore() && !this.isVanished(player)) {

            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&l+&8]&6 " + username + "&7 joined the server."));

        } else if (!player.hasPlayedBefore()) {

            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&l+&8]&6 " + username + "&7 joined the server for the&6 first&7 time."));

        } else if (this.isVanished(player)) {

            event.setJoinMessage(null);

        }

    }

}
