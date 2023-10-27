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

import java.util.HashMap;
import java.util.Map;

public class HTMLStatementPrinter {
    private HTMLPrinter htmlPrinter = new HTMLTextPrinter();

    public String print(Invoice invoice, Map<String, Play> plays) {
        return htmlPrinter.generateHTML(invoice, plays);
    }
}
