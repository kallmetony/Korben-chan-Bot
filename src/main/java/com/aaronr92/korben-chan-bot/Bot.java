package com.aaronr92;

import com.aaronr92.listeners.MessageListener;
import com.aaronr92.listeners.ReadyListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    public static final String TOKEN = "OTE0NTg5NDI3NjAxMzk1NzMz.GhCCg-.YZyQWexUK3EkUhsnnmlKOLXfixQ1oJTrq2ng34";

    public static void main(String[] args) throws LoginException {

        JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.playing("World of Tanks"))
                .addEventListeners(new ReadyListener(), new MessageListener())
                .build();
    }
}
