package AetheriusEngine.core.math;

/*
Lolita's Revenge
June 30 2017

Handles randomized numbers and whatnot.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class WeightedRandom {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the function of the class.
     */

    private int low, high;
    private double val = 0;
    private double sigma;

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the class.
     */

    public WeightedRandom() {}

    public WeightedRandom(int i1, int i2) {
        low = i1;
        high = i2;
    }

    public WeightedRandom(int i1, int i2, double sig) {
        low = i1;
        high = i2;
        sigma = sig;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed outside of the class to edit it's characteristics.
     */

    public int getLow() { return low; }
    public int getHigh() { return high; }
    public int getMedian() { return (low + high) / 2; }
    public double getSigma() { return sigma; }
    public double getValue() { return val; }

    public void setLow(int i) { low = i; }
    public void setHigh(int i) { high = i; }
    public void setSigma(double s) { sigma = s; }

    public double getRandom() {
        Random r = new Random();
        val = low + r.nextInt(high);
        return val;
    }

    public int getWeightedRandom(int decimal) {
        return 0;
    }

    public double getDeviation(int decimal) {
        if (decimal < 0) throw new IllegalArgumentException(); //We can't have less than 0 decimal points. Throw an exception if this happens.
        Random r = new Random();
        val = Math.pow(((1 / sigma * (Math.sqrt(2 * Math.PI))) * Math.E), -(Math.pow(((((low * Math.pow(10, decimal)) + r.nextInt((high * (int)Math.pow(10, decimal)))) / Math.pow(10, decimal)) - getMedian()), 2) / (2 * Math.pow(sigma, 2)))); //Standard deviation formula // todo: fix
        BigDecimal rounded = new BigDecimal(val);
        rounded = rounded.setScale(decimal, RoundingMode.HALF_UP);
        return rounded.doubleValue();
    }

    //------------------------------------------------------------------------------------------------------------------

}
