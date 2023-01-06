package com.aaronr92.korben_chan_bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SendEmbedCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("post")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(event.getOption("?????????").getAsString());
            embedBuilder.setDescription(event.getOption("?????????").getAsString());
            embedBuilder.setColor(Color.BLACK);
            if (event.getOption("????") != null) {
                switch (event.getOption("????").getAsString()) {
                    case ("White") -> embedBuilder.setColor(Color.WHITE);
                    case ("Pink") -> embedBuilder.setColor(Color.PINK);
                    case ("Red") -> embedBuilder.setColor(Color.RED);
                    case ("Orange") -> embedBuilder.setColor(Color.ORANGE);
                }
            }
            event.getOption("?????").getAsChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            event.reply("???????!").setEphemeral(true).queue();
        }
    }
}
