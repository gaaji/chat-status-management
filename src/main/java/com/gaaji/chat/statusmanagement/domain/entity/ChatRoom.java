package com.gaaji.chat.statusmanagement.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@RedisHash("room")
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class ChatRoom implements Serializable {

    @Id
    @NonNull
    private RoomId roomId;

    @NonNull
    private Map<MemberId, Subscribe> members;

    public static ChatRoom create(String _roomId, List<String> _memberIds) {
        RoomId roomId = RoomId.of(_roomId);

        Map<MemberId, Subscribe> members = new HashMap<>();
        _memberIds.forEach(
                memberId -> members.put(MemberId.of(memberId), Subscribe.create()));

        return ChatRoom.of(roomId, members);
    }

    public ChatRoom addMember(String memberId) {
        members.put(MemberId.of(memberId), Subscribe.create());

        return this;
    }

    public ChatRoom removeMember(String memberId) {
        members.remove(MemberId.of(memberId));

        return this;
    }

    public ChatRoom updateMemberSubscribed(String _memberId) {
        members.replace(MemberId.of(_memberId), Subscribe.subscribe());

        return this;
    }

    public ChatRoom updateMemberUnsubscribed(String _memberId) {
        members.replace(MemberId.of(_memberId), Subscribe.unsubscribe());

        return this;
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
