package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.entity.Keyword;
import com.aaronr92.korben_chan_bot.listener.MessageListener;
import com.aaronr92.korben_chan_bot.repository.KeywordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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

    private final KeywordRepository keywordRepository;

    public AddKeywordCommand(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

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
        Keyword keyword = new Keyword();
        keyword.setWord(word);
        keyword.setReply(reply);

        keywordRepository.save(keyword);
        MessageListener.keywords.put(word, reply);
    }
}