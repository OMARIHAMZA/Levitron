package physics;

import org.lwjgl.util.vector.Vector3f;

public class MagneticField_OLD {


    //TODO: DELETE THIS CLASS

    private static final float A = 1.9608e-1f, b = 67.6864f, a = 25f, z0 = a / (float) Math.sqrt(2);


    public static Vector3f getMagneticField(float x, float y, float z) {
        Vector3f B = new Vector3f();
        B.x = -0.5f * getB0Prime(y) * x;
        B.z = -0.5f * getB0Prime(y) * z;
        B.y = getB0(y) - ((x * x + z * z) * .25f) * getB0Prime2(y);
        return B;
    }

    private static float getB0(float z) {
        return (float) (A * (b * b / Math.pow((b * b + (z - z0) * (z - z0)), 1.5)
                - a * a / Math.pow(a * a + (z - z0) * (z - z0), 1.5)));
    }

    public static float getB0Prime(float z) {
        return (float) (3 * A * (((a * a * (z - z0)) / Math.pow((a * a + (z - z0) * (z - z0)), 2.5)) - ((b * b * (z - z0)) / Math.pow((b * b + (z - z0) * (z - z0)), 2.5))));
    }

    public static float getB0Prime2(float z) {
        return (float) (3 * A * (((a * a) / Math.pow((a * a + (z - z0) * (z - z0)), 2.5))
                - ((b * b) / Math.pow((b * b + (z - z0) * (z - z0)), 2.5))
                + ((5 * b * b * (z - z0) * (z - z0)) / Math.pow((b * b + (z - z0) * (z - z0)), 3.5))
                - (
                (5 * a * a * (z - z0) * (z - z0)) / Math.pow((a * a + (z - z0) * (z - z0)), 3.5)
        )));
    }

    public static float getB0Prime3(float z) {
        double term1, term2, term3, term4;
        term1 = (15 * b * b * (z - z0)) / (Math.pow((b * b + (z - z0) * (z - z0)), 3.5));
        term2 = -1 * (15 * a * a * (z - z0)) / (Math.pow((a * a + (z - z0) * (z - z0)), 3.5));
        term3 = (35 * a * a * (z - z0) * (z - z0) * (z - z0)) / Math.pow(a * a + (z - z0) * (z - z0), 4.5);
        term4 = -1 * (35 * b * b * (z - z0) * (z - z0) * (z - z0)) / Math.pow(b * b + (z - z0) * (z - z0), 4.5);
        return (float) (3 * A * (term1 + term2 + term3 + term4));
    }

}
