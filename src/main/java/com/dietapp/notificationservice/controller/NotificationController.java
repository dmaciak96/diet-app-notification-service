package com.dietapp.notificationservice.controller;

import com.dietapp.notificationservice.model.NotificationMapper;
import com.dietapp.notificationservice.model.http.NotificationHttpResponse;
import com.dietapp.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(NotificationController.NOTIFICATIONS_ENDPOINT)
public class NotificationController {

    public static final String NOTIFICATIONS_ENDPOINT = "/notifications";

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping
    public ResponseEntity<Page<NotificationHttpResponse>> getAllNotifications(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                              @RequestParam(required = false, defaultValue = "25") int pageSize) {
        var products = notificationService.getAll(PageRequest.of(pageNumber, pageSize));
        return ResponseEntity
                .ok(products.map(notificationMapper::toHttpResponse));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationHttpResponse> getProductById(@PathVariable UUID notificationId) {
        var productDto = notificationService.getById(notificationId);
        return ResponseEntity.ok(notificationMapper.toHttpResponse(productDto));
    }
}
