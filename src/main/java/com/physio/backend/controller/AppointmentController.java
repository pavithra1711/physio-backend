package com.physio.backend.controller;

import com.physio.backend.service.WhatsAppService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "https://physiocare.netlify.app")
@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final WhatsAppService whatsAppService;

    public AppointmentController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping
    public String createAppointment(@RequestBody Map<String, String> request) {

        String name = request.get("name");
        String mobile = request.get("mobile");
        String service = request.get("service");
        String message = request.get("message");

        String text = "📌 New Appointment Request:\n"
                + "Name: " + name + "\n"
                + "Mobile: " + mobile + "\n"
                + "Service: " + service + "\n"
                + "Message: " + message;

        whatsAppService.send(text);

        return "Appointment request received successfully";
    }
}