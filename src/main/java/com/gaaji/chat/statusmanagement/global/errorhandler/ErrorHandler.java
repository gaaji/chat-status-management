package com.gaaji.chat.statusmanagement.global.errorhandler;

import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatRoomCreatedDto;
import com.gaaji.chat.statusmanagement.global.feign.ChatApiServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorHandler {

    private final ChatApiServiceClient chatApiServiceClient;

    public ChatRoomCreatedDto handleChatRoomIdNotFound(String roomId) {
        return chatApiServiceClient.getChatRoom(roomId);
    }

}
