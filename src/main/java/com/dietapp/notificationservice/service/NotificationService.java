package com.dietapp.notificationservice.service;

import com.dietapp.notificationservice.model.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    NotificationDto create(NotificationDto notificationDto);

    NotificationDto update(UUID id, NotificationDto notificationDto);

    String delete(UUID id);

    Page<NotificationDto> getAll(Pageable pageable);

    NotificationDto getById(UUID id);
}
