package com.gaaji.chat.statusmanagement.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chat.statusmanagement.domain.controller.dto.ChattedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService{

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOPIC_SEND_MESSAGE = "chat-chatNotified";

    @Override
    public void sendMessageNotification(ChattedDto chattedDto) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(chattedDto);

        log.info("send kafka message - topic: {}, message: {}", TOPIC_SEND_MESSAGE, message);
        kafkaTemplate.send(TOPIC_SEND_MESSAGE, message);
    }
}
