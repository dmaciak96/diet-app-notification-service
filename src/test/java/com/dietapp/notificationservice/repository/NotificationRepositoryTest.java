package com.dietapp.notificationservice.repository;

import com.dietapp.notificationservice.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void before() {
        notificationRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullyCreateNotification() {
        var entity = DataProvider.createEntity(null, null);
        var result = notificationRepository.saveAndFlush(entity);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getProperties().stream().findFirst().get().getId()).isNotNull();
    }

    @Test
    void shouldSuccessfullyFetchAllNotifications() {
        notificationRepository.saveAllAndFlush(List.of(DataProvider.createEntity(null, null),
                DataProvider.createEntity(null, null),
                DataProvider.createEntity(null, null)));

        var result = notificationRepository.findAll();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getId()).isNotNull();
        assertThat(result.get(1).getId()).isNotNull();
        assertThat(result.get(2).getId()).isNotNull();
    }

    @Test
    void shouldSuccessfullyFetchNotificationById() {
        var savedNotifications = notificationRepository.saveAllAndFlush(List.of(DataProvider.createEntity(null, null),
                DataProvider.createEntity(null, null),
                DataProvider.createEntity(null, null)));

        assertThat(notificationRepository.findById(savedNotifications.get(0).getId())).isNotNull();
        assertThat(notificationRepository.findById(savedNotifications.get(1).getId())).isNotNull();
        assertThat(notificationRepository.findById(savedNotifications.get(2).getId())).isNotNull();
    }

    @Test
    void shouldSuccessfullyDeleteNotificationWithAllProperties() {
        var savedNotification = notificationRepository.saveAndFlush(DataProvider.createEntity(null, null));
        notificationRepository.deleteById(savedNotification.getId());
        var result = notificationRepository.findById(savedNotification.getId());
        assertThat(result).isEmpty();
    }
}