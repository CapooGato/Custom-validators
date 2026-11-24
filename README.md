# Secure Architecture, SAST & Automated Testing Suite (`Custom validators`)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?style=flat&logo=springboot&logoColor=white)
![ArchUnit](https://img.shields.io/badge/ArchUnit-1.3.0-blue?style=flat)
![DevSecOps](https://img.shields.io/badge/Pipeline-SAST%20%7C%20SonarQube%20%7C%20IT-FC6D26?style=flat&logo=gitlab&logoColor=white)

## Overview
A defensive programming and application security laboratory project built on **Java 21** and **Spring Boot**. The repository demonstrates multi-layered software verification—combining **ArchUnit** architectural fitness functions, unit/integration testing, domain input validation, and Static Application Security Testing (SAST) in GitLab CI/CD to detect OWASP vulnerabilities (such as SQL Injection, Reflected XSS, hardcoded secrets, and sensitive data exposure).

---

## Key Features & Security Engineering
- **Architectural Fitness Functions (`ArchUnit`)**: Automated tests (`ArchitectureArchTest`) enforcing layered architecture boundaries (`api` -> `application` -> `domain`), cyclic dependency checks, and naming conventions.
- **Domain Authentication & Policy Enforcement (`StudentLoginValidator`)**:
  - Institutional email domain verification (`student.tu.kielce.pl`).
  - Password complexity enforcement (minimum length, uppercase character, numeric digit requirements).
  - Strict 6-digit student ID regex validation.
- **Vulnerability Analysis & SAST Benchmarking**: Includes controlled vulnerable endpoints (`/api/user`, `/api/greet`, `/api/debug`) designed to test and verify automated security scanners (SonarQube, Semgrep) against **SQL Injection**, **Cross-Site Scripting (XSS)**, **Information Disclosure**, and **Hardcoded Credentials**.
- **Comprehensive Test Pyramid**:
  - Unit tests (`StudentLoginValidatorTest`, `ControllerTest`, `StudentControllerTest`, `CounterTest`).
  - Integration tests (`ControllerIT`) executed via Maven Failsafe (`-Pintegration`).
  - Architecture tests (`ArchitectureArchTest`).

---

## Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 4.0.3 (Spring Web MVC)
- **Testing & Architecture:** JUnit 5, ArchUnit 1.3.0, Maven Surefire & Failsafe, JaCoCo
- **CI/CD & DevSecOps:** GitLab CI/CD, SonarQube

---

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/student/login` | Validates student login credentials, password policy, and student ID |
| `GET` | `/api/hit` | Thread-safe atomic request counter (`AtomicInteger` application-scoped bean) |
| `GET` | `/api/user` | SAST benchmark endpoint for SQL Injection detection |
| `GET` | `/api/greet` | SAST benchmark endpoint for XSS detection |
| `GET` | `/api/debug` | SAST benchmark endpoint for secret exposure detection |

---

## Running Locally & Running Tests

```bash
# Run unit and architecture tests
./mvnw test

# Run integration tests
./mvnw verify -Pintegration

# Start the application
./mvnw spring-boot:run
```
