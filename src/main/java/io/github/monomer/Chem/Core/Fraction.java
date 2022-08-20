package io.github.monomer.Chem.Core;

import java.util.Objects;

/**
 * This helper class is used to represent basic fractions.
 *
 * @author Elon Litman
 * @version 1.7
 * @see VectorManipulationUtil
 * @see EmpiricalFormulaUtil
 */
public class Fraction{
    public boolean active;
    private final int numerator;
    private final int denominator;

    public Fraction() {
        this.active = false;
        this.numerator = 0;
        this.denominator = 1;
    }

    public Fraction(int numerator, int denominator){
        this.active = true;
        int GCF = GCD(numerator, denominator);
        this.numerator = numerator/GCF;
        this.denominator = denominator/GCF;
    }

    /**
     * Used to get a fraction from a decimal.
     *
     * @param input An input as a Double
     * @return A fraction
     */
    public static Integer[] getFraction(double input)
    {
        int p0 = 1;
        int q0 = 0;
        int p1 = (int) Math.floor(input);
        int q1 = 1;
        int p2;
        int q2;

        double r = input - p1;
        double next_cf;
        while(true)
        {
            r = 1.0 / r;
            next_cf = Math.floor(r);
            p2 = (int) (next_cf * p1 + p0);
            q2 = (int) (next_cf * q1 + q0);

            // Limit the numerator and denominator to be 256 or less
            if(p2 > 256 || q2 > 256)
                break;

            // Remember the last two fractions
            p0 = p1;
            p1 = p2;
            q0 = q1;
            q1 = q2;

            r -= next_cf;
        }

        input = (double) p1 / q1;
        // Hard upper and lower bounds for ratio
        if(input > 256.0)
        {
            p1 = 256;
            q1 = 1;
        }
        else if(input < 1.0 / 256.0)
        {
            p1 = 1;
            q1 = 256;
        }
        return new Integer[] {p1, q1};
    }

    /**
     * Used to get GCF of two numbers.
     *
     * @param a first number
     * @param b second number
     * @return GCF of a and b
     */
    public static int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }

    /**
     * Used to get LCM of two numbers.
     *
     * @param a first number
     * @param b second number
     * @return LCM of a and b
     */
    public static int LCM(int a, int b) {
        return (a * b) / GCD(a, b);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    /**
     * A method for multiplying fractions.
     *
     * @param firstFraction First Fraction
     * @param secondFraction Second Fraction
     * @return Product of fractions
     */
    public static Fraction multiply(Fraction firstFraction, Fraction secondFraction){
        return new Fraction(firstFraction.getNumerator()*secondFraction.getNumerator(), firstFraction.getDenominator()*secondFraction.getDenominator());
    }

    /**
     * A method for adding fractions
     *
     * @param firstFraction First Fraction
     * @param secondFraction Second Fraction
     * @return Addition of fractions
     */
    public static Fraction add(Fraction firstFraction, Fraction secondFraction){
        if (firstFraction.getNumerator()!=0 || secondFraction.getNumerator()!=0) {
            int additionLCM = LCM(firstFraction.getDenominator(), secondFraction.getDenominator());
            int scaledFirstNumerator=firstFraction.getNumerator()*additionLCM/firstFraction.getDenominator();
            int scaledSecondNumerator=secondFraction.getNumerator()*additionLCM/secondFraction.getDenominator();
            return new Fraction(scaledFirstNumerator+scaledSecondNumerator, additionLCM);
        } else if(!(firstFraction.getNumerator() == 0)) {
            return new Fraction(secondFraction.getNumerator(), secondFraction.getDenominator());
        } else {
            return new Fraction(firstFraction.getNumerator(), firstFraction.getDenominator());
        }
    }

    /**
     * A method for subtracting fractions.
     *
     * @param firstFraction First Fraction
     * @param secondFraction Second Fraction
     * @return Subtraction of fractions
     */
    public static Fraction subtract(Fraction firstFraction, Fraction secondFraction){
        return add(firstFraction, negate(secondFraction));
    }

    /**
     * A method for negating fractions.
     *
     * @param fraction Fraction to be negated
     * @return Negated value of fraction
     */
    public static Fraction negate(Fraction fraction){
        return new Fraction(-fraction.getNumerator(), fraction.getDenominator());
    }

    @Override
    public String toString() {
        return numerator+"/"+denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return active == fraction.active && numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, numerator, denominator);
    }
}
