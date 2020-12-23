package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.Format;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        /*

            Initialize a few things here.

         */

        final FileConfiguration config = ZorionChat.plugin.getConfig();
        final VanishCheck vanish = new VanishCheck();
        final Format formatConverter = new Format();

        /*

            Predefine a couple of values here.

         */

        final String type = "configFormat";
        final Player player = event.getPlayer();

        /*

            Execute this block when player has joined before and is not vanished.

         */

        if (player.hasPlayedBefore() && !vanish.isVanished(player)) {

            String message = config.getString("join-message");

            if (message.equals("")) {

                event.setJoinMessage(null);

            } else {

                event.setJoinMessage(formatConverter.formatConversion(type, message, player));

            }

        /*

            Execute this block when player hasn't joined before.

         */

        } else if (!player.hasPlayedBefore()) {

            String message = config.getString("first-join-message");

            if (message.equals("")) {

                event.setJoinMessage("");

            } else {

                event.setJoinMessage(formatConverter.formatConversion(type, message, player));

            }

        /*

            Execute this block when player is vanished.

         */

        } else if (vanish.isVanished(player)) {

            event.setJoinMessage(null);

        }

    }

}
