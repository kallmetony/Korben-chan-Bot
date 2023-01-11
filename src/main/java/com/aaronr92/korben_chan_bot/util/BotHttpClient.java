package com.aaronr92.korben_chan_bot.util;

import com.aaronr92.korben_chan_bot.exception.AlreadyInExpeditionException;
import com.aaronr92.korben_chan_bot.exception.ExpeditionNotFoundException;
import com.aaronr92.korben_chan_bot.exception.UserNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BotHttpClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String serverPath =
            "http://host.docker.internal:9098/api/v1/";
    private static final String boxPath = "box/";
    private static final String userPath = "user/";
    private static final String expeditionPath = "expedition/";

    public static JsonObject openBox(long id)
            throws IOException, InterruptedException,
            IllegalArgumentException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + boxPath + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 403)
            throw new IllegalArgumentException("User cannot open box today");

        return new Gson().fromJson(response.body(), JsonObject.class);
    }

    public static JsonObject getUser(long id)
            throws IOException, InterruptedException,
            UserNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + userPath + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 404)
            throw new UserNotFoundException();

        return new Gson().fromJson(response.body(), JsonObject.class);
    }

    public static Integer createExpedition(long userId, String tankName)
            throws IOException, InterruptedException,
            UserNotFoundException, AlreadyInExpeditionException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + "expedition" + "?userId=" + userId + "&tankName=" + tankName))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 404)
            throw new UserNotFoundException();

        if (response.statusCode() == 409)
            throw new AlreadyInExpeditionException();

        return response.statusCode();
    }

    public static JsonObject getExpedition(long userId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + expeditionPath + "user/" + userId))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 404)
            throw new ExpeditionNotFoundException();

        return new Gson().fromJson(response.body(), JsonObject.class);
    }
}
