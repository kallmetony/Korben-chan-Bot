package com.aaronr92.korben_chan_bot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FlipCoinCommand extends ListenerAdapter {

    Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("подкинуть")) {
            if (random.nextInt(2) == 0) {
                event.reply("Тебе выпал T95e6!").queue();
            } else {
                event.reply("Тебе выпал Объект 907!").queue();
            }
        }
    }
}
