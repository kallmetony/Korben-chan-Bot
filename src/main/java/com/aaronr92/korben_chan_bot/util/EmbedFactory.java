package com.aaronr92.korben_chan_bot.util;

import com.aaronr92.korben_chan_bot.Bot;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collection;

public class EmbedFactory {

    private final String BOX = "\uD83C\uDF81 Коробка \uD83C\uDF81";
    private final String BOT = "Korben-chan";
    private final String EXPEDITION = "\uD83D\uDEA9 Вылазка \uD83D\uDEA9";
    private final String CLOCK = "⏰ Оставшееся время ⏰";
    private final String IN_EXPEDITION = "В вылазке";
    private final String SHOP = "\uD83D\uDED2 Магазин \uD83D\uDED2";

    public MessageEmbed getEmbed(Type type, @Nullable String... args) {
        EmbedBuilder builder = new EmbedBuilder();
        final SelfUser bot = Bot.jda.getSelfUser();
        switch (type) {
            case MONEY -> {
                builder.addField(
                        BOX,
                        "Тебе выпало `" + args[0] + "` \uD83E\uDE99 монет!",
                        false);
                builder.setColor(Color.YELLOW);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case TANK -> {
                builder.addField(
                        BOX,
                        "Тебе выпал танк ⭐`" + args[0] + "` ⭐",
                        false
                );
                builder.setColor(Color.MAGENTA);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case BOX_ERROR -> {
                builder.addField(
                        BOX,
                        "❌ Следующую коробку можно открыть только завтра ❌",
                        false
                );
                builder.setColor(Color.RED);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case USER_NOT_FOUND -> {
                builder.addField(
                        BOT,
                        "Пользователь не найден. Открой хотябы одну коробку",
                        false
                );
                builder.setColor(Color.RED);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case SHIP -> {
                builder.addField(
                        "\uD83D\uDC95 Совместимость \uD83D\uDC95",
                        args[1] + " и " + args[2] + " совместимы на `" + args[0] + "` %",
                        false
                );
                builder.setColor(Color.PINK);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case SUCCESSFUL_EXPEDITION_CREATION -> {
                builder.addField(
                        EXPEDITION,
                        "✅ Успешное начало вылазки! ✅\n\n" +
                                "Напиши `/вылазка` ещё раз для получения подробной информации",
                        false
                );
                builder.setColor(Color.GREEN);
                User user = Bot.retrieveUserById(Long.parseLong(args[0]));
                builder.setAuthor(user.getName(), null, user.getAvatarUrl());
            }
            case EXPEDITION_CREATION -> {
                builder.setTitle(EXPEDITION);
                builder.addField(
                        "Выбор танка",
                        "Выбери один танк",
                        false
                );
                builder.setColor(Color.PINK);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case HELP -> {
                builder.setTitle("Помощь");
                builder.setDescription("Тут всё, что умеет бот");
                builder.addField("Коробка", "Бесплатная коробка, содержащая золото и другие ценные награды", true);
                builder.addField("Вылазка", "Вылазка, в результате которой ты получишь достойные награды, рандомная длительность до `6` часов", true);
                builder.addField("Ангар", "Информация о твоих танках и количестве золота", true);
                builder.setColor(Color.PINK);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case NOT_ENOUGH_TANKS -> {
                builder.setTitle(EXPEDITION);
                builder.addField(
                        "❌ Невозможно начать вылазку ❌",
                        "Для начала вылазки нужно иметь хотя-бы один танк",
                        false
                );
                builder.setColor(Color.RED);
            }
            case SELL_TANK -> {
                builder.setTitle(args[0]);
                builder.setDescription("Ты можешь продать этот танк, тебе вернется 50% от его цены");
                builder.setColor(Color.PINK);
                builder.setAuthor(bot.getName(), null, bot.getAvatarUrl());
            }
            case ALREADY_IN_EXPEDITION -> {
                builder.setTitle("❌ Танк уже в вылазке ❌");
                builder.setDescription("Подожди пока он вернется");
                builder.setColor(Color.RED);
            }
            case TANK_NOT_FOUND -> {
                builder.setTitle("❌ Танк не найден ❌");
                builder.setColor(Color.RED);
            }
            case SUCCESS -> {
                builder.setTitle("✅ Операция успешно выполнена ✅");
                builder.setColor(Color.GREEN);
                if (args.length == 2)
                    builder.setAuthor(args[0], null, args[1]);
            }
            case NOT_ENOUGH_MONEY -> {
                builder.setTitle("❌ Недостаточно средств ❌");
                builder.setColor(Color.RED);
                if (args.length == 2)
                    builder.setAuthor(args[0], null, args[1]);
            }
            case TANK_ALREADY_IN_HANGAR -> {
                builder.setTitle("❌ Танк уже в твоем ангаре ❌");
                builder.setColor(Color.PINK);
                if (args.length == 2)
                    builder.setAuthor(args[0], null, args[1]);
            }
        }
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
                "`" + remainingTime + "` ч.",
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

    public MessageEmbed getShop(
            Collection<JsonObject> tanks,
            @Nullable Collection<JsonObject> boxes
    ) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(SHOP);
//        for (JsonObject box :
//                boxes) {
//            // TODO: Add fields for each box
//        }
        builder.addBlankField(false);
        for (JsonObject tank :
                tanks) {
            builder.addField(
                    tank.get("name").getAsString(),
                    '*' + tank.get("description").getAsString() + "*\n" +
                    "**Цена: " + tank.get("price").getAsString() + " **\uD83E\uDE99",
                    true
            );
            // TODO: Add fields for each Tank
        }
        builder.addBlankField(false);
        builder.addField(
                "Слот в ангаре",
                "**Цена: " + 1000 + " **\uD83E\uDE99",
                false);
        builder.setColor(Color.pink);
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
        NOT_ENOUGH_TANKS,
        NOT_ENOUGH_MONEY,
        SELL_TANK,
        TANK_NOT_FOUND,
        TANK_ALREADY_IN_HANGAR,
        SUCCESS,
        HELP
    }
}
