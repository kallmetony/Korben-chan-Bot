package com.aaronr92.korben_chan_bot.hander;

import com.aaronr92.korben_chan_bot.service.ButtonService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonHandler extends ListenerAdapter {

    private final ButtonService buttonService;

    public ButtonHandler(ButtonService buttonService) {
        this.buttonService = buttonService;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Button button = event.getButton();
        String buttonId = button.getId();
        if (buttonId.startsWith("Tank"))
            buttonService.createExpedition(event);
        else if (buttonId.startsWith("Hangar"))
            buttonService.getUserTank(event, button.getLabel());
        else if (buttonId.startsWith("Sell"))
            buttonService.sellTank(event, button.getId());
        else if (buttonId.startsWith("Buy"))
            buttonService.buy(event, button);
    }
}
