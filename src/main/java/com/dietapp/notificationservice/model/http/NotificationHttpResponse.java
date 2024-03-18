package com.dietapp.notificationservice.model.http;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NotificationHttpResponse(UUID id,
                                       String message,
                                       String code,
                                       Map<String, String> properties,
                                       Instant createdDate,
                                       Instant lastUpdatedDate) {
}
