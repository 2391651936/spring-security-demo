package com.example.demo.controller;

import com.example.demo.model.SysUser;
import com.example.demo.util.CaptchaUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "sss";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/success")
    public String success(ModelMap map) {
        // 获取当前登录的用户
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.addAttribute("user", user);
        return "success";
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @ResponseBody
    @GetMapping("/a")
    public String a() {
        return "aaaa";
    }

    @ResponseBody
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
