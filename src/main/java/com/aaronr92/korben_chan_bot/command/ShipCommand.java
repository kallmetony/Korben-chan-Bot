package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShipCommand extends ListenerAdapter {
    private final CommandService commandService;

    public ShipCommand(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ship")) {
            commandService.ship(event);
        }
    }
}
