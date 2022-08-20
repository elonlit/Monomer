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
>>>

>>>Element carbon=Element("C"); // Declare Element from its symbol
        
        >>>System.out.println(carbon);
        Carbon{atomicNumber=6.0,elementName='Carbon',elementSymbol='C',atomicMass=12.011,neutrons=6.0,protons=6.0,electrons=6.0,period=2.0,group=14.0,phase='solid',radioactive=false,natural=true,metal=false,nonmetal=true,metalloid=false,type='Nonmetal',atomicRadius='0.91',electronegativity='2.55',firstIonization='11.2603',density=2.27,meltingPoint=3948.15,boilingPoint=4300.0,isotopes=7.0,discoverer='Prehistoric',specificHeat=0.709,shells=2.0,valence=4.0,config='[He] 2s2 2p2',massNumber=12.0}

        >>>System.out.println(carbon.atomicMass)
        12.011
```

### Compounds

```java
>>>

>>>Compound moronicAcid=new Compound("C30H46O3"); // Moronic acid (3-oxoolean-18-en-28-oic acid) is a natural triterpene

        >>>System.out.println(moronicAcid.humanReadableOccurrences);
        {Oxygen=3,Hydrogen=46,Carbon=30}

        >>>System.out.println(moronicAcid.getMolarMass());
        454.695

        >>>System.out.println(moronicAcid.getPercentCompositionByMass(new Element("O"))); // Get percentage composition by mass of a constituent element of choice
        10.555866
```

### Stoichiometric Conversions

```java
>>>

>>>var water=new Compound('H2O');

        >>>System.out.println(water.formula);
        H2O

        >>>System.out.println(water.getAmounts("Molecules",2e+24)); // Accepted inputs: grams, moles, and molecules
        {Grams=59.8505,Molecules=2.0E24,Moles=3.3222592}
```

## Citing

If you use [Monomer]() in your scientific work, please consider citing:

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
