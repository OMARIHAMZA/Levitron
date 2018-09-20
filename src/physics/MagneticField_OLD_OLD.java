package physics;

import org.lwjgl.util.vector.Vector3f;

public class MagneticField_OLD_OLD {


    //TODO: DELETE THIS CLASS

    public static final float A = 1.9608f, a = 250f, y0 = a / (float) Math.sqrt(2);



    public static Vector3f getMagneticField(float x, float y, float z) {

        Vector3f B = new Vector3f();

        B.y = (float) (getB0(y) + getB1(y) * (y - y0) + getB2(y) * Math.pow((y - y0), 2) - 0.5 * getB2(y) * (x * x + z * z));
        B.x = (float) (-0.5 * getB1(y) * x - getB2(y) * x * (y - y0));
        B.z = (float) (-0.5 * getB1(y) * z - getB2(y) * z * (y - y0));

        return B;


    }

    public static Vector3f getMagneticForce(Vector3f position, Vector3f dipoleMoment) {

        Vector3f magnForce = new Vector3f();

        magnForce.y = (float) (-dipoleMoment.x * getB2(position.y) * position.x - dipoleMoment.z * getB2(position.y) * position.z

                + dipoleMoment.y * (getB1(position.y) + 2 * getB2(position.y) * (position.y - y0)));


        magnForce.x = (float) (dipoleMoment.x * (-0.5 * getB1(position.y) - getB2(position.y) * (position.y - y0))
                - dipoleMoment.y * getB2(position.y) * position.x);

        magnForce.z = (float) (dipoleMoment.z * (-0.5 * getB1(position.y) - getB2(position.y) * (position.y - y0))
                - dipoleMoment.y * getB2(position.y) * position.z);


        return magnForce;

    }


    public static double getB0(float z0) {
        return A * (a * a * a / Math.pow((a * a + z0 * z0), 1.5));
    }

    public static double getB1(float z0) {
        return 3 * A * (a * a * a * z0 / Math.pow((a * a + z0 * z0), 2.5));
    }

    public static double getB2(float z0) {
        return (3 / 2 * A * a * a * a) * (4 * z0 * z0 - a * a) / Math.pow((a * a + z0 * z0), 3.5);
    }

}
