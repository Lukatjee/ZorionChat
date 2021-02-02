package eu.lukatjee.zorionchat.zorionchat.utils;

import eu.lukatjee.zorionchat.zorionchat.ZorionChat;
import eu.lukatjee.zorionchat.zorionchat.commands.MainCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChatLock {

    public String chatLock(Player player) {

        final HashMap<String, String> chatFlow = MainCommand.chatFlow;
        final FileConfiguration configuration = ZorionChat.plugin.getConfig();
        final FormatterUtil formatting = new FormatterUtil();

        final String chatLocked = configuration.getString("chat-locked");
        final String chatUnlocked = configuration.getString("chat-unlocked");

        if (chatFlow.get("chat") == null) {

            chatFlow.put("chat", "locked");
            return formatting.format(chatLocked);

        } else if (chatFlow.get("chat").equals("locked")) {

            chatFlow.replace("chat", "locked", "unlocked");
            return formatting.format(chatUnlocked);

        } else if (chatFlow.get("chat").equals("unlocked")) {

            chatFlow.replace("chat", "unlocked", "locked");
            return formatting.format(chatLocked);

        }

        return null;

    }

}
