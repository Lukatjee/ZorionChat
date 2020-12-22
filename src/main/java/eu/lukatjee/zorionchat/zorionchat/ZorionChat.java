package eu.lukatjee.zorionchat.zorionchat;

import eu.lukatjee.zorionchat.zorionchat.commands.MainCommand;
import eu.lukatjee.zorionchat.zorionchat.events.OnQuit;
import eu.lukatjee.zorionchat.zorionchat.events.OnJoin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZorionChat extends JavaPlugin implements Listener {

    public static ZorionChat plugin;

    public void onEnable() {

        plugin = this;

        /*

            Register events here.

         */

        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnQuit(), this);

        /*

            Register commands here.

         */

        getCommand("zorionchat").setExecutor(new MainCommand());

        /*

            Get configuration file.

         */

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        /*

            Check whether placeholderapi is installed or not.

         */

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            Bukkit.getPluginManager().registerEvents(this, this);

        } else {

            getLogger().info("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);

        }

    }

}
