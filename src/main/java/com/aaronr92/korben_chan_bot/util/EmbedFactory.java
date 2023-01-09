package com.aaronr92.korben_chan_bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class EmbedFactory {

    private final String BOX = "\uD83C\uDF81 Коробка \uD83C\uDF81";
    public MessageEmbed getEmbed(Type type, @Nullable String text) {
        switch (type) {
            case MONEY -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.addField(
                        BOX,
                        "Тебе выпало " + text + "\uD83E\uDE99 монет!",
                        false);
                builder.setColor(Color.YELLOW);
                return builder.build();
            }
            case TANK -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.addField(
                        BOX,
                        "Тебе выпал танк ⭐" + text + " ⭐",
                        false
                );
                builder.setColor(Color.MAGENTA);
                return builder.build();
            }
            case BOX_ERROR -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.addField(
                        BOX,
                        "❌ Следующую коробку можно открыть только завтра ❌",
                        false
                );
                builder.setColor(Color.RED);
                return builder.build();
            }
        }
        return null;
    }

    public enum Type {
        MONEY,
        TANK,
        BOX_ERROR
    }
}
