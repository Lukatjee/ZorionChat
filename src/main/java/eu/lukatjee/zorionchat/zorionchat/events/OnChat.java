package eu.lukatjee.zorionchat.zorionchat.events;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.Format;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        /*

            Initialize a few things here.

         */

        final Format formatConverter = new Format();
        final FileConfiguration config = ZorionChat.plugin.getConfig();

        /*

            Predefine a couple of values here.

         */

        final Player player = event.getPlayer();
        final String message = event.getMessage();

        /*

            Format the chatformat entered in config.yml.

         */

        String type = "configFormat";
        final String loadedFormat = config.getString("chat-format");
        final String format = formatConverter.formatConversion(type, loadedFormat, player);


        /*

            This block replaces the default minecraft chatformat.

         */

        type = "chatFormat";
        final String convertedMessage = formatConverter.formatConversion(type, message, player);

        event.setFormat(format + convertedMessage);

    }

}
