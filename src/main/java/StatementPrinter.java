import java.text.NumberFormat;
import java.util.*;


public class StatementPrinter {
    public String print(Invoice invoice, HashMap<String, Play> plays) {
        Printer textPrinter = new TextPrinter();
        return textPrinter.print(invoice, plays);
    }
}
