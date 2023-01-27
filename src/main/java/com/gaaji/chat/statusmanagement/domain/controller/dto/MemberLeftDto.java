package com.gaaji.chat.statusmanagement.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLeftDto {
    private String chatRoomId;
    private String memberId;
}
