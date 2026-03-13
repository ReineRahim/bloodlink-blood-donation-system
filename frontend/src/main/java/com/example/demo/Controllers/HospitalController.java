package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HospitalController {
    private static final String BASE_URL = "http://localhost:9090/api/hospitals";  // adjust if needed

    private final HttpClient client = HttpClient.newHttpClient();

    // Create a new Hospital (POST)
    public void createHospital(String hospitalName, String contactNumber, String address, String hospitalPass) {
        JSONObject json = new JSONObject();
        json.put("hospitalName", hospitalName);
        json.put("contactNumber", contactNumber);
        json.put("address", address);
        json.put("hospitalPass", hospitalPass);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Hospital Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error creating hospital: " + e.getMessage());
        }
    }

    // Get all Hospitals (GET)
    public JSONArray getAllHospitals() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching hospitals: " + e.getMessage());
            return new JSONArray();
        }
    }

    // Update Hospital (PUT)
    public void updateHospital(int hospitalId, String hospitalName, String contactNumber, String address, String hospitalPass) {
        JSONObject json = new JSONObject();
        json.put("id", hospitalId);
        json.put("hospitalName", hospitalName);
        json.put("contactNumber", contactNumber);
        json.put("address", address);
        json.put("hospitalPass", hospitalPass);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + hospitalId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Hospital Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error updating hospital: " + e.getMessage());
        }
    }

    // Delete Hospital by ID (DELETE)
    public void deleteHospital(int hospitalId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + hospitalId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Hospital Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error deleting hospital: " + e.getMessage());
        }
    }
}
