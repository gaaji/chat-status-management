package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import com.gaaji.chat.statusmanagement.domain.entity.Subscribe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManagementServiceImplTest {

    @Autowired
    ManagementService managementService;

    @Test
    void 채팅방_저장() {
        // given
        ChatRoom testRoom = ChatRoom.create("room_1", List.of("member_1", "member_2"));

        // when
        managementService.saveNewChatRoom("room_1", List.of("member_1", "member_2"));

        // then
        ChatRoom findRoom = managementService.findByRoomId("room_1");
        Assertions.assertEquals(testRoom.getRoomId(), findRoom.getRoomId());
        Assertions.assertEquals(testRoom.getMembers().size(), findRoom.getMembers().size());
        Assertions.assertFalse(findRoom.getMembers().get(MemberId.of("member_1")).getIsSubscribe());

        // rollback
        managementService.deleteByRoomId(findRoom.getRoomId().getId());
    }

    @Test
    void 유저_채팅방_구독_및_해제_검증() {
        // 테스트 데이터 추가
        managementService.saveNewChatRoom("room_1", List.of("member_1", "member_2"));

        // 구독 이벤트 시작
        managementService.subscribe("room_1", "member_1");

        // 구독 변경 여부 검증
        ChatRoom subRoom = managementService.findByRoomId("room_1");
        Subscribe subscribe = subRoom.getMembers().get(MemberId.of("member_1"));
        Assertions.assertTrue(subscribe.getIsSubscribe());
        System.out.println("Subscribe: "+ subRoom);

        // 구독 해제 이벤트 시작
        managementService.unsubscribe("room_1", "member_1");

        // 구독 해제 변경 여부 검증
        ChatRoom unsubRoom = managementService.findByRoomId("room_1");
        Subscribe unsubscribe = unsubRoom.getMembers().get(MemberId.of("member_1"));
        Assertions.assertFalse(unsubscribe.getIsSubscribe());
        System.out.println("Unsubscribe: "+ unsubRoom);

        // rollback
        managementService.deleteByRoomId("room_1");
    }

}