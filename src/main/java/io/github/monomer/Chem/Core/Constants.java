package io.github.monomer.Chem.Core;

/**
 * Store common chemistry constants using an enum. Yes, I know this is an anti-pattern...
 *
 * @author Elon Litman
 * @version 1.7
 * @see Element
 * @see Compound
 * @see io.github.monomer.Chem.QuantumMechanics
 */
public enum Constants {

    // Relates the number of constituent particles in a sample to the amount of substance in that sample
    AVOGADRO_CONSTANT(6.02e+23f),
    // The speed of light
    C(2.998e+8f),
    // Planck’s constant
    PLANCK_CONSTANT(6.626e-34f),
    // Rydberg's constant
    RYDBERG_CONSTANT(1.0974e+7f),
    // Ion-product constant of water
    Kw(1.0e-14f),
    // Faraday's constant in coulombs
    F(96485),
    ;

    private final float value;

    Constants(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public static final String symbolsMap =
            """
                    H Hydrogen
                    He Helium
                    Li Lithium
                    Be Beryllium
                    B Boron
                    C Carbon
                    N Nitrogen
                    O Oxygen
                    F Fluorine
                    Ne Neon
                    Na Sodium
                    Mg Magnesium
                    Al Aluminum
                    Si Silicon
                    P Phosphorus
                    S Sulfur
                    Cl Chlorine
                    Ar Argon
                    K Potassium
                    Ca Calcium
                    Sc Scandium
                    Ti Titanium
                    V Vanadium
                    Cr Chromium
                    Mn Manganese
                    Fe Iron
                    Co Cobalt
                    Ni Nickel
                    Cu Copper
                    Zn Zinc
                    Ga Gallium
                    Ge Germanium
                    As Arsenic
                    Se Selenium
                    Br Bromine
                    Kr Krypton
                    Rb Rubidium
                    Sr Strontium
                    Y Yttrium
                    Zr Zirconium
                    Nb Niobium
                    Mo Molybdenum
                    Tc Technetium
                    Ru Ruthenium
                    Rh Rhodium
                    Pd Palladium
                    Ag Silver
                    Cd Cadmium
                    In Indium
                    Sn Tin
                    Sb Antimony
                    Te Tellurium
                    I Iodine
                    Xe Xenon
                    Cs Cesium
                    Ba Barium
                    La Lanthanum
                    Ce Cerium
                    Pr Praseodymium
                    Nd Neodymium
                    Pm Promethium
                    Sm Samarium
                    Eu Europium
                    Gd Gadolinium
                    Tb Terbium
                    Dy Dysprosium
                    Ho Holmium
                    Er Erbium
                    Tm Thulium
                    Yb Ytterbium
                    Lu Lutetium
                    Hf Hafnium
                    Ta Tantalum
                    W Tungsten
                    Re Rhenium
                    Os Osmium
                    Ir Iridium
                    Pt Platinum
                    Au Gold
                    Hg Mercury
                    Tl Thallium
                    Pb Lead
                    Bi Bismuth
                    Po Polonium
                    At Astatine
                    Rn Radon
                    Fr Francium
                    Ra Radium
                    Ac Actinium
                    Th Thorium
                    Pa Protactinium
                    U Uranium
                    Np Neptunium
                    Pu Plutonium
                    Am Americium
                    Cm Curium
                    Bk Berkelium
                    Cf Californium
                    Es Einsteinium
                    Fm Fermium
                    Md Mendelevium
                    No Nobelium
                    Lr Lawrencium
                    Rf Rutherfordium
                    Db Dubnium
                    Sg Seaborgium
                    Bh Bohrium
                    Hs Hassium
                    Mt Meitnerium
                    Ds Darmstadtium
                    Rg Roentgenium
                    Cn Copernicium
                    Uut Ununtrium
                    Fl Flerovium
                    Uup Ununpentium
                    Lv Livermorium
                    Uus Ununseptium
                    Uuo Ununoctium""";
}