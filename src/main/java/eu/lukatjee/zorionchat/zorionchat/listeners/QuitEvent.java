package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final Player player = event.getPlayer();

        final String quitMessage = configuration.getString("quit-message");

        final boolean vanish = new VanishCheck().isVanished(player);
        final FormatterUtil formatting = new FormatterUtil();

        if (!vanish && !quitMessage.equals("")) {

            event.setQuitMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, quitMessage)));

        } else if (vanish) {

            event.setQuitMessage(null);

        }

    }

}
