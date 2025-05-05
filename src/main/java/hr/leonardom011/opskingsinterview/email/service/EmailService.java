package hr.leonardom011.opskingsinterview.email.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {

    void sendNewMail(String to, String subject, String body, String attachment) throws MessagingException, IOException;
}
