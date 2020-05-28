package ru.itis.demo.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.demo.dto.UserDto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Properties;

@Component
public class EmailSender {
    private Properties properties;
    private Configuration configuration;

    @Autowired
    public EmailSender(Properties properties, Configuration configuration) {
        this.properties = properties;
        this.configuration = configuration;
    }

    public void sendMail(Session session, String subject, String receiver, Writer html) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject);

        BodyPart body = new MimeBodyPart();
        body.setContent(html.toString(), "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        message.setContent(multipart);

        Transport.send(message);
    }

    public Session createSession() {
        return Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("email.user"),
                        properties.getProperty("email.password"));
            }
        });
    }

    public Writer processHtml(ServletContext servletContext, UserDto userDto) throws IOException, TemplateException {
        configuration.setServletContextForTemplateLoading(servletContext, "WEB-INF/views/");
        Template template = configuration.getTemplate("/email.ftlh");
        HashMap<String, String> root = new HashMap<>();
        root.put("token", userDto.getToken());
        root.put("email", userDto.getEmail());
        Writer html = new StringWriter();
        template.process(root, html);
        return html;
    }
}
