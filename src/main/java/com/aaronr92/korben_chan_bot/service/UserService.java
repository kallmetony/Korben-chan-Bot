package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.exception.AlreadyInExpeditionException;
import com.aaronr92.korben_chan_bot.exception.UserNotFoundException;
import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class UserService {

    private final EmbedFactory embedFactory;

    public UserService(EmbedFactory embedFactory) {
        this.embedFactory = embedFactory;
    }

    /**
     * Opens a box for specified user
     * @param id an id if a user
     * @return embed to reply
     */
    public MessageEmbed openBox(long id) {
        try {
            JsonObject json = BotHttpClient
                    .openBox(id);

            String reward = json.get("reward").getAsString();

            if (reward.startsWith(" ")) {
                return embedFactory
                        .getEmbed(EmbedFactory.Type.TANK, reward);
            }
            return embedFactory
                    .getEmbed(EmbedFactory.Type.MONEY, reward);
        } catch (IOException | InterruptedException ignored) {

        } catch (IllegalArgumentException e) {
            return embedFactory
                    .getEmbed(EmbedFactory.Type.BOX_ERROR);
        }
        return null;
    }

    /**
     * Returns all information about user
     * @param id an id of a user
     * @return embed to reply
     */
    public MessageEmbed getUserInfo(long id) {
        try {
            JsonObject json = BotHttpClient.getUser(id);

            Collection<JsonObject> tanks = getTanks(id);

            return embedFactory.getUserInfoEmbed(
                    id,
                    json.get("money").getAsInt(),
                    tanks,
                    json.get("maxHangarSize").getAsInt()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            return embedFactory.getEmbed(
                    EmbedFactory.Type.BOX_ERROR);
        }
    }

    /**
     *
     * @param userId an id of a user
     * @return
     */
    public MessageEmbed getUserStartExpedition(long userId) {
        try {
            JsonObject json = BotHttpClient.getUser(userId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Creates an expedition for a user
     * @param userId an id of a user
     * @param tankName name of a tank
     * @return embed to reply
     */
    public MessageEmbed createExpedition(long userId, String tankName) {
        try {
            if (BotHttpClient.createExpedition(userId, tankName) == 201) {
                return embedFactory
                        .getEmbed(EmbedFactory.Type.SUCCESSFUL_EXPEDITION_CREATION);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            return embedFactory
                    .getEmbed(EmbedFactory.Type.USER_NOT_FOUND);
        } catch (AlreadyInExpeditionException e) {
            return getExpedition(userId);
        }
        return null;
    }

    /**
     * Returns a successful start message
     * @param userId an id of a user
     * @return embed to reply
     */
    public MessageEmbed expeditionCreationEmbed(long userId) {
        return embedFactory
                .getEmbed(EmbedFactory.Type.EXPEDITION_CREATION);
    }

    /**
     * Returns information about expedition
     * @param userId an id of a user
     * @return embed to reply
     */
    public MessageEmbed getExpedition(long userId) {
        try {
            JsonObject json = BotHttpClient.getExpedition(userId);
            String remainingTime = json.get("remainingTime").getAsString();
            JsonObject tank = json.get("tank").getAsJsonObject();

            return embedFactory.getExpeditionEmbed(remainingTime, tank);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns collection of JsonObjects of user tanks
     * @param userId an id of a user
     * @return collection containing user tanks
     */
    private Collection<JsonObject> getTanks(long userId) {
        try {
            JsonObject json = BotHttpClient.getUser(userId);

            Collection<JsonObject> tanks = new HashSet<>();

            json.get("tanks").getAsJsonArray().forEach(jsonElement ->
                    tanks.add((JsonObject) jsonElement));

            return tanks;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a collection of Strings of all tank names
     * @param userId an id of a user
     * @return collection containing tank names
     */
    public Collection<String> getTanksNames(long userId) {
        Collection<JsonObject> tanks = getTanks(userId);
        Collection<String> tanksName = new ArrayList<>();

        tanks.forEach(tank -> {
            tanksName.add(tank.get("name").getAsString());
        });

        return tanksName;
    }
}