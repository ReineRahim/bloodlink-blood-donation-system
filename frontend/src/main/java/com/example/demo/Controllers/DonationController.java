package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DonationController {
    private static final String BASE_URL = "http://localhost:9090/api/donations"; // Change as needed

    private final HttpClient client = HttpClient.newHttpClient();

    private String donorUsername;

    public void setDonorUsername(String donorUsername) {
        this.donorUsername = donorUsername;
    }

    // Create a new Donation (POST)
    public void createDonation(String donorName, String bloodType, int volumeMl, String donationDate) {
        JSONObject json = new JSONObject();
        json.put("donorName", donorName);
        json.put("bloodType", bloodType);
        json.put("volumeMl", volumeMl);
        json.put("donationDate", donationDate); // Expecting ISO date string (e.g. "2025-05-25")

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Donation Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error creating donation: " + e.getMessage());
        }
    }

    // Get all Donations (GET)
    public JSONArray getAllDonations() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching donations: " + e.getMessage());
            return new JSONArray();
        }
    }

    // Get Donation by ID (GET)
    public JSONObject getDonationById(int donationId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donationId))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching donation: " + e.getMessage());
            return new JSONObject();
        }
    }

    // Update a Donation (PUT)
    public void updateDonation(int donationId, String donorName, String bloodType, int volumeMl, String donationDate) {
        JSONObject json = new JSONObject();
        json.put("id", donationId); // assuming your backend expects ID in the JSON body
        json.put("donorName", donorName);
        json.put("bloodType", bloodType);
        json.put("volumeMl", volumeMl);
        json.put("donationDate", donationDate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donationId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Donation Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error updating donation: " + e.getMessage());
        }
    }

    // Delete a Donation by ID (DELETE)
    public void deleteDonation(long donationId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donationId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Donation Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error deleting donation: " + e.getMessage());
        }
    }

    // Update only the status of a Donation (PUT or PATCH depending on backend support)
    public void updateDonationStatus(int donationId, String newStatus) {
        JSONObject json = new JSONObject();
        json.put("status", newStatus); // Assuming 'status' is the exact field name in your backend

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + donationId + "/status")) // optional: customize path as needed
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

