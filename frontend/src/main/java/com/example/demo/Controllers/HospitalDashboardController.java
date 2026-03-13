package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HospitalDashboardController {

    private static final String BASE_URL = "http://localhost:9090/api/bloodrequests";
    private final HttpClient client = HttpClient.newHttpClient();

    // Create new Blood Request (POST)
    public void createRequest(String hospitalName, String staffName, String bloodType, int volume) {
        JSONObject json = new JSONObject();
        json.put("hospitalName", hospitalName);
        json.put("staffName", staffName);
        json.put("bloodTypeNeeded", bloodType);
        json.put("requiredVolume", volume);
        json.put("status", "Pending");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Request created: " + response.body());
        } catch (Exception e) {
            System.err.println("Error creating request: " + e.getMessage());
        }
    }

    // Get all requests by hospital name (GET)
    public JSONArray getRequestsByHospital(String hospitalName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?hospital=" + hospitalName))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (Exception e) {
            System.err.println("Error fetching requests: " + e.getMessage());
            return new JSONArray();
        }
    }

    // Update request status (PUT)
    public void updateRequestStatus(int id, String newStatus) {
        JSONObject json = new JSONObject();
        json.put("status", newStatus);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id + "/status"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status updated: " + response.body());
        } catch (Exception e) {
            System.err.println("Error updating status: " + e.getMessage());
        }
    }

    // Delete request (DELETE)
    public void deleteRequest(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Request deleted: " + response.body());
        } catch (Exception e) {
            System.err.println("Error deleting request: " + e.getMessage());
        }
    }
}
