package com.dietapp.notificationservice.model;

import com.dietapp.notificationservice.model.dto.NotificationDto;
import com.dietapp.notificationservice.model.entity.CustomProperty;
import com.dietapp.notificationservice.model.entity.Notification;
import com.dietapp.notificationservice.model.http.NotificationHttpResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NotificationMapper {

    @Mapping(target = "properties", ignore = true)
    NotificationDto toDto(Notification entity);

    @AfterMapping
    default NotificationDto mapDtoProperties(Notification entity, @MappingTarget NotificationDto notificationDto) {
        var propertiesMap = entity.getProperties().stream()
                .collect(Collectors.toMap(CustomProperty::getKey, CustomProperty::getValue));

        return NotificationDto.builder()
                .id(notificationDto.id())
                .code(notificationDto.code())
                .message(notificationDto.message())
                .properties(propertiesMap)
                .version(notificationDto.version())
                .createdDate(notificationDto.createdDate())
                .lastUpdatedDate(notificationDto.lastUpdatedDate())
                .build();
    }

    @Mapping(target = "properties", ignore = true)
    Notification toEntity(NotificationDto dto);

    @AfterMapping
    default Notification mapEntityProperties(@MappingTarget Notification entity, NotificationDto dto) {
        var propertiesSet = dto.properties().entrySet().stream()
                .map(entry -> CustomProperty.builder()
                        .key(entry.getKey())
                        .value(entry.getValue())
                        .notification(entity)
                        .build())
                .collect(Collectors.toSet());

        entity.setProperties(propertiesSet);
        return entity;
    }

    NotificationHttpResponse toHttpResponse(NotificationDto dto);
}
