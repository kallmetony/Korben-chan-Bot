package com.aaronr92.korben_chan_bot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MessageListener extends ListenerAdapter {

    public static Map<String, String> keywords = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        if (keywords.containsKey(message.toLowerCase())) {
            event.getChannel().sendMessage(keywords.get(message.toLowerCase())).queue();
        }
        if (message.equals(message.toUpperCase()) &&
                message.length() > 4 &&
                !message.matches("^[a-z0-9\\s\\-]*$") &&
                !message.contains("@")) {
            event.getMessage().reply("Не капси уёбина").queue();
        }
        log.info(message);
    }

    @Deprecated
    public static String encodeMessage(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
