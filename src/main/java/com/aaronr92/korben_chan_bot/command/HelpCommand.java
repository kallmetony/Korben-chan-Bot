package com.aaronr92.korben_chan_bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("инфо")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Команды");
            embedBuilder.setDescription("Список всех команд бота");
            embedBuilder.addField("`/сказать`", "Говорит то, что указано в параметре", true);
            embedBuilder.addField("`/добавить`", "Добавляет новый вариант фраза-ответ", true);
            embedBuilder.addBlankField(true);
            embedBuilder.addField("`/член`", "Называет случайную длину", true);
            embedBuilder.addField("`/открыть`", "Открывает коробку, из которой падают танки", true);
            embedBuilder.addBlankField(true);
            embedBuilder.setColor(Color.PINK);
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
