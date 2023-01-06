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
            embedBuilder.setDescription("Незнание правил не освобождает от ответственности!");
            embedBuilder.addField("Правила",
                    """
                    1. Без расчленёнки, детского nsfw-контента, и всего подобного
                    2. Без любых срачей (см пункт 3). За такое выдается мут
                    3. Оскорбления, в том числе завуалированные запрещены
                    4. Запрещается провоцировать других участников сервера на срачи
                    5. За оффтопик можно получить мут
                    6. Сообщения без капса""", true);
            embedBuilder.setFooter("Веселись, удачи!");
            embedBuilder.setColor(Color.PINK);
            event.getOption("канал").getAsChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
