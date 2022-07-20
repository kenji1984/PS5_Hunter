package com.example.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.service.EmailService;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Value("${mail.smtp}")
    private String smtp;
    @Value("${mail.port}")
    private String port;
    @Value("${mail.from}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @Bean
    public EmailService emailService() {
	return new EmailService(javaMailSender());
    }

    @Bean
    public JavaMailSender javaMailSender() {
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	mailSender.setHost(smtp);
	mailSender.setPort(Integer.parseInt(port));
	mailSender.setUsername(username);
	mailSender.setPassword(password);

	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	//props.put("mail.debug", "true");

	return mailSender;
    }

}
