package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.statusmanagement.domain.controller.ChattedDto;
import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ChatRoomRepository chatRoomRepository;
    private final KafkaService kafkaService;

    @Override
    public void sendMessageNotification(String roomId, String senderId, String content) throws JsonProcessingException {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(roomId)).orElseThrow();

        List<String> notificationMemberIds = chatRoom.getMemberIdsByUnsubscribe(senderId);

        ChattedDto chattedDto = ChattedDto.of(roomId, senderId, notificationMemberIds, content);
        kafkaService.sendMessageNotification(chattedDto);
    }
}
