package com.gaaji.chat.statusmanagement.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreatedDto {
    private String roomId;
    private List<String> memberIds;
}
