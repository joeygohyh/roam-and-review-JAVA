package app.backend.controllers;

import app.backend.models.EmailRequest;
import app.backend.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

  @Autowired
  EmailService emailService;

  @PostMapping("/send")
  public ResponseEntity<String> sendEmail(
    @RequestBody EmailRequest emailRequest
  ) throws MessagingException {
    String subject = "Thank you for subscribing to Roam&Review's newsletter";

    String body =
      "Thank you for subscribing to our newsletter " +
      emailRequest.getName() +
      "! Stay tuned for our launch!";

    emailService.sendSimpleMessage(emailRequest.getEmail(), subject, body);

    return ResponseEntity.ok("{\"message\": \"Successfully subscribed!\"}");
  }
}
