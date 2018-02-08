package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Aspect
@Component
@Slf4j
public class LogPointCut {

    @Pointcut("execution(* com.example.demo.controller..*.*(..)))")
    private void pointCut() {}

    @AfterThrowing(throwing = "ex", pointcut = "pointCut()")
    public void LogPoint(JoinPoint joinPoint, Throwable ex) {
        log.error(ex.toString() + ", " + ex.getMessage());
    }

    @Before("pointCut()")
    public void beforePoint(JoinPoint joinPoint) throws ClassNotFoundException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IP: " + request.getRemoteAddr());
        stringBuilder.append(", URL: " + request.getRequestURL().toString());
        stringBuilder.append(", HTTP_METHOD: " + request.getMethod());
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            stringBuilder.append(", name: " + name);
            stringBuilder.append("-->value: " + request.getParameter(name));
        }
        log.info(stringBuilder.toString());
    }
}