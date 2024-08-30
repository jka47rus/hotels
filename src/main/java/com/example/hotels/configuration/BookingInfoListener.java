package com.example.hotels.configuration;

import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.service.BookingInfoService;
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
public class BookingInfoListener {

    private final BookingInfoService bookingInfoService;

    @KafkaListener(topics = "${app.kafka.bookingTopic}",
            groupId = "${app.kafka.bookingGroupId}",
            containerFactory = "bookingInfoConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload BookingInfo bookingInfo,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp){
        log.info("Received message: {}", bookingInfo);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);

        bookingInfoService.save(bookingInfo);

    }


}
