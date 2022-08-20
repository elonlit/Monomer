package io.github.monomer.Chem.QuantumMechanics;

import io.github.monomer.Chem.Core.Element;
import io.github.monomer.Chem.Core.VectorManipulationUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * A nucleus class for instantiating nuclei with relevant attributes.
 *
 * @author Elon Litman
 * @version 1.7
 * @see io.github.monomer.Chem.QuantumMechanics
 */
public class Nucleus {

    public Element element;

    public int charge;

    public float mass;

    public float[] coordinates;

    public Nucleus(String element, int charge, float mass, float[] coordinates) throws IOException {
        this.element = new Element(element);
        this.charge = charge;
        this.mass = mass;
        this.coordinates = coordinates;
    }

    /**
     * A method that computes the nuclear-nuclear repulsion energy for two nuclei.
     *
     * @param nuc1 The first Nucleus object.
     * @param nuc2 The second Nucleus object.
     * @return A floating-point representation of the repulsion energy.
     */
    public static float coulombsLaw(Nucleus nuc1, Nucleus nuc2) {
        float r12 = VectorManipulationUtil.coordinateDistance(nuc1.coordinates, nuc2.coordinates);

        return (nuc1.charge * nuc2.charge) / r12;
    }

    /**
     * A method that constructs a matrix containing the repulsion energies of each nuclei-nuclei interaction.
     *
     * @param nucleiArray A list of Nucleus objects
     * @return A matrix of the repulsion energies in floating-point precision
     */
    public static float[][] coulombMatrix(Nucleus[] nucleiArray) {
        int matrixLength = nucleiArray.length;
        float[][] matrix = new float[matrixLength][matrixLength];

        for(int i=0; i<matrixLength; i++) {
            for(int j=0; j<matrixLength; j++) {
                if (i != j) {
                    matrix[i][j] = coulombsLaw(nucleiArray[i], nucleiArray[j]);
                }
            }
        }
        return matrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nucleus nucleus = (Nucleus) o;
        return charge == nucleus.charge && Float.compare(nucleus.mass, mass) == 0 && element.equals(nucleus.element) && Arrays.equals(coordinates, nucleus.coordinates);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(element, charge, mass);
        result = 31 * result + Arrays.hashCode(coordinates);
        return result;
    }

    @Override
    public String toString() {
        return "Nucleus{" +
                "element=" + element +
                ", charge=" + charge +
                ", mass=" + mass +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
