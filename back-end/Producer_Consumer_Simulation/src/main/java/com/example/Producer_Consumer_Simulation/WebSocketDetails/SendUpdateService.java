package com.example.Producer_Consumer_Simulation.WebSocketDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendUpdateService {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public SendUpdateService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendUpdate(String message) {
        messagingTemplate.convertAndSend("/topic/updates", message);
    }
}