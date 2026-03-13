package com.example.demo.Controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LogInStaffController {
    private static final String BASE_URL = "http://localhost:9090/api/staff";
    private String loggedInUsername;
    private String loggedInRole;

    public boolean loginStaff(String username, String role, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String loginUrl = BASE_URL + "/login"
                + "?username=" + URLEncoder.encode(username, StandardCharsets.UTF_8)
                + "&role=" + URLEncoder.encode(role, StandardCharsets.UTF_8)
                + "&staff_password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Accept", "text/plain")
                .POST(HttpRequest.BodyPublishers.noBody())  // passing parameters via URL query
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean success = response.statusCode() == 200 && response.body().equalsIgnoreCase("Login successful");

        if (success) {
            this.loggedInUsername = username;
            this.loggedInRole = role;
        }
        return success;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }
}
