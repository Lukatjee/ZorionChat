package eu.lukatjee.zorionchat.zorionchat.utils;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class VanishCheck {

    public boolean isVanished(Player player) {

        for (MetadataValue meta : player.getMetadata("vanished")) {

            if (meta.asBoolean()) return true;

        }

        return false;

    }

}
