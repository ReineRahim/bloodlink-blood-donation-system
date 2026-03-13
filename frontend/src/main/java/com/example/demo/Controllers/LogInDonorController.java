package com.example.demo.Controllers;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class LogInDonorController {
    private static final String BASE_URL = "http://localhost:9090/api/donors";
    private String loggedInUsername;

    // POST /api/login?username=xxx&password=yyy
    public boolean login(String username, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String loginUrl = BASE_URL + "/login?username=" + username + "&password=" + password;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Accept", "text/plain")
                .POST(HttpRequest.BodyPublishers.noBody()) // parameters in URL
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean success = response.statusCode() == 200 && response.body().equalsIgnoreCase("Login successful");
        if (success) {
            this.loggedInUsername = username;
        }
        return success;

    }


    public String getUsername() {
        return this.loggedInUsername;
    }

}
