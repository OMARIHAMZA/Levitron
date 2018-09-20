package toolBox;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Quaternion {
    private double W, X, Y, Z;      // components of a quaternion

    public Quaternion() {
        W = 1.0f;
        X = 0.0f;
        Y = 0.0f;
        Z = 0.0f;
    }

    public void add(Quaternion q) {
        this.W += q.W;
        this.X += q.X;
        this.Y += q.Y;
        this.Z += q.Z;
    }

    // initialized constructor
    public Quaternion(float w, float x, float y, float z) {
        W = w;
        X = x;
        Y = y;
        Z = z;
    }

    // quaternion multiplication
    public void mult(Quaternion q) {
        double w = W * q.W - (X * q.X + Y * q.Y + Z * q.Z);

        double x = W * q.X + q.W * X + Y * q.Z - Z * q.Y;
        double y = W * q.Y + q.W * Y + Z * q.X - X * q.Z;
        double z = W * q.Z + q.W * Z + X * q.Y - Y * q.X;

        W = w;
        X = x;
        Y = y;
        Z = z;

    }

    public void rotateByVector(Vector3f vector) {

        Quaternion q = new Quaternion(0.0f, vector.x, vector.y, vector.z);


        this.mult(q);


    }

    public void addScaledVector(Vector3f vector, float scale) {

        Quaternion q = new Quaternion(0, vector.x * scale, vector.y * scale, vector.z * scale);

        q.mult(this);

        W += q.W * (0.5f);
        X += q.X * (0.5f);
        Y += q.Y * (0.5f);
        Z += q.Z * (0.5f);


    }

    static public Quaternion ConvertToQuaternion(float rotY, float rotz, float rotx) {        //angles in radian

        float c1 = (float) Math.cos(rotY / 2);
        float s1 = (float) Math.sin(rotY / 2);

        float c2 = (float) Math.cos(rotz / 2);
        float s2 = (float) Math.sin(rotz / 2);

        float c3 = (float) Math.cos(rotx / 2);
        float s3 = (float) Math.sin(rotx / 2);

        float c1c2 = c1 * c2;
        float s1s2 = s1 * s2;

        float w = c1c2 * c3 - s1s2 * s3;

        float x = c1c2 * s3 + s1s2 * c3;

        float y = s1 * c2 * c3 + c1 * s2 * s3;


        float z = c1 * s2 * c3 - s1 * c2 * s3;


        return new Quaternion(w, x, y, z);
    }


    static public Matrix4f GetMatrixFromQuatrnion(Quaternion q) {

        float xx = (float) (q.X * q.X);
        float xy = (float) (q.X * q.Y);
        float xz = (float) (q.X * q.Z);
        float xw = (float) (q.X * q.W);


        float yy = (float) (q.Y * q.Y);
        float yz = (float) (q.Y * q.Z);
        float yw = (float) (q.Y * q.W);

        float zz = (float) (q.Z * q.Z);
        float zw = (float) (q.Z * q.W);


        Matrix4f mat = new Matrix4f();
        mat.setIdentity();


        mat.m00 = 1 - 2 * (yy + zz);
        mat.m01 = 2 * (xy - zw);
        mat.m02 = 2 * (xz + yw);


        mat.m10 = 2 * (xy + zw);
        mat.m11 = 1 - 2 * (xx - zz);
        mat.m12 = 2 * (yz - xw);


        mat.m20 = 2 * (xz - yw);
        mat.m21 = 2 * (yz + xw);
        mat.m22 = 1 - 2 * (xx - yy);


        mat.m30 = mat.m31 = mat.m32 = 0;
        mat.m03 = mat.m31 = mat.m32 = 0;
        mat.m33 = 1;


        return mat;

    }

    public static Vector3f getEularAngles(Quaternion q1) {

        Vector3f eular_angles = new Vector3f();

        double sqw = q1.W * q1.W;
        double sqx = q1.X * q1.X;
        double sqy = q1.Y * q1.Y;
        double sqz = q1.Z * q1.Z;

        double test = q1.X * q1.Y + q1.Z * q1.W;

        if (test > 0.499) { // singularity at north pole


            eular_angles.y = 2 * (float) Math.atan2(q1.X, q1.W);
            eular_angles.z = (float) (Math.PI / 2);
            eular_angles.x = 0;
            eular_angles.y = (float) Math.toDegrees(eular_angles.y);
            eular_angles.z = (float) Math.toDegrees(eular_angles.z);
            eular_angles.x = (float) Math.toDegrees(eular_angles.x);

            return eular_angles;
        }
        if (test < -0.499) { // singularity at south pole
            eular_angles.y = -2 * (float) Math.atan2(q1.X, q1.W);
            eular_angles.z = -(float) (Math.PI / 2);
            ;

            eular_angles.x = 0;


            eular_angles.y = (float) Math.toDegrees(eular_angles.y);
            eular_angles.z = (float) Math.toDegrees(eular_angles.z);
            eular_angles.x = (float) Math.toDegrees(eular_angles.x);

            return eular_angles;
        }

        eular_angles.y = (float) Math.atan2(2 * q1.Y * q1.W - 2 * q1.X * q1.Z, 1 - 2 * sqy - 2 * sqz);
        eular_angles.z = (float) Math.asin(2 * test);

        eular_angles.x = (float) Math.atan2(2 * q1.X * q1.W - 2 * q1.Y * q1.Z, 1 - 2 * sqx - 2 * sqz);


        eular_angles.y = (float) Math.toDegrees(eular_angles.y);
        eular_angles.z = (float) Math.toDegrees(eular_angles.z);
        eular_angles.x = (float) Math.toDegrees(eular_angles.x);


        return eular_angles;


    }

    // conjugates the quaternion
    public Quaternion conjugate() {
        X = -X;
        Y = -Y;
        Z = -Z;
        return this;
    }

    // inverts the quaternion
    public Quaternion reciprical() {
        float norme = (float) Math.sqrt(W * W + X * X + Y * Y + Z * Z);
        if (norme == 0.0)
            norme = 1.0f;

        float recip = 1.0f / norme;

        W = W * recip;
        X = -X * recip;
        Y = -Y * recip;
        Z = -Z * recip;

        return this;
    }

    // sets to unit quaternion
    public Quaternion normalize() {
        float norme = (float) Math.sqrt(W * W + X * X + Y * Y + Z * Z);
        if (norme == 0.0) {
            W = 1.0f;
            X = Y = Z = 0.0f;
        } else {
            float recip = 1.0f / norme;

            W *= recip;
            X *= recip;
            Y *= recip;
            Z *= recip;
        }
        return this;
    }

    // Makes quaternion from axis
    public Quaternion fromAxis(float Angle, float x, float y, float z) {
        float omega, s, c;
        int i;

        s = (float) Math.sqrt(x * x + y * y + z * z);

        if (Math.abs(s) > Float.MIN_VALUE) {
            c = 1.0f / s;

            x *= c;
            y *= c;
            z *= c;

            omega = -0.5f * Angle;
            s = (float) Math.sin(omega);

            X = s * x;
            Y = s * y;
            Z = s * z;
            W = (float) Math.cos(omega);
        } else {
            X = Y = 0.0f;
            Z = 0.0f;
            W = 1.0f;
        }
        normalize();
        return this;
    }

    public Quaternion fromAxis(float Angle, Vector3f axis) {
        return this.fromAxis(Angle, axis.x, axis.y, axis.z);
    }

    // Rotates towards other quaternion
	 /* public void slerp(Quaternion a, Quaternion b, float t)
	  {
	    float omega, cosom, sinom, sclp, sclq;
	    int i;


	    cosom = a.X*b.X + a.Y*b.Y + a.Z*b.Z + a.W*b.W;


	    if ((1.0f+cosom) > Float.MIN_VALUE)
	    {
	      if ((1.0f-cosom) > Float.MIN_VALUE)
	      {
	        omega = (float) Math.acos(cosom);
	        sinom = (float) Math.sin(omega);
	        sclp = (float) (Math.sin((1.0f-t)*omega) / sinom);
	        sclq = (float) (Math.sin(t*omega) / sinom);
	      }
	      else
	      {
	        sclp = 1.0f - t;
	        sclq = t;
	      }

	      X = sclp*a.X + sclq*b.X;
	      Y = sclp*a.Y + sclq*b.Y;
	      Z = sclp*a.Z + sclq*b.Z;
	      W = sclp*a.W + sclq*b.W;
	    }
	    else
	    {
	      X =-a.Y;
	      Y = a.X;
	      Z =-a.W;
	      W = a.Z;

	      sclp = (float) Math.sin((1.0f-t) * Math.PI * 0.5);
	      sclq = (float) Math.sin(t * Math.PI * 0.5);

	      X = sclp*a.X + sclq*b.X;
	      Y = sclp*a.Y + sclq*b.Y;
	      Z = sclp*a.Z + sclq*b.Z;
	    }
	  }
*/
    public Quaternion exp() {
        float Mul;
        float Length = (float) Math.sqrt(X * X + Y * Y + Z * Z);

        if (Length > 1.0e-4)
            Mul = (float) (Math.sin(Length) / Length);
        else
            Mul = 1.0f;

        W = (float) Math.cos(Length);

        X *= Mul;
        Y *= Mul;
        Z *= Mul;

        return this;
    }

    public Quaternion log() {
        float Length;

        Length = (float) Math.sqrt(X * X + Y * Y + Z * Z);
        Length = (float) Math.atan(Length / W);

        W = 0.0f;

        X *= Length;
        Y *= Length;
        Z *= Length;

        return this;
    }

};