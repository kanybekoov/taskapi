package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTaskCreated(Task task, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New task created");
        message.setText("Task: " + task.getTitle() +
                "\nDescription: " + task.getDescription() +
                "\nCompleted: " + (task.isCompleted() ? "Yes" : "No"));
        mailSender.send(message);
    }
}
