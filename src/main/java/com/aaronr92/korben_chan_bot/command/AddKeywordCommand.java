package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.listener.MessageListener;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Random;

public class AddKeywordCommand extends ListenerAdapter {
    private final String[] replies = {"Готово!", "Есть!", "Добавил я..", "Ну и что дальше?"};
    private final Random r = new Random();
    private final Logger log = LoggerFactory.getLogger(AddKeywordCommand.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("добавить")) {
            String word = Objects.requireNonNull(event.getOption("слово")).getAsString().toLowerCase();
            String reply = Objects.requireNonNull(event.getOption("ответ")).getAsString();
            MessageListener.keywords.put(word, reply);
            save(word, reply);
            event.reply(replies[r.nextInt(replies.length)]).queue();
        }
    }

    private void save(String word, String reply) {
        MessageListener.keywords.put(word, reply);
    }
}