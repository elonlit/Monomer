package io.github.monomer.Chem.Core;

import io.github.monomer.Monomers.Monomer;
import io.github.monomer.Polymers.Seq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class for instantiating Element objects.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Compound
 * @see Monomer
 * @see Seq
 */
public class Element {

    public float atomicNumber;

    public String elementName;

    public String elementSymbol;

    public float atomicMass;

    public float neutrons;

    public float protons;

    public float electrons;

    public float period;

    public float group;

    // The state of matter of the element at room temperature
    public String phase;

    public boolean radioactive;

    public boolean natural;

    public boolean metal;

    public boolean nonmetal;

    public boolean metalloid;

    public String type;

    public String atomicRadius;

    // Could be NaN
    public String electronegativity;

    // Could be NaN
    public String firstIonization;

    public float density;

    public float meltingPoint;

    public float boilingPoint;

    public float isotopes;

    public String discoverer;

    public float specificHeat;

    public float shells;

    public float valence;

    public String config;

    public float massNumber;

    public Element(String elementSymbol) throws IOException {
        elementSymbol = elementSymbol.substring(0,1).toUpperCase() + elementSymbol.substring(1).toLowerCase();

        String filePath = new File("").getAbsolutePath();

        BufferedReader reader = new BufferedReader(new FileReader(filePath + "/src/main/java/com/github/monomer/Chem/Core/PeriodicTable.csv"));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        String elementRow = null;
        // Find the data on the element we need
        for (String row : lines) {
            String[] parsedRows = row.split(",");
            if(parsedRows[3].equals(elementSymbol) || parsedRows[2].equals(elementSymbol)) {
                elementRow = row;
                break;
            }
        }

        assert elementRow != null;
        ArrayList<String> parsedElementRow = new ArrayList<>(Arrays.asList(elementRow.split(",")));
        parsedElementRow.remove(0);

        // Initialize fields
        this.atomicNumber = Float.parseFloat(parsedElementRow.get(0));

        this.elementName = parsedElementRow.get(1);

        this.elementSymbol = parsedElementRow.get(2);

        this.atomicMass = Float.parseFloat(parsedElementRow.get(3));

        this.neutrons = Float.parseFloat(parsedElementRow.get(4));

        this.protons = Float.parseFloat(parsedElementRow.get(5));

        this.electrons = Float.parseFloat(parsedElementRow.get(6));

        this.period = Float.parseFloat(parsedElementRow.get(7));

        this.group = Float.parseFloat(parsedElementRow.get(8));

        this.phase = parsedElementRow.get(9);

        this.radioactive = Boolean.parseBoolean(parsedElementRow.get(10));

        this.natural = Boolean.parseBoolean(parsedElementRow.get(11));

        this.metal = Boolean.parseBoolean(parsedElementRow.get(12));

        this.nonmetal = Boolean.parseBoolean(parsedElementRow.get(13));

        this.metalloid = Boolean.parseBoolean(parsedElementRow.get(14));

        this.type = parsedElementRow.get(15);

        this.atomicRadius = parsedElementRow.get(16);

        this.electronegativity = parsedElementRow.get(17);

        this.firstIonization = parsedElementRow.get(18);

        this.density = Float.parseFloat(parsedElementRow.get(19));

        this.meltingPoint = Float.parseFloat(parsedElementRow.get(20));

        this.boilingPoint = Float.parseFloat(parsedElementRow.get(21));

        this.isotopes = Float.parseFloat(parsedElementRow.get(22));

        this.discoverer = parsedElementRow.get(23);

        this.specificHeat = Float.parseFloat(parsedElementRow.get(25));

        this.shells = Float.parseFloat(parsedElementRow.get(26));

        this.valence = Float.parseFloat(parsedElementRow.get(27));

        this.config = parsedElementRow.get(28);

        this.massNumber = Float.parseFloat(parsedElementRow.get(29));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Float.compare(element.atomicNumber, atomicNumber) == 0 && Float.compare(element.atomicMass, atomicMass) == 0 && Float.compare(element.electrons, electrons) == 0 && Float.compare(element.period, period) == 0 && Float.compare(element.group, group) == 0 && radioactive == element.radioactive && natural == element.natural && metal == element.metal && nonmetal == element.nonmetal && metalloid == element.metalloid && Float.compare(element.density, density) == 0 && Float.compare(element.meltingPoint, meltingPoint) == 0 && Float.compare(element.boilingPoint, boilingPoint) == 0 && Float.compare(element.isotopes, isotopes) == 0 && Float.compare(element.specificHeat, specificHeat) == 0 && Float.compare(element.shells, shells) == 0 && Float.compare(element.valence, valence) == 0 && Float.compare(element.massNumber, massNumber) == 0 && elementName.equals(element.elementName) && elementSymbol.equals(element.elementSymbol) && phase.equals(element.phase) && type.equals(element.type) && atomicRadius.equals(element.atomicRadius) && electronegativity.equals(element.electronegativity) && firstIonization.equals(element.firstIonization) && discoverer.equals(element.discoverer) && config.equals(element.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atomicNumber, elementName, elementSymbol, atomicMass, protons, electrons, period, group, phase, radioactive, natural, metal, nonmetal, metalloid, type, atomicRadius, electronegativity, firstIonization, density, meltingPoint, boilingPoint, isotopes, discoverer, specificHeat, shells, valence, config, massNumber);
    }

    @Override
    public String toString() {
        return this.elementName + "{" +
                "atomicNumber=" + atomicNumber +
                ", elementName='" + elementName + '\'' +
                ", elementSymbol='" + elementSymbol + '\'' +
                ", atomicMass=" + atomicMass +
                ", neutrons=" + neutrons +
                ", protons=" + protons +
                ", electrons=" + electrons +
                ", period=" + period +
                ", group=" + group +
                ", phase='" + phase + '\'' +
                ", radioactive=" + radioactive +
                ", natural=" + natural +
                ", metal=" + metal +
                ", nonmetal=" + nonmetal +
                ", metalloid=" + metalloid +
                ", type='" + type + '\'' +
                ", atomicRadius='" + atomicRadius + '\'' +
                ", electronegativity='" + electronegativity + '\'' +
                ", firstIonization='" + firstIonization + '\'' +
                ", density=" + density +
                ", meltingPoint=" + meltingPoint +
                ", boilingPoint=" + boilingPoint +
                ", isotopes=" + isotopes +
                ", discoverer='" + discoverer + '\'' +
                ", specificHeat=" + specificHeat +
                ", shells=" + shells +
                ", valence=" + valence +
                ", config='" + config + '\'' +
                ", massNumber=" + massNumber +
                '}';
    }
}
