package com.aaronr92.korben_chan_bot.hander;

import com.aaronr92.korben_chan_bot.service.CommandService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Random;

public class CommandHandler extends ListenerAdapter {

    private final CommandService commandService;
    private final Logger log;
    private final Random random;
    private final String[] doneReplies;

    public CommandHandler(CommandService commandService) {
        this.commandService = commandService;
        this.log = LoggerFactory.getLogger(CommandHandler.class);
        random = new Random();
        doneReplies = new String[]{"Готово", "Есть!", "Сделано!", "Ну и что дальше?"};
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "вылазка" -> commandService.expedition(event);
            case "вылазки" -> commandService.getExpeditionsPage(event);
            case "помощь" -> commandService.help(event);
            case "ангар" -> commandService.getUserInfo(event);
            case "коробка" -> commandService.openBox(event);
            case "магазин" -> commandService.openShop(event);
            case "сказать" -> event.reply(event.getOption("текст").getAsString()).queue();
            case "ship" -> commandService.ship(event);
            case "info" -> {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Добро пожаловать!");
                embedBuilder.setDescription("Незнание правил не освобождает от ответственности!");
                embedBuilder.addField("Информация о сервере",
                        """
                        Сервер создан в Июле 2022 года, в период когда были ночные стримы.
                        Мы постоянно поддерживаем общение, вливайтесь в наш коллектив!
                        """, false);
                embedBuilder.addField("Правила",
                        """
                        1. Для повышения просто общайтесь в чатике, можете даже получить личную роль
                        2. Без расчленёнки, детского nsfw-контента, и всего подобного
                        3. Без любых срачей (см пункт 4). За такое выдается мут
                        4. Оскорбления, в том числе завуалированные запрещены
                        5. Запрещается провоцировать других участников сервера на срачи
                        6. За оффтопик можно получить мут
                        7. Меньше капса
                        """, false);
                embedBuilder.setFooter("Веселись, удачи!");
                embedBuilder.setColor(Color.PINK);
                event.getOption("канал").getAsChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
