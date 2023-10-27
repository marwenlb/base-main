/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
public class Customer {
    private String name;
    private int customerNumber;
    private int loyaltyPoints;

    public Customer(String name, int customerNumber, int loyaltyPoints) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getName() {
        return name.toString();
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void deductLoyaltyPoints(int points) {
        loyaltyPoints -= points;
    }
    
}
