package com.aaronr92.korben_chan_bot;

import com.aaronr92.korben_chan_bot.hander.ButtonHandler;
import com.aaronr92.korben_chan_bot.hander.CommandHandler;
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
        ButtonService buttonService = new ButtonService(userService, embedFactory);

        jda = JDABuilder
                .createLight(TOKEN, GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.playing("Мир Танков"))
                .addEventListeners(new CommandHandler(commandService), new ButtonHandler(buttonService),
                        new MessageListener())
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("сказать", "Говорит что-то")
                        .addOptions(new OptionData(OptionType.STRING, "текст",
                                "Текст для того, чтобы сказать", true)),
                Commands.slash("магазин", "Открыть магазин"),
                Commands.slash("коробка", "Открыть ежедневную коробку!"),
                Commands.slash("ангар", "Информация о том, что у тебя есть"),
                Commands.slash("ship", "Совместимость между двумя пользователями")
                        .addOptions(new OptionData(OptionType.USER, "пользователь_1",
                                "Первый пользователь", true))
                        .addOptions(new OptionData(OptionType.USER, "пользователь_2",
                                "Второй пользователь", true)),
                Commands.slash("вылазка", "Начать вылазку или посмотреть её статус"),
                Commands.slash("помощь", "Узнай что может бот!"),
                // Admin commands
                Commands.slash("info", "Отправить сообщение в информационный канал")
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

    public static User retrieveUserById(long userId) {
        RestAction<User> userAction = jda.retrieveUserById(userId);
        User user = userAction.complete();
        userAction.queue();

        return user;
    }
}
