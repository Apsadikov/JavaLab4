package ru.itis.fileloader.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Aspect
@Component
@EnableAspectJAutoProxy
public class EmailAspect {
    @Autowired
    Environment environment;

    @Pointcut(value = "execution(* ru.itis.fileloader.services.FileService.uploadFile(..))")
    public void sendEmail() {
        System.out.println("Before annotation");
    }

    @AfterReturning(pointcut = "sendEmail()", returning = "string")
    public void afterCallMethodSaveFile(JoinPoint jp, String string) throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", environment.getProperty("mail.smtp.host"));
        props.put("mail.smtp.socketFactory.port", environment.getProperty("mail.smtp.socketFactory.port"));
        props.put("mail.smtp.socketFactory.class", environment.getProperty("mail.smtp.socketFactory.class"));
        props.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.port", environment.getProperty("mail.smtp.port"));
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(environment.getProperty("email.user"),
                        environment.getProperty("email.password"));
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress((String) jp.getArgs()[0]));
        message.setSubject("Confirm email");
        message.setText("localhost://files/" + string);

        Transport.send(message);
    }

}
