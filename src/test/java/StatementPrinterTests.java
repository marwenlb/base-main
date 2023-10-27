import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.*;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet", new Play("Hamlet", Play.PlayType.TRAGEDY));
        plays.put("as-like",  new Play("As You Like It", Play.PlayType.COMEDY));
        plays.put("othello",  new Play("Othello", Play.PlayType.TRAGEDY));

        Customer customer = new Customer("BigCo", 123, 160); // Créez un objet Customer avec 160 points de fidélité

        Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);
        verify(result);
    }
    
    @Test
    void testHTMLInvoice() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new Play("Hamlet", Play.PlayType.TRAGEDY));
        plays.put("as-like",  new Play("As You Like It", Play.PlayType.COMEDY));
        plays.put("othello",  new Play("Othello", Play.PlayType.TRAGEDY));

        Customer customer = new Customer("BigCo", 123, 160); // Créez un objet Customer avec 160 points de fidélité

        Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        HTMLStatementPrinter statementPrinter = new HTMLStatementPrinter();
        String result = statementPrinter.print(invoice, plays);
        verifyHtml(result);
        
    }
/*
    
     Avec la classe enum de play ce test ne passe plus
    @Test
    void statementWithNewPlayTypes() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("henry-v",  new Play("Henry V", Play.PlayType.HISTORY));
        plays.put("as-like",  new Play("As You Like It", Play.PlayType.PASTORAL));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }
 */    

 @Test
    void testAudienceForTragedy() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", new Play("Play Name", Play.PlayType.TRAGEDY));

        Customer customer = new Customer("Customer #123", 123, 160); // Créez un objet Customer avec 160 points de fidélité

        Invoice invoice = new Invoice(customer, List.of(new Performance("playID", 30)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        String expectedStatement = "Statement for Customer #123\n" +
            "  Play Name: $400.00 (30 seats)\n" +
            "Amount owed is $385.00\n" +
            "You earned 0 credits\n";

        Assertions.assertEquals(expectedStatement, result);
    }
    
    
    @Test
    void testAudienceForComedy() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", new Play("Play Name", Play.PlayType.COMEDY));

        Customer customer = new Customer("Test", 123, 160); // Créez un objet Customer avec 160 points de fidélité

        Invoice invoice = new Invoice(customer, List.of(new Performance("playID", 20)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        String expectedStatement = "Statement for Test\n" +
            "  Play Name: $360.00 (20 seats)\n" +
            "Amount owed is $345.00\n" +
            "You earned 4 credits\n"; // Mise à jour de cette ligne en fonction du comportement attendu

        Assertions.assertEquals(expectedStatement, result);
    }
 
}

