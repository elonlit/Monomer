package io.github.monomer.Chem.Reactions;

import io.github.monomer.Chem.Core.Compound;

import java.io.IOException;

/**
 * A class for instantiating a Combustion Reaction.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Reaction
 */
public non-sealed class Combustion extends Reaction {

    public Combustion(Compound compound) throws IOException {
        super(new Compound[]{compound, new Compound("O2")}, new Compound[]{new Compound("H2O"), new Compound("CO2")});
    }

    public Combustion(String compound) throws IOException {
        super(compound + " + " + "O2" + " --> " + "H2O" + " + " + "CO2");
    }
}
