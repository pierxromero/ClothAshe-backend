# ClothAshe-backend

**ClothAshe-backend** is a RESTful API built with **Spring Boot**, **Java**, and **MySQL** for managing an online clothing store. The system is designed with scalability, security, and maintainability in mind, implementing access control with **Spring Security**, JWT authentication, and a clean, layered architecture.

> ⚠️ **Important**: To run the project properly in **IntelliJ IDEA**, make sure you have **Lombok** support enabled. You can activate it via `File > Settings > Plugins > Lombok` and then check the “Enable annotation processing” option under `Build, Execution, Deployment > Compiler > Annotation Processors`.

---

## Key Features

- 🔐 **Authentication & Authorization**  
  - User registration and login with JWT-based security.  
  - Role-based access control (Admin, User, Owner).  
  - Password change, account locking/unlocking, and user role management by admins.

- 👤 **User Management**  
  - View and update user profile.  
  - User account deletion.  
  - Management of multiple user addresses with full CRUD and pagination.

- 🛍️ **Product Management**  
  - CRUD operations for products, categories, brands, colors, and sizes.  
  - Special product queries: top-rated, by stock availability, by price range, and by category.  
  - Physical product attributes like color, size, and brand management.

- 🛒 **Shopping Cart**  
  - Add, update quantity, remove, and clear items in the user's cart.  
  - Calculation of cart subtotal.

- ✅ **Order Processing**  
  - Create, view, cancel, and return orders.  
  - Update order status (admin-only).  
  - View orders by authenticated user or all orders (admin-only).

- 💳 **Payment Handling**  
  - Create payments associated with orders.  
  - Payment status management.

- ⭐ **Favorites**  
  - Add and remove favorite products.  
  - Retrieve favorites by user or for the authenticated user.

- 💬 **Product Comments & Reviews**  
  - Create, update, delete, and list product comments.  
  - View comments by user, product, or for the authenticated user.  
  - Admin-only endpoints to manage all comments.

- ❓ **User Inquiries**  
  - Submit new inquiries.  
  - Admins can answer inquiries.  
  - List inquiries by user or all inquiries (admin-only).

- 🧾 **API Documentation**  
  - Fully documented endpoints accessible via Swagger UI.

---

## Tech Stack
- **Java 17** (tested; compatibility with other versions not verified)  
- **Spring Boot 3**  
- **Spring Security**  
- **Spring Data JPA**  
- **JWT**  
- **Lombok**  
- **MySQL**  
- **Swagger (springdoc-openapi)**

---

## General Architecture

- Layered architecture: `Controller`, `Service`, `Repository`, `DTO`, `Entity`, `Mapper`.  
- Extensive validation using annotations (`@Valid`, `@NotBlank`, etc.).  
- Custom global exception handling.  
- Use of DTOs to decouple entities from business logic and API responses.

---

## Pending Features

The following features were planned but not implemented due to time constraints. These are the only two features I clearly remember in detail; I had other ideas initially, but due to the workload from other subjects and the approaching deadline, I couldn’t recall them fully. To keep the documentation complete and focused, I included these two.

1. ### 🔄 Logical deletion instead of physical  
   The project began using physical deletion for simplicity. A transition to logical deletion using a field such as `isDeleted` was planned, to preserve historical data and improve traceability, but could not be completed.

2. ### 🧠 Extended logic for the `PaymentStatus` enum  
   The idea was to enhance the payment logic: if an invalid amount was passed when creating a payment, a record would still be generated with the status **REJECTED**. A separate endpoint would then allow updating the status to **PAID** once a valid amount was submitted. This would have better simulated real-world payment handling but remains unimplemented.

---

## Notes on Development Process

- The Git branch management may not follow the best industry practices due to my current learning stage with Git and GitHub. Despite this, I ensured the stability and functionality of the codebase for the project deliverables.

---

## Setup and Run Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/ClothAshe-backend.git
Configure the application.yml or application.properties file with your database credentials.

Make sure Lombok is enabled as mentioned above.

Run the application from IntelliJ or with:

bash
Copiar
Editar
./mvnw spring-boot:run
Swagger UI
Once the application is running, access the interactive documentation at:

bash
Copiar
Editar
http://localhost:8080/swagger-ui.html
java
Copiar
Editar
