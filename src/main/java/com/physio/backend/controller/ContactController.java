package com.physio.backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.physio.backend.dto.ContactRequest;
import com.physio.backend.service.EmailService;

@CrossOrigin(origins = "https://physiocare.netlify.app")
@RestController
@RequestMapping("/api")
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<String> contact(@RequestBody ContactRequest request) {

        emailService.send(
            request.getName(),
            request.getEmail(),
            request.getMessage()
        );

        return ResponseEntity.ok("Message received");
    }

    @GetMapping("/test-email")
    public String testEmail() {

        emailService.send(
            "Test User",
            "test@gmail.com",
            "Test email from Physio Clinic System"
        );

        return "Email Sent";
    }
}