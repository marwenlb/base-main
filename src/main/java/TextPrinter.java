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
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        result.append(String.format("Statement for %s\n", invoice.customer));

        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);

            AmountCalculator amountCalculator = getAmountCalculator(play);

            int thisAmount = amountCalculator.calculateAmount(performance, play);
            int credits = amountCalculator.calculateVolumeCredits(performance, play);

            volumeCredits += credits;

            result.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), performance.audience));
            totalAmount += thisAmount;
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

