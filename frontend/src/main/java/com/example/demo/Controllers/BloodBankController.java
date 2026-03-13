package com.example.demo.Controllers;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BloodBankController {
    private static final String BASE_URL = "http://localhost:9090/api";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void createBloodBank(JSONObject bloodBankData) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bloodBankData.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error creating blood bank: " + e.getMessage());
        }
    }

    // GET all blood banks
    public static JSONArray getAllBloodBanks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching blood banks: " + e.getMessage());
            return new JSONArray();
        }
    }

    // GET blood bank by ID
    public static JSONObject getBloodBankById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching blood bank by ID: " + e.getMessage());
            return new JSONObject();
        }
    }

    // UPDATE blood bank
    public static void updateBloodBank(int id, JSONObject updatedData) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(updatedData.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error updating blood bank: " + e.getMessage());
        }
    }

    // DELETE blood bank
    public static void deleteBloodBank(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error deleting blood bank: " + e.getMessage());
        }
    }
}
