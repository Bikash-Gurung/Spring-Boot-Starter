package com.example.SpringBootStarter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.SpringBootStarter.auth.AuthenticationService;
import com.example.SpringBootStarter.auth.dto.RegisterRequest;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SpringBootStarterApplication {

	@Value("${application.user.admin.first-name}")
	private String adminFirstName;
	@Value("${application.user.admin.last-name}")
	private String adminLastName;
	@Value("${application.user.admin.email}")
	private String adminEmail;
	@Value("${application.user.admin.password}")
	private String adminPassword;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStarterApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname(adminFirstName)
					.lastname(adminLastName)
					.email(adminEmail)
					.password(adminPassword)
					.build();
			service.registerAdmin(admin);
		};
	}

}
