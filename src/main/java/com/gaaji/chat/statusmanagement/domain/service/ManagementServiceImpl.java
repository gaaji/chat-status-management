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
    public ChatRoom saveNewChatRoom(String _roomId, List<String> _memberIds) {
        ChatRoom chatRoom = ChatRoom.create(_roomId, _memberIds);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom findByRoomId(String _roomId) {
        return chatRoomRepository.findById(RoomId.of(_roomId)).orElseThrow();
    }

    @Override
    public void deleteByRoomId(String _roomId) {
        chatRoomRepository.deleteById(RoomId.of(_roomId));
    }

    @Override
    public void subscribe(String _roomId, String _memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(_roomId)).orElseThrow();

        chatRoom.updateMemberSubscribed(_memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void unsubscribe(String _roomId, String _memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(_roomId)).orElseThrow();

        chatRoom.updateMemberUnsubscribed(_memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void addMemberToChatRoom(String _roomId, String _memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(_roomId)).orElseThrow();

        chatRoom.addMember(_memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void removeMemberFromChatRoom(String _roomId, String _memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of(_roomId)).orElseThrow();

        chatRoom.removeMember(_memberId);

        chatRoomRepository.save(chatRoom);
    }

}
