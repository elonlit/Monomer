package io.github.monomer.Chem.Core;

import io.github.monomer.Chem.Reactions.Reaction;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * A utility class that can be used to solve almost any chemical equation, though square brackets are not supported. These need to be expanded manually.
 *
 * @author Elon Litman
 * @version 1.7
 * @see EmpiricalFormulaUtil
 */
public final class EquationBalancerUtil {

    /**
     * A method that evaluates if a chemical equation is already balanced.
     *
     * @param equation A String equation
     * @return A boolean corresponding to whether the equation is balanced
     */
    public static boolean isBalanced(String equation) {
        String[] data = equation.split("-->");
        String left = data[0].strip();
        String right = data[1].strip();
        int limitleft=0,limitright=0;
        for(int i=0;i<left.length();i++)
        {
            if(Character.isUpperCase(left.charAt(i)))
                limitleft++;
        }
        for(int i=0;i<right.length();i++)
        {
            if(Character.isUpperCase(right.charAt(i)))
                limitright++;
        }

        String plus = "\\+";
        String[] Left = left.split(plus);
        String[] Right =right.split(plus);
        String[] lout =new String[limitleft];
        int[] lout1 =new int[limitleft];

        String[] rout =new String[limitright];
        int[] rout1 =new int[limitright];
        int lind=0, rind=0;
        for (String l : Left) {
            int multiplier = 1;

            for (int j = 0; j < l.length(); j++) {
                char s = l.charAt(j);
                if (Character.isDigit(s) && multiplier == 1) {
                    multiplier = Character.getNumericValue(s);
                    if (Character.isDigit(l.charAt(j + 1))) {
                        j++;
                        while (true) {
                            if (j < l.length() && Character.isDigit(l.charAt(j))) {
                                multiplier = multiplier * 10 + Character.getNumericValue(l.charAt(j));
                                j++;

                            } else {
                                j--;
                                break;
                            }
                        }
                    }
                }
                if (Character.isUpperCase(s)) {
                    int k = j + 1;
                    String temp = s + "";
                    while (true) {
                        if (k < l.length()) {
                            if (Character.isLowerCase(l.charAt(k))) {
                                temp = temp + l.charAt(k);
                            }
                            if (Character.isUpperCase(l.charAt(k)) || Character.isDigit(l.charAt(k))) {
                                break;
                            }
                            k++;
                        } else
                            break;
                    }

                    lout[lind] = temp;
                    int coff = 1;
                    if (k < l.length())
                        if (Character.isDigit(l.charAt(k))) {
                            coff = Character.getNumericValue(l.charAt(k));
                            k++;
                            while (true) {
                                if (k < l.length() && Character.isDigit(l.charAt(k))) {
                                    coff = coff * 10 + Character.getNumericValue(l.charAt(k));
                                    k++;

                                } else
                                    break;
                            }
                        }
                    lout1[lind] = coff * multiplier;
                    j = k - 1;
                    lind++;
                }
            }
        }
        for (String r : Right) {
            int multiplier = 1;

            for (int j = 0; j < r.length(); j++) {
                char s = r.charAt(j);
                if (Character.isDigit(s) && multiplier == 1) {
                    multiplier = Character.getNumericValue(s);
                    if (Character.isDigit(r.charAt(j + 1))) {
                        j++;
                        while (true) {
                            if (j < r.length() && Character.isDigit(r.charAt(j))) {
                                multiplier = multiplier * 10 + Character.getNumericValue(r.charAt(j));
                                j++;

                            } else {
                                j--;
                                break;
                            }
                        }
                    }
                }
                if (Character.isUpperCase(s)) {
                    int k = j + 1;
                    String temp = s + "";
                    while (true) {
                        if (k < r.length()) {
                            if (Character.isLowerCase(r.charAt(k))) {
                                temp = temp + r.charAt(k);
                            }
                            if (Character.isUpperCase(r.charAt(k)) || Character.isDigit(r.charAt(k))) {
                                break;
                            }
                            k++;
                        } else
                            break;
                    }

                    rout[rind] = temp;
                    int coff = 1;
                    if (k < r.length())
                        if (Character.isDigit(r.charAt(k))) {
                            coff = Character.getNumericValue(r.charAt(k));
                            k++;
                            while (true) {
                                if (k < r.length() && Character.isDigit(r.charAt(k))) {
                                    coff = coff * 10 + Character.getNumericValue(r.charAt(k));
                                    k++;

                                } else
                                    break;
                            }
                        }
                    rout1[rind] = coff * multiplier;
                    j = k - 1;
                    rind++;

                }
            }
        }
        for(int i=0; i<limitleft; i++) {
            for(int j=i+1; j<limitleft; j++) {
            if(lout[i].equals(lout[j]))
            {
                lout1[i]=lout1[i]+lout1[j];
                lout1[j]=0;
                lout[j]="xyzz";
                limitleft--;
            }
        }
        }
        int r = limitright;
        for(int i=0; i<r; i++) {
            for(int j=i+1; j<r; j++) {
                if(rout[i].equals(rout[j])) {
                    rout1[i] = rout1[i] + rout1[j];
                    rout1[j] = 0;
                    rout[j] = "xyzz";
                    limitright--;
                }
            }
        }

        int res = 0;
        for(int i=0; i<lout.length; i++) {
            for(int j=0;j<rout.length;j++) {
                if(lout[i].equals(rout[j])) {
                    if(lout1[i] != rout1[j]) {
                        res++;
                    }
                }
            }
        }
        return res == 0;
    }

    public static boolean isBalanced(Reaction reaction) {
        String equation = reaction.equation;
        String[] data = equation.split("-->");
        String left = data[0].strip();
        String right = data[1].strip();
        int limitleft=0,limitright=0;
        for(int i=0;i<left.length();i++)
        {
            if(Character.isUpperCase(left.charAt(i)))
                limitleft++;
        }
        for(int i=0;i<right.length();i++)
        {
            if(Character.isUpperCase(right.charAt(i)))
                limitright++;
        }

        String plus = "\\+";
        String[] Left = left.split(plus);
        String[] Right =right.split(plus);
        String[] lout =new String[limitleft];
        int[] lout1 =new int[limitleft];

        String[] rout =new String[limitright];
        int[] rout1 =new int[limitright];
        int lind=0, rind=0;
        for (String l : Left) {
            int multiplier = 1;

            for (int j = 0; j < l.length(); j++) {
                char s = l.charAt(j);
                if (Character.isDigit(s) && multiplier == 1) {
                    multiplier = Character.getNumericValue(s);
                    if (Character.isDigit(l.charAt(j + 1))) {
                        j++;
                        while (true) {
                            if (j < l.length() && Character.isDigit(l.charAt(j))) {
                                multiplier = multiplier * 10 + Character.getNumericValue(l.charAt(j));
                                j++;

                            } else {
                                j--;
                                break;
                            }
                        }
                    }
                }
                if (Character.isUpperCase(s)) {
                    int k = j + 1;
                    String temp = s + "";
                    while (true) {
                        if (k < l.length()) {
                            if (Character.isLowerCase(l.charAt(k))) {
                                temp = temp + l.charAt(k);
                            }
                            if (Character.isUpperCase(l.charAt(k)) || Character.isDigit(l.charAt(k))) {
                                break;
                            }
                            k++;
                        } else
                            break;
                    }

                    lout[lind] = temp;
                    int coff = 1;
                    if (k < l.length())
                        if (Character.isDigit(l.charAt(k))) {
                            coff = Character.getNumericValue(l.charAt(k));
                            k++;
                            while (true) {
                                if (k < l.length() && Character.isDigit(l.charAt(k))) {
                                    coff = coff * 10 + Character.getNumericValue(l.charAt(k));
                                    k++;

                                } else
                                    break;
                            }
                        }
                    lout1[lind] = coff * multiplier;
                    j = k - 1;
                    lind++;
                }
            }
        }
        for (String r : Right) {
            int multiplier = 1;

            for (int j = 0; j < r.length(); j++) {
                char s = r.charAt(j);
                if (Character.isDigit(s) && multiplier == 1) {
                    multiplier = Character.getNumericValue(s);
                    if (Character.isDigit(r.charAt(j + 1))) {
                        j++;
                        while (true) {
                            if (j < r.length() && Character.isDigit(r.charAt(j))) {
                                multiplier = multiplier * 10 + Character.getNumericValue(r.charAt(j));
                                j++;

                            } else {
                                j--;
                                break;
                            }
                        }
                    }
                }
                if (Character.isUpperCase(s)) {
                    int k = j + 1;
                    String temp = s + "";
                    while (true) {
                        if (k < r.length()) {
                            if (Character.isLowerCase(r.charAt(k))) {
                                temp = temp + r.charAt(k);
                            }
                            if (Character.isUpperCase(r.charAt(k)) || Character.isDigit(r.charAt(k))) {
                                break;
                            }
                            k++;
                        } else
                            break;
                    }

                    rout[rind] = temp;
                    int coff = 1;
                    if (k < r.length())
                        if (Character.isDigit(r.charAt(k))) {
                            coff = Character.getNumericValue(r.charAt(k));
                            k++;
                            while (true) {
                                if (k < r.length() && Character.isDigit(r.charAt(k))) {
                                    coff = coff * 10 + Character.getNumericValue(r.charAt(k));
                                    k++;

                                } else
                                    break;
                            }
                        }
                    rout1[rind] = coff * multiplier;
                    j = k - 1;
                    rind++;

                }
            }
        }
        for(int i=0; i<limitleft; i++) {
            for(int j=i+1; j<limitleft; j++) {
                if(lout[i].equals(lout[j]))
                {
                    lout1[i]=lout1[i]+lout1[j];
                    lout1[j]=0;
                    lout[j]="xyzz";
                    limitleft--;
                }
            }
        }
        int r = limitright;
        for(int i=0; i<r; i++) {
            for(int j=i+1; j<r; j++) {
                if(rout[i].equals(rout[j])) {
                    rout1[i] = rout1[i] + rout1[j];
                    rout1[j] = 0;
                    rout[j] = "xyzz";
                    limitright--;
                }
            }
        }

        int res = 0;
        for(int i=0; i<lout.length; i++) {
            for(int j=0;j<rout.length;j++) {
                if(lout[i].equals(rout[j])) {
                    if(lout1[i] != rout1[j]) {
                        res++;
                    }
                }
            }
        }
        return res == 0;
    }

    /**
     * Balances a chemical equation procedurally as shown in <a href="https://jaminsantiago.files.wordpress.com/2013/04/balancing-chemical-equations-easy-algebraic-method.pdf">...</a> using polyatomic replacement. This method can balance single replacement, double replacement, synthesis, decomposition, combustion, and redox reactions.
     *
     * @param reaction An unbalanced String reaction
     * @return A balanced String reaction
     */
    public static String getBalancedReaction(String reaction) {
        String preReactantsString = reaction.split("-->")[0].strip();
        String preProductsString = reaction.split("-->")[1].strip();

        StringBuilder storeString = new StringBuilder();
        for(int i=0; i<preReactantsString.length(); i++){
            char character = preReactantsString.charAt(i);
            if(Character.isLetter(character) || Character.isDigit(character) || character=='+' || character=='(' || character==')'){
                storeString.append(character);
            }
        }
        preReactantsString= storeString.toString();
        storeString = new StringBuilder();
        for(int i=0; i<preProductsString.length(); i++){
            char character = preProductsString.charAt(i);
            if(Character.isLetter(character) || Character.isDigit(character) || character=='+' || character=='(' || character==')'){
                storeString.append(character);
            }
        }
        preProductsString = storeString.toString();
        String[] replacementArray = polyatomicReplacement(preReactantsString, preProductsString);
        String reactantsString = replacementArray[0];
        String productsString = replacementArray[1];
        Hashtable<Integer, Hashtable<String, Integer>> reactants = parseString(reactantsString);
        Hashtable<Integer, Hashtable<String, Integer>> products = parseString(productsString);

        // Checking if same elements are in lists
        LinkedList<String> reactantsElements = getElements(reactantsString);
        Collections.sort(reactantsElements);
        LinkedList<String> productsElements = getElements(productsString);
        Collections.sort(productsElements);
        LinkedList<String> elements;
        boolean contain;
        for (Object reactantsElement : reactantsElements) {
            // Noinspection SuspiciousMethodCalls
            contain = productsElements.contains(reactantsElement);
            if (!contain) {
                System.out.println("Error: Same elements need to be on both sides of the equation.");
                System.exit(0);
            }
        }
        for (Object productsElement : productsElements) {
            // Noinspection SuspiciousMethodCalls
            contain = reactantsElements.contains(productsElement);
            if (!contain) {
                System.out.println("Error: Same elements need to be on both sides of the equation.");
                System.exit(0);
            }
        }
        elements = reactantsElements;

        // Solving algorithm using two-variable simplified algebraic method
        boolean a_used = false;
        boolean b_used = false;
        LinkedList<Fraction> reactantsA = new LinkedList<>();
        LinkedList<Fraction> reactantsB = new LinkedList<>();
        LinkedList<Boolean> reactantsFilled = new LinkedList<>();
        for(int i=0; i<reactants.size(); i++){
            reactantsA.addLast(new Fraction());
            reactantsB.addLast(new Fraction());
            reactantsFilled.addLast(false);
        }
        LinkedList<Fraction> productsA = new LinkedList<>();
        LinkedList<Fraction> productsB = new LinkedList<>();
        LinkedList<Boolean> productsFilled = new LinkedList<>();
        for(int i=0; i<products.size(); i++){
            productsA.addLast(new Fraction());
            productsB.addLast(new Fraction());
            productsFilled.addLast(false);
        }
        // First step
        // One-to-one unfilled relationships
        LinkedList<String> elementsUsed = new LinkedList<>();
        for (String currentElement : elements) {
            Integer reactantIndex = null;
            Integer productIndex = null;
            int counter = 0;
            for (int j=0; j<reactants.size(); j++) {
                Hashtable<String, Integer> compoundTable = reactants.get(j);
                if (compoundTable.containsKey(currentElement)) {
                    counter++;
                    reactantIndex = j;
                }
            }
            if (counter > 1) {
                continue;
            }
            counter = 0;
            for (int j = 0; j < products.size(); j++) {
                Hashtable<String, Integer> compoundTable = products.get(j);
                if (compoundTable.containsKey(currentElement)) {
                    counter++;
                    productIndex = j;
                }
            }
            if (counter > 1) {
                continue;
            }
            if (reactantIndex != null && productIndex != null) {
                if (!reactantsFilled.get(reactantIndex) && !productsFilled.get(productIndex)) {
                    // Create ratio
                    Integer reactantQuantity = (reactants.get(reactantIndex)).get(currentElement);
                    Integer productQuantity = (products.get(productIndex)).get(currentElement);
                    Fraction reactantCoefficient;
                    Fraction productCoefficient;
                    if (reactantQuantity > productQuantity) {
                        // Set reactant coefficient to 1 and product coefficient to ratio
                        reactantCoefficient = new Fraction(1, 1);
                        productCoefficient = new Fraction(reactantQuantity, productQuantity);
                    } else if (reactantQuantity < productQuantity) {
                        productCoefficient = new Fraction(1, 1);
                        reactantCoefficient = new Fraction(productQuantity, reactantQuantity);
                    } else {
                        productCoefficient = new Fraction(1, 1);
                        reactantCoefficient = new Fraction(1, 1);
                    }
                    elementsUsed.addLast(currentElement);
                    reactantsFilled.set(reactantIndex, true);
                    productsFilled.set(productIndex, true);
                    if (!a_used ) {
                        // Use a as the first variable
                        a_used  = true;
                        reactantsA.set(reactantIndex, reactantCoefficient);
                        productsA.set(productIndex, productCoefficient);
                    } else if (!b_used) {
                        //Use b as the second variable
                        b_used = true;
                        reactantsB.set(reactantIndex, reactantCoefficient);
                        productsB.set(productIndex, productCoefficient);
                    } else {
                        System.out.println("This equation uses more than two variables.");
                        System.exit(0);
                    }
                }
            }
        }
        //Remaining elements
        for (String value : elementsUsed) {
            elements.remove(value);
        }

        //Second step:
        //One-to-one relationships with one filled and one unfilled
        elementsUsed=new LinkedList<>();
        for (String currentElement : elements) {
            Integer reactantIndex = null;
            Integer productIndex = null;
            int counter = 0;
            for (int j = 0; j < reactants.size(); j++) {
                Hashtable<String, Integer> compoundTable = reactants.get(j);
                if (compoundTable.containsKey(currentElement)) {
                    counter++;
                    reactantIndex = j;
                }
            }
            if (counter > 1) {
                continue;
            }
            counter = 0;
            for (int j = 0; j < products.size(); j++) {
                Hashtable<String, Integer> compoundTable = products.get(j);
                if (compoundTable.containsKey(currentElement)) {
                    counter++;
                    productIndex = j;
                }
            }
            if (counter > 1) {
                continue;
            }
            if (reactantIndex != null && productIndex != null) {
                if (!reactantsFilled.get(reactantIndex) ^ !productsFilled.get(productIndex)) {
                    elementsUsed.addLast(currentElement);
                    Integer reactantQuantity = (reactants.get(reactantIndex)).get(currentElement);
                    Integer productQuantity = (products.get(productIndex)).get(currentElement);
                    Fraction filledCoefficient;
                    Fraction unfilledCoefficient;
                    if (reactantsFilled.get(reactantIndex)) {
                        // Product Side is unfilled
                        if (reactantsA.get(reactantIndex).active) {
                            // Use a as variable
                            filledCoefficient = reactantsA.get(reactantIndex);
                        } else {
                            // Use b as variable
                            filledCoefficient = reactantsB.get(reactantIndex);
                        }
                        unfilledCoefficient = Fraction.multiply(filledCoefficient, new Fraction(reactantQuantity, productQuantity));
                        productsFilled.set(productIndex, true);
                        if (reactantsA.get(reactantIndex).active) {
                            productsA.set(productIndex, unfilledCoefficient);
                        } else {
                            productsB.set(productIndex, unfilledCoefficient);
                        }
                    } else {
                        // Reactant Side is unfilled
                        if (productsA.get(productIndex).active) {
                            // Use a as variable
                            filledCoefficient = productsA.get(productIndex);
                        } else {
                            // Use b as variable
                            filledCoefficient = productsB.get(productIndex);
                        }
                        unfilledCoefficient = Fraction.multiply(filledCoefficient, new Fraction(productQuantity, reactantQuantity));
                        reactantsFilled.set(reactantIndex, true);
                        if (productsA.get(productIndex).active) {
                            reactantsA.set(reactantIndex, unfilledCoefficient);
                        } else {
                            reactantsB.set(reactantIndex, unfilledCoefficient);
                        }
                    }
                }
            }
        }
        // Remaining elements
        for (String value : elementsUsed) {
            elements.remove(value);
        }

        // Third Step:
        // Complex relationship, all but one filled
        int sizeOfElementsList=elements.size();
        for(int k=0; k<sizeOfElementsList; k++){
            if(!reactantsFilled.contains(false) && !productsFilled.contains(false)){
                break;
            }
            for (String currentElement : elements) {
                Boolean unfilledIndexOnProduct = null;
                Integer unfilledIndex = null;
                LinkedList<Integer> reactantFilledIndexes = new LinkedList<>();
                LinkedList<Integer> productFilledIndexes = new LinkedList<>();
                int totalOccurrences = 0;
                for (int j = 0; j < reactants.size(); j++) {
                    Hashtable<String, Integer> compoundTable = reactants.get(j);
                    if (compoundTable.containsKey(currentElement)) {
                        totalOccurrences++;
                        if (reactantsFilled.get(j))
                            reactantFilledIndexes.addLast(j);
                        else {
                            unfilledIndex = j;
                            unfilledIndexOnProduct = false;
                        }
                    }
                }
                for (int j = 0; j < products.size(); j++) {
                    Hashtable<String, Integer> compoundTable = products.get(j);
                    if (compoundTable.containsKey(currentElement)) {
                        totalOccurrences++;
                        if (productsFilled.get(j))
                            productFilledIndexes.addLast(j);
                        else {
                            unfilledIndex = j;
                            unfilledIndexOnProduct = true;
                        }
                    }
                }
                if (unfilledIndex != null && totalOccurrences == reactantFilledIndexes.size() + productFilledIndexes.size() + 1) {
                    Fraction reactantASum = new Fraction(0, 1);
                    Fraction reactantBSum = new Fraction(0, 1);
                    for (int s : reactantFilledIndexes) {
                        int quantity = reactants.get(s).get(currentElement);
                        reactantASum = Fraction.add(reactantASum, Fraction.multiply(reactantsA.get(s), new Fraction(quantity, 1)));
                        reactantBSum = Fraction.add(reactantBSum, Fraction.multiply(reactantsB.get(s), new Fraction(quantity, 1)));
                    }
                    Fraction productASum = new Fraction(0, 1);
                    Fraction productBSum = new Fraction(0, 1);
                    for (int s : productFilledIndexes) {
                        int quantity = (products.get(s)).get(currentElement);
                        productASum = Fraction.add(productASum, Fraction.multiply(productsA.get(s), new Fraction(quantity, 1)));
                        productBSum = Fraction.add(productBSum, Fraction.multiply(productsB.get(s), new Fraction(quantity, 1)));
                    }
                    if (unfilledIndexOnProduct) {
                        productsFilled.set(unfilledIndex, true);
                        int unfilledQuantity = (products.get(unfilledIndex)).get(currentElement);
                        // Create Ratio
                        Fraction aCoefficient = Fraction.multiply( Fraction.subtract(reactantASum, productASum),new Fraction(1, unfilledQuantity));
                        Fraction bCoefficient = Fraction.multiply( Fraction.subtract(reactantBSum, productBSum),new Fraction(1, unfilledQuantity));
                        productsA.set(unfilledIndex, aCoefficient);
                        productsB.set(unfilledIndex, bCoefficient);
                    } else {
                        reactantsFilled.set(unfilledIndex, true);
                        int unfilledQuantity = (reactants.get(unfilledIndex)).get(currentElement);
                        // Create Ratio
                        Fraction aCoefficient = Fraction.multiply( Fraction.subtract(productASum, reactantASum),new Fraction(1, unfilledQuantity));
                        Fraction bCoefficient = Fraction.multiply( Fraction.subtract(productBSum, reactantBSum),new Fraction(1, unfilledQuantity));
                        reactantsA.set(unfilledIndex, aCoefficient);
                        reactantsB.set(unfilledIndex, bCoefficient);
                    }
                    elements.remove(currentElement);
                    break;
                }
            }
        }

        //Final variable solution
        LinkedList<Integer> reactantCoefficients;
        LinkedList<Integer> productCoefficients;
        if(b_used) {
            String elementToUse = elements.get(0);
            Fraction reactantASum = new Fraction(0, 1);
            for (int i = 0; i < reactants.size(); i++) {
                if (reactants.get(i).containsKey(elementToUse)) {
                    reactantASum = Fraction.add(reactantASum, Fraction.multiply(reactantsA.get(i), new Fraction(reactants.get(i).get(elementToUse), 1)));
                }
            }
            Fraction reactantBSum = new Fraction(0, 1);
            for (int i = 0; i < reactants.size(); i++) {
                if (reactants.get(i).containsKey(elementToUse)) {
                    reactantBSum = Fraction.add(reactantBSum, Fraction.multiply(reactantsB.get(i), new Fraction(reactants.get(i).get(elementToUse), 1)));
                }
            }
            Fraction productASum = new Fraction(0, 1);
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).containsKey(elementToUse)) {
                    productASum = Fraction.add(productASum, Fraction.multiply(productsA.get(i), new Fraction(products.get(i).get(elementToUse), 1)));
                }
            }
            Fraction productBSum = new Fraction(0, 1);
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).containsKey(elementToUse)) {
                    productBSum = Fraction.add(productBSum, Fraction.multiply(productsB.get(i), new Fraction(products.get(i).get(elementToUse), 1)));
                }
            }
            Fraction sumOfA;
            Fraction sumOfB;
            if ((reactantASum.getNumerator() / reactantASum.getDenominator()) >= (productASum.getNumerator() / productASum.getDenominator())) {
                sumOfA = Fraction.subtract(reactantASum, productASum);
                sumOfB = Fraction.subtract(productBSum, reactantBSum);
            } else {
                sumOfA = Fraction.subtract(productASum, reactantASum);
                sumOfB = Fraction.subtract(reactantBSum, productBSum);
            }
            if (sumOfA.getNumerator() < 0 && sumOfB.getNumerator() < 0) {
                sumOfA = Fraction.negate(sumOfA);
                sumOfB = Fraction.negate(sumOfB);
            }
            // Turning sums into smallest whole numbers
            // GCF and fractional restrictions
            int GCF = Fraction.GCD(sumOfA.getNumerator(), sumOfB.getNumerator());
            int LCD = Fraction.LCM(sumOfA.getDenominator(), sumOfB.getDenominator());
            sumOfA = Fraction.multiply(sumOfA, new Fraction(LCD, GCF));
            sumOfB = Fraction.multiply(sumOfB, new Fraction(LCD, GCF));
            // Final solution
            int varA = sumOfB.getNumerator() / sumOfB.getDenominator();
            int varB = sumOfA.getNumerator() / sumOfA.getDenominator();
            // Final check
            int finalLCD = 1;
            for (int i=0; i<reactants.size(); i++)
                finalLCD = Fraction.LCM(finalLCD, Fraction.add(Fraction.multiply(reactantsA.get(i), new Fraction(varA, 1)), Fraction.multiply(reactantsB.get(i), new Fraction(varB,1))).getDenominator());
            for (int i=0; i<products.size(); i++)
                finalLCD = Fraction.LCM(finalLCD, Fraction.add(Fraction.multiply(productsA.get(i), new Fraction(varA, 1)), Fraction.multiply(productsB.get(i), new Fraction(varB,1))).getDenominator());
            varA *= finalLCD;
            varB *= finalLCD;
            // Final substitution
            reactantCoefficients = new LinkedList<>();
            for (int i = 0; i < reactants.size(); i++) {
                Fraction coefficientSumFraction = Fraction.add(Fraction.multiply(reactantsA.get(i), new Fraction(varA, 1)), Fraction.multiply(reactantsB.get(i), new Fraction(varB, 1)));
                reactantCoefficients.addLast(coefficientSumFraction.getNumerator() / coefficientSumFraction.getDenominator());
            }
            productCoefficients = new LinkedList<>();
            for (int i = 0; i < products.size(); i++) {
                Fraction coefficientSumFraction = Fraction.add(Fraction.multiply(productsA.get(i), new Fraction(varA, 1)), Fraction.multiply(productsB.get(i), new Fraction(varB, 1)));
                productCoefficients.addLast(coefficientSumFraction.getNumerator() / coefficientSumFraction.getDenominator());
            }
        } else {
            // Only variable A was used
            int LCD = 1;
            for(Fraction f: reactantsA){
                LCD = Fraction.LCM(LCD, f.getDenominator());
            }
            for(Fraction f: productsA){
                LCD = Fraction.LCM(LCD, f.getDenominator());
            }
            // Scaling
            reactantCoefficients = new LinkedList<>();
            for(Fraction f : reactantsA){
                reactantCoefficients.addLast(Fraction.multiply(f, new Fraction(LCD, 1)).getNumerator());
            }
            productCoefficients = new LinkedList<>();
            for(Fraction f: productsA){
                productCoefficients.addLast(Fraction.multiply(f, new Fraction(LCD, 1)).getNumerator());
            }
        }
        // Final display
        StringBuilder reactantString = new StringBuilder();
        String[] reactantsArray=(preReactantsString.replace(" ","")).split("\\+");
        for (int i=0; i<reactants.size(); i++) {
            if(reactantCoefficients.get(i).equals(1)) {
                reactantString.append(reactantsArray[i]);
            } else {
                reactantString.append(reactantCoefficients.get(i)).append(reactantsArray[i]);
            }
            if(i<reactants.size()-1) {
                reactantString.append(" + ");
            }
        }
        StringBuilder productString= new StringBuilder();
        String[] productsArray=(preProductsString.replace(" ","")).split("\\+");
        for (int i=0; i<products.size(); i++) {
            if(productCoefficients.get(i).equals(1)) {
                productString.append(productsArray[i]);
            } else {
                productString.append(productCoefficients.get(i)).append(productsArray[i]);
            }
            if(i<products.size()-1){
                productString.append(" + ");
            }
        }
        return reactantString + " --> " + productString;
    }

    /**
     * Parses string to get Hashtable representation of a side of a chemical equation.
     *
     * @param inputString String representation of a side of a chemical equation.
     * @return Hashtable representation of a chemical equation side
     */
    private static Hashtable<Integer, Hashtable<String, Integer>> parseString(String inputString){
        Hashtable<Integer, Hashtable<String, Integer>> compoundTable= new Hashtable<>();
        String storeString = "";
        int index = 0;
        for (int j=0; j<inputString.length(); j++) {
            if (Character.toString(inputString.charAt(j)).equals("+")) {
                compoundTable.put(index, parseCompound(storeString));
                storeString="";
                index=index+1;

            }
            else {
                storeString = storeString.concat(Character.toString(inputString.charAt(j)));
            }
        }
        compoundTable.put(index, parseCompound(storeString));
        return compoundTable;
    }

    /**
     * Replaces common polyatomic ions with their own custom element.
     *
     * @param reactantStr String representation of reactant side
     * @param productStr String representation of product side
     * @return Packaged version of new reactant and product strings
     */
    private static String[] polyatomicReplacement(String reactantStr, String productStr){
        reactantStr = reactantStr.replace(" ", "");
        productStr = productStr.replace(" ", "");
        String[] replacementNames = {"A", "D", "E", "G", "J", "L", "M", "Q", "R", "X"};
        String[] commonIons = {"NH4", "C2H3O2", "HCO3","HSO4", "ClO", "ClO3", "ClO2", "OCN", "CN", "H2PO4", "OH", "NO3", "NO2", "ClO4", "MnO4", "SCN", "CO3", "CrO4", "Cr2O7", "HPO4", "SO4", "SO3", "S2O3", "BO3", "PO4"};
        int index=0;
        for(String ion: commonIons){
            if(reactantStr.contains(ion) && productStr.contains(ion)){
                reactantStr = reactantStr.replace(ion, replacementNames[index]);
                productStr = productStr.replace(ion, replacementNames[index]);
                index++;
            }
        }
        return new String[] {reactantStr, productStr};
    }

    /**
     * Parses each compound to get Hashtable of elements and quantities in compound
     *
     * @param inputString String representation of compound
     * @return Hashtable representation of compound
     */
    private static Hashtable<String, Integer> parseCompound(String inputString) {
        Hashtable<String, Integer> dictionary = new Hashtable<>();
        String symbol = "";
        String numString = "";
        StringBuilder paranthesesStoreString = new StringBuilder();
        boolean parenthesesOn = false;
        boolean parenthesesEnd = false;
        String parenthesesScaler = "";
        for (int i=0; i<inputString.length(); i++) {
            char character = inputString.charAt(i);
            if (Character.isLetter(character)) {
                // Checks that this is a letter
                if (String.valueOf(character).toUpperCase().equals(String.valueOf(character))){
                    if(!parenthesesOn && !parenthesesEnd) {
                        // This is uppercase
                        if (!symbol.equals("")) {
                            // Symbol is filled and needs to be dumped
                            if (!dictionary.containsKey(symbol)) {
                                try {
                                    dictionary.put(symbol, Integer.valueOf(numString));
                                } catch (NumberFormatException exception) {
                                    dictionary.put(symbol, 1);
                                }
                            } else {
                                try {
                                    dictionary.put(symbol, Integer.parseInt(numString) + dictionary.get(symbol));
                                } catch (NumberFormatException exception) {
                                    dictionary.put(symbol, 1 + dictionary.get(symbol));
                                }
                            }
                            symbol = "";
                            numString = "";
                        }
                        symbol = symbol.concat(String.valueOf(character));
                    } else if(parenthesesOn && !parenthesesEnd){
                        paranthesesStoreString.append(character);
                    } else {
                        Hashtable<String, Integer> parenthesesParse=parseCompound(paranthesesStoreString.toString());
                        if(parenthesesScaler.equals(""))
                            parenthesesScaler="1";
                        for(String key: parenthesesParse.keySet()){
                            if (!dictionary.containsKey(key)) {
                                dictionary.put(key, parenthesesParse.get(key) * Integer.parseInt(parenthesesScaler));
                            } else {
                                dictionary.put(key, parenthesesParse.get(key) * Integer.parseInt(parenthesesScaler) + dictionary.get(key));
                            }
                        }
                        paranthesesStoreString = new StringBuilder();
                        parenthesesEnd = false;
                        parenthesesScaler = "";
                        symbol = symbol.concat(String.valueOf(character));
                    }
                }
                else {
                    if(!parenthesesOn)
                        symbol = symbol.concat(String.valueOf(character));
                    else
                        paranthesesStoreString.append(character);
                }
            } else if (Character.isDigit(character)) {
                // This is a number
                if(!parenthesesOn && !parenthesesEnd) {
                    numString = numString.concat(String.valueOf(character));
                }else if(parenthesesEnd && !parenthesesOn){
                    parenthesesScaler += character;
                }else if(!parenthesesEnd){
                    paranthesesStoreString.append(character);
                }
            } else if(character=='(') {
                // Start Statement
                if(!parenthesesEnd) {
                    parenthesesOn = true;
                    if (!dictionary.containsKey(symbol)) {
                        try {
                            dictionary.put(symbol, Integer.valueOf(numString));
                        } catch (NumberFormatException exception) {
                            dictionary.put(symbol, 1);
                        }
                    } else {
                        try {
                            dictionary.put(symbol, Integer.parseInt(numString) + dictionary.get(symbol));
                        } catch (NumberFormatException exception) {
                            dictionary.put(symbol, 1 + dictionary.get(symbol));
                        }
                    }
                    symbol = "";
                    numString = "";
                } else {
                    Hashtable<String, Integer> parenthesesParse=parseCompound(paranthesesStoreString.toString());
                    if(parenthesesScaler.equals(""))
                        parenthesesScaler="1";
                    for(String key: parenthesesParse.keySet()) {
                        if (!dictionary.containsKey(key)) {
                            dictionary.put(key, parenthesesParse.get(key)*Integer.parseInt(parenthesesScaler));
                        } else {
                            dictionary.put(key, parenthesesParse.get(key)*Integer.parseInt(parenthesesScaler) + dictionary.get(key));
                        }
                    }
                    paranthesesStoreString = new StringBuilder();
                    parenthesesOn = true;
                    parenthesesEnd = false;
                    parenthesesScaler = "";
                }
            } else if(character==')') {
                // End statement
                parenthesesEnd=true;
                parenthesesOn=false;
            }
        }
        if(!parenthesesEnd) {
            if (numString.equals("")) {
                numString = "1";
            }
            if (!dictionary.containsKey(symbol)) {
                dictionary.put(symbol, Integer.valueOf(numString));
            } else {
                dictionary.put(symbol, Integer.parseInt(numString) + dictionary.get(symbol));
            }
        } else {
            Hashtable<String, Integer> parenthesesParse=parseCompound(paranthesesStoreString.toString());
            if(parenthesesScaler.equals(""))
                parenthesesScaler="1";
            for(String key: parenthesesParse.keySet()){
                if (!dictionary.containsKey(key)) {
                    dictionary.put(key, parenthesesParse.get(key) * Integer.parseInt(parenthesesScaler));
                } else {
                    dictionary.put(key, parenthesesParse.get(key) * Integer.parseInt(parenthesesScaler) + dictionary.get(key));
                }
            }
        }
        return dictionary;
    }

    /**
     * Gets all elements used on a side an equation
     *
     * @param inputString String representation of chemical equation side
     * @return LinkedList of all elements on side
     */
    private static LinkedList<String> getElements(String inputString){
        LinkedList<String> elements=new LinkedList<>();
        String elementString="";
        char character = 0;
        for (int i=0; i<inputString.length(); i++){
            character=inputString.charAt(i);
            if (Character.isLetter(character)){
                if (String.valueOf(character).toUpperCase().equals(String.valueOf(character))){
                    if (!elementString.equals("")){
                        if (!elements.contains(elementString)){
                            elements.add(elementString);
                        }
                        elementString="";
                    }
                }
                elementString = elementString.concat(Character.toString(character));
            }
            else if (Character.toString(character).equals("+")){
                if (!elements.contains(elementString)){
                    elements.add(elementString);
                }
                elementString="";
            }
        }
        if (!Character.toString(character).equals("")){
            if (!elements.contains(elementString)) {
                elements.add(elementString);
            }
        }
        return elements;
    }
}