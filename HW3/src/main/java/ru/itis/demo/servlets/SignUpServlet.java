package ru.itis.demo.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import ru.itis.demo.dto.UserDto;
import ru.itis.demo.models.User;
import ru.itis.demo.services.SignUpService;

import javax.mail.MessagingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        signUpService = springContext.getBean(SignUpService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/sign-up.ftlh").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = signUpService.signUp(UserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build());
        if (userDto.getId() != null) {
            resp.getWriter().write("Check your Email");
            try {
                signUpService.sendEmail(getServletContext(), userDto);
            } catch (MessagingException | TemplateException e) {
                e.printStackTrace();
                System.out.println(e.toString());
                resp.getWriter().write("Something went wrong");
            }
        } else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
