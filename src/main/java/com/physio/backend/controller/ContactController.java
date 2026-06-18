package com.physio.backend.controller;

import com.physio.backend.service.EmailService;
import com.physio.backend.service.WhatsAppService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "https://physiocare.netlify.app")
@RestController
@RequestMapping("/api")
public class ContactController {

    private final WhatsAppService whatsAppService;
    private final EmailService emailService;

    public ContactController(WhatsAppService whatsAppService, EmailService emailService) {
        this.whatsAppService = whatsAppService;
        this.emailService = emailService;
    }

    @PostMapping("/contact")
    public String contact(@RequestBody Map<String, String> request) {

        String name = request.get("name");
        String mobile = request.get("mobile");
        String message = request.get("message");

        String text = "📩 New Contact Request:\n"
                + "Name: " + name + "\n"
                + "Mobile: " + mobile + "\n"
                + "Message: " + message;

        whatsAppService.send(text);
        emailService.send(text);

        return "Message sent successfully";
    }
    @GetMapping("/test-email")
    public String testEmail() {
        emailService.send("Test email from Physio Clinic System");
        return "Email Sent";
    }
}