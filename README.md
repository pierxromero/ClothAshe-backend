# ClothAshe-backend
* The AdminEntity class was removed, and authentication logic was unified into a single User entity, with a Role field to distinguish between ADMIN and CLIENT.
* The User and Person entities were merged to simplify the data model and eliminate redundancy.
* Implemented the AuthController with /auth/login and /auth/register endpoints, along with their respective DTOs: LoginRequest, LoginResponse, and RegisterRequest.
* Spring Security was configured using a custom JwtAuthenticationFilter to validate JWT tokens on each request.
* A robust SecurityConfig was added with stateless authentication (STATELESS), and role/permission support using @PreAuthorize.
* Critical issues with MapStruct were fixed, caused by incompatible versions and the absence of proper Java 17 configuration.
* Resolved a Maven compiler conflict (./mvnw clean install) and learned how to reset the project setup when persistent compilation errors occur.

