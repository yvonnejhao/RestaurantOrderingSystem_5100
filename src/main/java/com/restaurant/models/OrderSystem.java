package main.java.com.restaurant.models;


import java.util.Scanner;

public class OrderSystem {

    public static void reviewOrder(Order order) {
        System.out.println("Please review your order:");
        for (MenuItem item : order.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.println("Total: $" + order.calculateTotal());
        System.out.print("Do you want to confirm your order? (yes/no): ");
        Scanner confirmationScanner = new Scanner(System.in);
        String confirmation = confirmationScanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Your order has been confirmed.");
        } else {
            System.out.println("Order cancelled.");
        }
        confirmationScanner.close();
    }

    
}

