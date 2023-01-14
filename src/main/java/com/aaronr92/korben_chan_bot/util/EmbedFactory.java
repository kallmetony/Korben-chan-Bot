package com.aaronr92.korben_chan_bot.util;

import com.aaronr92.korben_chan_bot.Bot;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.SelfUser;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collection;

public class EmbedFactory {

    private final String BOX = "\uD83C\uDF81 Коробка \uD83C\uDF81";
    private final String BOT = "Korben-chan";
    private final String EXPEDITION = "\uD83D\uDEA9 Вылазка \uD83D\uDEA9";
    private final String CLOCK = "⏰ Оставшееся время ⏰";
    private final String IN_EXPEDITION = "В вылазке";

    private final SelfUser bot = Bot.jda.getSelfUser();

    public MessageEmbed getEmbed(Type type, @Nullable String... args) {
        EmbedBuilder builder = new EmbedBuilder();
        switch (type) {
            case MONEY -> {
                builder.addField(
                        BOX,
                        "Тебе выпало " + args[0] + " \uD83E\uDE99 монет!",
                        false);
                builder.setColor(Color.YELLOW);
            }
            case TANK -> {
                builder.addField(
                        BOX,
                        "Тебе выпал танк ⭐" + args[0] + " ⭐",
                        false
                );
                builder.setColor(Color.MAGENTA);
            }
            case BOX_ERROR -> {
                builder.addField(
                        BOX,
                        "❌ Следующую коробку можно открыть только завтра ❌",
                        false
                );
                builder.setColor(Color.RED);
            }
            case USER_NOT_FOUND -> {
                builder.addField(
                        BOT,
                        "Пользователь не найден. Открой хотябы одну коробку",
                        false
                );
                builder.setColor(Color.RED);
            }
            case SHIP -> {
                builder.addField(
                        "\uD83D\uDC95 Совместимость \uD83D\uDC95",
                        args[1] + " и " + args[2] + " совместимы на " + args[0] + "%",
                        false
                );
                builder.setColor(Color.PINK);
            }
            case SUCCESSFUL_EXPEDITION_CREATION -> {
                builder.addField(
                        EXPEDITION,
                        "✅ Успешное начало вылазки! ✅",
                        false
                );
                builder.setColor(Color.GREEN);
            }
            case EXPEDITION_CREATION -> {
                builder.setTitle(EXPEDITION);
                builder.addField(
                        "Выбор танка",
                        "Выбери один танк",
                        false
                );
                builder.setColor(Color.PINK);
            }
            case HELP -> {
                builder.setTitle("Помощь");
                builder.setDescription("Тут всё, что умеет бот");
                builder.addField("Коробка", "Бесплатная коробка, содержащая золото и другие ценные награды", true);
                builder.addField("Вылазка", "Вылазка, в результате которой ты получишь достойные награды", true);
                builder.addField("Ангар", "Информация о твоих танках и количестве золота", true);
                builder.setColor(Color.PINK);
            }
        }
        builder.setAuthor(bot.getName(), bot.getAvatarUrl(), bot.getAvatarUrl());
        return builder.build();
    }

    public MessageEmbed getUserInfoEmbed(
            long id, int money, Collection<JsonObject> tanks, int hangarSize) {
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

    public MessageEmbed getExpeditionEmbed(
            String remainingTime,
            JsonObject tank
    ) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(EXPEDITION);
        builder.addField(
                CLOCK,
                remainingTime,
                false
        );
        builder.addField(
                IN_EXPEDITION,
                tank.get("name").getAsString(),
                false
        );
        builder.setColor(Color.PINK);
        return builder.build();
    }

    public enum Type {
        MONEY,
        TANK,
        BOX_ERROR,
        USER_NOT_FOUND,
        SHIP,
        SUCCESSFUL_EXPEDITION_CREATION,
        ALREADY_IN_EXPEDITION,
        EXPEDITION_CREATION,
        HELP
    }
}
