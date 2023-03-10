package com.gaaji.chat.statusmanagement.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@RedisHash("room")
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class ChatRoom implements Serializable {

    @Id
    private final RoomId roomId;

    private final Map<MemberId, Subscribe> members;

    public static ChatRoom create(String roomId, List<String> memberIds) {
        Map<MemberId, Subscribe> members = new HashMap<>();
        memberIds.forEach(
                memberId -> members.put(MemberId.of(memberId), Subscribe.create()));

        return ChatRoom.of(RoomId.of(roomId), members);
    }

    public ChatRoom addMember(String memberId) {
        members.put(MemberId.of(memberId), Subscribe.create());

        return this;
    }

    public ChatRoom removeMember(String memberId) {
        members.remove(MemberId.of(memberId));

        return this;
    }

    public ChatRoom updateMemberSubscribed(String memberId) {
        members.replace(MemberId.of(memberId), Subscribe.subscribe());

        return this;
    }

    public ChatRoom updateMemberUnsubscribed(String memberId) {
        members.replace(MemberId.of(memberId), Subscribe.unsubscribe());

        return this;
    }

    public List<String> getMemberIdsByUnsubscribe(String senderId) {
        return members.entrySet().stream()
                .filter(e -> !e.getValue().getIsSubscribe())
                .filter(e -> !Objects.equals(e.getKey().getId(), senderId))
                .map(e -> e.getKey().getId())
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ChatRoom cr = (ChatRoom) o;
        return Objects.equals(this.roomId, cr.roomId);
    }

    @Override
    public int hashCode() {
        return this.roomId.hashCode();
    }

}
