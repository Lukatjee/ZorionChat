package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.Format;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class OnQuit implements Listener {

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {

        /*

            Initialize a few things here.

         */

        FileConfiguration config = ZorionChat.plugin.getConfig();
        VanishCheck vanish = new VanishCheck();
        Format formatConverter = new Format();

        /*

            Predefine a couple of values here.

         */

        Player player = event.getPlayer();
        String type = "configFormat";

        /*

            Execute this block when the player is not vanished.

         */

        if (!vanish.isVanished(player)) {

            String message = config.getString("leave-message");

            if (message.equals("")) {

                event.setQuitMessage(null);

            } else {

                event.setQuitMessage(formatConverter.formatConversion(type, message, player));

            }

        /*

            Execute this block when the player is vanished.

         */

        } else {

            event.setQuitMessage(null);

        }

    }

}
