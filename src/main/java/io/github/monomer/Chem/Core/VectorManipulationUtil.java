package io.github.monomer.Chem.Core;

import java.util.*;

/**
 * A utility class for vector manipulation and other miscellaneous math.
 *
 * @author Elon Litman
 * @version 1.7
 * @see io.github.monomer.Chem.QuantumMechanics
 */
public final class VectorManipulationUtil {

    public static float[] addVectors(float[] r1, float[] r2) {
        return new float[]{r1[0] + r2[0], r1[1] + r2[1], r1[2] + r2[2]};
    }

    public static float[] subtractVectors(float[] r1, float[] r2) {
        return new float[]{r1[0] - r2[0], r1[1] - r2[1], r1[2] - r2[2]};
    }

    public static float[] multiplyVectors(float[] r1, float scalar) {
        return new float[]{r1[0] * scalar, r1[1] * scalar, r1[2] * scalar};
    }

    public static float[] divideVectors(float[] r1, float scalar) {
        return new float[]{r1[0] / scalar, r1[1] / scalar, r1[2] / scalar};
    }

    public static float coordinateDistance(float[] r1, float[] r2) {
        return (float) Math.sqrt(Math.pow(r1[0] - r2[0], 2) + Math.pow(r1[1] - r2[1], 2) + Math.pow(r1[2] - r2[2], 2));
    }

    public static float dotProduct(float[] r1, float[] r2) {
        float i = r1[0] * r2[0];
        float j = r1[1] * r2[1];
        float k = r1[2] * r2[2];
        return i + j + k;
    }

    public static float[] crossProduct(float[] r1, float[] r2) {
        float i = r1[1] * r2[2] - r1[2] * r2[1];
        float j = r1[2] * r2[0] - r1[0] * r2[2];
        float k = r1[0] * r2[1] - r1[1] * r2[0];
        return new float[]{i, j, k};
    }

    public static float rho(float[] r) {
        return (float) Math.sqrt(Math.pow(r[0], 2) + Math.pow(r[1], 2) + Math.pow(r[2], 2));
    }

    public static float[] normalize(float[] r) {
        float magnitude = rho(r);
        float rX = r[0] / magnitude;
        float rY = r[1] / magnitude;
        float rZ = r[2] / magnitude;

        return new float[]{rX, rY, rZ};
    }

    public static float[] createQuaternion(float[] axis, float angle) {
        axis = normalize(axis);
        float a = (float) Math.cos((double)angle / 2);
        float b = (float) (axis[0] * Math.sin(angle / 2));
        float c = (float) (axis[1] * Math.sin(angle / 2));
        float d = (float) (axis[2] * Math.sin(angle / 2));

        return new float[]{a, b, c, d};
    }

    public static float[] getQuaternionConjugate(float[] q) {
        return new float[]{q[0], -q[1], -q[2], -q[3]};
    }

    public static float[] multiplyQuaternions(float[] q1, float[] q2) {
        float a1 = q1[0];
        float b1 = q1[1];
        float c1 = q1[2];
        float d1 = q1[3];

        float a2 = q2[0];
        float b2 = q2[1];
        float c2 = q2[2];
        float d2 = q2[3];

        float a = (a1 * a2) - (b1 * b2) - (c1 * c2) - (d1 * d2);
        float b = (a1 * b2) + (b1 * a2) + (c1 * d2) - (d1 * c2);
        float c = (a1 * c2) - (b1 * d2) + (c1 * a2) + (d1 * b2);
        float d = (a1 * d2) + (b1 * c2) - (c1 * b2) + (d1 * a2);

        return new float[]{a, b, c, d};
    }

    public static float[] rotateQuaternion(float[] q, float[] point) {
        point = new float[]{0f, point[0], point[1], point[2]};
        point = multiplyQuaternions(multiplyQuaternions(q, point), getQuaternionConjugate(q));
        return new float[]{point[1], point[2], point[3]};
    }

    public static float theta(float[] r) {
        float rhoI = rho(r);

        if(rhoI <= 1e-3f) {
            return 0f;
        } else {
            return (float) Math.acos(r[2] / rhoI);
        }
    }

    public static float phi(float[] r) {
        if(Math.abs(r[1]) <= 1e-3f) {
            return 0f;
        } else {
            return (float) Math.atan2(r[1], r[0]);
        }
    }

    public static float[] gaussianProductCoordinate(float a, float[] r1, float b, float[] r2) {
        float i = ((a * r1[0] + b * r2[0]) / (a + b));
        float j = ((a * r1[1] + b * r2[1]) / (a + b));
        float k = ((a * r1[2] + b * r2[2]) / (a + b));

        return new float[]{i, j, k};
    }

    public static float[] cartesianToSpherical(float[] r) {
        return new float[]{rho(r), theta(r), phi(r)};
    }

    public static float doubleFactorial(float n) {
        if (n == 0 || n==1) {
            return 1;
        }
        return n * doubleFactorial(n - 2);
    }

    public static <T> List<Collection<T>> product(Collection<T> a, int r) {
        List<Collection<T>> result = Collections.nCopies(1, Collections.emptyList());
        for (Collection<T> pool : Collections.nCopies(r, new LinkedHashSet<>(a))) {
            List<Collection<T>> temp = new ArrayList<>();
            for (Collection<T> x : result) {
                for (T y : pool) {
                    Collection<T> z = new ArrayList<>(x);
                    z.add(y);
                    temp.add(z);
                }
            }
            result = temp;
        }
        return result;
    }
}
