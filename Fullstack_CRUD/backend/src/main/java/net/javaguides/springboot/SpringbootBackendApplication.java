package net.javaguides.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class SpringbootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
	
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Make sure your API path is correctly matched
                .allowedOrigins("http://localhost:3000") // Your React app URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed headers
                .allowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
    }

}
