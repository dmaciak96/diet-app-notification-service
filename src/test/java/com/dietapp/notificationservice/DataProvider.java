package com.dietapp.notificationservice;

import com.dietapp.notificationservice.model.dto.NotificationDto;
import com.dietapp.notificationservice.model.entity.CustomProperty;
import com.dietapp.notificationservice.model.entity.Notification;
import com.dietapp.notificationservice.model.http.NotificationHttpResponse;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DataProvider {

    public static final Instant CREATED_DATE = Instant.now();
    public static final Instant LAST_UPDATED_DATE = Instant.now();
    public static final int VERSION = 20;
    public static final String CODE = "testCode";
    public static final String MESSAGE = "test message";
    public static final String KEY = "testKey";
    public static final String VALUE = "testValue";

    public static Notification createEntity(UUID id, UUID propertyId) {
        var notification = Notification.builder()
                .lastUpdatedDate(LAST_UPDATED_DATE)
                .createdDate(CREATED_DATE)
                .code(CODE)
                .version(VERSION)
                .message(MESSAGE)
                .id(id)
                .build();

        notification.setProperties(Set.of(CustomProperty.builder()
                .notification(notification)
                .key(KEY)
                .value(VALUE)
                .id(propertyId)
                .build()));
        return notification;
    }

    public static NotificationDto createDto(UUID id) {
        return NotificationDto.builder()
                .properties(Map.of(KEY, VALUE))
                .lastUpdatedDate(LAST_UPDATED_DATE)
                .createdDate(CREATED_DATE)
                .version(VERSION)
                .message(MESSAGE)
                .code(CODE)
                .id(id)
                .build();
    }

    public static NotificationHttpResponse createHttpResponse(UUID id) {
        return NotificationHttpResponse.builder()
                .properties(Map.of(KEY, VALUE))
                .lastUpdatedDate(LAST_UPDATED_DATE)
                .createdDate(CREATED_DATE)
                .message(MESSAGE)
                .code(CODE)
                .id(id)
                .build();
    }
}
