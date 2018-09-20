package physics;

import GUI.SimulationValues;
import org.lwjgl.util.vector.Vector3f;

public class AirDrag {
    private static float rotationalDragConstant = 1e-4f, linearAirDragConstant = 2e-3f * SimulationValues.linearDrag, airDensity, magnusCoefficient = 2e-3f * SimulationValues.magnusCoefficient;
    public static float rotationalDampingConstant = 1e-2f * SimulationValues.rotationalDamping;

    public static Vector3f rotationalAirDrag(Vector3f angularVelocity) {
        float magnitude = (float) Math.sqrt(angularVelocity.x * angularVelocity.x +
                angularVelocity.y * angularVelocity.y +
                angularVelocity.z * angularVelocity.z);
        Vector3f result =new Vector3f(angularVelocity);
        result.scale(-magnitude*rotationalDampingConstant);
        return result;
    }

    public static Vector3f magnusForce(Vector3f angularVelocity, Vector3f linearVelocity) {
        Vector3f result = new Vector3f();
        Vector3f.cross(angularVelocity, linearVelocity, result);
        result.scale(airDensity * magnusCoefficient*1e-3f);
        return result;
    }

    public static Vector3f linearAirDrag(Vector3f velocity) {
        float magnitude = (float) Math.sqrt(velocity.x * velocity.x +
                velocity.y * velocity.y +
                velocity.z * velocity.z);
        Vector3f result = new Vector3f(velocity);
        result.scale(-magnitude * linearAirDragConstant*1e-3f);
        return result;
    }

}