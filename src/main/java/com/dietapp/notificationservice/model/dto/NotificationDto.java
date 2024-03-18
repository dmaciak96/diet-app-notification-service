package com.dietapp.notificationservice.model.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
public record NotificationDto(UUID id,
                              String message,
                              String code,
                              Map<String, String> properties,
                              int version,
                              Instant createdDate,
                              Instant lastUpdatedDate) {
}
