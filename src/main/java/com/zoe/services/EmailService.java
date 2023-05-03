package com.zoe.services;

import com.zoe.models.Order;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Order order) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(order.getSenders().getMail());
        message.setSubject("Nova ordem criada");
        message.setText("Uma nova ordem foi criada com sucesso.");

        javaMailSender.send(message);
    }
}
