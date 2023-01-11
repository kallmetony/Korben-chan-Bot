package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Random;
import java.util.Set;

public class CommandService {

    private final UserService userService;
    private final EmbedFactory embedFactory;
    private final Random random = new Random();
    private final Set<String> shipUsers = Set.of("597911681720647726",
            "494204801761148928");

    public CommandService(UserService userService, EmbedFactory embedFactory) {
        this.userService = userService;
        this.embedFactory = embedFactory;
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

    public void ship(SlashCommandInteractionEvent event) {
        User u1 = event.getOption("пользователь_1").getAsUser();
        User u2 = event.getOption("пользователь_2").getAsUser();

        int percent = random.nextInt(101);

        if (shipUsers.contains(u1.getId()) &&
                shipUsers.contains(u2.getId())) {
            percent = 100;
        }

        event.replyEmbeds(embedFactory
                .getEmbed(EmbedFactory.Type.SHIP, String.valueOf(percent)))
                .queue();
    }
}
