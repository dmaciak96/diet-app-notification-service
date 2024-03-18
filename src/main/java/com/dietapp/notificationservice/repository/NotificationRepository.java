package com.dietapp.notificationservice.repository;

import com.dietapp.notificationservice.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
