package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;

import java.util.List;

public interface ManagementService {

    void saveNewChatRoom(String _roomId, List<String> _memberIds);

    ChatRoom findByRoomId(String _roomId);

    void deleteByRoomId(String _roomId);

}
