package com.gaaji.chat.statusmanagement.domain.service;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.MemberId;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import com.gaaji.chat.statusmanagement.domain.repository.ChatRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("구독해제 상태인 유저 아이디 조회")
    void 구독해제_상태_유저_조회() {
        // given
        given(chatRoomRepository.findById(any()))
                .willReturn(Optional.of(ChatRoom.create("room_1",
                        List.of("member_1", "member_2", "member_3", "member_4", "member_5", "member_6", "member_7", "member_8"))));

        // 채팅방의 3명 구독 중
        ChatRoom chatRoom = chatRoomRepository.findById(RoomId.of("room_1")).orElseThrow();
        chatRoom.updateMemberSubscribed("member_1");
        chatRoom.updateMemberSubscribed("member_3");
        chatRoom.updateMemberSubscribed("member_5");

        // member_3이 발신자
        List<MemberId> notificationMemberIds = chatRoom.getMemberIdsByUnsubscribe("member_3");
        Assertions.assertNotNull(notificationMemberIds);
        Assertions.assertEquals(5, notificationMemberIds.size());
    }
}