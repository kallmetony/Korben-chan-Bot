package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.exception.ExpeditionNotFoundException;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
import java.util.Random;

public class CommandService {

    private final UserService userService;
    private final EmbedFactory embedFactory;
    private final Random random = new Random();

    public CommandService(UserService userService, EmbedFactory embedFactory) {
        this.userService = userService;
        this.embedFactory = embedFactory;
    }

    /**
     * Opens a box
     * @param event an event
     */
    public void openBox(SlashCommandInteractionEvent event) {
        event.replyEmbeds(userService
                .openBox(event.getMember().getIdLong()))
                .setEphemeral(false)
                .queue();
    }

    /**
     * Gets user info
     * @param event an event
     */
    public void getUserInfo(SlashCommandInteractionEvent event) {
        event.replyEmbeds(userService.getUserInfo(event.getMember().getIdLong()))
                .setEphemeral(false)
                .queue();
    }

    /**
     * Ships one user to another returning a compatibility between them
     * @param event an event
     */
    public void ship(SlashCommandInteractionEvent event) {
        User u1 = event.getOption("пользователь_1").getAsUser();
        User u2 = event.getOption("пользователь_2").getAsUser();

        int percent = random.nextInt(101);

        event.replyEmbeds(embedFactory
                .getEmbed(EmbedFactory.Type.SHIP,
                        String.valueOf(percent),
                        u1.getName(),
                        u2.getName()
                ))
                .queue();
    }

    /**
     * Starts expedition or gets remaining time
     * @param event an event
     */
    public void expedition(SlashCommandInteractionEvent event) {
        long userId = event.getMember().getIdLong();

        try {
            event.replyEmbeds(userService.getExpedition(userId))
                    .queue();
            return;
        } catch (ExpeditionNotFoundException ignored) { }

        List<String> tanks = (List<String>) userService
                .getTanksNames(userId);

        if (tanks.size() == 0)
            return;

        ItemComponent[] buttons = new ItemComponent[tanks.size()];

        for (int i = 0; i < tanks.size(); i++) {
            String name = tanks.get(i);
            buttons[i] = Button.primary("Tank " + name, name);
        }

        event.replyEmbeds(userService.expeditionCreationEmbed(userId))
                .addActionRow(buttons)
                .setEphemeral(true)
                .queue();
    }

    /**
     * Sends help message
     * @param event an event
     */
    public void help(SlashCommandInteractionEvent event) {
        event.replyEmbeds(embedFactory.getEmbed(EmbedFactory.Type.HELP))
                .setEphemeral(false)
                .queue();
    }
}
