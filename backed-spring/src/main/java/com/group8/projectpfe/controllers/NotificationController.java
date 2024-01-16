package com.group8.projectpfe.controllers;

import com.google.firebase.messaging.FirebaseMessaging;
import com.group8.projectpfe.domain.dto.NotificationMessage;
import com.group8.projectpfe.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/notification")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        return notificationService.sendNotificationByToken(notificationMessage);

    }

}
