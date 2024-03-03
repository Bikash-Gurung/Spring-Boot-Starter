# Spring Boot 3 and Spring Security 6 - Starter Project

-> This project is created as a boilerplate for REST API project to speed up the development. This project provides all basic features like JWT based Authentication, Role and Permission based Authorization so developers can add business logic on top of it.

## Technologoes Used:

- Spring Boot 3
- Spring Security 6
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok
- Docker and Docker Compose
- JWT
- Google style check

## Features:

1. User Registration
2. Login
3. Refresh access token
4. Logout
5. Change password

## Steps to run:

### Option-1

**Step 1:** Clone this repository on your local machine

**Step 2:** Create application.yml file by referencing application.example.yml file and provide values for the properties

**Step 3:** Then run "docker-compose up"

### Option-2

**Step 1:** Clone this repository on your local machine

**Step 2:** Intall MySQL in your machine (if not installed)

**Step 3:** Create application.yml file by referencing application.example.yml file and provide values for the properties

**Step 4:** Run Spring Boot App using IDE or command "mvn spring-boot:run"

## Diagram:

![Alt text](src/main/resources/static/Spring%20Starter%20System.png)

![Alt text](src/main/resources/static/Spring%20Starter%20Flow%20Diagram.png)

## ToDos:

1. Email notification
2. Forget password (Send OTP in email)
3. Store tokens in Redis
4. Create profiles for fidderent environments (DEV, QA, PROD)
5. Add Swagger Documentation
6. Custom exceptions
7. Add API detail in README.md (Description, Request, Response)
