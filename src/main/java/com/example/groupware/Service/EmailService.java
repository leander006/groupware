package com.example.groupware.Service;

import com.example.groupware.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    public String sendNewEmployeeEmail(String managerEmail, String employeeName, Long phoneNumber, String emailId) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(managerEmail);
            mailMessage.setSubject("New Employee Added");
            mailMessage.setText(employeeName + " will now work under you. Mobile number is " + phoneNumber + " and email is " + emailId);
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
