package com.aaronr92.korben_chan_bot.util;

import com.aaronr92.korben_chan_bot.exception.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BotHttpClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String serverPath =
            "http://host.docker.internal:9098/api/v1/";
    private static final String boxPath = "box/";
    private static final String userPath = "user/";
    private static final String expeditionPath = "expedition/";
    private static final String tankPath = "tank/";

    /**
     * Sends a request to the server to open a box and get result of action
     * @param id an id of a user
     * @return JsonObject representing response of the server
     * @throws IllegalArgumentException if user cannot open box today
     */
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

    /**
     * Sends a request to the server to get user info
     * @param id an id of a user
     * @return JsonObject representing response of the server
     * @throws IOException
     * @throws InterruptedException
     * @throws UserNotFoundException if the user is not found in db
     */
    public static JsonObject getUser(long id)
            throws IOException, InterruptedException,
            UserNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + userPath + id)
                        .replace(" ", "%20")))
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

    /**
     * Sends a request to the server to create new expedition
     * @param userId an id of a user
     * @param tankName name of the tank
     * @return request status code
     * @throws UserNotFoundException if the user is not found in db
     * @throws AlreadyInExpeditionException if the user is already in expedition
     */
    public static Integer createExpedition(long userId, String tankName)
            throws IOException, InterruptedException,
            UserNotFoundException, AlreadyInExpeditionException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + "expedition" + "?userId=" +
                        userId + "&tankName=" + tankName)
                        .replace(" ", "%20")))
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

    /**
     * Sends a request to the server to get info about current expedition
     * @param userId an id of a user
     * @return JsonObject representing response of the server
     * @throws IOException
     * @throws InterruptedException
     * @throws ExpeditionNotFoundException if the last expedition is not found or finished
     */
    public static JsonObject getExpedition(long userId)
            throws IOException, InterruptedException, ExpeditionNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + expeditionPath + "user/" + userId)
                        .replace(" ", "%20")))
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

    public static void sellTank(Long userId, String tankName)
            throws IOException, InterruptedException,
            AlreadyInExpeditionException, TankNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + userPath + userId +
                        "?tankName=" + tankName + "&operation=SELL")
                        .replace(" ", "%20")))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 409)
            throw new AlreadyInExpeditionException();

        if (response.statusCode() == 404)
            throw new TankNotFoundException();
    }

    public static JsonObject getTankById(long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + tankPath + id)
                        .replace(" ", "%20")))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 404)
            throw new TankNotFoundException();

        return new Gson().fromJson(response.body(), JsonObject.class);
    }

    public static void buyTank(long userId, String tankName)
            throws NotEnoughMoneyException, TankAlreadyInUserHangarException,
            IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + userPath + userId +
                        "?tankName=" + tankName + "&operation=BUY")
                        .replace(" ", "%20")))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 400)
            throw new NotEnoughMoneyException();

        if (response.statusCode() == 409)
            throw new TankAlreadyInUserHangarException();
    }

    public static JsonObject getExpeditionPage(long userId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + "expedition?" + "id=" + userId)
                        .replace(" ", "%20")))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return new Gson().fromJson(response.body(), JsonObject.class);
    }
    
    public static int buySlot(long userId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((serverPath + userPath + userId +
                        "?operation=SLOT")
                        .replace(" ", "%20")))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.statusCode();
    }
}
