package com.example.SpringBootStarter;

import static com.example.SpringBootStarter.user.Role.ADMIN;
import static com.example.SpringBootStarter.user.Role.MANAGER;

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

	@Value("${application.user.manager.first-name}")
	private String managerFirstName;
	@Value("${application.user.manager.last-name}")
	private String managerLastName;
	@Value("${application.user.manager.email}")
	private String managerEmail;
	@Value("${application.user.manager.password}")
	private String managerPassword;

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
					.role(ADMIN)
					.build();
			service.register(admin);
			// System.out.println("Admin token: " +
			// service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname(managerFirstName)
					.lastname(managerLastName)
					.email(managerEmail)
					.password(managerPassword)
					.role(MANAGER)
					.build();
			service.register(manager);

			// System.out.println("Manager token: " +
			// service.register(manager).getAccessToken());
		};
	}

}
