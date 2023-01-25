package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;

import java.util.List;

public interface ManagementService {

    void saveNewChatRoom(String _roomId, List<String> _memberIds);

    ChatRoom findByRoomId(String _roomId);

    void deleteByRoomId(String _roomId);

    void subscribe(String _roomId, String _memberId);

    void unsubscribe(String _roomId, String _memberId);

}
