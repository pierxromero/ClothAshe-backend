# ClothAshe-backend

Added a configuration class to allow requests from Postman, as they were initially blocked.  
Implemented the `ProductController` with basic CRUD operations for product management via API.  
Introduced a `GlobalExceptionHandler` that currently handles basic exceptions for improved error management.  
Renamed the enum `AdminRole` to `Role` and added a new value `CLIENT`, which is present in both the `Admin` and `User` entities.  

Most importantly, a bug was discovered and fixed in the MapStruct mapping caused by a misconfiguration in the `pom.xml`, where Lombok plugins were declared separately, preventing proper conversion.
