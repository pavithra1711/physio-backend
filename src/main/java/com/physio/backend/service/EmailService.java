package com.physio.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private static final String RESEND_URL = "https://api.resend.com/emails";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void send(String name, String email, String mobile, String service, String messageBody) {

        try {

            String apiKey = System.getenv("RESEND_API_KEY");

            if (apiKey == null || apiKey.isBlank()) {
                throw new RuntimeException("RESEND_API_KEY is not configured");
            }

            Map<String, Object> payload = new HashMap<>();

            payload.put("from", "Physio Clinic <onboarding@resend.dev>");
            payload.put("to", "1711.pavithra@gmail.com");
            payload.put("subject", "New Clinic Inquiry");

            payload.put(
            	    "text",
            	    "New Clinic Inquiry\n\n" +
            	    "Name: " + name + "\n" +
            	    "Mobile: " + mobile + "\n" +
            	    "Service: " + service + "\n" +
            	    "Email: " + email + "\n" +
            	    "Message: " + messageBody
            	);

            String json = objectMapper.writeValueAsString(payload);

            System.out.println("===== RESEND REQUEST =====");
            System.out.println(json);

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(RESEND_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {

                String responseBody = response.body() != null
                        ? response.body().string()
                        : "";

                System.out.println("===== RESEND RESPONSE =====");
                System.out.println("Status: " + response.code());
                System.out.println("Body: " + responseBody);

                if (!response.isSuccessful()) {
                    throw new RuntimeException(
                            "Resend API Error (" + response.code() + "): " + responseBody
                    );
                }

                System.out.println("Email sent successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}