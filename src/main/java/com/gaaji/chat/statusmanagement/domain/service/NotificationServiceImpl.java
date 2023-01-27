package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatNotifiedDto;
import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ManagementService managementService;
    private final KafkaService kafkaService;

    @Override
    public void sendMessageNotification(String roomId, String senderId, String content) throws JsonProcessingException {
        ChatRoom chatRoom = managementService.findByRoomId(roomId);

        List<String> notificationMemberIds = chatRoom.getMemberIdsByUnsubscribe(senderId);

        if(notificationMemberIds.size() > 0) {
            ChatNotifiedDto chatNotifiedDto = ChatNotifiedDto.of(roomId, senderId, notificationMemberIds, content);
            kafkaService.sendMessageNotification(chatNotifiedDto);
        }
    }
}
