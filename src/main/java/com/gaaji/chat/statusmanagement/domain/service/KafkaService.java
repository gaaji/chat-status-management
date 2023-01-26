package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.statusmanagement.domain.controller.ChattedDto;

public interface KafkaService {

    void sendMessageNotification(ChattedDto chattedDto) throws JsonProcessingException;

}
