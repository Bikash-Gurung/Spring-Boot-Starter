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

**Step 1:** Clone this repository on your local machine

**Step 2:** Create application.yml file by referencing application.example.yml file and provide values for the properties

**Step 3:** Run following command in project directory "mvn clean install -Dmaven.test.skip=true"

**Step 4:** Then run "docker-compose up"

## Diagram:

![Alt text](src/main/resources/static/Spring%20Starter%20System.png)

![Alt text](src/main/resources/static/Spring%20Starter%20Flow%20Diagram.png)

## ToDos:

1. Email notification
2. Forget password (Send OTP in email)
3. Store tokens in Redis
4. Create profiles for fidderent environments (DEV, QA, PROD)
5. Add Swagger Documentation
