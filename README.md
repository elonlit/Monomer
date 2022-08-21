<h1 align="center">🧪 Monomer ⚗️</h1>

<p align="center">
A pure Java package for cheminformatics.
</p>

<div align="center">
  <!-- License -->
  <a href="https://github.com/elonlit/Monomer/LICENSE.txt">
    <img src="https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000" />
  </a>
  <a href="https://javadoc.io/doc/io.github.elonlit/monomer">
    <img src="https://javadoc.io/badge2/io.github.elonlit/monomer/javadoc.svg" />
  </a>
  <a href="https://search.maven.org/artifact/io.github.elonlit/monomer/1.7/jar">
    <img src="https://img.shields.io/maven-central/v/io.github.elonlit/monomer" />
  </a>
</div>

<div align="center">
  <h3>
    <a href="https://javadoc.io/doc/io.github.elonlit/monomer/">
      Documentation
    </a>
    <span> | </span>
    <a href="https://github.com/elonlit/monomer/issues">
      Report a Bug
    </a>
    <span> | </span>
    <a href="https://github.com/elonlit/monomer/pulls">
      Submit a Pull Request
</a>
  </h3>
</div>

## Installation

The artifact can be found at Maven Central:

```xml
<dependency>
  <groupId>io.github.elonlit</groupId>
  <artifactId>monomer</artifactId>
  <version>1.7</version>
</dependency>
```

## Features

- [Properties of all Elements](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Core/Element.html)
- [Compounds](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Core/Compound.html)
    * Formula
    * Molar Mass
    * Percentage Composition by Mass
    * Stoichiometric Amounts
- [Empirical Formulae](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Core/EmpiricalFormulaUtil.html)
    * Empirical Formula by Percentage Composition
    * Combustion Analysis of Hydrocarbons
- [Chemical Reactions](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Reactions/package-summary.html)
    * Balancing the Equation
    * Combustion Reactions
    * Stoichiometric Amounts
    * Limiting Reagent
- [Aqueous Solutions](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Solutions/package-summary.html)
    * Solute
    * Molarity (mol/L)
    * Stoichiometric Amounts
    * Dilutions
- [Electrochemistry](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/Electrochemistry/package-summary.html)
    * Galvanic (Voltaic) Cells
        * Anode, Cathode, Cell Potential
    * Electrolysis
- [Quantum Mechanics](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Chem/QuantumMechanics/package-summary.html)
    * Electromagnetic Waves
        * Frequency, Wavelength, Energy per photon
    * Energy in *n*th Hydrogen Orbital
    * Rydberg Equation
    * Nuclear-nuclear Repulsions
    * Vector Fields in Cylindrical and Spherical Coordinates
    * Quaternions
    * Gaussian Functions
- [Monomers](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Monomers/package-summary.html)
    * Base Monomer
    * DNA
    * RNA
    * Residues
- [Polymers](https://javadoc.io/doc/io.github.elonlit/monomer/latest/io/github/monomer/Polymers/package-summary.html)
    * Generic Seq

### Elements

```java
Element carbon = Element("C"); // Declare Element from its symbol
        
System.out.println(carbon); // Carbon{atomicNumber=6.0,elementName='Carbon',elementSymbol='C',atomicMass=12.011,neutrons=6.0,protons=6.0,electrons=6.0,period=2.0,group=14.0,phase='solid',radioactive=false,natural=true,metal=false,nonmetal=true,metalloid=false,type='Nonmetal',atomicRadius='0.91',electronegativity='2.55',firstIonization='11.2603',density=2.27,meltingPoint=3948.15,boilingPoint=4300.0,isotopes=7.0,discoverer='Prehistoric',specificHeat=0.709,shells=2.0,valence=4.0,config='[He] 2s2 2p2',massNumber=12.0}

System.out.println(carbon.atomicMass) // 12.011
```

### Compounds

```java
Compound moronicAcid = new Compound("C30H46O3"); // Moronic acid (3-oxoolean-18-en-28-oic acid) is a natural triterpene

System.out.println(moronicAcid.humanReadableOccurrences); // {Oxygen=3,Hydrogen=46,Carbon=30}

System.out.println(moronicAcid.getMolarMass()); // 454.695
        
System.out.println(moronicAcid.getPercentCompositionByMass(new Element("O"))); // 10.555866
```

### Stoichiometric Conversions

```java
var water = new Compound("H2O"); // Instantiate a water compound

System.out.println(water.formula); // H2O
        
// Accepted inputs: grams, moles, and molecules
System.out.println(water.getAmounts("Molecules", 2e+24)); // {Grams=59.8505,Molecules=2.0E24,Moles=3.3222592}
```

### Empirical Formula Utilities

```java
// Construct a HashMapping of Elements in a Compound to their constituent relative frequencies
HashMap<Element, Float> percentCompositionMap = new HashMap<Element, Float>() {{
    put(new Element("C"), 52.14f);
    put(new Element("O"), 34.73f);
    put(new Element("H"), 13.13f);
}};

// Instantiate a Compound from the resulting empirical formula of a Compound that is composed of 52.14% C, 34.73% O, and 13.13% H by mass
Compound adipicAcid = new Compound(EmpiricalFormulaUtil.getEmpiricalFormulaFromPercentComposition(percentCompositionMap));
System.out.println(adipicAcid); // C2H6O1: {Oxygen=1, Hydrogen=6, Carbon=2}

// A hydrocarbon fuel is fully combusted with 18.214 g of oxygen to yield 23.118 g of carbon dioxide and 4.729 g of water. 
// Find the empirical formula for the hydrocarbon
Compound compound = new Compound(EmpiricalFormulaUtil.getHydrocarbonFromCombustion(23.118f, 4.729f));
System.out.println(compound); // C1H1: {Hydrogen=1, Carbon=1}
```

### Chemical Equations

```java
var photosynthesis = new Reaction("CO2 + H2O --> C6H12O6 + O2"); // Reactions delimited by "-->"

// Automatic balancing
System.out.println(photosynthesis.equation); // 6CO2 + 6H2O --> C6H12O6 + 6O2

System.out.println(EquationBalancerUtil.isBalanced(photosynthesis)); // true

// Get the amounts of ALL compounds in the above reaction given 5 grams of C6H12O6
System.out.println(photosynthesis.getAmounts("C6H12O6", "Grams", 5f)); // [{Grams=7.328481, Molecules=1.0024645E23, Moles=0.16652234}, {Grams=2.9998999, Molecules=1.0024645E23, Moles=0.16652234}, {Grams=5.0, Molecules=1.6707742E22, Moles=0.027753724}, {Grams=5.3283815, Molecules=1.0024645E23, Moles=0.16652234}]

// Find the limiting reagent of the reaction when using 3 moles of the first reactant (CO2) and 1 mole of the second reactant (H2O)
Compound limitingReagent = photosynthesis.getLimitingReagent("Moles", 3, 1);

System.out.println(limitingReagent); // H2O: {Oxygen=1, Hydrogen=2}
```




### Solutions

```java
// Instantiate a Solution with the solute and molarity
Solution solution = new Solution("NaCl", 0.25f);

// Find the dilution of 2.5 L of 0.25M NaCl to a 0.125M NaCl solution:
System.out.println(solution.dilute(2.5f, 0.125f, 0)); // {Volume=5.0, Molarity=0.125, Solute=NaCl}
```

### Electrochemistry

```java
// Make a Galvanic Cell with Lead and Zinc electrodes
GalvanicCell galvanicCell = new GalvanicCell("Pb", "Zn");

System.out.println(galvanicCell); // GalvanicCell{Cell='Zn|Zn2+||Pb2+|Pb', Anode=Zn, Cathode=Pb, CellPotential=0.63}
        
// A HashMapping of arguments to values of either two of "amps", "seconds", or "grams."
HashMap<String, Float> kwargs = new HashMap<>() {{
    put("seconds", 24*60*60f);
    put("grams", 805f);
}};

// Moles of electrons transferred
int mol = 3;

// How much current was used to produce 805 grams of Aluminum metal from Al2O3 in 24 hours?
System.out.println(GalvanicCell.electrolysis("Al", mol, kwargs)); // {Amps=99.95144, Grams=805.0, Seconds=86400.0}
```

### Quantum Mechanics

```java
// Determine the wavelength, frequency, and energy of a wave with energy 2.84e-19 Joules per particle
Wave red = new Wave("Energy", 2.84e-19);
System.out.println(red); // Wave{wavelength=6.99462948008421E-7, frequency=4.286145536363085E14, energy=2.84E-19}

// Determine the energy of an electron in the 3rd orbital of a Hydrogen atom in Joules
System.out.println(Wave.energyOfHydrogenOrbital(3)); // -2.4221748553237596E-19
```

### Monomers & Seq

```java
// Instantiate a Seq of type DNA
Seq<DNA> myCodingStrand = new Seq<>();

// Populate myCodingStrand with random DNA nucleotides
Random random = new Random();
for(int i=0; i<15000; i++) {
    myCodingStrand.elongate(new DNA(String.valueOf(DNA.nucleotidesDNA.values()[new Random().nextInt(DNA.nucleotidesDNA.values().length)])));
}
```

## Citing

If you use [Monomer](https://github.com/elonlit/Monomer) in your scientific work, please consider citing:

|     E. Litman, *Monomer* - An object-oriented cheminformatics library, 2022\-- . Available at:
[https://github.com/elonlit/monomer](https://github.com/elonlit/monomer).

The reference in the [BibLaTeX](https://www.ctan.org/pkg/biblatex?lang=en) format:

```{.sourceCode .latex}
@software{monomer2022,
   author = {Litman, Elon},
   title = {{Monomer} -- An object-oriented cheminformatics library},
   url = {https://github.com/elonlit/Monomer},
   version = {1.7},
   date = {2022--},
}
```
