package com.example.demo.sercuity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启 @PreAuthorize 注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService customUserService() {
        return new CustomUserService();
    }

    /**
     * 指定密码加密所使用的加密器为： passwordEncoder，我不知道怎么在使用自定义 DaoAuthenticationProvider 后仍然使用加密器
     * @return
     */
    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 用户认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(new LoginAuthenticationProvider(userDetailsService()))
                .userDetailsService(customUserService());

//        auth
//                .userDetailsService(customUserService())  // 获取返回的user，
//                .passwordEncoder(md5PasswordEncoder());  // 根据密码生成器对比密码
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(customUserService());
        provider.setPasswordEncoder(md5PasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 无需登录即可访问的url
        http
                .authorizeRequests()
                .antMatchers("/", "/captcha").permitAll()  // 所有人可以访问
                .antMatchers("/*.ftl").permitAll()  // 配置所有的错误界面，所有错误界面以 .ftl 结尾
                .antMatchers("/a").hasRole("admin")  // 只有有 admin 权限的用户可以访问
                .anyRequest().authenticated();  // 其余全部需要认证才行

        // 登录路由，成功跳转的界面
        http
                .addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).exceptionHandling()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/success")
                .permitAll();

        // 退出跳转的界面
        http
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)  // 清空session
                .and()
                .httpBasic();
    }

    @Bean
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myFilter = new MyUsernamePasswordAuthenticationFilter();
        myFilter.setAuthenticationManager(authenticationManagerBean());
        myFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        myFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return myFilter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/success");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login");
    }
}
