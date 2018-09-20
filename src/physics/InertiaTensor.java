package physics;


import org.lwjgl.util.vector.Matrix3f;


public class InertiaTensor {

    private Matrix3f components = new Matrix3f();
    private float i1, i2, i3;

    public InertiaTensor(float mass, float radius, float length) {
        calculateComponents(mass, radius, length);
        components.setIdentity();
        components.m00 = i1;
        components.m11 = i3;
        components.m22 = i2;
    }

    public Matrix3f getComponents() {
        return components;
    }

    private void calculateComponents(float mass, float radius, float length) {
        i1 = i2 = (3.0f / 20.0f) * mass * (radius * radius + (length * length * 4.0f));
        i3 = (3.0f / 10.0f) * mass * radius * radius;
    }
}
