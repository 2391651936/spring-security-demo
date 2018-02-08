package com.example.demo.config;

import com.example.demo.model.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class StaticUtil {
    public static final int MAX_NUMBER = 10;  // 每页显示的数量
    public static final String VALIDATE_CODE = "validate";  // session 中验证码的 key
    public static final boolean START_VALIDATE = true;  // 开启验证码

    /**
     * 获取当前登录的用户
     * @return 当前的用户
     */
    public static SysUser getCurrentUser() {
        return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
