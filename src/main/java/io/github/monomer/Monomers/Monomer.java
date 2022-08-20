package io.github.monomer.Monomers;

import io.github.monomer.Polymers.Seq;

/**
 * A generic class for representing monomers to be used in Seq objects.
 *
 * @author Elon Litman
 * @see Seq
 */
public sealed class Monomer permits DNA, RNA, Residue {

    public String state = null;

    public float molecularWeight = 0f;

    // Constructors
    public Monomer() {
        ;
    }

    public Monomer(String monomer) {
        this.state = toUpperCase(monomer);
    }

    public Monomer(Character monomer) {
        this.state = toUpperCase(Character.toString(monomer));
    }

    public Monomer(String monomer, float molecularWeight) {
        this.state = toUpperCase(monomer);
        this.molecularWeight = molecularWeight;
    }

    @Override
    public String toString() {
        return this.state;
    }

    public static String toUpperCase(String source) {
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }
}