
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
class HTMLTextPrinter implements HTMLPrinter  {

    @Override
    public String generateHTML(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title></head><body>");

        html.append("<h1>Invoice</h1>");
        html.append("<p>Client: " + invoice.customer + "</p>");

        html.append("<table border=\"1\">");
        html.append("<tr><th>Piece</th><th>Seats sold</th><th colspan='2'>Price</th></tr>");

        for (Performance performance : invoice.performances) {
            Play play = plays.get(performance.playID);

            AmountCalculator amountCalculator = getAmountCalculator(play);

            int thisAmount = amountCalculator.calculateAmount(performance, play);
            int credits = amountCalculator.calculateVolumeCredits(performance, play);

            volumeCredits += credits;

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

