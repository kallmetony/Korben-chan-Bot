package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InfoCommand extends ListenerAdapter {

    private final CommandService service;

    public InfoCommand(CommandService service) {
        this.service = service;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("инфо")) {
            service.getUserInfo(event);
        }
    }
}
