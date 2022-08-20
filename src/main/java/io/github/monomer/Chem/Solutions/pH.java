package io.github.monomer.Chem.Solutions;

import java.util.Objects;

/**
 * A utility class that calculates from any inputted pH, pOH, [H+], or [OH-] all of the corresponding values.
 *
 * @author Elon Litman
 * @version 1.7
 * @see Solution
 */
public final class pH {

    public float pH;

    public float pOH;

    public float H;

    public float OH;

    public pH(String arg, float value) {
        arg = arg.replaceAll("\\[", "").replaceAll("\\]", "");
        switch (arg) {
            case "pH" -> {
                this.pH = value;
                this.pOH = 14 - pH;
                this.H = (float) Math.pow(10, -1 * pH);
                this.OH = (float) Math.pow(10, -1 * pOH);
            }
            case "pOH" -> {
                this.pH = 14 - value;
                this.pOH = value;
                this.H = (float) Math.pow(10, -1 * pH);
                this.OH = (float) Math.pow(10, -1 * value);
            }
            case "H+" -> {
                this.pH = (float) (-1 * Math.log10(value));
                this.pOH = 14 - pH;
                this.H = value;
                this.OH = (float) Math.pow(10, -1 * pOH);
            }
            case "OH-" -> {
                this.pOH = (float) (-1 * Math.log10(value));
                this.pH = 14 - pOH;
                this.H = (float) Math.pow(10, -1 * pH);
                this.OH = value;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        pH pH1 = (pH) o;
        return Float.compare(pH1.pH, pH) == 0 && Float.compare(pH1.pOH, pOH) == 0 && Float.compare(pH1.H, H) == 0 && Float.compare(pH1.OH, OH) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pH, pOH, H, OH);
    }

    @Override
    public String toString() {
        return "pH{" +
                "pH=" + pH +
                ", pOH=" + pOH +
                ", H=" + H +
                ", OH=" + OH +
                '}';
    }
}
