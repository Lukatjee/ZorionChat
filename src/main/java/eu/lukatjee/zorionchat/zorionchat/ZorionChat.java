package eu.lukatjee.zorionchat.zorionchat;

import eu.lukatjee.zorionchat.zorionchat.commands.MainCommand;
import eu.lukatjee.zorionchat.zorionchat.commands.MessageCommand;
import eu.lukatjee.zorionchat.zorionchat.commands.ReplyCommand;
import eu.lukatjee.zorionchat.zorionchat.commands.StaffChat;
import eu.lukatjee.zorionchat.zorionchat.listeners.ChatListener;
import eu.lukatjee.zorionchat.zorionchat.listeners.JoinEvent;
import eu.lukatjee.zorionchat.zorionchat.listeners.QuitEvent;
import eu.lukatjee.zorionchat.zorionchat.utils.SocialSpy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZorionChat extends JavaPlugin implements Listener {

    public static ZorionChat plugin;

    public void onEnable() {

        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);

        getCommand("zorionchat").setExecutor(new MainCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("socialspy").setExecutor(new SocialSpy());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            Bukkit.getPluginManager().registerEvents(this, this);

        } else {

            getLogger().info("Could not find PlaceholderAPI! " + "This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);

        }

    }

}
