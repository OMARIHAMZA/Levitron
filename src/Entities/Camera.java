package Entities;

import models.SpinningTopModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position ;
	private Vector3f front;
	private Vector3f up;
	private Vector3f right;

	private Vector3f lookat;

	final float MOUSE_SENSITIVITY =0.05f;

	private float pitch =0.0f,yaw=90.0f ,roll;
	private float speed = 0.1f;
	private boolean isLocked;

	public Camera(Vector3f position, boolean isLocked,Vector3f LookAt) {

//		position = new Vector3f(0,20,-50);
		/*position = new Vector3f(SpinningTopModel.getSpinningTopObjects().get(0).getPosition().x + offsetX,
				SpinningTopModel.getSpinningTopObjects().get(0).getPosition().y + offsetY,
				SpinningTopModel.getSpinningTopObjects().get(0).getPosition().z + offsetZ);*/

		this.position = position;
		this.lookat=LookAt;
		up = new Vector3f(0,1,0);
		front = new Vector3f(position.x - SpinningTopModel.getSpinningTopObjects().get(0).getPosition().x,
				position.y - SpinningTopModel.getSpinningTopObjects().get(0).getPosition().y,
				position.z - SpinningTopModel.getSpinningTopObjects().get(0).getPosition().z);

		this.isLocked = isLocked;
		right = new Vector3f();
		updateCameraVectors();


	}

	private float xIncrement = 0, yIncrement = 0, zIncrement = 0;
	public void updatePosition(){
		this.position.x = SpinningTopModel.getSpinningTopObjects().get(0).getPosition().x - 10;
		this.position.y = SpinningTopModel.getSpinningTopObjects().get(0).getPosition().y - 10;
		this.position.z = SpinningTopModel.getSpinningTopObjects().get(0).getPosition().z - 10;
	}
	public void move()
	{

	    //updateCameraVectors();
       /* if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            pitch += speed;
            updateCameraVectors();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)){
            pitch -= speed;
            updateCameraVectors();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            yaw += speed;
            updateCameraVectors();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            yaw -= speed;
            updateCameraVectors();
        }*/
		if (!isLocked){
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{

				position.z += front.z * speed;

			}else if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{

				position.z-= front.z*speed;
				position.y-= front.y*speed;
				position.x-= front.x*speed;

			}else if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				position.z+= right.z*speed;
				position.y+= right.y*speed;
				position.x+= right.x*speed;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			{
				position.z -= right.z * speed;
				position.y -= right.y * speed;
				position.x -= right.x * speed;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			{
				position.y += speed;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			{
				position.z-= up.z*speed;
				position.y-= up.y*speed;
				position.x-= up.x*speed;

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_K))
			{

				IncreaseRotation(( speed * 0.05f) , 0);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				IncreaseRotation( - ( speed * 0.05f) , 0);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_J))
			{
				IncreaseRotation(0 ,- ( speed * 0.05f));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_U))
			{
				IncreaseRotation(0 , ( speed * 0.05f));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R)){

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)){
				speed+=0.1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)){
				if ((speed - 0.1) > 0)
					speed-=0.1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				System.out.println("( " + position.x + " , " + position.y + " , " + position.z + " )");
			}
		}


		/*if(Keyboard.isKeyDown(Keyboard.KEY_W)){
		if (zIncrement < 8000) {
			position.z += 50;
			zIncrement += 50;
		}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			if (zIncrement >= -8000){
				position.z -= 50;
				zIncrement -= 50;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			if (xIncrement < 8000) {
				position.x += 50;
				xIncrement += 50;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			if (xIncrement >= -8000) {
				position.x -= 50;
				xIncrement -= 50;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			if (yIncrement < 8000) {
				position.y += 50;
				yIncrement += 50;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			if (yIncrement >= -8000) {
				position.y -= 50;
				yIncrement -= 50;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_U)){
			pitch +=0.2;
			updateCameraVectors();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_J)){
			pitch -=0.2;
			updateCameraVectors();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			System.out.println("Yaw: " + yaw + "\nPitch: " + pitch +
							"\nPosition: " + position.x + ',' + position.y + ',' + position.z);
		}*/
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void IncreaseRotation(float x, float y) {

		x*= MOUSE_SENSITIVITY;
		y*= MOUSE_SENSITIVITY;

		pitch+=y;
		yaw+=x;

		updateCameraVectors();
	}

	private void updateCameraVectors() {

		Vector3f front= new Vector3f();
		Vector3f cross1= new Vector3f();
		Vector3f cross2= new Vector3f();


		front.x = (float) (Math.cos(Math.toRadians(yaw))   *  Math.cos(Math.toRadians(pitch)));
		front.y = (float)  Math.sin(Math.toRadians(pitch));
		front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));


		front.normalise(this.front);


		Vector3f.cross(this.front, up, cross1);
		cross1.normalise(right);


		Vector3f.cross(right, this.front, cross2);
		cross2.normalise(up);



	}

	public Vector3f getFront() {
		return front;
	}

	public Vector3f getUp() {
		return up;
	}

	public Vector3f getRight() {
		return right;
	}

	public Vector3f getLookat() {
		return lookat;
	}
}
