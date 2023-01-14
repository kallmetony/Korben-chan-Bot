package com.aaronr92.korben_chan_bot.hander;

import com.aaronr92.korben_chan_bot.service.ButtonService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonHandler extends ListenerAdapter {

    private final ButtonService buttonService;

    public ButtonHandler(ButtonService buttonService) {
        this.buttonService = buttonService;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getId().startsWith("Tank"))
            buttonService.createExpedition(event);
    }
}
