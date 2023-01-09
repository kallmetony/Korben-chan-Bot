package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class CommandService {

    private final UserService userService;


    public CommandService(UserService userService) {
        this.userService = userService;
    }

    public void openBox(SlashCommandInteractionEvent event) {
        event.replyEmbeds(userService
                .openBox(event.getMember().getIdLong()))
                .setEphemeral(false)
                .queue();
    }

    public void getUserInfo(SlashCommandInteractionEvent event) {
        event.replyEmbeds(userService.getUserInfo(event.getMember().getIdLong()))
                .setEphemeral(false)
                .queue();
    }
}
