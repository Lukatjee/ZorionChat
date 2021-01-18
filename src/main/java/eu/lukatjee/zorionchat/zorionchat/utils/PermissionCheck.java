package eu.lukatjee.zorionchat.zorionchat.utils;

import org.bukkit.entity.Player;

public class PermissionCheck {

    public boolean permission(Player player, String permission) {

        return player.hasPermission(permission);

    }

}
