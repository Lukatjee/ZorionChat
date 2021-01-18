package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final Player player = event.getPlayer();

        final String joinMessage = configuration.getString("join-message");
        final String firstJoinMessage = configuration.getString("first-join-message");

        final boolean vanish = new VanishCheck().isVanished(player);
        final FormatterUtil formatting = new FormatterUtil();


        if (player.hasPlayedBefore() && !vanish && !joinMessage.equals("")) {

            event.setJoinMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, joinMessage)).replace("%", "%%"));

            } else if (!player.hasPlayedBefore() && !firstJoinMessage.equals("")) {

                event.setJoinMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, firstJoinMessage)).replace("%", "%%"));

            } else if (vanish) {

                event.setJoinMessage(null);

        }

    }

}
