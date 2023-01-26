package com.gaaji.chat.statusmanagement.domain.service;

public interface NotificationService {

    void sendMessageNotification(String roomId, String senderId, String content);

}
