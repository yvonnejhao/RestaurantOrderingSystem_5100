package com.restaurant.models;

import java.util.Scanner;

public class OrderSystem {

    /**
     * This method allows the user to review their order.
     * It prints out each item in the order along with its price,
     * calculates and displays the total cost, and asks the user
     * to confirm or cancel the order.
     *
     * @param order The order to be reviewed.
     */
    public static void reviewOrder(Order order) {
        // Print the order details
        System.out.println("Please review your order:");
        for (MenuItem item : order.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        
        // Print the total cost of the order
        System.out.println("Total: $" + order.calculateTotal());
        
        // Ask the user to confirm or cancel the order
        System.out.print("Do you want to confirm your order? (yes/no): ");
        Scanner confirmationScanner = new Scanner(System.in);
        String confirmation = confirmationScanner.nextLine();
        
        // Process the user's confirmation
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Your order has been confirmed.");
        } else {
            System.out.println("Order cancelled.");
        }
        
        // Close the scanner to avoid resource leaks
        confirmationScanner.close();
    }
}

