package eu.lukatjee.zorionchat.zorionchat.commands;

import eu.lukatjee.zorionchat.zorionchat.utils.FormatterUtil;
import eu.lukatjee.zorionchat.zorionchat.utils.ReloadUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final ReloadUtil reload = new ReloadUtil();
        final FormatterUtil formatting = new FormatterUtil();

        if (args.length > 0) {

                if (args[0].equals("reload")) { reload.ReloadCommand(sender); }

        } else {

            sender.sendMessage(formatting.format("&6This feature is being worked on."));

        }

        return false;

    }

}
