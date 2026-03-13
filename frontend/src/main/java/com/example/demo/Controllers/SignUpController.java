package com.example.demo.Controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SignUpController {
    private static final String BASE_URL = "http://localhost:9090/api"; // replace with your API base URL
    private String signedUpDonorUsername;

    public boolean signUpDonor(String firstName, String lastName, String username, String password,
                               String gender, String address, String contactNumber, String birthDate)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        // Construct JSON body (assuming your server accepts JSON)
        String jsonBody = String.format("""
            {
                "firstName": "%s",
                "lastName": "%s",
                "username": "%s",
                "password": "%s",
                "gender": "%s",
                "address": "%s",
                "contactNumber": "%s",
                "birthDate": "%s"
            }
            """, escapeJson(firstName), escapeJson(lastName), escapeJson(username), escapeJson(password),
                escapeJson(gender), escapeJson(address), escapeJson(contactNumber), escapeJson(birthDate));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/donor/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        boolean success = response.statusCode() == 201 || (response.statusCode() == 200 && response.body().contains("success"));

        if (success) {
            this.signedUpDonorUsername = username;
        }

        return success;
    }

    public String getSignedUpDonorUsername() {
        return this.signedUpDonorUsername;
    }

    // Simple JSON escape for quotes and backslashes to avoid malformed JSON
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
