package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class DemoApplication {

    /**
     * 错误界面，使用时：需要在controller中配置。
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.ftl");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.ftl");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.ftl");
            container.addErrorPages(error403Page, error404Page, error500Page);
        });
    }

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
