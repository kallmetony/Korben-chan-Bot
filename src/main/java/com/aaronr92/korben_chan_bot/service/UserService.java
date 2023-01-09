package com.aaronr92.korben_chan_bot.service;

import com.aaronr92.korben_chan_bot.util.BotHttpClient;
import com.aaronr92.korben_chan_bot.util.EmbedFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserService {

    private final EmbedFactory embedFactory = new EmbedFactory();

    public UserService() { }

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
                    .getEmbed(EmbedFactory.Type.BOX_ERROR, null);
        }
        return null;
    }

    public MessageEmbed getUserInfo(long id) {
        try {
            JsonObject json = BotHttpClient.getUser(id);

            Set<JsonObject> tanks = new HashSet<>();
            json.get("tanks").getAsJsonArray().forEach(jsonElement ->
                    tanks.add((JsonObject) jsonElement));

            return embedFactory.getUserInfoEmbed(
                    id,
                    json.get("money").getAsInt(),
                    tanks,
                    json.get("maxHangarSize").getAsInt()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            // TODO: send not found embed
            System.out.println("Not found!");
        }
        return null;
    }
}
