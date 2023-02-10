package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatRoomCreatedDto;
import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import com.gaaji.chat.statusmanagement.global.errorhandler.ResourceErrorHandler;
import com.gaaji.chat.statusmanagement.global.exception.CSMException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{

    private final ChatRoomRepository chatRoomRepository;
    private final ResourceErrorHandler resourceErrorHandler;

    @Override
    public ChatRoom saveNewChatRoom(String roomId, List<String> memberIds) {
        ChatRoom chatRoom = ChatRoom.create(roomId, memberIds);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom findByRoomId(String roomId) {
        return chatRoomRepository.findById(RoomId.of(roomId))
                .or(() -> {
                    try {
                        ChatRoomCreatedDto chatRoomCreatedDto = resourceErrorHandler.handleChatRoomIdNotFound(roomId);
                        return Optional.of(
                            saveNewChatRoom(chatRoomCreatedDto.getRoomId(), chatRoomCreatedDto.getMemberIds())
                        );
                    } catch (FeignException e) {
                        String msg = String.format("cannot retrieve chat room from chat-api; chat-room-id: %s", roomId);
                        throw new CSMException(msg);
                    }
                }
        ).orElseThrow();
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
