package com.dietapp.notificationservice.service;

import com.dietapp.notificationservice.exception.NotificationNotFoundException;
import com.dietapp.notificationservice.model.NotificationMapper;
import com.dietapp.notificationservice.model.dto.NotificationDto;
import com.dietapp.notificationservice.model.entity.CustomProperty;
import com.dietapp.notificationservice.model.entity.Notification;
import com.dietapp.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationDto create(NotificationDto notificationDto) {
        log.info("Saving new notification (code: {}, message: {})",
                notificationDto.code(), notificationDto.message());
        var notification = notificationMapper.toEntity(notificationDto);
        notification.setVersion(0);

        var savedNotification = notificationRepository.saveAndFlush(notification);
        log.info("Notification {} was saved (id: {})", savedNotification.getCode(), savedNotification.getId());

        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional
    public NotificationDto update(UUID id, NotificationDto notificationDto) {
        log.info("Updating notification (code: {}, id: {})", notificationDto.code(), id);
        var notificationToUpdate = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found by id %s".formatted(id)));

        notificationToUpdate.setMessage(notificationDto.message());
        notificationToUpdate.setCode(notificationDto.code());
        notificationToUpdate.getProperties().clear();
        notificationToUpdate.getProperties().addAll(mapToCustomProperties(notificationDto.properties(), notificationToUpdate));

        var updatedNotification = notificationRepository.saveAndFlush(notificationToUpdate);
        log.info("Notification {} was updated (id: {})", updatedNotification.getCode(), updatedNotification.getId());

        return notificationMapper.toDto(updatedNotification);
    }

    private Set<CustomProperty> mapToCustomProperties(Map<String, String> properties, Notification notification) {
        return properties.entrySet().stream()
                .map(entry -> CustomProperty.builder()
                        .key(entry.getKey())
                        .value(entry.getValue())
                        .notification(notification)
                        .build())
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        log.info("Removing notification (id: {})", id);
        var notification = notificationRepository.findById(id).orElseThrow(() ->
                new NotificationNotFoundException("Notification not found by id %s".formatted(id)));
        notificationRepository.delete(notification);
        log.info("Notification {} was removed (id: {})", notification.getCode(), id);
        return notification.getCode();
    }

    @Override
    @Transactional
    public Page<NotificationDto> getAll(Pageable pageable) {
        log.info("Get all notifications (Page number: {}, Page size: {})", pageable.getPageNumber(), pageable.getPageSize());
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::toDto);
    }

    @Override
    @Transactional
    public NotificationDto getById(UUID id) {
        log.info("Get notification by id {}", id);
        var notification = notificationRepository.findById(id);
        return notificationMapper.toDto(notification.orElseThrow(() ->
                new NotificationNotFoundException("Notification not found by id %s".formatted(id))));
    }
}
