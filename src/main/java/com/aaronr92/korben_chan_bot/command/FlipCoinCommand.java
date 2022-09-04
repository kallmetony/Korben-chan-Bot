package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.Emote;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FlipCoinCommand extends ListenerAdapter {

    Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("открыть")) {
            int randNum = random.nextInt(6);
            if (randNum == 0) {
                event.reply("Тебе выпал терпильный T95e6!").queue();
            } else if (randNum == 1) {
                event.reply("Тебе выпал Агент 907!").queue();
            } else if (randNum == 2) {
                event.reply("Тебе выпал достойный 279!").queue();
            } else if (randNum == 3) {
                event.reply("Тебе выпал легендарный т-22 ср.!").queue();
            } else if (randNum == 4) {
                event.reply(String.format("Тебе выпал лучший танк %s, VK 72.01 (K)!", Emote.JOVE.getCode())).queue();
            } else if (randNum == 5) {
                event.reply("Тебе выпал неповторимый Лев!").queue();
            }
        }
    }
}
