package com.looment.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.looment.notificationservice.dtos.EmailLocked;
import com.looment.notificationservice.dtos.EmailOTP;
import com.looment.notificationservice.dtos.EmailReset;
import com.looment.notificationservice.exceptions.KafkaMessageNull;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final ObjectMapper objectMapper;
    private final JavaMailSender mailSender;

    private Object extractMessage(ConsumerRecords<?, ?> records) {
        Object message = new Object();
        for (ConsumerRecord<?, ?> record : records) {
            message = record.value();
            if (message == null) {
                log.error("Kafka message is Null");
                throw new KafkaMessageNull();
            }
        }
        return message;
    }

    private void sendMail(String to, String username, String header, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo("fahmyyamyy@gmail.com");
        helper.setTo(to);
        helper.setReplyTo("cs@looment.com");
        helper.setSubject("Looment: " + header);
        helper.setText("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:500px;overflow:auto;line-height:2\">\n" +
                "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                "    <div style=\"border-bottom:1px solid #eee\">\n" +
                "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Looment</a>\n" +
                "    </div>\n" +
                "    <p style=\"font-size:1.1em\">Hi " + username + ",</p>\n" +
                text +
                "    <p style=\"font-size:0.9em;\">Regards,<br />Looment</p>\n" +
                "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                "      <p>Looment</p>\n" +
                "      <p>Jakarta, Indonesia</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>", true);
        mailSender.send(message);
    }

    @KafkaListener(groupId = "email-doc", topics = "user-otp")
    public void sendOTP(ConsumerRecords<?, ?> records) throws JsonProcessingException, MessagingException {
        Object message = extractMessage(records);

        EmailOTP emailOTP = objectMapper.readValue(message.toString(), EmailOTP.class);
        String text =
                "    <p>Use the following OTP to Log in to your account. OTP is valid for 10 minutes</p>\n" +
                        "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + emailOTP.getOtp() + "</h2>\n";
        sendMail(emailOTP.getEmail(), emailOTP.getUsername(), "One-Time Password", text);
    }

    @KafkaListener(groupId = "email-doc", topics = "user-locked")
    public void accountLocked(ConsumerRecords<?, ?> records) throws JsonProcessingException, MessagingException {
        Object message = extractMessage(records);

        EmailLocked emailLocked = objectMapper.readValue(message.toString(), EmailLocked.class);
        String text =
                "    <p>A failed login attempt was made for your account with the username: <b>" + emailLocked.getUsername() + "</b>.<br>" +
                        " Please review the login activity and take appropriate action if necessary,\n" +
                        " or you can reset your password if needed.</p>";

        sendMail(emailLocked.getEmail(), emailLocked.getUsername(), "Account Locked", text);
    }

    @KafkaListener(groupId = "email-doc", topics = "user-reset-password")
    public void resetPassword(ConsumerRecords<?, ?> records) throws JsonProcessingException, MessagingException {
        Object message = extractMessage(records);
        EmailReset emailReset = objectMapper.readValue(message.toString(), EmailReset.class);
        String text =
                "    <p>We received your password reset request, below are your new password</p>\n" +
                        " <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + emailReset.getPassword() + "</h2>\n" +
                        " <p>We recommend to change those password with your new one.</p>\n";

        sendMail(emailReset.getEmail(), emailReset.getUsername(), "Password Reset", text);
    }
}
