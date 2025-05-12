package ru.master.service.admin.service;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail (String to) throws MessagingException;
}
