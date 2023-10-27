/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Labassi
 */
class TragedyCalculator implements AmountCalculator {
    @Override
    public int calculateAmount(Performance performance, Play play) {
        int thisAmount = 40000;
        if (performance.audience > 30) {
            thisAmount += 1000 * (performance.audience - 30);
        }
        return thisAmount;
    }

    @Override
    public int calculateVolumeCredits(Performance performance, Play play) {
        return Math.max(performance.audience - 30, 0);
    }
}
