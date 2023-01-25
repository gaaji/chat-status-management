package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void saveNewChatRoom(String _roomId, List<String> _memberIds) {
        ChatRoom chatRoom = ChatRoom.create(_roomId, _memberIds);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom findByRoomId(String _roomId) {
        RoomId roomId = RoomId.of(_roomId);

        return chatRoomRepository.findById(roomId).orElseThrow();
    }

}
