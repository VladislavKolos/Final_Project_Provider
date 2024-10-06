# Provider Management System
Project Overview

The Provider Management System is a comprehensive application designed for managing clients 
and services of a provider, with functionality tailored to both clients and administrators. Clients can 
manage their personal details and subscription plans, while administrators have full control over the 
system’s operations, including client registration, plan management, and promotional campaigns.

**Features**

For Clients
- Profile Management: Clients can update their personal information such as username, email, phone number and password.
- Tariff Plan Management: Clients have the ability to view, subscribe, and switch between available tariff plans.
- Promotions and Discounts: Clients can view and take advantage of ongoing promotional offers and discounts.

For Administrators
- Client Management: Administrators can register new clients, view, edit, and delete client information.
- Tariff Plan Management: Administrators can create, update, and remove tariff plans, as well as adjust existing plans according to business requirements.
- Promotions Management: Administrators can announce promotional offers, manage discounts, and create special campaigns.

**Tech Stack**
Backend:
- Java 17 (Amazon Corretto)
- Spring Boot (REST API framework)
- Spring Security (JWT-based authentication)
- Hibernate (JPA for ORM)
- PostgreSQL (Database)

Frontend (optional):
- Swagger (for API documentation)

Tools & Libraries:
- Maven (build automation)
- Docker (containerization)
- Log4j2 (logging)

**Getting Started**

Prerequisites
- JDK 17 (Amazon Corretto)
- Maven 3.8+
- Docker
- Postman (optional, for API testing)

**API Documentation**
The Provider Management System offers a fully documented RESTful API that allows interaction with both client and admin functionalities.

Access API Documentation:
- Swagger UI: http://localhost:8080/swagger-ui
- API Docs: http://localhost:8080/api-docs

**Security**

The application implements JWT (JSON Web Token) based authentication using Spring Security. 
Access to the REST API is controlled by roles (CLIENT, ADMIN) ensuring that certain resources and 
functionalities are restricted based on user roles.

Security Features:
- JWT Authentication: Secures API endpoints with stateless authentication.
- Role-based Authorization: Clients and Administrators have different levels of access and permissions.
- Custom Filters: Custom JWT filter to handle token validation and authentication.
