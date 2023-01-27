package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationService {

    void sendMessageNotification(String roomId, String senderId, String content) throws JsonProcessingException;

}
