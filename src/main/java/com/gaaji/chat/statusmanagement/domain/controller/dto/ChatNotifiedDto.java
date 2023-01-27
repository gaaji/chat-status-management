package com.gaaji.chat.statusmanagement.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ChatNotifiedDto {
    private String roomId;
    private String senderId;
    private List<String> receiverIds;
    private String content;
}
