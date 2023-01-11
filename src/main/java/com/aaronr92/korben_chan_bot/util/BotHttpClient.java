package com.aaronr92.korben_chan_bot.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BotHttpClient {
    private static final HttpClient http = HttpClient.newHttpClient();
    private static final String serverPath =
            "http://host.docker.internal:9098/api/v1/";

    private static final String boxPath = "box/";
    private static final String userPath = "user/";

    public static JsonObject openBox(long id)
            throws IOException, InterruptedException,
            IllegalArgumentException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + boxPath + id))
                .GET()
                .build();

        HttpResponse<String> response = http.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 403)
            throw new IllegalArgumentException("User cannot open box today");

        return new Gson().fromJson(response.body(), JsonObject.class);
    }

    public static JsonObject getUser(long id)
            throws IOException, InterruptedException, IllegalArgumentException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + userPath + id))
                .GET()
                .build();

        HttpResponse<String> response = http.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 404)
            throw new IllegalArgumentException("This user does not exist");

        return new Gson().fromJson(response.body(), JsonObject.class);
    }
}
