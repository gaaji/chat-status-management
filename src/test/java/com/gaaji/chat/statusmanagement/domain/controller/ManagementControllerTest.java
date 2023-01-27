package com.gaaji.chat.statusmanagement.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import com.gaaji.chat.statusmanagement.domain.entity.Subscribe;
import com.gaaji.chat.statusmanagement.domain.service.ManagementServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManagementControllerTest {

    @Autowired
    private ManagementController controller;

    @Autowired
    private ManagementServiceImpl service;

    @Nested
    @DisplayName("Management 컨트롤러 정상 시나리오 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class 정상_시나리오 {

        @Test
        @Order(1)
        @DisplayName("채팅방 생성 테스트")
        void createNewChatRoomTest() throws JsonProcessingException {
            String body = "{\"roomId\":\"room_1\", \"memberIds\": [ \"member_1\",\"member_2\"] }";
            controller.createNewChatRoom(body);

            ChatRoom chatRoom = service.findByRoomId("room_1");
            Assertions.assertNotNull(chatRoom);
            Assertions.assertEquals("room_1", chatRoom.getRoomId().getId());
            Assertions.assertEquals(2, chatRoom.getMembers().size());
        }

        @Test
        @Order(2)
        @DisplayName("채팅방 삭제 테스트")
        void deleteChatRoomTest() throws JsonProcessingException {
            service.saveNewChatRoom("room_2", List.of("member_1"));

            String body = "{ \"roomId\": \"room_2\" }";

            controller.deleteChatRoom(body);
        }

        @Test
        @Order(3)
        @DisplayName("채팅방 입장 테스트")
        void addMemberTest() throws JsonProcessingException {
            String body = "{ \"chatRoomId\": \"room_1\", \"memberId\": \"member_3\" }";
            controller.addMember(body);

            ChatRoom chatRoom = service.findByRoomId("room_1");
            Assertions.assertNotNull(chatRoom);
            Assertions.assertEquals(3, chatRoom.getMembers().size());
            Assertions.assertNotNull(chatRoom.getMembers().get(MemberId.of("member_3")));
        }

        @Test
        @Order(4)
        @DisplayName("채팅방 퇴장 테스트")
        void removeMemberTest() throws JsonProcessingException {
            String body = "{ \"chatRoomId\": \"room_1\", \"memberId\": \"member_3\" }";
            controller.removeMember(body);

            ChatRoom chatRoom = service.findByRoomId("room_1");
            Assertions.assertNotNull(chatRoom);
            Assertions.assertEquals(2, chatRoom.getMembers().size());
            Assertions.assertNull(chatRoom.getMembers().get(MemberId.of("member_3")));
        }

        @Test
        @Order(5)
        @DisplayName("유저 구독 상태로 변경 테스트")
        void subscribedChatRoom() throws JsonProcessingException {
            String body = "{ \"roomId\" : \"room_1\", \"memberId\": \"member_1\" }";
            controller.subscribe(body);

            ChatRoom chatRoom = service.findByRoomId("room_1");
            Assertions.assertNotNull(chatRoom);
            Subscribe subscribe = chatRoom.getMembers().get(MemberId.of("member_1"));
            Assertions.assertNotNull(subscribe);
            Assertions.assertTrue(subscribe.getIsSubscribe());
        }

        @Test
        @Order(6)
        @DisplayName("유저 구독 해제 상태로 변경 테스트")
        void unsubscribedChatRoom() throws JsonProcessingException {
            String body = "{ \"roomId\" : \"room_1\", \"memberId\": \"member_1\" }";
            controller.unsubscribe(body);

            ChatRoom chatRoom = service.findByRoomId("room_1");
            Assertions.assertNotNull(chatRoom);
            Subscribe subscribe = chatRoom.getMembers().get(MemberId.of("member_1"));
            Assertions.assertNotNull(subscribe);
            Assertions.assertFalse(subscribe.getIsSubscribe());
        }

        @Test
        @Order(999)
        void rollback() {
            service.deleteByRoomId("room_1");
        }
    }
}