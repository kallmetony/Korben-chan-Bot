package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.exception.AlreadyInExpeditionException;
import com.aaronr92.korben_chan_bot.exception.NotEnoughMoneyException;
import com.aaronr92.korben_chan_bot.exception.TankAlreadyInUserHangarException;
import com.aaronr92.korben_chan_bot.exception.TankNotFoundException;
import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.IOException;

public class ButtonService {
    private final UserService userService;
    private final EmbedFactory embedFactory;

    public ButtonService(UserService userService, EmbedFactory embedFactory) {
        this.userService = userService;
        this.embedFactory = embedFactory;
    }

    /**
     * Creates new expedition with specified tank
     * @param event an event
     */
    public void createExpedition(ButtonInteractionEvent event) {
        event.replyEmbeds(userService
                .createExpedition(event.getUser().getIdLong(),
                        event.getButton().getLabel()))
                .queue();
    }

    public void getUserTank(ButtonInteractionEvent event, String tankName) {
        event.replyEmbeds(embedFactory.getEmbed(EmbedFactory.Type.SELL_TANK, tankName))
                .addActionRow(Button.danger("Sell " + tankName, "Продать"))
                .setEphemeral(true)
                .queue();
    }

    public void sellTank(ButtonInteractionEvent event, String buttonId) {
        String tankName = buttonId.split(" ")[1];

        try {
            BotHttpClient.sellTank(event.getUser().getIdLong(), tankName);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (AlreadyInExpeditionException e) {
            event.replyEmbeds(embedFactory.getEmbed(EmbedFactory.Type.ALREADY_IN_EXPEDITION))
                    .setEphemeral(true)
                    .queue();
            return;
        } catch (TankNotFoundException e) {
            event.replyEmbeds(embedFactory.getEmbed(EmbedFactory.Type.TANK_NOT_FOUND))
                    .setEphemeral(true)
                    .queue();
            return;
        }

        User author = event.getUser();

        event.replyEmbeds(embedFactory.getEmbed(
                EmbedFactory.Type.SUCCESS,
                        author.getName(),
                        author.getAvatarUrl()
                ))
                .queue();
    }

    public void buy(ButtonInteractionEvent event, Button button) {
        String[] buttonId = button.getId().split(" ");
        switch (buttonId[1]) {
            case "Tank" -> {
                try {
                    BotHttpClient.buyTank(event.getUser().getIdLong(), button.getLabel());
                    event.replyEmbeds(embedFactory
                                    .getEmbed(EmbedFactory.Type.SUCCESS))
                            .setEphemeral(true)
                            .queue();
                } catch (NotEnoughMoneyException e) {
                    event.replyEmbeds(embedFactory
                                    .getEmbed(EmbedFactory.Type.NOT_ENOUGH_MONEY))
                            .setEphemeral(true)
                            .queue();
                } catch (TankAlreadyInUserHangarException e) {
                    event.replyEmbeds(embedFactory
                                    .getEmbed(EmbedFactory.Type.TANK_ALREADY_IN_HANGAR))
                            .setEphemeral(true)
                            .queue();
                } catch (IOException | InterruptedException ignored ) { }
            }
        }
    }
}
