package io.github.monomer.Monomers;

/**
 * A subclass of the Monomer class with functionality for manipulating amino acids.
 *
 * @author Elon Litman
 * @see Monomer
 * @see Residue
 * @see DNA
 */
public non-sealed class Residue extends Monomer {

    public enum residues {
        Ala,
        Arg,
        Asn,
        Asp,
        Cys,
        Gln,
        Glu,
        Gly,
        His,
        Ile,
        Leu,
        Lys,
        Met,
        Phe,
        Pro,
        Ser,
        Thr,
        Trp,
        Tyr,
        Val
    }

    private void checkMonomerValidity() {
        ;
    }

    public Residue(String monomer) {
        super(monomer);
    }

    public Residue(Character monomer) {
        super(monomer);
    }
}
