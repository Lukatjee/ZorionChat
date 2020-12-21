package eu.lukatjee.zorionchat.zorionchat.events;

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

        final Player player = event.getPlayer();
        final String username = player.getName();

        if (!this.isVanished(player)) {

            event.setQuitMessage("&8[&c&l-&8]&6 " + username + "&7 left the server.");

        } else {

            event.setQuitMessage(null);

        }

    }

}
