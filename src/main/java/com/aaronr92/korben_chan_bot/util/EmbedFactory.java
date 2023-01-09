package com.aaronr92.korben_chan_bot.util;

import com.aaronr92.korben_chan_bot.Bot;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Set;

public class EmbedFactory {

    private final String BOX = "\uD83C\uDF81 Коробка \uD83C\uDF81";

    public MessageEmbed getEmbed(Type type, @Nullable String text) {
        switch (type) {
            case MONEY -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.addField(
                        BOX,
                        "Тебе выпало " + text + " \uD83E\uDE99 монет!",
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

    public MessageEmbed getUserInfoEmbed(
            long id, int money, Set<JsonObject> tanks, int hangarSize) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(Bot.retrieveUsernameById(id));
        builder.addField("Баланс", money + " \uD83E\uDE99", false);
        builder.addField("Ангар", "Техника " + tanks.size() + "/" + hangarSize, false);
        if (tanks.size() == 0) {
            builder.addField("Техника", "Пока нет ни одной единицы техники", false);
        } else
            tanks.forEach(tank -> {
                builder.addField(
                        tank.get("name").getAsString(),
                                Util.getTankClass(tank
                                .get("tankClass").getAsString()) + "-" + tank.get("level").getAsInt() + "\n" +
                                "Цена: " + tank.get("price").getAsInt() + " \uD83E\uDE99",
                        true
                );
            });
        builder.setColor(Color.PINK);

        return builder.build();
    }

    public enum Type {
        MONEY,
        TANK,
        BOX_ERROR
    }
}
