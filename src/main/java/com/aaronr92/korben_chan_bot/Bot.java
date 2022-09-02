package com.aaronr92.korben_chan_bot;

import com.aaronr92.korben_chan_bot.command.AddKeywordCommand;
import com.aaronr92.korben_chan_bot.command.SayCommand;
import com.aaronr92.korben_chan_bot.listener.MessageListener;
import com.aaronr92.korben_chan_bot.listener.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    public static final String TOKEN = "OTE0NTg5NDI3NjAxMzk1NzMz.GhCCg-.YZyQWexUK3EkUhsnnmlKOLXfixQ1oJTrq2ng34";

    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.playing("World of Tanks"))
                .addEventListeners(new ReadyListener(), new MessageListener(), new SayCommand(), new AddKeywordCommand())
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("высрать", "Говорит что-то")
                        .addOptions(new OptionData(OptionType.STRING, "текст", "Текст для того, чтобы сказать", true)),
                Commands.slash("добавить", "Добавляет новый случай слово - ответ")
                        .addOptions(new OptionData(OptionType.STRING, "слово", "Фраза, на которую бот ответит", true))
                        .addOptions(new OptionData(OptionType.STRING, "ответ", "Фраза, которой бот ответит", true))
        ).queue();
    }
}
