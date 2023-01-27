package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatRoomCreatedDto;
import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import com.gaaji.chat.statusmanagement.global.errorhandler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{

    private final ChatRoomRepository chatRoomRepository;
    private final ErrorHandler errorHandler;

    @Override
    public ChatRoom saveNewChatRoom(String roomId, List<String> memberIds) {
        ChatRoom chatRoom = ChatRoom.create(roomId, memberIds);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom findByRoomId(String roomId) {
        ChatRoom chatRoom;
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(RoomId.of(roomId));
        if ( chatRoomOptional.isPresent() ) {
            chatRoom = chatRoomOptional.get();
        } else {
            ChatRoomCreatedDto chatRoomCreatedDto = errorHandler.handleChatRoomIdNotFound(roomId);
            chatRoom = saveNewChatRoom(chatRoomCreatedDto.getRoomId(), chatRoomCreatedDto.getMemberIds());
        }
        return chatRoom;
    }

    @Override
    public void deleteByRoomId(String roomId) {
        ChatRoom chatRoom = findByRoomId(roomId);

        deleteChatRoom(chatRoom);
    }

    @Override
    public void deleteChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }

    @Override
    public void subscribe(String roomId, String memberId) {
        ChatRoom chatRoom = findByRoomId(roomId);

        chatRoom.updateMemberSubscribed(memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void unsubscribe(String roomId, String memberId) {
        ChatRoom chatRoom = findByRoomId(roomId);

        chatRoom.updateMemberUnsubscribed(memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void addMemberToChatRoom(String roomId, String memberId) {
        ChatRoom chatRoom = findByRoomId(roomId);

        chatRoom.addMember(memberId);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void removeMemberFromChatRoom(String roomId, String memberId) {
        ChatRoom chatRoom = findByRoomId(roomId);

        chatRoom.removeMember(memberId);

        chatRoomRepository.save(chatRoom);
    }

}
