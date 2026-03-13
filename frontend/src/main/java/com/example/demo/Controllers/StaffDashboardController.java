package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StaffDashboardController {
    private static final String BASE_URL = "http://localhost:9090/api";
    private final HttpClient client = HttpClient.newHttpClient();

    public JSONArray getAllRequests() {
        return fetchArray(BASE_URL + "/bloodrequests");
    }

    public JSONArray getAllDonations() {
        return fetchArray(BASE_URL + "/donations");
    }

    public JSONArray getAllDonors() {
        return fetchArray(BASE_URL + "/donors");
    }

    public void deleteRequest(int id) {
        sendDelete(BASE_URL + "/bloodrequests/" + id);
    }

    public void deleteDonation(int id) {
        sendDelete(BASE_URL + "/donations/" + id);
    }

    public void deleteDonor(int id) {
        sendDelete(BASE_URL + "/donors/" + id);
    }

    public void updateRequestStatus(int id, String status) {
        sendPut(BASE_URL + "/bloodrequests/" + id + "/status", new JSONObject().put("status", status));
    }

    public void updateDonationStatus(int id, String status) {
        sendPut(BASE_URL + "/donations/" + id + "/status", new JSONObject().put("status", status));
    }

    // Utility Methods
    private JSONArray fetchArray(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private void sendDelete(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPut(String url, JSONObject payload) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}