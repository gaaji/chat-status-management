package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatNotifiedDto;

public interface KafkaService {

    void sendMessageNotification(ChatNotifiedDto chatNotifiedDto) throws JsonProcessingException;

}
