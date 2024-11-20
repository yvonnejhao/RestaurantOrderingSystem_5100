# Restaurant Ordering System

## Purpose and Scope Statement
The Restaurant Ordering System is designed to assist restaurant staff and customers in managing orders efficiently. This system has two main users – admin and customer. The admin can manage the menu, update orders, and view order history. Customers can browse the menu, create orders, and view their order status. Inventory and supplier management are out of scope for this project.

## Requirements Narrative
### Admin Functions:
- View all menu items
- Modify an existing menu item
- Delete a menu item
- Add a new menu item
- View all orders
- Update order status

### Customer Functions:
- View all menu items
- Add items to cart
- Place an order

## Objectives
- A basic login screen for user access (admin/customer)
- Separate views for each user role with tailored functionalities:
  - Admin can manage menu items and orders effectively
  - Customers can browse the menu and place orders

## Functional Specification
The application is divided between two main users - Admin and Customer. The user’s functionality is based on their role, and they are directed to the corresponding view upon login.

### General Functions:
- `loadMenuFromCSV()`
- `logIn()`

### Admin Functions:
- `updateMenuItem()`
- `removeMenuItem()`
- `addMenuItem()`
- `updateOrderStatus()`

### Customer Functions:
- `addItem()`
- `addToCart()`
- `checkout()`
- `placeOrder()`

## Classes Needed
- **Main**: Entry point of the application. Initializes the Restaurant instance and opens the LoginView.
- **Restaurant**: Manages the menu and orders. Provides methods to load, save, and update menu items and orders.
- **LoginView**: Handles user login for customers and admins. Displays the login interface and navigates to the appropriate view based on user input.
- **MenuView**: Displays the menu to customers. Allows customers to add items to their cart and place orders.
- **OrderManagementView**: Allows admins to view and manage orders. Provides functionality to update order statuses.
- **MenuManagementView**: Allows admins to manage the menu. Provides functionality to add, modify, and delete menu items.
- **MenuItem**: Represents a menu item with a name and price.
- **Order**: Represents an order with an order number, table number, status, and list of menu items.
- **CSVUtils**: Utility class for reading from and writing to CSV files. Used for persisting menu items and orders.
- **ButtonRenderer**: Custom renderer for buttons in table cells.
- **ButtonEditor**: Custom editor for buttons in table cells. Handles button actions.

## Technologies Needed
- **Java Framework**: No specific Java framework
- **UI**: Java Swing
- **Database**: CSV files for persistent data storage

## Logic Specification
### Application Initialization:
- The `Main` class serves as the entry point of the application.
- In the `main` method, an instance of the `Restaurant` class is created.
- The `SwingUtilities.invokeLater` method is used to ensure that the GUI creation and updates are performed on the Event Dispatch Thread (EDT).
- A new instance of `LoginView` is created, passing the `Restaurant` instance to it, and the login view is made visible.

### Login Process:
- The `LoginView` class handles user login for both customers and admins.
- Users are prompted to enter their credentials (table number for customers, admin ID for admins).
- Based on the input, the application navigates to the appropriate view (menu view for customers, admin dashboard for admins).

### Menu Display and Order Placement:
- The `MenuView` class displays the menu items to customers.
- Customers can add items to their cart and place orders.
- The `Order` class represents an order, containing details such as order number, table number, status, and list of menu items.
- The `reviewOrder` method allows customers to review their order, confirm it, or cancel it.

### Order Management:
- The `OrderManagementView` class allows admins to view and manage orders.
- Admins can update the status of orders (e.g., pending, completed).

### Menu Management:
- The `MenuManagementView` class allows admins to manage the menu items.
- Admins can add new items, modify existing items, or delete items from the menu.
- The `MenuItem` class represents a menu item with properties such as name and price.

### Data Persistence:
- The `CSVUtils` class provides utility methods for reading from and writing to CSV files.
- Menu items and orders are persisted in CSV files, allowing the application to load and save data.


## Prepared By:
- Ming-Hsiang, Lee
- Yi-Chen, Chao