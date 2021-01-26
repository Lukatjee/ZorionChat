package eu.lukatjee.zorionchat.zorionchat.utils;

import org.bukkit.entity.Player;

public class PermissionCheck {

    // [0] Come on... is this so hard to understand?

    public boolean permission(Player player, String permission) {

        return player.hasPermission(permission);

    }

}
