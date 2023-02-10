package com.gaaji.chat.statusmanagement.global.feign;

import com.gaaji.chat.statusmanagement.domain.controller.dto.ChatRoomCreatedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("chat-api")
public interface ChatApiServiceClient {

    @GetMapping("/chat-rooms/{chatRoomId}")
    ChatRoomCreatedDto getChatRoom(@PathVariable("chatRoomId") String roomId);

}
