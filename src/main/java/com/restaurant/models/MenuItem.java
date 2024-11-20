package com.restaurant.models;

// The MenuItem class represents an item in a restaurant's menu.
public class MenuItem {
    // The name of the menu item.
    private String name;
    
    // The price of the menu item.
    private double price;

    // Constructor to initialize the MenuItem with a name and price.
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getter method for the name of the menu item.
    public String getName() {
        return name;
    }

    // Setter method for the name of the menu item.
    public void setName(String name) { 
        this.name = name;
    }

    // Getter method for the price of the menu item.
    public double getPrice() {
        return price;
    }

    // Setter method for the price of the menu item.
    public void setPrice(double price) { 
        this.price = price;
    }
}