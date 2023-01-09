package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.service.UserService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OpenBoxCommand extends ListenerAdapter {

    private final UserService userService;

    public OpenBoxCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("открыть")) {
            userService.openBox(event);
        }
    }
}
