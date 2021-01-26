package eu.lukatjee.zorionchat.zorionchat.listeners;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.SocialSpy;
import eu.lukatjee.zorionchat.zorionchat.utils.VanishCheck;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {

        // [0] Initial variables

        final Player player = event.getPlayer();

        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String joinMessage = configuration.getString("join-message");
        final String firstJoinMessage = configuration.getString("first-join-message");
        final boolean vanish = new VanishCheck().isVanished(player);

        // [1] Checks whether the player has joined before, that the player isn't vanished and whether the joinmessage hasn't been disabled

        if (player.hasPlayedBefore() && !vanish && !joinMessage.equals("")) {

            event.setJoinMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, joinMessage)));

            } else if (!player.hasPlayedBefore() && !firstJoinMessage.equals("")) {

                event.setJoinMessage(formatting.format(PlaceholderAPI.setPlaceholders(player, firstJoinMessage)));

            } else if (vanish) {

                event.setJoinMessage(null);

        }

        // [2] Sets all the players hashmaps to the default values

        channelMover(player);

    }

    // [3] Current channel hashmap made accessible in other classes

    public static HashMap<UUID, String> currentChannel = new HashMap<UUID, String>();

    // [2] Actual object that sets hashmap values to default

    private void channelMover(Player player) {

        final UUID playerUUID = Bukkit.getServer().getPlayer(player.getName()).getUniqueId();
        currentChannel.put(playerUUID, "global");
        SocialSpy.socialSpyEnabled.put(playerUUID, "false");

        }

}
