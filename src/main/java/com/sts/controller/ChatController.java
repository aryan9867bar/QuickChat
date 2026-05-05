package com.sts.controller;

import com.sts.model.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/{roomID}/sendMessage") // Listen to messages sent to /app/{roomID}/sendMessage
    @SendTo("/topic/{roomID}/messages") // Broadcast to subscribers of /topic/{roomID}/messages
    public ChatMessage sendMessage(@DestinationVariable String roomID, ChatMessage message) {
        System.out.println("Received message in room " + roomID + ": " + message.getContent());
        return message; // Return the message to be broadcasted
    }
}
