package com.gaaji.chat.statusmanagement.domain.repository;

import com.gaaji.chat.statusmanagement.domain.entity.ChatRoom;
import com.gaaji.chat.statusmanagement.domain.entity.RoomId;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface ChatRoomRepository extends CrudRepository<ChatRoom, RoomId> {
}
