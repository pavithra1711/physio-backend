package com.physio.backend.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String URL = "https://api.resend.com/emails";
    private final OkHttpClient client = new OkHttpClient();

    public void send(String name, String email, String messageBody) {

        System.out.println("RESEND KEY: " + System.getenv("RESEND_API_KEY"));

        try {
            String json = "{"
                    + "\"from\":\"Physio Clinic <onboarding@resend.dev>\","
                    + "\"to\":\"sripavithra1711@gmail.com\","
                    + "\"subject\":\"New Clinic Inquiry\","
                    + "\"text\":\"Name: " + name + "\\nEmail: " + email + "\\nMessage: " + messageBody + "\""
                    + "}";

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + System.getenv("RESEND_API_KEY"))
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {

                System.out.println("STATUS: " + response.code());
                System.out.println("BODY: " + response.body().string());

                if (!response.isSuccessful()) {
                    throw new RuntimeException("Email failed: " + response.body().string());
                }

                System.out.println("Email sent successfully via Resend!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}