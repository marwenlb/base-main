import java.text.NumberFormat;
import java.util.*;


public class StatementPrinter {

    public String print(Invoice invoice, HashMap<String, Play> plays) {
        int totalAmount = 0; // To store the total amount
        int volumeCredits = 0; // To store the total volume credits
        StringBuilder result = new StringBuilder(); // Efficiently build the final statement

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Create the statement header
        result.append(String.format("Statement for %s\n", invoice.customer));

        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);
            int thisAmount = 0; // To store the amount for the current performance

            // Calculate the amount for this performance based on the type of play
            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (performance.audience > 30) {
                        thisAmount += 1000 * (performance.audience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (performance.audience > 20) {
                        thisAmount += 10000 + 500 * (performance.audience - 20);
                    }
                    thisAmount += 300 * performance.audience;
                    break;
                default:
                    throw new Error("Unknown type: " + play.type);
            }

            // Add volume credits based on the size of the audience
            volumeCredits += Math.max(performance.audience - 30, 0);
            // Add extra credits for every five comedy attendees
            if ("comedy".equals(play.type)) {
                volumeCredits += Math.floor(performance.audience / 5);
            }

            // Create a line for this performance in the statement
            result.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), performance.audience));
            totalAmount += thisAmount;
        }

        // Add the total amount owed to the statement
        result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount / 100)));
        // Add the total credits earned to the statement
        result.append(String.format("You earned %s credits\n", volumeCredits));

        return result.toString();
    }
}
