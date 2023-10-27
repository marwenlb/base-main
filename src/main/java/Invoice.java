import java.util.*;

class Invoice {
    Customer customer;
    List<Performance> performances;

    public Invoice(Customer customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }
}

