package com.aaronr92.korben_chan_bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SendInfoCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("info")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Добро пожаловать!");
            embedBuilder.setDescription("Если ты оказался здесь, значит ты либо классный пацан, либо так надо.");
            embedBuilder.addField("Есть всего 4 правила", """
                    1. Без расчленёнки, детского nsfw-контента, и всего подобного.
                    2. Без срачей иначе 10 минут мута, живем мирно, ведь все вежливые же ^^
                    3. Оффтопик не приветствуется
                    4. Сообщения без капса""", true);
            embedBuilder.setFooter("Веселись и развлекайся <3");
            embedBuilder.setColor(Color.PINK);
            event.getOption("канал").getAsChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
