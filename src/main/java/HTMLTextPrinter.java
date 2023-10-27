
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
class HTMLTextPrinter implements HTMLPrinter {
    @Override
    public String generateHTML(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        // Create a StringBuilder for generating the HTML content
        StringBuilder html = new StringBuilder();
        
        // Start creating the HTML document
        html.append("<html><head><title>Invoice</title></head><body>");
        html.append("<h1>Invoice</h1>");
        html.append("<p>Client: " + invoice.customer.getName() + "</p>");
        // Create a table to display performance details
        html.append("<table border=\"1\">");
        html.append("<tr><th>Piece</th><th>Seats sold</th><th colspan='2'>Price</th></tr>");

        // Iterate through the performances in the invoice
        for (Performance performance : invoice.performances) {
            // Get the corresponding play for the performance
            Play play = plays.get(performance.playID);

            AmountCalculator amountCalculator = getAmountCalculator(play);
            // Calculate the amount and volume credits for this performance
            int thisAmount = amountCalculator.calculateAmount(performance, play);
            int credits = amountCalculator.calculateVolumeCredits(performance, play);
            volumeCredits += credits;

            // Add performance details to the HTML table
            html.append("<tr><td>" + play.name + "</td><td>" + performance.audience + "</td><td colspan='2'>$" + (thisAmount / 100.0) + "</td></tr>");
                        totalAmount += thisAmount;
        }

        // Include total amount and volume credits in the HTML
        html.append("<tr><td colspan='2'><b>Total Owed</b></td><td >$" + (totalAmount / 100.0) + "</td></tr>");
        html.append("<tr><td colspan='2'><b>Volume Credits</b></td><td>" + volumeCredits + "</td></tr>");

        // Close the HTML table and add payment information
        html.append("</table>");
        html.append("<p>Payment is required under 30 days. We can break your knees if you don't do so.</p>");

        // Close the HTML document
        html.append("</body></html>");
        
        // Convert the StringBuilder result to a String and return it
        return html.toString();
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


