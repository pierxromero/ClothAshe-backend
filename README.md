# ClothAshe Backend — Extended Functionality Branch

This branch introduces a significant expansion of the backend logic and architecture for the **ClothAshe** online clothing store system.

## 🆕 Key Updates

- **Full Controller Layer Implemented**  
  All necessary REST controllers were created, enabling interaction with the core entities of the system (products, users, orders, etc.).

- **Extended Service Layer Logic**  
  New service methods were added to support enhanced business logic and system functionalities beyond basic CRUD operations.

- **New Role: `OWNER`**  
  A new `OWNER` role was introduced with the following characteristics:
  - Has full access to the system.
  - Can assign and manage other users’ roles.
  - Is immutable — its privileges cannot be changed by other roles.
  - Introduces a clear **hierarchy**:  
    `OWNER → ADMIN → CLIENT`.
