package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OpenBoxCommand extends ListenerAdapter {

    private final CommandService service;

    public OpenBoxCommand(CommandService commandService) {
        this.service = commandService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("открыть")) {
            service.openBox(event);
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

    }
}
