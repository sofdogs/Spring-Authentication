# Spring Boot 3.2.12 Security with JWT Implementation 

This project demonstrates a complete implementation of **security using Spring Boot 3.2.12 and JSON Web Tokens (JWT)**. It covers user authentication, role-based access control, password encryption, and more — all integrated with Spring Security.
___

## 🔐 Features

- ✅ User registration and login with JWT authentication
- ✅ Password encryption using **BCrypt**
- ✅ Role-based authorization using **Spring Security**
- ✅ Customized access denied handling
- ✅ Secure logout mechanism
- ✅ Refresh token functionality

---

## 🛠️ Technologies Used

- **Spring Boot 3.2.12**
- **Spring Security**
- **JSON Web Tokens (JWT)**
- **BCrypt Password Encoder**
- **Maven**
- **PostgreSQL**

---

## 🚀 Getting Started

### 🔧 Prerequisites

Make sure you have the following installed:

- Java JDK **17+**
- Maven **3+**
- PostgreSQL (running locally)

---

## 🧪 API Overview

- POST /api/v1/auth/register - Register a new user
- POST /api/v1/authenticate - Login and receive access + refresh token
- POST /api/v1/auth/refresh-token - Get a new access token using a refresh token

### Secure endpoints requre a valid JWT in the header: Authorization: Bearer <access_token>





