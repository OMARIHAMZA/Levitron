package Entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private Vector3f Position;
	private Vector3f Color;
	
	
	public Light(Vector3f pos,Vector3f color)
	{
		this.Position=pos;
		this.Color=color;
	}
	


	public void setPosition(Vector3f position) {
		Position = position;
	}


	public void setColor(Vector3f color) {
		Color = color;
	}


	public Vector3f getPosition() {
		return Position;
	}


	public Vector3f getColor() {
		return Color;
	}
	
	
	

}
