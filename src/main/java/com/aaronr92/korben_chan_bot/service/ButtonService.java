package com.aaronr92.korben_chan_bot.service;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonService {
    private final UserService userService;

    public ButtonService(UserService userService) {
        this.userService = userService;
    }

    public void createExpedition(ButtonInteractionEvent event) {
        event.replyEmbeds(userService
                .createExpedition(event.getUser().getIdLong(),
                        event.getButton().getLabel()))
                .queue();
    }
}
