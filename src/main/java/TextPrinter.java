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
  Customer customer = invoice.getCustomer(); // Obtenez l'objet Customer depuis l'invoice
        int totalAmount = 0;
        int volumeCredits = 0;
        int loyaltyPoints = customer.getLoyaltyPoints(); // Obtenez les points de fidélité du client
        StringBuilder result = new StringBuilder();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

String customerName = invoice.customer.getName(); // Access the customer's name
        result.append(String.format("Statement for %s\n", customerName));
        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);

            AmountCalculator amountCalculator = getAmountCalculator(play);

            int thisAmount = amountCalculator.calculateAmount(performance, play);
            int credits = amountCalculator.calculateVolumeCredits(performance, play);

            volumeCredits += credits;

            result.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), performance.audience));
            totalAmount += thisAmount;
        }
        if (loyaltyPoints > 150) {
            totalAmount -= 1500;
            loyaltyPoints -= 150;
        }
        result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount / 100)));
        result.append(String.format("You earned %s credits\n", volumeCredits));

        return result.toString();
    }

    private AmountCalculator getAmountCalculator(Play play) {
        if (play.getType() == Play.PlayType.COMEDY) {
            return new ComedyCalculator();
        } else if (play.getType() == Play.PlayType.TRAGEDY) {
            return new TragedyCalculator();
        } else {
            throw new Error("Unknown type: " + play.getType());
        }
    }
}

