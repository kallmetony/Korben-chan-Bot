package com.aaronr92.korben_chan_bot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MessageListener extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        String message = event.getMessage().getContentDisplay();

//        if (message.equals(message.toUpperCase()) &&
//                message.length() > 4 &&
//                !message.matches("^[a-z0-9\\s\\-]*$") &&
//                !message.contains("@")) {
//            event.getMessage().reply("Не капси").queue();
//        }

        if (log.isDebugEnabled()) {
            log.debug("{} send: {}", event.getAuthor().getName(), message);
        }
    }

    @Deprecated
    public static String encodeMessage(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
