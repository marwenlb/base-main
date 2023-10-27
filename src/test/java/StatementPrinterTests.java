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
        plays.put("hamlet",  new Play("Hamlet", "tragedy"));
        plays.put("as-like",  new Play("As You Like It", "comedy"));
        plays.put("othello",  new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
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
        plays.put("hamlet",  new Play("Hamlet", "tragedy"));
        plays.put("as-like",  new Play("As You Like It", "comedy"));
        plays.put("othello",  new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        HTMLStatementPrinter statementPrinter = new HTMLStatementPrinter();
        String result = statementPrinter.print(invoice, plays);

        verifyHtml(result);
        
        // Vous pouvez également ajouter des assertions pour vérifier le contenu du rendu HTML si nécessaire.
    }

    @Test
    void statementWithNewPlayTypes() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("henry-v",  new Play("Henry V", "history"));
        plays.put("as-like",  new Play("As You Like It", "pastoral"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }
    
  @Test
    void testAudienceForTragedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new Play("Play Name", "tragedy"));

    Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 30)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    String expectedStatement = "Statement for Customer Name\n" +
            "  Play Name: $400.00 (30 seats)\n" +
            "Amount owed is $400.00\n" +
            "You earned 0 credits\n";

        Assertions.assertEquals(expectedStatement, result);
    }
    
    
    @Test
    void testAudienceForComedy() {
    HashMap<String, Play> plays = new HashMap<>();
    plays.put("playID", new Play("Play Name", "comedy"));

    Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 20)));

    StatementPrinter statementPrinter = new StatementPrinter();
    var result = statementPrinter.print(invoice, plays);

    String expectedStatement = "Statement for Customer Name\n" +
            "  Play Name: $360.00 (20 seats)\n" +
            "Amount owed is $360.00\n" +
            "You earned 4 credits\n"; // Mise à jour de cette ligne en fonction du comportement attendu

        Assertions.assertEquals(expectedStatement, result);
    }
}
