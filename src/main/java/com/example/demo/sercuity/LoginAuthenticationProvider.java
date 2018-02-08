package com.example.demo.sercuity;

import lombok.Setter;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.DigestUtils;

public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    private SaltSource saltSource;

    // 从 DaoAuthenticationProvider 里 copy 过来的
    @Setter
    private PasswordEncoder passwordEncoder;

    public LoginAuthenticationProvider(UserDetailsService userDetailsService) {
        super();
        this.setPasswordEncoder((PasswordEncoder)(new PlaintextPasswordEncoder()));
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 加盐密码不是很清楚，就放在这里了
        Object salt = null;
        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        String presentedPassword = authentication.getCredentials().toString();
        presentedPassword = DigestUtils.md5DigestAsHex(presentedPassword.getBytes());
        if (!this.passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            // 这里重写了 DaoAuthenticationProvider 且抛出的不是密码错误异常，BadCredentialException，而是：LockedException
            throw new LockedException("密码不正确");
        }
    }
}
