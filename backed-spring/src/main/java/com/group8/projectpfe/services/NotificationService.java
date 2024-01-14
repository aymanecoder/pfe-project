package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.NotificationMessage;

public interface NotificationService {
    String sendNotificationByToken(NotificationMessage notificationMessage);
}
