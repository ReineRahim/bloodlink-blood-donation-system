package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BloodRequestController {
    private static final String BASE_URL = "http://localhost:9090/api/bloodrequests"; // Change URL to your backend

    private final HttpClient client = HttpClient.newHttpClient();

    // Create a new BloodRequest (POST)
    public void createBloodRequest(String hospitalName, String staffName, String bloodTypeNeeded, int requiredVolumeMl, String status) {
        JSONObject json = new JSONObject();
        json.put("hospitalName", hospitalName);
        json.put("staffName", staffName);
        json.put("bloodTypeNeeded", bloodTypeNeeded);
        json.put("requiredVolumeMl", requiredVolumeMl);
        json.put("status", status);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error creating blood request: " + e.getMessage());
        }
    }

    // Get all BloodRequests (GET)
    public JSONArray getAllBloodRequests() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching blood requests: " + e.getMessage());
            return new JSONArray();
        }
    }

    // Get BloodRequest by ID (GET)
    public JSONObject getBloodRequestById(int requestId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + requestId))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching blood request: " + e.getMessage());
            return new JSONObject();
        }
    }

    // Update BloodRequest (PUT)
    public void updateBloodRequest(int requestId, String hospitalName, String staffName, String bloodTypeNeeded, int requiredVolumeMl, String status) {
        JSONObject json = new JSONObject();
        json.put("id", requestId); // assuming your backend expects ID in the JSON body
        json.put("hospitalName", hospitalName);
        json.put("staffName", staffName);
        json.put("bloodTypeNeeded", bloodTypeNeeded);
        json.put("requiredVolumeMl", requiredVolumeMl);
        json.put("status", status);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + requestId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error updating blood request: " + e.getMessage());
        }
    }

    // Delete BloodRequest by ID (DELETE)
    public void deleteBloodRequest(int requestId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + requestId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error deleting blood request: " + e.getMessage());
        }
    }

    public void updateRequestStatus(int requestId, String newStatus) {
        JSONObject json = new JSONObject();
        json.put("status", newStatus); // Assuming 'status' is the exact field name in your backend

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + requestId + "/status")) // optional: customize path as needed
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Status Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error updating donation status: " + e.getMessage());
        }
    }
}
