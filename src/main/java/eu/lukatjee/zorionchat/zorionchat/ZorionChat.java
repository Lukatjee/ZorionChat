package eu.lukatjee.zorionchat.zorionchat;

import eu.lukatjee.zorionchat.zorionchat.events.OnQuit;
import eu.lukatjee.zorionchat.zorionchat.events.OnJoin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZorionChat extends JavaPlugin implements Listener {

    public void onEnable() {

        /*

            Register events here.
            These are external events.

         */

        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnQuit(), this);

    }

}
