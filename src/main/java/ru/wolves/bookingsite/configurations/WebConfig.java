package ru.wolves.bookingsite.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
 @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println( "Файл конфигурации вступил в силу");
        registry.addResourceHandler("/static/fonts/**").addResourceLocations("file:C:\\Users\\golov\\IdeaProjects\\bookingSite\\src\\main\\resources\\static\\fonts");
    }
}
