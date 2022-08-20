package io.github.monomer.Chem.Reactions;

import io.github.monomer.Chem.Core.Compound;
import io.github.monomer.Chem.Core.EquationBalancerUtil;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for instantiating a Reaction with a set of reactant and product Compounds.
 * Warning: the Reaction will be balanced automatically, unless the user specifies otherwise.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Combustion
 */
public sealed class Reaction permits Combustion {

    public Compound[] reactants;

    public Compound[] products;

    public String equation;

    public boolean isBalanced;

    /**
     * Instantiate a Reaction object using a string to represent the formula.
     *
     * @param equation A formula String, where the formulae of reactants are separated by + signs on the left side of the arrow -->, and on the right side of the arrow, the formulae of products are separated by + signs.
     */
    public Reaction(String equation) throws IOException {
        // Strip any coefficients
        if(EquationBalancerUtil.isBalanced(equation)) {
            equation = equation.replaceAll("(?<=\\ )\\d*(?=\\w)", " ").replaceAll("(\\ )+", " ");
        }

        // Match all reactants
        Pattern patternReactants = Pattern.compile("\\ ?.+(?=-->)\\ ?", Pattern.CASE_INSENSITIVE);

        Matcher reactantsMatcher = patternReactants.matcher(equation);
        reactantsMatcher.find();
        String[] reactantsStrArr = reactantsMatcher.group().replaceAll("\\s+","").split("\\+");

        // Match all products
        Pattern patternProducts = Pattern.compile("\\ ?(?<=-->).+\\ ?", Pattern.CASE_INSENSITIVE);

        Matcher productsMatcher = patternProducts.matcher(equation);
        productsMatcher.find();
        String[] productsStrArr = productsMatcher.group().replaceAll("\\s+", "").split("\\+");

        // Make Compound arrays to hold reactants and products
        Compound[] reactants = new Compound[reactantsStrArr.length];
        Compound[] products = new Compound[productsStrArr.length];

        // Instantiate Compound objects using those reactants
        for(int i=0; i<reactantsStrArr.length; i++) {
            reactants[i] = new Compound(reactantsStrArr[i]);
        }

        for(int i=0; i<productsStrArr.length; i++) {
            products[i] = new Compound(productsStrArr[i]);
        }

        // Balance
        if(!EquationBalancerUtil.isBalanced(equation)) {
            equation = EquationBalancerUtil.getBalancedReaction(equation);
        }

        // Assignment to fields
        this.reactants = reactants;
        this.products = products;
        this.equation = equation;
        this.isBalanced = EquationBalancerUtil.isBalanced(equation);
    }

    public Reaction(String[] reactants, String[] products) throws IOException {
        String equation = String.join(" + ", reactants) + " --> " + String.join(" + ", products);

        // Balance
        if(!EquationBalancerUtil.isBalanced(equation)) {
            equation = EquationBalancerUtil.getBalancedReaction(equation);
        }

        List<Compound> tempReactants = null;
        List<Compound> tempProducts = null;
        for(String reactant : reactants) {
            tempReactants.add(new Compound(reactant));
        }
        for(String product : products) {
            tempProducts.add(new Compound(product));
        }
        assert tempReactants != null;
        assert tempProducts != null;
        this.reactants = tempReactants.toArray(new Compound[tempReactants.size()]);
        this.products = tempProducts.toArray(new Compound[tempProducts.size()]);
        this.equation = equation;
        this.isBalanced = EquationBalancerUtil.isBalanced(equation);
    }

    public Reaction(Compound[] reactants, Compound[] products) throws IOException {
        String[] reactantsStr = new String[reactants.length];
        for(int i=0; i<reactants.length; i++) {
            reactantsStr[i] = reactants[i].formula;
        }
        String[] productsStr = new String[products.length];
        for(int i=0; i<products.length; i++) {
            productsStr[i] = products[i].formula;
        }

        String equation = String.join(" + ", reactantsStr) + " --> " + String.join(" + ", productsStr);

        // Balance
        if(!EquationBalancerUtil.isBalanced(equation)) {
            equation = EquationBalancerUtil.getBalancedReaction(equation);
        }

        this.equation = equation;
        this.reactants = reactants;
        this.products = products;
        this.isBalanced = EquationBalancerUtil.isBalanced(equation);
    }

    public Reaction() {
        ;
    }

    private <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    /**
     * A method that calculates stoichiometric amounts of all Compounds in the reaction given the amount of one Compound.
     *
     * @param compoundNameArg The formula of the chosen Compound in the Reaction
     * @param unitArg The unit of the quantity of the chosen Compound (either grams, moles, or molecules)
     * @param quantity The amount of the chosen Compound
     * @return The amounts of each Compound in the reaction as a List of HashMaps
     */
    public ArrayList<HashMap<String, Float>> getAmounts(String compoundNameArg, String unitArg, float quantity) throws IOException {
        ArrayList<HashMap<String, Float>> stoichiometricAmounts = new ArrayList<>();

        compoundNameArg = compoundNameArg.substring(0, 1).toUpperCase() + compoundNameArg.substring(1).toLowerCase();
        unitArg = unitArg.substring(0, 1).toUpperCase() + unitArg.substring(1).toLowerCase();

        Compound[] allCompounds = concatenate(reactants, products);
        Map<String, Float> indexMoles = new Compound(compoundNameArg).getAmounts(unitArg, quantity);

        Compound indexCompound = new Compound(compoundNameArg);

        float indexAmount = indexCompound.getAmounts(unitArg, quantity).get("Moles");

        Pattern indexCoefficientPattern = Pattern.compile("\\ ?\\d+(?=" + indexCompound.formula + ")\\ ?", Pattern.CASE_INSENSITIVE);

        Matcher indexCoefficientMatcher = indexCoefficientPattern.matcher(equation);
        indexCoefficientMatcher.find();

        float indexCoefficient;
        try {
            indexCoefficient = Integer.parseInt(indexCoefficientMatcher.group().strip());
        } catch (IllegalStateException ignored) {
            indexCoefficient = 1;
        }

        for(int i=0; i<allCompounds.length; i++) {
            // Check coefficients
            Pattern patternCoefficients = Pattern.compile("\\ ?\\d+(?=" + allCompounds[i].formula + ")\\ ?", Pattern.CASE_INSENSITIVE);

            Matcher coefficientsMatcher = patternCoefficients.matcher(equation);
            coefficientsMatcher.find();

            int coefficient = 1;
            try {
                coefficient = Integer.parseInt(coefficientsMatcher.group().strip());
            } catch (IllegalStateException ignored) {
                ;
            }
            HashMap<String, Float> compoundAmounts = allCompounds[i].getAmounts("Moles", (coefficient / indexCoefficient) * indexMoles.get("Moles"));
            stoichiometricAmounts.add(compoundAmounts);
        }
        return stoichiometricAmounts;
    }

    /**
     * A method for obtaining the limiting reagent (or reactant) in the chemical Reaction.
     *
     * @param unitArg The units of each amount (can either be "Grams", "Moles", or "Molecules")
     * @param quantities The positional amounts of each reactant to use in the Reaction
     * @return The limiting reagent
     */
    public Compound getLimitingReagent(String unitArg, float... quantities) {
        HashMap<Compound, Float> compoundToMoleMap = new HashMap<>();

        for(int i=0; i<quantities.length; i++) {
            float moles = reactants[i].getAmounts(unitArg, quantities[i]).get("Moles");
            compoundToMoleMap.put(reactants[i], moles);
        }

        HashMap<Compound, Float> compoundMolesToMolesOfProduct = new HashMap<>();
        HashMap<Compound, Float> compoundMolesOccurrences = new HashMap<>();

        constructCompoundMolesOccurrencesMap(compoundMolesOccurrences, reactants);

        constructCompoundMolesOccurrencesMap(compoundMolesOccurrences, products);

        for(Compound c : compoundMolesOccurrences.keySet()) {
            try {
                compoundMolesToMolesOfProduct.put(c, compoundToMoleMap.get(c) * (compoundMolesOccurrences.get(products[0]) / compoundMolesOccurrences.get(c)));
            } catch(NullPointerException ignored) {
                ;
            }
        }

        return Collections.min(compoundMolesToMolesOfProduct.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void constructCompoundMolesOccurrencesMap(HashMap<Compound, Float> compoundMolesOccurrences, Compound[] compounds) {
        for(Compound c : compounds) {
            Pattern coefficientPattern = Pattern.compile("\\ ?\\d+(?=" + c.formula + ")\\ ?", Pattern.CASE_INSENSITIVE);

            Matcher coefficientMatcher = coefficientPattern.matcher(equation);
            coefficientMatcher.find();

            float coefficient;
            try {
                coefficient = Integer.parseInt(coefficientMatcher.group().strip());
            } catch (IllegalStateException ignored) {
                coefficient = 1;
            }
            compoundMolesOccurrences.put(c, coefficient);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reaction reaction = (Reaction) o;
        return isBalanced == reaction.isBalanced && Arrays.equals(reactants, reaction.reactants) && Arrays.equals(products, reaction.products) && equation.equals(reaction.equation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(equation, isBalanced);
        result = 31 * result + Arrays.hashCode(reactants);
        result = 31 * result + Arrays.hashCode(products);
        return result;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "reactants=" + Arrays.toString(reactants) +
                ", products=" + Arrays.toString(products) +
                ", equation='" + equation + '\'' +
                ", isBalanced=" + isBalanced +
                '}';
    }
}
