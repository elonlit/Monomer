package io.github.monomer.Chem.Core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * A class that constructs a Compound object from a molecular formula.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Element
 */
public class Compound {

    public String formula;

    public Map<Element, Integer> occurrences;

    public Map<String, Integer> humanReadableOccurrences;

    public Compound(String formula) throws IOException {
        this.formula = formula;
        this.occurrences = parseFormula(formula);
    }

    private static Map<String,String> getSymbolMap(){
        Map<String, String> elementMap = new HashMap<>();

        Scanner in = new Scanner(Constants.symbolsMap);

        while(in.hasNext()){
            String shortform = in.next();
            String longform = in.next();
            elementMap.put(shortform, longform);
            elementMap.put(longform.toUpperCase(), longform);
        }

        return elementMap;
    }

    /**
     * A method for parsing a molecular formula into an occurrence mapping of the constituent elements to their frequencies.
     *
     * You can group element with parenthesis and apply counts to the groups (e.g. C2(HO)2).
     * Any other symbols are stripped from the formula (e.g. - or +). Leaving them in does no harm
     *
     * @param formula The molecular formula of the compound as a String
     * @return A mapping of Elements to their frequencies in the Compound
     */
    public Map<Element, Integer> parseFormula(String formula) throws IOException {

        Map<String, String> elementMap = getSymbolMap();

        formula = formula.replaceAll("[^a-zA-Z\\d()]", "");

        Map<String,Integer> elementCounts = new HashMap<>();
        int len = formula.length();
        for(int i = 0; i < len;){
            boolean isGroup = false;
            if(formula.charAt(i) == '('){
                i++;
                isGroup = true;
            }
            int repeatCount = 1;
            Map<String, Integer> atomsInGroup = new HashMap<>();
            do{
                int start = i;
                int restore_i = 0;
                String element = null;
                String restoreElement = null;
                while(i < len && Character.isLetter(formula.charAt(i))){
                    i++;
                    element = formula.substring(start, i);
                    String elementFromMap = elementMap.get(element);
                    if(elementFromMap == null)
                        elementFromMap = elementMap.get(element.toUpperCase());
                    if(elementFromMap != null){
                        restore_i = i;
                        restoreElement = elementFromMap;
                    }
                }
                if(restoreElement != null){
                    i = restore_i;
                    element = restoreElement;
                }

                if(element == null || element.equals("")){
                    System.out.println("Parse error: could not detect an element where one was expected in formula string.");
                    System.out.println("Remaining formula to parse: " + formula.substring(i));
                    System.exit(1);
                }

                start = i;
                while(i < len && Character.isDigit(formula.charAt(i))){
                    i++;
                }
                int count;
                try {
                    count = Integer.parseInt(formula.substring(start, i));
                } catch(NumberFormatException e){
                    count = 1;
                }
                /* System.out.println("element: "+element); */
                atomsInGroup.put(element, count);
                if(i < len && formula.charAt(i) == ')') {
                    if(!isGroup) {
                        System.out.println("Parse error: unmatched parenthesis detected...");
                    }
                    i++;
                    isGroup = false;

                    start = i;
                    while(i < len && Character.isDigit(formula.charAt(i))) {
                        i++;
                    }

                    try {
                        repeatCount = Integer.parseInt(formula.substring(start, i));
                    } catch(NumberFormatException ignored) {
                        ;
                    }
                }
            } while(isGroup);
            for(String atomType : atomsInGroup.keySet()){
                int currentValue = 0;
                if(elementCounts.containsKey(atomType))
                    currentValue = elementCounts.get(atomType);
                elementCounts.put(atomType, currentValue + atomsInGroup.get(atomType) * repeatCount);
            }
        }
        Map<Element, Integer> occurrenceMap = new HashMap<>();
        for(String element : elementCounts.keySet()) {
            occurrenceMap.put(new Element(element), elementCounts.get(element));
        }
        this.humanReadableOccurrences = elementCounts;
        return occurrenceMap;
    }

    /**
     * A method that obtains the molar mass (g/mol) of the Compound.
     *
     * @return A floating-point representation of the molar mass of the Compound
     */
    public float getMolarMass() {
        float molarMass = 0f;
        for(Element e : this.occurrences.keySet()) {
            molarMass += e.atomicMass * this.occurrences.get(e);
        }
        return molarMass;
    }

    /**
     * A method that gets the percentage composition by mass of a certain element in the Compound.
     *
     * @param element An Element object
     * @return The percentage composition of mass of the Element in the Compound in floating-point precision
     */
    public float getPercentCompositionByMass(Element element) {
        return (this.occurrences.get(element) * element.atomicMass) / this.getMolarMass() * 100;
    }

    /**
     * A method that gets stoichiometric amounts of the Compound given a measurement.
     *
     * @param arg The unit of the provided quantity of substance (either "Grams", "Moles", or "Molecules")
     * @param quantity The gram, mole, OR molecule amounts of the Compound
     * @return The gram, mole, AND molecule amounts of the Compound in a HashMap
     */
    public HashMap<String, Float> getAmounts(String arg, float quantity) {
        HashMap<String, Float> stoichiometricAmounts = new HashMap<>();
        arg = arg.substring(0, 1).toUpperCase() + arg.substring(1).toLowerCase();
        switch (arg) {
            case "Grams" -> {
                stoichiometricAmounts.put("Grams", quantity);
                stoichiometricAmounts.put("Moles", quantity / this.getMolarMass());
                stoichiometricAmounts.put("Molecules", (quantity / this.getMolarMass() * Constants.AVOGADRO_CONSTANT.getValue()));
            }
            case "Moles" -> {
                stoichiometricAmounts.put("Grams", quantity * this.getMolarMass());
                stoichiometricAmounts.put("Moles", quantity);
                stoichiometricAmounts.put("Molecules", quantity * Constants.AVOGADRO_CONSTANT.getValue());
            }
            case "Molecules" -> {
                stoichiometricAmounts.put("Grams", (quantity / Constants.AVOGADRO_CONSTANT.getValue()) * this.getMolarMass());
                stoichiometricAmounts.put("Moles", quantity / Constants.AVOGADRO_CONSTANT.getValue());
                stoichiometricAmounts.put("Molecules", quantity);
            }
        }
        return stoichiometricAmounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compound compound = (Compound) o;
        return formula.equals(compound.formula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formula);
    }

    @Override
    public String toString() {
        return this.formula + ": " + this.humanReadableOccurrences.toString();
    }
}
