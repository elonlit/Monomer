package io.github.monomer.Chem.Solutions;

import io.github.monomer.Chem.Core.Compound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class for instantiating and manipulating aqueous solutions.
 *
 * @author Elon Litman
 * @version 1.7
 * @see pH
 */
public class Solution {

    public Compound solute;

    public float molarity;

    /**
     * A constructor for instantiating a Solution object with the formula of the solute and the molarity in mol/L.
     *
     * @param solute The formula of the solute as a String
     * @param molarity The number of moles of solute per liter of solution
     */
    public Solution(String solute, float molarity) throws IOException {
        this.solute = new Compound(solute);
        this.molarity = molarity;
    }

    /**
     * A constructor for instantiating a Solution object with the formula of the solute and the number of grams/L.
     *
     * @param solute The formula of the solute as a String
     * @param gramsOfSolute The number of grams of solute
     * @param litersOfSolution The number of liters of solution
     */
    public Solution(String solute, float gramsOfSolute, float litersOfSolution) throws IOException {
        this.solute = new Compound(solute);
        this.molarity = this.solute.getAmounts("Grams", gramsOfSolute).get("Moles") / litersOfSolution;
    }

    /**
     * A method for calculating dilutions using the formula M1 * V1 = M2 * V2.
     *
     * @param V1 The starting volume of the solution (must be specified)
     * @param M2 The ending molarity after dilution (can be 0f if not considered)
     * @param V2 The ending volume after dilution (can be 0f if not considered)
     * @return The new volume and molarity as a HashMap of Strings
     */
    public HashMap<String, String> dilute(float V1, float M2, float V2) {
        // Raise an error if both M2 and V2 are specified;
        assert !(M2 != 0f || V2 != 0f) : "May not specify both M2 and V2";

        Compound s = this.solute;
        float molarity = this.molarity;

        if(M2 != 0f) {
            return new HashMap<>() {{
                put("Solute", s.formula);
                put("Volume", (molarity * V1) / M2 + "");
                put("Molarity", M2 + "");
            }};
        } else if(M2 == 0f) {
            return new HashMap<>() {{
                put("Solute", s.formula);
                put("Volume", V2 + "");
                put("Molarity", (molarity * V1) / V2 + "");
            }};
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Float.compare(solution.molarity, molarity) == 0 && solute.equals(solution.solute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solute, molarity);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solute=" + solute +
                ", molarity=" + molarity +
                '}';
    }
}
