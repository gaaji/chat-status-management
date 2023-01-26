package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void sendMessageNotification(String roomId, String senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(roomId)).orElseThrow();

        List<MemberId> notificationMemberIds = chatRoom.getMemberIdsByUnsubscribe(senderId);

        // member id 리스트를 루핑돌면서 Kafka 알림 메시지 발행
    }
}
