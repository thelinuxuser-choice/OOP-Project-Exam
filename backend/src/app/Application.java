package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import java.io.File;

/**
 * Main application class to start the Spring Boot web server.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "model", "util"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Application started successfully.");
    }

    /**
     * This configuration tells Spring Boot to host your HTML/CSS/JS files
     * located in the "frontend" folder at the root of localhost:8080.
     */
    @Configuration
    public static class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            String frontendPath = "file:C:/Users/SHENAL CHANDUPA/Desktop/OOP Project/OOP-Project-Exam/frontend/";
            registry.addResourceHandler("/**")
                    .addResourceLocations(frontendPath, "file:frontend/", "file:../frontend/");
        }

        @Override
        public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/index.html");
        }

        @Bean
        public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
            return server -> {
                File webapp = new File("C:\\Users\\SHENAL CHANDUPA\\Desktop\\OOP Project\\OOP-Project-Exam\\backend\\src\\main\\webapp");
                if (webapp.exists()) {
                    server.setDocumentRoot(webapp);
                }
            };
        }
    }
}
