package me.kjeok.monvoca.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**")
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("*") // 허용할 프론트엔드의 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*");
    }
}
