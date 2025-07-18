package com.example.taskapi.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        System.out.println("---- REQUEST ----");
        System.out.println(req.getMethod() + " " + req.getRequestURI());

        chain.doFilter(request, response);

        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("---- RESPONSE ----");
        System.out.println("Status: " + resp.getStatus());
        System.out.println("------------------");
    }
}
