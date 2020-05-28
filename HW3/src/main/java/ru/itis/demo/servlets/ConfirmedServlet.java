package ru.itis.demo.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.demo.services.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirmed")
public class ConfirmedServlet extends HttpServlet {
    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        signUpService = springContext.getBean(SignUpService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isConfirmed = signUpService.confirmedEmail(req.getParameter("email"), req.getParameter("token"));
        if (isConfirmed) {
            resp.getWriter().write("Email confirmed");
        } else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
