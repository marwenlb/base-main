import java.util.*;

public class Invoice {

  public String customer;
  public List<Performance> performances;

  public Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
    
  }
  
   public String toHTML(HashMap<String, Play> plays) {
        HTMLStatementPrinter htmlPrinter = new HTMLStatementPrinter();
        return htmlPrinter.print(this, plays);
    }
  
}
