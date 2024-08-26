# Task Management (Gerenciamento de Task)

Task management is a microservice-based application designed to provide the following services:

- Eureka Server: provides service discovery and registration, ensuring that all microservices can find and communicate with each other efficiently

- Gateway Service: acts as a single entry point for all clients requests, routing them to the appropriate microservice and handling load balancer.

- Task Service: manages tasks, including creating, updating, reading...

- Task Submission Service: manages task submissions, including creating, validating, deleting, and updating the status of submissions

- User Service: manages user accounts, JWT authentication and authorization

## Dependencies of each service:

**User Service (Spring Boot Project)**

- Lombok
- Spring Web
- Spring Security
- Spring Boot DevTools
- Spring Data JPA
- PostgreSQL Driver
- Eureka Discovery Client

**Task Service (Spring Boot Project)**

- Lombok
- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- PostgreSQL Driver
- Eureka Discovery Client
- OpenFeign (SpringCloud Routing)

**Submission Task Service (Spring Boot Project)**

- Lombok
- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- PostgreSQL Driver
- Eureka Discovery Client
- OpenFeign (SpringCloud Routing)

**Eureka Server (Spring Boot Project)**

- Eureka Server
- Spring Boot Actuator

**Gateway Service (Spring Boot Project)**

- Gateway
- Eureka Discovery Client

## Installation

Follow these steps to install the prerequisites for this project:

1. Install [Docker](https://docs.docker.com)
2. Install OpenJDK 17 or any other JDK version 17

```bash
sudo apt install openjdk-17-jdk
```

3. install maven

```bash
sudo apt install maven -y
```

## Usage

1. Clone the repository

```bash
git clone https://github.com/jaimegsn/gerenciamento-tasks.git
```

2. Navigate into each service directory: `task-user-service`, `task-service`, and `task-submission-service`, and start Docker Compose to initialize the database:

```bash
docker compose up
```

or

```bash
docker-compose up
```

3. Enter each service and start them using the following command in the terminal:

```bash
mvn spring-boot:run
```

4. Test the API using POSTMAN, HTTPie, or similar tools at `localhost:5000`
