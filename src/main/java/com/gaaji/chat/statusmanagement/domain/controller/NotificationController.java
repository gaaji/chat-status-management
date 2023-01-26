package com.gaaji.chat.statusmanagement.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.statusmanagement.domain.controller.dto.ChattedDto;
import com.gaaji.chat.statusmanagement.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "chat-chatted")
    public void sendMessageNotification(String body) throws JsonProcessingException {
        ChattedDto chattedDto = objectMapper.readValue(body, ChattedDto.class);

        notificationService.sendMessageNotification(chattedDto.getRoomId(), chattedDto.getSenderId(), chattedDto.getContent());
    }
}
