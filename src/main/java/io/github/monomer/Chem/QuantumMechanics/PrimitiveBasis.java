package io.github.monomer.Chem.QuantumMechanics;

import java.util.Arrays;
import java.util.Objects;

import static io.github.monomer.Chem.Core.VectorManipulationUtil.doubleFactorial;

/**
 * A Primitive Gaussian basis function.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Nucleus
 */
public class PrimitiveBasis {

    public float contraction;

    public float exponent;

    public float[] coordinates;

    public int[] integralCoordinates;

    public float normalizationConstant;

    public PrimitiveBasis(float contraction, float exponent, float[] coordinates, int[] integralExponents) {
        this.contraction = contraction;
        this.exponent = exponent;
        this.coordinates = coordinates;
        this.integralCoordinates = integralExponents;
        this.normalizationConstant = getNormalizationConstant();
    }

    /**
     * A method that calculates the normalization constant for the primitive gaussian and stores it in the field "NormalizationConstant" when invoked in the constructor.
     *
     * @return The normalization constant in floating-point precision.
     */
    private float getNormalizationConstant() {
        float out1 = 0;
        float out2 = 0;
        float out3 = 0;
        if (this.normalizationConstant == 0f) {
            float l = this.integralCoordinates[0];
            float m = this.integralCoordinates[1];
            float n = this.integralCoordinates[2];

            out1 = doubleFactorial(2 * l - 1) * doubleFactorial(2 * m - 1) * doubleFactorial(2 * n - 1);
            out2 = (float) Math.pow((Math.PI / (2 * this.exponent)), (3 / 2));
            out3 = (float) Math.pow((4 * this.exponent), (l + m + m));
        }

        return (float) (1 / Math.sqrt((out1 * out2) / out3));
    }

    /**
     * Calculates the value of the function at point (x, y, z).
     *
     * @param x The x-coordinate of the point in floating-point precision
     * @param y The y-coordinate of the point in floating-point precision
     * @param z The z-coordinate of the point in floating-point precision
     * @return The value of the function in floating-point precision
     */
    public float getValue(float x, float y, float z) {
        float rX = this.coordinates[0];
        float rY = this.coordinates[1];
        float rZ = this.coordinates[2];

        float lX = this.integralCoordinates[0];
        float lY = this.integralCoordinates[1];
        float lZ = this.integralCoordinates[2];

        return (float) (this.normalizationConstant * this.contraction * Math.pow((x - rX), lX) * Math.pow((y - rY), lY) * Math.pow((z - rZ), lZ) * Math.exp(-this.exponent * (Math.pow((x - rX), 2) + Math.pow((y - rY), 2) + Math.pow((z - rZ), 2))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveBasis that = (PrimitiveBasis) o;
        return Float.compare(that.contraction, contraction) == 0 && Float.compare(that.exponent, exponent) == 0 && Float.compare(that.normalizationConstant, normalizationConstant) == 0 && Arrays.equals(coordinates, that.coordinates) && Arrays.equals(integralCoordinates, that.integralCoordinates);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(contraction, exponent, normalizationConstant);
        result = 31 * result + Arrays.hashCode(coordinates);
        result = 31 * result + Arrays.hashCode(integralCoordinates);
        return result;
    }

    @Override
    public String toString() {
        return "PrimitiveBasis{" +
                "contraction=" + contraction +
                ", exponent=" + exponent +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", integralCoordinates=" + Arrays.toString(integralCoordinates) +
                ", normalizationConstant=" + normalizationConstant +
                '}';
    }
}
