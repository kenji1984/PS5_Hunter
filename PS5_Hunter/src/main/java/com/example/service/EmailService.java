package com.example.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService {
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;
    @Value("${mail.to}")
    private String to;
    @Value("${mail.subject}")
    private String subject;

    public EmailService(JavaMailSender mailSender) {
	this.mailSender = mailSender;
    }

    public void send(String content) throws MessagingException {
	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	helper.setSubject(subject);
	helper.setFrom(from);

	String[] toAddresses = to.contains("|") ? to.split("\\|") : new String[] { to };
	helper.setTo(toAddresses);
	helper.setText(content, true);

	mailSender.send(message);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}
