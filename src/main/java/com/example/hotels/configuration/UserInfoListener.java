package com.example.hotels.configuration;

import com.example.hotels.dto.kafka.UserInfo;
import com.example.hotels.entity.UserInfoMongo;
import com.example.hotels.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInfoListener {

    private final UserInfoService userInfoService;

    @KafkaListener(topics = "${app.kafka.usersTopic}",
            groupId = "${app.kafka.usersGroupId}",
            containerFactory = "userInfoConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload UserInfo userInfo,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", userInfo);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);


        userInfoService.save(UserInfoMongo.builder()
                .userId(userInfo.getUserId())
                .build());

    }
}
