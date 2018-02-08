package com.example.demo.sercuity;

import com.example.demo.config.StaticUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 判断验证码的过滤器
 */

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (StaticUtil.START_VALIDATE) {
            checkValidateCode(request);
        }
        return super.attemptAuthentication(request, response);
    }

    private void checkValidateCode(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String sessionValidateCode = obtainSessionValidateCode(session);

        // 使上一次的验证码失效
        session.setAttribute(StaticUtil.VALIDATE_CODE, null);
        String validateCodeParameter = obtainValidateCodeParameter(request);
        if (validateCodeParameter.equals("")) {
            throw new BadCredentialsException("请填写验证码");
        }
        if (!sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
            throw new AuthenticationServiceException("验证码错误！");
        }
    }

    private String obtainValidateCodeParameter(HttpServletRequest request) {
        Object obj = request.getParameter(StaticUtil.VALIDATE_CODE);
        return null == obj ? "" : obj.toString().toLowerCase();
    }

    private String obtainSessionValidateCode(HttpSession session) {
        Object obj = session.getAttribute(StaticUtil.VALIDATE_CODE);
        return null == obj ? "" : obj.toString().toLowerCase();
    }
}
