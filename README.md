
# E-Commerce API

A robust backend system for an e-commerce platform developed using **Java**, **Spring Boot**, **Spring Security**, and **Spring Data JPA**. 
This project handles user registration, authentication, product management, and order processing — all through RESTful APIs.

## 🧩 Features

- ✅ User registration & login with role-based access control  
- 🛒 Product listing and management (CRUD)  
- 📦 Order creation and tracking  
- 🔐 Secure authentication using Spring Security & JWT  
- 🗃️ Database integration with Spring Data JPA (MySQL)  
- 🧪 Input validation and exception handling  

## 🛠️ Tech Stack

- **Language:** Java 17  
- **Frameworks:** Spring Boot, Spring Security, Spring Data JPA  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **Containerization:** Docker  
- **Other Tools:** Git, Postman, Lombok

## 🔐 Authentication

This API uses JWT (JSON Web Token) for secure authentication. Access tokens are provided on login and must be included in the `Authorization` header for protected endpoints:

```
Authorization: Bearer <token>
```

## 📂 Project Structure

```
src
├── config         # Security configuration
├── controller     # REST controllers
├── dto            # Data transfer objects
├── entity         # JPA entities
├── repository     # Spring Data JPA repositories
├── service        # Business logic layer
└── util           # Utility classes
```

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/IamAbdullayev/E-Commerce-API.git
   cd E-Commerce-API
   ```

2. **Configure application properties**
   Set your MySQL credentials and JWT secret in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   jwt.secret=your_secret_key
   ```

3. **Build and run the app**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Test API with Postman or curl**

## 🧪 Example Endpoints

- `POST /api/v1/auth/register` – Register a new user  
- `POST /api/v1/auth/login` – Authenticate user and receive JWT  
- `GET /api/v1/products` – Get list of all products  
- `POST /api/v1/orders` – Create a new order (authenticated)

## 🧠 Author

**Ramazan Abdullayev**  
[GitHub](https://github.com/IamAbdullayev) · [LinkedIn](https://www.linkedin.com/in/ramazanabdu11ayev) · [Telegram](https://t.me/iamabdullayev)

---
