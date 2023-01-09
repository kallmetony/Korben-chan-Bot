package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class UserService {

    private final EmbedFactory embedFactory = new EmbedFactory();

    public UserService() { }

    public void openBox(SlashCommandInteractionEvent event) {
        try {
            JsonObject json = BotHttpClient
                    .openBox(event.getMember().getIdLong());

            String reward = json.get("reward").getAsString();

            if (reward.startsWith(" ")) {
                event.replyEmbeds(embedFactory
                                .getEmbed(EmbedFactory.Type.TANK, reward))
                        .setEphemeral(false)
                        .queue();
                return;
            }
            event.replyEmbeds(embedFactory
                            .getEmbed(EmbedFactory.Type.MONEY, reward))
                    .setEphemeral(false)
                    .queue();
        } catch (IOException | InterruptedException ignored) {

        } catch (IllegalArgumentException e) {
            event.replyEmbeds(embedFactory
                            .getEmbed(EmbedFactory.Type.BOX_ERROR, null))
                    .setEphemeral(false)
                    .queue();
        }
    }
}
