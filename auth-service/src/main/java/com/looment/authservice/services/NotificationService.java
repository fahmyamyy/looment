package com.looment.authservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.looment.authservice.dtos.requests.OTPEmail;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;

public class NotificationService {
    private ObjectMapper objectMapper;

    @KafkaListener(groupId = "email-doc", topics = "user-otp", autoStartup = "true")
    public void otp(String user) throws JsonProcessingException, MessagingException {
//        EmailRequest emailRequest = objectMapper.readValue(user, EmailRequest.class);
        String a = user;
        OTPEmail otpEmail = objectMapper.readValue(user, OTPEmail.class);
//        sendOtp(otpEmail);
//        System.out.println(user);
    }
}
