package ru.itis.demo.services;

import freemarker.template.TemplateException;
import ru.itis.demo.dto.UserDto;
import ru.itis.demo.models.User;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.IOException;

public interface SignUpService {
    UserDto signUp(UserDto userDto);

    boolean confirmedEmail(String email, String token);

    void sendEmail(ServletContext servletContext, UserDto userDto) throws MessagingException, IOException, TemplateException;
}
