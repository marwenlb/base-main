/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Labassi
 */

public class HTMLStatementPrinter {
    private StatementPrinter statementPrinter = new StatementPrinter();

    public String print(Invoice invoice, HashMap<String, Play> plays) {
        int totalAmount = 0; // To store the total amount
        int volumeCredits = 0; 
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title></head><body>");

        html.append("<h1>Invoice</h1>");
        html.append("<p>Client: " + invoice.customer + "</p>");

        html.append("<table border=\"1\">");
        html.append("<tr><th>Piece</th><th>Seats sold</th><th colspan='2'>Price</th></tr>");

        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);
            int thisAmount = 0; // To store the amount for the current performance
            // Calculate the amount for this performance based on the type of play
            switch (play.getType()) {
                case TRAGEDY :
                    thisAmount = 40000;
                    if (performance.audience > 30) {
                        thisAmount += 1000 * (performance.audience - 30);
                    }
                    break;
                case COMEDY:
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
            if (play.getType() == Play.PlayType.COMEDY) {
                volumeCredits += Math.floor(performance.audience / 5);
            }


            html.append("<tr><td>" + play.name + "</td><td>" + performance.audience + "</td><td colspan='2'>$" + (thisAmount / 100.0) + "</td></tr>");
            totalAmount += thisAmount;
        }

        html.append("<tr><td colspan='2'><b>Total Owed</b></td><td >$" + (totalAmount / 100.0) + "</td></tr>");
        html.append("<tr><td colspan='2'><b>Volume Credits</b></td><td>" + volumeCredits + "</td></tr>");

        html.append("</table>");

        html.append("<p>Payment is required under 30 days. We can break your knees if you don't do so.</p>");

        html.append("</body></html>");
        return html.toString();
    }
}

