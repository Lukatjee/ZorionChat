package eu.lukatjee.zorionchat.zorionchat;

import eu.lukatjee.zorionchat.zorionchat.commands.MainCommand;
import eu.lukatjee.zorionchat.zorionchat.events.OnQuit;
import eu.lukatjee.zorionchat.zorionchat.events.OnJoin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZorionChat extends JavaPlugin implements Listener {

    public void onEnable() {

        /*

            Register events here.

         */

        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnQuit(), this);

        /*

            Register commands here.

         */

        getCommand("zorionchat").setExecutor(new MainCommand());

    }

}
