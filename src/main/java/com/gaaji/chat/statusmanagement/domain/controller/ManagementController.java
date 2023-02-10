package com.gaaji.chat.statusmanagement.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.statusmanagement.domain.controller.dto.*;
import com.gaaji.chat.statusmanagement.domain.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "chat-chatRoomCreated", errorHandler = "kafkaExceptionHandler")
    public void createNewChatRoom(String body) throws JsonProcessingException {
        ChatRoomCreatedDto chatRoomCreatedDto = objectMapper.readValue(body, ChatRoomCreatedDto.class);

        managementService.saveNewChatRoom(chatRoomCreatedDto.getRoomId(), chatRoomCreatedDto.getMemberIds());
    }

    @KafkaListener(topics = "chat-chatRoomDeleted", errorHandler = "kafkaExceptionHandler")
    public void deleteChatRoom(String body) throws JsonProcessingException {
        ChatRoomDeletedDto chatRoomDeletedDto = objectMapper.readValue(body, ChatRoomDeletedDto.class);

        managementService.deleteByRoomId(chatRoomDeletedDto.getRoomId());
    }

    @KafkaListener(topics = "chat-memberAdded", errorHandler = "kafkaExceptionHandler")
    public void addMember(String body) throws JsonProcessingException {
        MemberAddedDto memberAddedDto = objectMapper.readValue(body, MemberAddedDto.class);

        managementService.addMemberToChatRoom(memberAddedDto.getChatRoomId(), memberAddedDto.getMemberId());
    }

    @KafkaListener(topics = "chat-memberLeft", errorHandler = "kafkaExceptionHandler")
    public void removeMember(String body) throws JsonProcessingException {
        MemberLeftDto memberLeftDto = objectMapper.readValue(body, MemberLeftDto.class);

        managementService.removeMemberFromChatRoom(memberLeftDto.getChatRoomId(), memberLeftDto.getMemberId());
    }

    @KafkaListener(topics = "chat-memberSubscribed", errorHandler = "kafkaExceptionHandler")
    public void subscribe(String body) throws JsonProcessingException {
        MemberSubscribedDto memberSubscribedDto = objectMapper.readValue(body, MemberSubscribedDto.class);

        managementService.subscribe(memberSubscribedDto.getRoomId(), memberSubscribedDto.getMemberId());
    }

    @KafkaListener(topics = "chat-memberUnsubscribed", errorHandler = "kafkaExceptionHandler")
    public void unsubscribe(String body) throws JsonProcessingException {
        MemberUnsubscribedDto memberUnsubscribedDto = objectMapper.readValue(body, MemberUnsubscribedDto.class);

        managementService.unsubscribe(memberUnsubscribedDto.getRoomId(), memberUnsubscribedDto.getMemberId());
    }
}
