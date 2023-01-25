package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import com.gaaji.chat.statusmanagement.domain.entity.Subscribe;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManagementServiceImplTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ManagementServiceImpl managementService;

    @Test
    @DisplayName("채팅방 저장 테스트")
    void 채팅방_저장() {
        // given
        ChatRoom testRoom = ChatRoom.create("room_1", List.of("member_1", "member_2"));
        given(chatRoomRepository.save(any())).willReturn(testRoom);

        // when
        ChatRoom saveRoom = managementService.saveNewChatRoom("room_1", List.of("member_1", "member_2"));

        // then
        Assertions.assertEquals(testRoom.getRoomId(), saveRoom.getRoomId());
        Assertions.assertEquals(testRoom.getMembers().size(), saveRoom.getMembers().size());
        Assertions.assertFalse(saveRoom.getMembers().get(MemberId.of("member_1")).getIsSubscribe());
    }

    @Test
    @DisplayName("구독 / 해제 테스트")
    void 유저가_채팅방을_구독_및_구독해제하는_경우() {
        //given
        given(chatRoomRepository.findById(any()))
                .willReturn(Optional.of(ChatRoom.create("room_1", List.of("member_1","member_2"))));

        // 구독 이벤트 시작
        managementService.subscribe("room_1", "member_1");

        // 구독 변경 여부 검증
        ChatRoom subRoom = managementService.findByRoomId("room_1");
        Subscribe subscribe = subRoom.getMembers().get(MemberId.of("member_1"));
        Assertions.assertTrue(subscribe.getIsSubscribe());

        // 구독 해제 이벤트 시작
        managementService.unsubscribe("room_1", "member_1");

        // 구독 해제 변경 여부 검증
        ChatRoom unsubRoom = managementService.findByRoomId("room_1");
        Subscribe unsubscribe = unsubRoom.getMembers().get(MemberId.of("member_1"));
        Assertions.assertFalse(unsubscribe.getIsSubscribe());
    }

    @Test
    @DisplayName("그룹 채팅방 입장 테스트")
    void 새로운_유저가_그룹_채팅방에_입장하는_경우() {
        // given
        given(chatRoomRepository.findById(any()))
                .willReturn(Optional.of(ChatRoom.create("room_1", List.of("member_1","member_2"))));

        // 멤버 3 채팅방 입장
        managementService.addMemberToChatRoom("room_1", "member_3");

        // 검증
        ChatRoom chatRoom = managementService.findByRoomId("room_1");
        Assertions.assertEquals(3, chatRoom.getMembers().size());
        Assertions.assertNotNull(chatRoom.getMembers().get(MemberId.of("member_3")));
        Assertions.assertFalse(chatRoom.getMembers().get(MemberId.of("member_3")).getIsSubscribe());
    }

    @Test
    @DisplayName("그룹 채팅방 퇴장 테스트")
    void 유저가_그룹_채팅방에서_나가는_경우() {
        // given
        given(chatRoomRepository.findById(any()))
                .willReturn(Optional.of(ChatRoom.create("room_1", List.of("member_1","member_2"))));

        // 멤버 2 채팅방 퇴장
        managementService.removeMemberFromChatRoom("room_1", "member_2");

        ChatRoom chatRoom = managementService.findByRoomId("room_1");
        Assertions.assertEquals(1, chatRoom.getMembers().size());
        Assertions.assertNull(chatRoom.getMembers().get(MemberId.of("member_2")));
    }

}