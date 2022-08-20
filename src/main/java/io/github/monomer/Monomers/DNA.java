package io.github.monomer.Monomers;

import java.util.HashMap;

/**
 * A subclass of the Monomer class with functionality for manipulating deoxyribonucleic acid (DNA) nucleotides.
 *
 * @author Elon Litman
 * @see Monomer
 * @see Residue
 * @see RNA
 */
public non-sealed class DNA extends Monomer {

    public enum nucleotidesDNA {
        A,
        T,
        G,
        C
    }

    /**
     * A static HashMap mapping of complementary DNA nucleotides.
     *
     * @see RNA#complementaryRNA
     */
    public static HashMap<String, String> complementaryDNA = new HashMap<>() {{
        put("A", "T");
        put("T", "A");
        put("G", "C");
        put("C", "G");
    }};

    public DNA(String monomer) {
        super(monomer);
    }

    public DNA(Character monomer) {
        super(monomer);
    }
}
