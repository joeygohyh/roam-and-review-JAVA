package app.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @MessageMapping("/chat")
  @SendTo("/topic/meet")
  public Message greeting(Message message) throws Exception {
    System.out.println(message.toString());
    return message;
  }

}
