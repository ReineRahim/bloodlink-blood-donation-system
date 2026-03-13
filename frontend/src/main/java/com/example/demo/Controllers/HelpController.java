package com.example.demo.Controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class HelpController {
    private static final String BASE_URL = "http://localhost:9090/api/help"; // Adjust as needed

    private final HttpClient client = HttpClient.newHttpClient();

    // Send a help access log (POST)
    public boolean logHelpAccess(String userType) {
        JSONObject json = new JSONObject();
        json.put("userType", userType);
        json.put("timestamp", java.time.LocalDateTime.now().toString());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/log"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Error logging help access: " + e.getMessage());
            return false;
        }
    }

    // Get help content (GET)
    public JSONObject getHelpContent() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/content"))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {
            System.err.println("Error fetching help content: " + e.getMessage());
            return new JSONObject();
        }
    }
}
