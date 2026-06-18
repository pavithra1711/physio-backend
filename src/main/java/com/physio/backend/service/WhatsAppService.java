package com.physio.backend.service;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WhatsAppService {

    private final String phone = "919150520609";

    public void send(String message) {
        try {
            String url = "https://wa.me/" + phone + "?text=" +
                    URLEncoder.encode(message, StandardCharsets.UTF_8);

            // For backend debugging
            System.out.println("WhatsApp URL:");
            System.out.println(url);

            // In real system:
            // Angular frontend will open this URL in new tab

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}