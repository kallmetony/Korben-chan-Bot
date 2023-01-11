package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.service.ButtonService;
import com.aaronr92.korben_chan_bot.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ExpeditionCommand extends ListenerAdapter {
    private final CommandService commandService;
    private final ButtonService buttonService;

    public ExpeditionCommand(CommandService commandService, ButtonService buttonService) {
        this.commandService = commandService;
        this.buttonService = buttonService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("вылазка")) {
            commandService.expedition(event);
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getId().startsWith("Tank")) {
            buttonService.startExpedition(event);
        }
    }
}
