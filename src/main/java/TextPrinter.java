import java.text.NumberFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
class TextPrinter implements Printer {
    @Override
    public String print(Invoice invoice, HashMap<String, Play> plays) {
        // Obtain the customer from the invoice
        Customer customer = invoice.getCustomer();
        int totalAmount = 0;
        int volumeCredits = 0;
        
        // Get the customer's loyalty points
        int loyaltyPoints = customer.getLoyaltyPoints();
        
        // Create a StringBuilder to build the result
        StringBuilder result = new StringBuilder();
        
        // Create a currency formatter for US locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String customerName = invoice.customer.getName();
        result.append(String.format("Statement for %s\n", customerName));
        
        // Iterate through the performances in the invoice
        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);
            AmountCalculator amountCalculator = getAmountCalculator(play);
            int thisAmount = amountCalculator.calculateAmount(performance, play);
            int credits = amountCalculator.calculateVolumeCredits(performance, play);
            volumeCredits += credits;

            // Add performance details to the result
            result.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), performance.audience));
            totalAmount += thisAmount;
        }

        // Apply a loyalty points discount if the customer has more than 150 points
        if (loyaltyPoints > 150) {
            totalAmount -= 1500;
            loyaltyPoints -= 150;
        }

        // Add the total amount and volume credits to the result
        result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount / 100));
        result.append(String.format("You earned %s credits\n", volumeCredits));

        // Convert the StringBuilder result to a String and return it
        return result.toString();
    }

    // Helper method to determine the appropriate AmountCalculator based on play type
    private AmountCalculator getAmountCalculator(Play play) {
        if (play.getType() == Play.PlayType.COMEDY) {
            return new ComedyCalculator();
        } else if (play.getType() == Play.PlayType.TRAGEDY) {
            return new TragedyCalculator();
        } else {
            // Throw an error for unknown play types
            throw new Error("Unknown type: " + play.getType());
        }
    }
}

