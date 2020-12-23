package eu.lukatjee.zorionchat.zorionchat.utils;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class VanishCheck {

    /*

        Return true/false whether the player is vanished or not.

     */

    public boolean isVanished(final Player player) {

        for (final MetadataValue meta : player.getMetadata("vanished")) {

            if (meta.asBoolean()) {

                return true;

            }

        }

        return false;

    }

}
