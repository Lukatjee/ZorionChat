package eu.lukatjee.zorionchat.zorionchat.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Format {

    public String formatConversion(String type, String input, Player player) {

        /*

            Convert messages that are sent by a player.

         */

        if (type.equals("chatFormat")) {

            if (player.hasPermission("zorionchat.chat.format")) {

                return ChatColor.translateAlternateColorCodes('&', input);

            } else {

                return input;

            }

        /*

            Convert messages that are defined in the config.yml.

         */

        } else if (type.equals("configFormat")) {

            return PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', input));

        }

        return "";

    }

}
