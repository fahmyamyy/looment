package com.looment.authservice.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;


@Component
public class TimeLogger implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String requestURI = request.getRequestURI();

        Optional<Long> optTime = Optional.of(System.currentTimeMillis() - startTime);
        String score = optTime.filter(time -> time < 500).map(time -> "VERY FAST")
                .or(() -> optTime.filter(time -> time < 1000).map(time -> "FAST"))
                .or(() -> optTime.filter(time -> time < 2000).map(time -> "NOT BAD"))
                .orElse("VERY BAD");

        logger.info("Request to {}, finished in {} ms [{}]", requestURI, executionTime, score);
    }
}