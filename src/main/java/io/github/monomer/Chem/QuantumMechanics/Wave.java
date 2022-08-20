package io.github.monomer.Chem.QuantumMechanics;

import io.github.monomer.Chem.Core.Constants;
import io.github.monomer.Chem.Core.Element;

import java.util.Objects;

/**
 * Makes a Wave object given either wavelength (in meters), frequency (in Hertz), or energy (in Joules per photon), and calculates all the aforementioned values.
 *
 * @author Elon Litman
 * @version 1.7
 * @see io.github.monomer.Chem.QuantumMechanics
 */
public class Wave {

    public double wavelength;

    public double frequency;

    public double energy;

    public Wave(String arg, double value) {
        arg = arg.substring(0, 1).toUpperCase() + arg.substring(1).toLowerCase();
        switch (arg) {
            case "Wavelength" -> {
                this.wavelength = value;
                this.frequency = Constants.C.getValue() / value;
                this.energy = (Constants.PLANCK_CONSTANT.getValue() * Constants.C.getValue()) / value;
            }
            case "Frequency" -> {
                this.wavelength = Constants.C.getValue() / value;
                this.frequency = value;
                this.energy = (Constants.PLANCK_CONSTANT.getValue() * Constants.C.getValue()) / this.wavelength;
            }
            case "Energy" -> {
                this.wavelength = (Constants.PLANCK_CONSTANT.getValue() * Constants.C.getValue()) / value;
                this.frequency = value / Constants.PLANCK_CONSTANT.getValue();
                this.energy = value;
            }
        }
    }

    /**
     * A method that calculates the energy of an electron in the <i>n</i>-th orbital of a hydrogen atom in Joules.
     *
     * @param orbital The <i>n</i>-th orbital of a hydrogen atom
     * @return The energy of an electron in Joules at the <i>n</i>-th orbital as a Double
     */
    public static double energyOfHydrogenOrbital(int orbital) {
        return (-1 * Constants.PLANCK_CONSTANT.getValue() * Constants.RYDBERG_CONSTANT.getValue() * Constants.C.getValue()) * (1.0 / (orbital * orbital));
    }

    /**
     * A method for calculating the electromagnetic wavelengths (in vacuum) of a spectral line in a hydrogenic chemical element using Rydberg's formula.
     *
     * @param element Any hydrogen-like chemical element
     * @param n1 The principal quantum number of the lower energy level
     * @param n2 The principal quantum number of the higher energy level for the atomic electron transition
     * @return A Wave object with the resultant wavelength
     */
    public static Wave rydberg(Element element, float n1, float n2) {
        double z = element.atomicNumber;
        assert n2 > n1 : "In the Rydberg Equation, n2 is always greater than n1.";
        double wavelengthOfElement = Math.pow(((Constants.RYDBERG_CONSTANT.getValue() * z * z) * ((1 / (n1 * n1)) - (1 / (n2 * n2)))), -1);
        return new Wave("Wavelength", wavelengthOfElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wave wave = (Wave) o;
        return Double.compare(wave.wavelength, wavelength) == 0 && Double.compare(wave.frequency, frequency) == 0 && Double.compare(wave.energy, energy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wavelength, frequency, energy);
    }

    @Override
    public String toString() {
        return "Wave{" +
                "wavelength=" + wavelength +
                ", frequency=" + frequency +
                ", energy=" + energy +
                '}';
    }
}
