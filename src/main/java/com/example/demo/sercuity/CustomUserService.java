package com.example.demo.sercuity;

import com.example.demo.respository.SysUserRepository;
import com.example.demo.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用来验证是否存在用户
 */

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(s);
        if (user == null) {
            // 这里本应该抛出：UsernameNotFoundException，由于不能被前台获取，所以抛出：BadCredentialException
            throw new BadCredentialsException("账号不存在!");
        }
        return user;
    }
}
