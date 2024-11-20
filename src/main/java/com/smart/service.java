package com.smart;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;

@Service
public class service {

    

     
    

    public static boolean sendEmail(String recipient, String subject, String body) {
        try {
            // Gmail OAuth 2.0 authentication properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Create Authenticator object to authenticate with Gmail
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("nikhill.hero@gmail.com", "thtm tzxb mvod etfg");
                }
            };

            // Create session using OAuth 2.0 authentication
            Session session = Session.getInstance(props, authenticator);

            // Create MimeMessage object
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nikhill.hero@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully!");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
       
    }
}

