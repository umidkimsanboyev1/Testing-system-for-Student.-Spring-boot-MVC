package uz.master.demotest.utils;

// Java program to send email

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class SendEmail {
    public void sendEmail(String... args) {
        String recipient = args[0];
        Properties properties=System.getProperties();
        String host = "smtp.gmail.com";
        String sender = "gmail codni yozinglar";
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("emailni yozinglar","gmail codni yozinglar");
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("code for email");
            message.setText("http://localhost:8080/auth/reset/" +args[1]);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
