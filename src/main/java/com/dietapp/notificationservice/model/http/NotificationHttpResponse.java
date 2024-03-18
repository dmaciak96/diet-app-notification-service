package com.dietapp.notificationservice.model.http;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
public record NotificationHttpResponse(UUID id,
                                       String message,
                                       String code,
                                       Map<String, String> properties,
                                       Instant createdDate,
                                       Instant lastUpdatedDate) {
}
