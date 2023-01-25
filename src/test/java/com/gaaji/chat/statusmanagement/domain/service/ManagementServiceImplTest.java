package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
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

}