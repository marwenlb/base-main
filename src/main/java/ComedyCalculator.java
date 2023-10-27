/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
class ComedyCalculator implements AmountCalculator {
    @Override
    public int calculateAmount(Performance performance, Play play) {
        int thisAmount = 30000;
        if (performance.audience > 20) {
            thisAmount += 10000 + 500 * (performance.audience - 20);
        }
        thisAmount += 300 * performance.audience;
        return thisAmount;
    }

    @Override
    public int calculateVolumeCredits(Performance performance, Play play) {
        return Math.max(performance.audience - 30, 0) + (int) Math.floor(performance.audience / 5);
    }
}