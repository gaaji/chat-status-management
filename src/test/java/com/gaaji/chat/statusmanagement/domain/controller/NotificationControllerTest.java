package com.gaaji.chat.statusmanagement.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationControllerTest {

    @Autowired
    private NotificationController notificationController;
    @Autowired
    private ManagementController managementController;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Notification 컨트롤러 정상 시나리오 테스트")
    class 정상_시나리오 {

        @Test
        @Order(1)
        void init() throws JsonProcessingException {
            String body = "{\"roomId\":\"room_1\", \"memberIds\": [ \"member_1\",\"member_2\",\"member_3\",\"member_4\",\"member_5\"] }";
            managementController.createNewChatRoom(body);
        }

        @Test
        @Order(2)
        @DisplayName("채팅 메시지 전달 테스트")
        void sendMessageNotification() throws JsonProcessingException {
            String body = "{ \"roomId\": \"room_1\", \"senderId\": \"member_1\", \"content\": \"알림서버 전달 메시지 테스트.\" }";
            notificationController.sendMessageNotification(body);
        }

        @Test
        @Order(999)
        void rollback() throws JsonProcessingException {
            String body = "{\"roomId\":\"room_1\"}";
            managementController.deleteChatRoom(body);
        }

    }

}