package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.exception.ExpeditionNotFoundException;
import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.util.Collection;
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
        long userId = event.getMember().getIdLong();
        List<String> tanks = (List<String>) userService.getTankNames(userId);

        ReplyCallbackAction replyAction = event.replyEmbeds(userService.getUserInfo(userId));
        if (tanks.size() != 0) {
            Button[] buttons = new Button[tanks.size()];

            for (int i = 0; i < tanks.size(); i++) {
                String name = tanks.get(i);
                buttons[i] = Button.secondary("Hangar " + name, name);
            }

            replyAction.addActionRow(buttons);
        }

        replyAction
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
                .getTankNames(userId);

        if (tanks.size() == 0) {
            event.replyEmbeds(embedFactory.getEmbed(EmbedFactory.Type.NOT_ENOUGH_TANKS,
                            String.valueOf(userId)))
                    .setEphemeral(true)
                    .queue();
            return;
        }

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

    public void openShop(SlashCommandInteractionEvent event) {
        Collection<JsonObject> tanks = List.of(
                BotHttpClient.getTankById()
        );
        Collection<JsonObject> boxes = List.of(
                BotHttpClient.getBoxById()
        );
        Button[] tankButtons = new Button[tanks.size()];
        for (JsonObject tank :
                tanks) {
            // TODO: Add tankButtons in button
        }
        Button[] boxButtons = new Button[boxes.size()];
        for (JsonObject box :
                boxes) {
            // TODO: Add box in button
        }
        MessageEmbed embed = embedFactory.getShop(tanks, boxes);
        event.replyEmbeds(embed)
                .addActionRow(tankButtons)
                .addActionRow(boxButtons)
                .addActionRow(Button.secondary("Buy Slot", "Слот"))
                .queue();
    }
}
