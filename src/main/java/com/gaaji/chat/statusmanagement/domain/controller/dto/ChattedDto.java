package com.gaaji.chat.statusmanagement.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChattedDto {
    private String roomId;
    private String senderId;
    private List<String> receiverIds;
    private String content;

    public static ChattedDto of(String roomId, String senderId, List<String> receiverIds, String content) {
        return new ChattedDto(roomId, senderId, receiverIds, content);
    }
}
