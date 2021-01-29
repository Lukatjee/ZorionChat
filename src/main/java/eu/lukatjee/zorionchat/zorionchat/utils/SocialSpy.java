package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SocialSpy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // [0] Initial variables

        final Player player = Bukkit.getServer().getPlayer(sender.getName());
        final UUID playerUUID = player.getUniqueId();

        final ZorionChat plugin = ZorionChat.plugin;
        final FileConfiguration configuration = plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String value = socialSpyEnabled.get(playerUUID);
        final String socialSpyEnabledMessage = configuration.getString("socialspy-enabled");
        final String socialSpyDisabled = configuration.getString("socialspy-disabled");
        final String noPermission = configuration.getString("no-permission");
        final String socialSpyPermission = configuration.getString("socialspy-permission");

        // [1] Check if the staff member has enabled socialspy

        if (player.hasPermission(socialSpyPermission)) {

            if (value.equals("true")) {

                socialSpyEnabled.replace(playerUUID, "true", "false");
                player.sendMessage(formatting.format(socialSpyDisabled));

            } else {

                socialSpyEnabled.replace(playerUUID, "false", "true");
                player.sendMessage(formatting.format(socialSpyEnabledMessage));

            }

        } else {

            player.sendMessage(formatting.format(noPermission));

        }

        return false;

    }

    public static HashMap<UUID, String> socialSpyEnabled = new HashMap<UUID, String>();

    public static void socialSpyHandler(String permission, Player player, Player receiver, String message, String messageDisabled) {

        final ZorionChat plugin = ZorionChat.plugin;
        final FileConfiguration configuration = plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        for (Player tempPlayer : Bukkit.getOnlinePlayers()) {

            UUID tempPlayerUUID = tempPlayer.getUniqueId();

            if (tempPlayer.hasPermission(permission)) {

                if (socialSpyEnabled.get((tempPlayerUUID)).equals("true")) {

                    final String spyFormat = configuration.getString("socialspy-format").replace("{sender}", player.getName()).replace("{receiver}", receiver.getName());

                    if (player != tempPlayer && receiver != tempPlayer) {

                        tempPlayer.sendMessage(formatting.format(spyFormat + message));

                    }

                }

            } else {

                if (socialSpyEnabled.get((tempPlayerUUID)).equals("true")) {

                    socialSpyEnabled.replace(tempPlayerUUID, "true", "false");
                    tempPlayer.sendMessage(formatting.format(messageDisabled));

                }

            }

        }

    }

}
