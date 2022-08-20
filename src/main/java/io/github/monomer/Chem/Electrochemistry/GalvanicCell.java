package io.github.monomer.Chem.Electrochemistry;

import io.github.monomer.Chem.Core.Constants;
import io.github.monomer.Chem.Core.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class for instantiating and experimenting with Galvanic (i.e. Voltaic) Cells.
 *
 * @author Elon Litman
 * @version 1.7
 * @see io.github.monomer.Chem.Core
 * @see io.github.monomer.Chem.Electrochemistry
 */
public class GalvanicCell {

    public final Map<Element, Float[]> REDOX_POTENTIALS = new HashMap<>() {{
        put(new Element("Ba"), new Float[]{-2.90f, 2f});
        put(new Element("Ca"), new Float[]{-2.87f, 2f});
        put(new Element("Na"), new Float[]{-2.71f, 2f});
        put(new Element("Mg"), new Float[]{-2.37f, 2f});
        put(new Element("Al"), new Float[]{-1.66f, 3f});
        put(new Element("Zn"), new Float[]{-0.76f, 2f});
        put(new Element("Fe"), new Float[]{-0.44f, 2f});
        put(new Element("Cd"), new Float[]{-0.403f, 2f});
        put(new Element("Co"), new Float[]{-0.277f, 2f});
        put(new Element("Ni"), new Float[]{-0.25f, 2f});
        put(new Element("Sn"), new Float[]{-0.136f, 2f});
        put(new Element("Pb"), new Float[]{-0.13f, 2f});
        // For H2
        put(new Element("H"), new Float[]{0f, 2f});
        put(new Element("Cu"), new Float[]{0.34f, 2f});
        put(new Element("Ag"), new Float[]{0.80f, 1f});
        // For F2
        put(new Element("F"), new Float[]{2.87f, 2f});
        // For Cl2
        put(new Element("Cl"), new Float[]{1.36f, 2f});
        // For Br2
        put(new Element("Br"), new Float[]{1.09f, 2f});
        // For I2
        put(new Element("I"), new Float[]{0.54f, 2f});
    }};

    public final String[] endings = {"2SO4", "SO4", "2(SO4)3"};

    public String lineNotation;

    public Element anode;

    public Element cathode;

    public Element[] electrodes;

    public float cellPotential;

    public GalvanicCell(String firstElectrode, String secondElectrode) throws IOException {
        Element e1 = new Element(firstElectrode.replaceAll("\\d", ""));
        Element e2 = new Element(secondElectrode.replaceAll("\\d", ""));

        if(REDOX_POTENTIALS.get(e1)[0] > REDOX_POTENTIALS.get(e2)[0]) {
            this.anode = e2;
            this.cathode = e1;
        } else {
            this.anode = e1;
            this.cathode = e2;
        }

        electrodes = new Element[]{anode, cathode};

        this.cellPotential = REDOX_POTENTIALS.get(cathode)[0] - REDOX_POTENTIALS.get(anode)[0];

        this.lineNotation = this.anode.elementSymbol + "|" + this.anode.elementSymbol + (REDOX_POTENTIALS.get(anode)[1] + "").replaceAll("\\.\\d*", "") + "+" + "||" + this.cathode.elementSymbol + (REDOX_POTENTIALS.get(cathode)[1] + "").replaceAll("\\.\\d*", "") + "+" + "|" + this.cathode.elementSymbol;
    }

    /**
     * A method for doing electrolysis.
     *
     * @param elementSymbol The symbol of a chemical element
     * @param moles The moles of electrons transferred
     * @param keywordArgsAndValues A HashMap that maps String arguments (either two of "amps", "seconds", "grams") to their respective values. Warning: the arguments are case-sensitive.
     * @return A HashMap describing the electrolysis
     */
    public static HashMap<String, Float> electrolysis(String elementSymbol, int moles, HashMap<String, Float> keywordArgsAndValues) throws IOException {
        assert keywordArgsAndValues.keySet().size() == 2: "Expecting two args from either grams=, amps=, or seconds=";

        Element e = new Element(elementSymbol);

        if((keywordArgsAndValues.containsKey("seconds") || keywordArgsAndValues.containsKey("sec") || keywordArgsAndValues.containsKey("second") || keywordArgsAndValues.containsKey("Second")) && (keywordArgsAndValues.containsKey("amps") || keywordArgsAndValues.containsKey("amperes") || keywordArgsAndValues.containsKey("Amperes"))) {
            return new HashMap<String, Float>() {{
                put("Seconds", keywordArgsAndValues.get("seconds"));
                put("Amps", keywordArgsAndValues.get("amps"));
                put("Grams", keywordArgsAndValues.get("seconds") * keywordArgsAndValues.get("amps") / Constants.F.getValue() / moles * e.atomicMass);
            }};
        } else if((keywordArgsAndValues.containsKey("seconds") || keywordArgsAndValues.containsKey("sec") || keywordArgsAndValues.containsKey("second") || keywordArgsAndValues.containsKey("Second")) && (keywordArgsAndValues.containsKey("grams") || keywordArgsAndValues.containsKey("Grams") || keywordArgsAndValues.containsKey("g"))) {
            return new HashMap<String, Float>() {{
                put("Seconds", keywordArgsAndValues.get("seconds"));
                put("Grams", keywordArgsAndValues.get("grams"));
                put("Amps", keywordArgsAndValues.get("grams") / e.atomicMass * moles * Constants.F.getValue() / keywordArgsAndValues.get("seconds"));
            }};
        } else{
            return new HashMap<String, Float>() {{
                put("Amps", keywordArgsAndValues.get("amps"));
                put("Grams", keywordArgsAndValues.get("grams"));
                put("Seconds", keywordArgsAndValues.get("grams") / e.atomicMass * moles * Constants.F.getValue() / keywordArgsAndValues.get("amps"));
            }};
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GalvanicCell that = (GalvanicCell) o;
        return Float.compare(that.cellPotential, cellPotential) == 0 && anode.equals(that.anode) && cathode.equals(that.cathode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anode, cathode, cellPotential);
    }

    @Override
    public String toString() {
        return "GalvanicCell{" +
                "Cell='" + lineNotation + '\'' + ", " +
                "Anode=" + anode.elementSymbol + ", " +
                "Cathode=" + cathode.elementSymbol + ", " +
                "CellPotential=" + cellPotential +
                '}';
    }
}
