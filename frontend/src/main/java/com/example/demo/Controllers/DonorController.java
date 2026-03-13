package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DonorController {
    private static final String BASE_URL = "http://localhost:9090/api/donors"; // adjust if needed

    private final HttpClient client = HttpClient.newHttpClient();

    // Create a new Donor (POST)
    public void createDonor(String firstName, String lastName, String username, String password,
                            String gender, String address, String contactNumber, String dateOfBirth) {
        JSONObject json = new JSONObject();
        json.put("DfirstName", firstName);
        json.put("DlastName", lastName);
        json.put("username", username);
        json.put("password", password);
        json.put("gender", gender);
        json.put("Daddress", address);
        json.put("DcontactNumber", contactNumber);
        json.put("dateOfBirth", dateOfBirth);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Donor Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error creating donor: " + e.getMessage());
        }
    }


    // Get all Donors (GET)
    public JSONArray getAllDonors() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/api/donors/all"))  // <-- FIXED ENDPOINT
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body().trim();


            if (!responseBody.startsWith("[")) {
                System.err.println("Expected JSON Array but got something else.");
                return new JSONArray(); // return empty array to avoid crash
            }

            return new JSONArray(responseBody);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching donors: " + e.getMessage());
            return new JSONArray();
        }
    }


    // Get Donor by ID (GET)
    public JSONObject getDonorById(int donorId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donorId))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching donor: " + e.getMessage());
            return new JSONObject();
        }
    }

    // Update Donor (PUT)
    public void updateDonor(int donorId, String firstName, String lastName, String username, String password,
                            String gender, String address, String contactNumber, String dateOfBirth) {
        JSONObject json = new JSONObject();
        json.put("id", donorId);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("username", username);
        json.put("password", password);
        json.put("gender", gender);
        json.put("address", address);
        json.put("contactNumber", contactNumber);
        json.put("dateOfBirth", dateOfBirth);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donorId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Donor Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error updating donor: " + e.getMessage());
        }
    }

    // Delete Donor by ID (DELETE)
    public void deleteDonor(int donorId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donorId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Donor Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error deleting donor: " + e.getMessage());
        }
    }
}

