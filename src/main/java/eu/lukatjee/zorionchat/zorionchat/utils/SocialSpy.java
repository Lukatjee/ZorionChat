package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SocialSpy {

    public static void socialSpy(Player player) {

        // [0] Initial variables

        final UUID playerUUID = player.getUniqueId();

        final ZorionChat plugin = ZorionChat.plugin;
        final FileConfiguration configuration = plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String value = socialSpyEnabled.get(playerUUID);
        final String socialSpyEnabledMessage = configuration.getString("socialspy-enabled");
        final String socialSpyDisabled = configuration.getString("socialspy-disabled");

        // [1] Check if the staff member has enabled socialspy

        if (value.equals("true")) {

            socialSpyEnabled.replace(playerUUID, "true", "false");
            player.sendMessage(formatting.format(socialSpyDisabled));

        } else {

            socialSpyEnabled.replace(playerUUID, "false", "true");
            player.sendMessage(formatting.format(socialSpyEnabledMessage));

        }

    }

    public static HashMap<UUID, String> socialSpyEnabled = new HashMap<UUID, String>();

}
