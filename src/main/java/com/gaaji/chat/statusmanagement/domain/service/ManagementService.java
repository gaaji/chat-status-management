package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;

import java.util.List;

public interface ManagementService {

    /**
     * 새로운 채팅방을 저장하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @param _memberIds 유저 ID 리스트
     * @return ChatRoom
     */
    ChatRoom saveNewChatRoom(String _roomId, List<String> _memberIds);

    /**
     * 채팅방 ID로 채팅방을 조회하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @return ChatRoom
     */
    ChatRoom findByRoomId(String _roomId);

    /**
     * 채팅방 ID로 채팅방을 삭제하는 메소드.
     *
     * @param _roomId 채팅방 ID
     */
    void deleteByRoomId(String _roomId);

    /**
     * 특정 채팅방의 특정 유저의 웹소켓 구독 상태를
     * 구독으로 변경하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @param _memberId 유저 ID
     */
    void subscribe(String _roomId, String _memberId);

    /**
     * 특정 채팅방의 특정 유저의 웹소켓 구독 상태를
     * 구독 해제로 변경하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @param _memberId 유저 ID
     */
    void unsubscribe(String _roomId, String _memberId);

    /**
     * 특정 채팅방에 새로운 유저를 추가하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @param _memberId 유저 ID
     */
    void addMemberToChatRoom(String _roomId, String _memberId);

    /**
     * 특정 채팅방에 특정 유저를 삭제하는 메소드.
     *
     * @param _roomId 채팅방 ID
     * @param _memberId 유저 ID
     */
    void removeMemberFromChatRoom(String _roomId, String _memberId);
}
