package ru.itis.demo.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.itis.demo.dto.UserDto;
import ru.itis.demo.models.User;
import ru.itis.demo.repositories.SignUpRepository;
import ru.itis.demo.util.EmailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;

@Component
public class SignUpServiceImpl implements SignUpService {
    private final EmailSender emailSender;
    private Environment environment;
    private SignUpRepository signUpRepository;
    private Configuration configuration;

    @Autowired
    public SignUpServiceImpl(SignUpRepository signUpRepository, Environment environment, Configuration configuration, EmailSender emailSender) {
        this.signUpRepository = signUpRepository;
        this.environment = environment;
        this.configuration = configuration;
        this.emailSender = emailSender;
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        signUpRepository.save(user);
        userDto.setId(user.getId());
        userDto.setToken(user.getToken());
        return userDto;
    }

    @Override
    public boolean confirmedEmail(String email, String token) {
        return signUpRepository.confirmedEmail(email, token);
    }

    @Override
    public void sendEmail(ServletContext servletContext, UserDto userDto) throws MessagingException, IOException, TemplateException {
        Session session = emailSender.createSession();
        Writer html = emailSender.processHtml(servletContext, userDto);
        emailSender.sendMail(session, "Confirm email", userDto.getEmail(), html);
    }
}
