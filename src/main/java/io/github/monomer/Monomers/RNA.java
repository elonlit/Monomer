package io.github.monomer.Monomers;

import java.util.HashMap;

/**
 * A subclass of the Monomer class with functionality for manipulating ribonucleic acid (RNA) nucleotides.
 *
 * @author Elon Litman
 * @see Monomer
 * @see Residue
 * @see DNA
 */
public non-sealed class RNA extends Monomer {

    public enum nucleotidesRNA {
        A,
        U,
        G,
        C
    }

    public static HashMap<String, String> complementaryRNA = new HashMap<String, String>() {{
        put("A", "U");
        put("U", "A");
        put("G", "C");
        put("C", "G");
    }};

    public RNA(String monomer) {
        super(monomer);
    }

    public RNA(Character monomer) {
        super(monomer);
    }
}
