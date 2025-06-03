# Service Layer Overhaul and DTO Structure Integration

Introduced a set of improved services with expanded functionality and better responsibility separation.
Implemented dedicated DTOs for request, response, and update operations to streamline data flow and validation.
Key services such as UserService, OrderService, AddressService, CartService, and ProductCommentService were enhanced to cover advanced business logic and ensure proper access control.

One major improvement includes consistently retrieving the currently authenticated user via authService.getAuthenticatedUser(), especially in user-specific actions like managing addresses, comments, cart items, or personal orders.

These upgrades not only improve maintainability but also prepare the application for production-level usage with a clear service contract and clean architecture.
