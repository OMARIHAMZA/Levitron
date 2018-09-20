package physics;

import GUI.SimulationValues;
import org.lwjgl.util.vector.Vector3f;

public class MagneticField {
    public static final float A = SimulationValues.magneticBaseForce, a = SimulationValues.baseRadius, y0 = a / (float) Math.sqrt(2);

    public static Vector3f getMagneticField(float x, float y, float z) {
        Vector3f magneticField = new Vector3f();
        magneticField.y = (float) (getB0(y) + getB1(y) * (y - y0) + getB2(y) * Math.pow((y - y0), 2) - 0.5 * getB2(y) * (x * x + z * z));
        magneticField.x = (float) (-0.5 * getB1(y) * Math.abs(x) - getB2(y) * Math.abs(x) * (y - y0));
        magneticField.z = (float) (-0.5 * getB1(y) * Math.abs(z )- getB2(y) * Math.abs(z) * (y - y0));
        magneticField.x*=150;
        magneticField.z*=150;

        return magneticField;
    }

    public static Vector3f getMagneticForce(Vector3f position, Vector3f dipoleMoment) {
        Vector3f magneticForce = new Vector3f();
        double termYMuX = -2 * getB2(position.y) - getB3(position.y) * (position.y - y0);
        double termYMuZ = termYMuX * dipoleMoment.z * Math.abs(position.z);
        termYMuX *= dipoleMoment.x * Math.abs(position.x);
        double termYMuY = 2 * getB1(position.y) + 4 * getB2(position.y) * (position.y - y0) + getB3(position.y) * (position.y - y0) * (position.y - y0)
                - 0.5 * getB3(position.y) * (Math.abs(position.x) * Math.abs(position.x) + Math.abs(position.z) * Math.abs(position.z));
        termYMuY *= dipoleMoment.y;
        magneticForce.y = (float) (termYMuX + termYMuY + termYMuZ);
        double termXZ1 = -0.5 * getB1(position.y) - getB2(position.y) * (position.y - y0);
        double termXZ2 = - dipoleMoment.y * getB2(position.y);
        magneticForce.x = (float)(termXZ1 * dipoleMoment.x + termXZ2 * Math.abs(position.x));
        magneticForce.z = (float)(termXZ1 * dipoleMoment.z + termXZ2 * Math.abs(position.z));
        magneticForce.x*=.5;magneticForce.z*=.5;
        return magneticForce;
    }

    private static double getB0(float y0) {
        return A * (a * a * a / Math.pow((a * a + y0 * y0), 1.5));
    }

    private static double getB1(float y0) {
        return 3 * A * (a * a * a * y0 / Math.pow((a * a + y0 * y0), 2.5));
    }

    private static double getB2(float y0) {
        return (3 / 2 * A * a * a * a) * (4 * y0 * y0 - a * a) / Math.pow((a * a + y0 * y0), 3.5);
    }

    private static double getB3(float y0) {
        double numerator = 1.5 * A * a * a * a * (8 * y0 * (a * a + y0 * y0) - 7 * (4 * y0 * y0 * y0 - a * a * y0));
        double denominator = Math.pow(a * a + y0 * y0, 4.5);
        return numerator / denominator;
    }
}
