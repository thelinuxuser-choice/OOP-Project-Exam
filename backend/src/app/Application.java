package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.CacheControl;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = {
    "app",
    "controller",
    "service",
    "model",
    "util",
    "com.oerms.springboot.controller",
    "com.oerms.springboot.dao",
    "com.oerms.springboot.service",
    "com.rashmi.onlineexam.controller",
    "com.rashmi.onlineexam.repository"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Application started! Visit: http://localhost:8080/");
    }

    @Configuration
    public static class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // Primary: serve from classpath static (always works in Spring Boot)
            // Fallback: dynamically find the frontend folder for live editing
            java.io.File currentDir = new java.io.File(System.getProperty("user.dir"));
            java.io.File frontendDir = new java.io.File(currentDir, "frontend");
            if (!frontendDir.exists() && currentDir.getParentFile() != null) {
                frontendDir = new java.io.File(currentDir.getParentFile(), "frontend");
            }
            String frontendUri = frontendDir.toURI().toString();
            
            registry.addResourceHandler("/**")
                    .addResourceLocations(
                            "classpath:/static/",
                            frontendUri
                    )
                    .setCacheControl(CacheControl.noCache());
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/index.html");
            registry.addViewController("/login").setViewName("forward:/login.html");
            registry.addViewController("/register").setViewName("forward:/register.html");
            registry.addViewController("/dashboard").setViewName("forward:/dashboard.html");
            registry.addViewController("/users").setViewName("forward:/users.html");
            registry.addViewController("/admin-users").setViewName("forward:/admin-users.html");
            registry.addViewController("/exams").setViewName("forward:/exams.html");
            registry.addViewController("/questions").setViewName("forward:/questions.html");
            registry.addViewController("/exam-session").setViewName("forward:/exam-session.html");
            registry.addViewController("/exam-taking").setViewName("forward:/exam-taking.html");
            registry.addViewController("/results").setViewName("forward:/results.html");
            registry.addViewController("/feedback").setViewName("forward:/feedback.html");
            registry.addViewController("/notifications").setViewName("forward:/notifications.html");
            registry.addViewController("/notification").setViewName("forward:/notifications.html");
        }
    }
}