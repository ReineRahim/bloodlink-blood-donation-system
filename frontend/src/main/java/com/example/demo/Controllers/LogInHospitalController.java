package com.example.demo.Controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LogInHospitalController {
    private static final String BASE_URL ="http://localhost:9090/api/hospitals";
    ; // replace with actual URL
    private String loggedInHospitalName;

    public boolean loginHospital(String hospitalName, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String loginUrl = BASE_URL + "/login?hospitalName=" + URLEncoder.encode(hospitalName, StandardCharsets.UTF_8)
                + "&hospitalPass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Accept", "text/plain")
                .POST(HttpRequest.BodyPublishers.noBody()) // send params via URL
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean success = response.statusCode() == 200 && response.body().equalsIgnoreCase("Login successful");

        if (success) {
            this.loggedInHospitalName = hospitalName;
        }

        return success;
    }

    public String getLoggedInHospitalName() {
        return this.loggedInHospitalName;
    }
}
