package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.commands.MessageCommand;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.SocialSpy;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuitEvent implements Listener {

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {

        final Player player = event.getPlayer();

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String quitMessage = configuration.getString("quit-message");

        final boolean vanish = new VanishCheck().isVanished(player);

        if (!vanish && !quitMessage.equals("")) {

            event.setQuitMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, quitMessage)));

        } else if (vanish) {

            event.setQuitMessage(null);

        }

        channelDataRemover(player);

    }

    private void channelDataRemover(Player player) {

        final UUID playerUUID = Bukkit.getServer().getPlayer(player.getName()).getUniqueId();
        JoinEvent.currentChannel.remove(playerUUID);
        MessageCommand.lastMessaged.remove(playerUUID);
        SocialSpy.socialSpyEnabled.remove(playerUUID);

    }

}
