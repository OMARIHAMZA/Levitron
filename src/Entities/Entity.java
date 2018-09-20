package Entities;


import GUI.SimulationValues;
import physics.AirDrag;
import physics.MagneticField;
import physics.InertiaTensor;
import physics.MagneticField_OLD;
import org.lwjgl.util.vector.*;

import models.TexturedModel;
import toolBox.Quaternion;

import static physics.AirDrag.rotationalDampingConstant;

public class Entity {

    private TexturedModel model;
    private Vector3f position;
    private float distanceScaling = 1f;
    private float mass = SimulationValues.spinningTopMass;
    private float mu = SimulationValues.dipoleValue;
    private Matrix4f transformation = Matrix4f.setIdentity(new Matrix4f());
    private Quaternion orientation = new Quaternion(0.9987503f, 0, 0, 0.0499792f);
    private Vector3f angularAcceleration = new Vector3f(0, 0, 0);
    private Vector3f angularVelocity = new Vector3f(0, SimulationValues.angularVelocity, 0);
    private float rotx, roty, rotz;
    private float Scale;
    private Vector3f velocity = new Vector3f(0, 0, 0);
    private Vector3f acceleration = new Vector3f(0, -9.8f, 0);
    private Vector3f magneticMoment = new Vector3f();
    private InertiaTensor inertiaTensor = new InertiaTensor(mass, 15f * SimulationValues.spinningTopRadius, 30f * SimulationValues.spinningTopLength);
    private Matrix3f inertiaTensorInverse = new Matrix3f();

    {
        Matrix3f.invert(inertiaTensor.getComponents(), inertiaTensorInverse);
    }

    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }

    public Entity(TexturedModel model, Vector3f position, float rotx, float roty, float rotz, float scale) {
        this.model = model;
        this.position = position;
        this.rotx = rotx;
        this.roty = roty;
        this.rotz = rotz;
        Scale = scale;
    }

    void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float rx, float ry, float rz) {
        this.rotx += rx;
        this.roty += ry;
        this.rotz += rz;
    }

    boolean in = false;

    public float getDistanceScaling() {
        return distanceScaling;
    }

    public float getMass() {
        return mass;
    }

    public float getMu() {
        return mu;
    }

    public Matrix4f getTransformation() {
        return transformation;
    }

    public Vector3f getAngularAcceleration() {
        return angularAcceleration;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getMagneticMoment() {
        return magneticMoment;
    }

    public InertiaTensor getInertiaTensor() {
        return inertiaTensor;
    }

    public Matrix3f getInertiaTensorInverse() {
        return inertiaTensorInverse;
    }

    public boolean isIn() {
        return in;
    }

    public void update(double deltaTime) {
        Vector4f dipoleMoment4 = new Vector4f(0, 1, 0, 0);
        Matrix4f.transform(transformation, dipoleMoment4, dipoleMoment4);
        Vector3f dipoleMoment3 = new Vector3f(dipoleMoment4.x, dipoleMoment4.y, dipoleMoment4.z);
        dipoleMoment3.scale(mu);
        Vector3f magneticForce = MagneticField.getMagneticForce(position, dipoleMoment3);
        Vector3f magnusForce = AirDrag.magnusForce(angularVelocity, velocity);
        Vector3f airDragForce = AirDrag.linearAirDrag(velocity);
        acceleration.x = (magneticForce.x / mass) * 0.35f + (magnusForce.x / mass) + (airDragForce.x / mass);
        acceleration.y = ((magneticForce.y / mass) - 9.8f) + (magnusForce.y / mass) + (airDragForce.y / mass);
        acceleration.z = (magneticForce.z / mass) * 0.35f + (magnusForce.z / mass) + (airDragForce.z / mass);
        this.velocity.x += acceleration.x * deltaTime;
        this.velocity.y += acceleration.y * deltaTime;
        this.velocity.z += acceleration.z * deltaTime;
        this.position.x += velocity.x * deltaTime * distanceScaling;
        this.position.y += velocity.y * deltaTime * distanceScaling;
        this.position.z += velocity.z * deltaTime * distanceScaling;
        angularVelocity.y -= rotationalDampingConstant * Math.signum(angularVelocity.y) * 6;
        Vector3f.cross(dipoleMoment3,
                MagneticField.getMagneticField(position.x, position.y, position.z), magneticMoment);
        Vector3f magneticAngularAcceleration = new Vector3f();
        Matrix3f.transform(inertiaTensorInverse, magneticMoment, magneticAngularAcceleration);
        angularAcceleration.y = magneticAngularAcceleration.y;
        angularAcceleration.z = magneticAngularAcceleration.z;
        angularAcceleration.x = magneticAngularAcceleration.x;
        this.angularVelocity.x += angularAcceleration.x * deltaTime;
        this.angularVelocity.y += angularAcceleration.y * deltaTime;
        this.angularVelocity.z += angularAcceleration.z * deltaTime;
        this.orientation.addScaledVector(angularVelocity, (float) deltaTime);
    }


    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotx() {
        return rotx;
    }

    public void setRotx(float rotx) {
        this.rotx = rotx;
    }

    public float getRoty() {
        return roty;
    }

    public void setRoty(float roty) {
        this.roty = roty;
    }

    public float getRotz() {
        return rotz;
    }

    public void setRotz(float rotz) {
        this.rotz = rotz;
    }

    public float getScale() {
        return Scale;
    }

    public void setScale(float scale) {
        Scale = scale;
    }

    public void setTransformationMatrix(Matrix4f matrix) {
        transformation = matrix;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }
}
