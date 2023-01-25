package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Override
    public void deleteByRoomId(String _roomId) {
        RoomId roomId = RoomId.of(_roomId);

        chatRoomRepository.deleteById(roomId);
    }

    @Override
    public void subscribe(String _roomId, String _memberId) {
        RoomId roomId = RoomId.of(_roomId);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        chatRoom.updateMemberSubscribed(_memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void unsubscribe(String _roomId, String _memberId) {
        RoomId roomId = RoomId.of(_roomId);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        chatRoom.updateMemberUnsubscribed(_memberId);

        chatRoomRepository.save(chatRoom);
    }

}
