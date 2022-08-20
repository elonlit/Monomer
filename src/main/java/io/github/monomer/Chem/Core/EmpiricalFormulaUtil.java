package io.github.monomer.Chem.Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.github.monomer.Chem.Core.Fraction.getFraction;

/**
 * A utility class for manipulating and generating empirical formulas.
 *
 * @author Elon Litman
 * @version 1.7
 * @see EquationBalancerUtil
 */
public final class EmpiricalFormulaUtil {

    private static HashMap<Compound, Float> reduceList(HashMap<Compound, Float> moles) {
        ArrayList<Float> moleQuantities = new ArrayList<>();
        ArrayList<Integer> denominatorList = new ArrayList<>();

        for(Compound c : moles.keySet()) {
            moleQuantities.add(moles.get(c));
            denominatorList.add(getFraction(moles.get(c))[1]);
        }

        moleQuantities.replaceAll(aFloat -> aFloat * Collections.max(denominatorList));

        Float minQuantity = Collections.min(moleQuantities);

        moleQuantities.replaceAll(aFloat -> aFloat / minQuantity);

        moleQuantities.replaceAll(a -> (float) Math.round(a));

        HashMap<Compound, Float> reducedMolesMapping = new HashMap<>();

        assert moles.size() == moleQuantities.size();

        ArrayList<Compound> compoundKeys = new ArrayList<>(moles.keySet());
        for(int i=0; i<compoundKeys.size(); i++) {
            reducedMolesMapping.put(compoundKeys.get(i), moleQuantities.get(i));
        }

        return reducedMolesMapping;
    }

    /**
     * A method for building an empirical formula from a mapping of Elements to their constituent relative frequencies in the Compound.
     *
     * @param percentComposition A HashMap of Element objects and proportions in floating-point precision
     * @return The empirical formula that corresponds to such a percent composition as a String
     * @throws IOException Incase the Element object does not exist
     */
    public static String getEmpiricalFormulaFromPercentComposition(Map<Element, Float> percentComposition) throws IOException {
        HashMap<Compound, Float> moles = new HashMap<>();

        // Divide each % by its atomic mass
        for(Element element : percentComposition.keySet()) {
            Compound c = new Compound(element.elementSymbol);
            moles.put(c, c.getAmounts("Grams", percentComposition.get(element)).get("Moles"));
        }

        Compound lowestMassConstituent = Collections.min(moles.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Divide the number of moles of each Compound by the lowest mass constituent
        for(Compound c : moles.keySet()) {
            moles.replace(c, moles.get(c) / lowestMassConstituent.getMolarMass());
        }

        // Find the lowest whole number ratio
        return getEmpiricalFormulaFromMolesMapping(moles);
    }

    /**
     * A method for building the empirical formula of a hydrocarbon given the grams of CO2 and grams of H2O formed in its combustion.
     *
     * @param CO2 The grams of carbon dioxide formed as a result of the combustion of the hydrocarbon
     * @param H2O The grams of water formed as a result of the combustion of the hydrocarbon
     * @return The empirical formula of the hydrocarbon
     */
    public static String getHydrocarbonFromCombustion(float CO2, float H2O) throws IOException {
        Compound carbon = new Compound("C");
        Compound hydrogen = new Compound("H");

        float molesC = carbon.getAmounts("Grams", CO2).get("Moles") * 2;
        float molesH = hydrogen.getAmounts("Grams", H2O).get("Moles");

        HashMap<Compound, Float> molesMapping = new HashMap<>() {{
            put(carbon, molesC);
            put(hydrogen, molesH);
        }};

        Compound lowestMassConstituent = Collections.min(molesMapping.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Divide the number of moles of each Compound by the lowest mass constituent
        for(Compound c : molesMapping.keySet()) {
            molesMapping.replace(c, molesMapping.get(c) / lowestMassConstituent.getMolarMass());
        }

        return getEmpiricalFormulaFromMolesMapping(molesMapping);
    }

    private static String getEmpiricalFormulaFromMolesMapping(HashMap<Compound, Float> molesMapping) {
        HashMap<Compound, Float> molesMappingReduced = reduceList(molesMapping);

        StringBuilder empiricalFormula = new StringBuilder();
        for(Compound c : molesMappingReduced.keySet()) {
            empiricalFormula.append(c.toString().split(":")[0]);
            empiricalFormula.append(Math.round(molesMappingReduced.get(c)));
        }
        return empiricalFormula.toString();
    }
}
