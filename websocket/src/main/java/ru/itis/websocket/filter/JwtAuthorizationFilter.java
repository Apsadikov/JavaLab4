package ru.itis.websocket.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.websocket.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthorizationFilter implements Filter {
    private UserService userService;

    @Autowired
    public JwtAuthorizationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getCookies() == null || request.getCookies().length == 0) {
            response.setStatus(403);
            return;
        }
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        Optional<Cookie> optionalCookie = cookies.stream().filter(cookie -> cookie.getName().equals("token")).findFirst();
        if (optionalCookie.isPresent()) {
            Cookie cookie = optionalCookie.get();
            if (userService.verify(cookie.getValue()).isPresent()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.setStatus(403);
            }
        } else {
            response.setStatus(403);
        }
    }

    @Override
    public void destroy() {

    }
}
