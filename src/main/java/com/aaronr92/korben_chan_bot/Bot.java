package com.aaronr92.korben_chan_bot;

import com.aaronr92.korben_chan_bot.command.*;
import com.aaronr92.korben_chan_bot.listener.FileReader;
import com.aaronr92.korben_chan_bot.listener.MessageListener;
import com.aaronr92.korben_chan_bot.service.ButtonService;
import com.aaronr92.korben_chan_bot.service.CommandService;
import com.aaronr92.korben_chan_bot.service.UserService;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;

public class Bot {

    public static JDA jda;

    public static void main(String[] args) {
        final String TOKEN = args[0];

        EmbedFactory embedFactory = new EmbedFactory();
        UserService userService = new UserService(embedFactory);
        CommandService commandService = new CommandService(userService, embedFactory);
        ButtonService buttonService = new ButtonService(userService);

        jda = JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.playing("Мир Танков"))
                .addEventListeners(new MessageListener(), new SayCommand(), new AddKeywordCommand(),
                        new FileReader(), new HelpCommand(), new SendEmbedCommand(), new SendInfoCommand(),
                        new OpenBoxCommand(commandService), new InfoCommand(commandService),
                        new ShipCommand(commandService), new ExpeditionCommand(commandService, buttonService))
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("сказать", "Говорит что-то")
                        .addOptions(new OptionData(OptionType.STRING, "текст",
                                "Текст для того, чтобы сказать", true)),
                Commands.slash("добавить", "Добавляет новый случай слово - ответ")
                        .addOptions(new OptionData(OptionType.STRING, "слово",
                                "Фраза, на которую бот ответит", true))
                        .addOptions(new OptionData(OptionType.STRING, "ответ",
                                "Фраза, которой бот ответит", true)),
                Commands.slash("открыть", "Открыть ежедневную коробку!"),
                Commands.slash("инфо", "Информация о тебе"),
                Commands.slash("ship", "Совместимость между двумя пользователями")
                        .addOptions(new OptionData(OptionType.USER, "пользователь_1",
                                "Первый пользователь"))
                        .addOptions(new OptionData(OptionType.USER, "пользователь_2",
                                "Второй пользователь")),
                Commands.slash("вылазка", "Начать вылазку или посмотреть её статус"),
                // Admin commands
                Commands.slash("post", "Отправляет ембед в выбранный канал")
                        .addOptions(new OptionData(OptionType.CHANNEL, "канал",
                                "Канал, в который будет оптравлено сообщение", true))
                        .addOptions(new OptionData(OptionType.STRING, "заголовок",
                                "Заголовок сообщения", true))
                        .addOptions(new OptionData(OptionType.STRING, "сообщение",
                                "Сообщение, которое будет отправлено", true))
                        .addOptions(new OptionData(OptionType.STRING, "цвет",
                                "Цвет эмбеда")
                                .addChoice("Белый", "White")
                                .addChoice("Розовый", "Pink")
                                .addChoice("Красный", "Red")
                                .addChoice("Оранжевый", "Orange"))
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)),
                Commands.slash("info", "Отправляет сообщение в информационный канал")
                        .addOptions(new OptionData(OptionType.CHANNEL, "канал",
                                "Канал, в который будет отправлено сообщение", true))
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
        ).queue();
    }

    public static String retrieveUsernameById(long userId) {
        String username;
        RestAction<User> userAction = jda.retrieveUserById(userId);
        username = userAction.complete().getName();
        userAction.queue();

        return username;
    }
}
