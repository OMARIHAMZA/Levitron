package Shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import textures.ModelTexture;
import toolBox.Maths;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE="src/Shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE="src/Shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_LightColor;
	private int location_LightPos;
	private int loction_reflectivity;
	private int location_shineDamper;
	
	
	
	
	


	

	public StaticShader() {
		super(VERTEX_FILE,FRAGMENT_FILE);
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texture_coords");
		super.bindAttribute(2, "normal");
		
		
	}

	@Override
	protected void getAllUniformsLocation() {
		location_transformationMatrix=super.getUniformLocation("transformationMatrix");
		location_projectionMatrix=super.getUniformLocation("projectionMatrix");
		location_viewMatrix= super.getUniformLocation("viewMatrix");
		location_LightColor=super.getUniformLocation("Light_color");
		location_LightPos=super.getUniformLocation("Light_position");
		loction_reflectivity=super.getUniformLocation("reflectivity");
		location_shineDamper=super.getUniformLocation("shineDamper");

		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		
	
		
		
	
		super.loadUpMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadUpMatrix(location_projectionMatrix, matrix);

		
		
	}
	public void loadViewMatrix(Camera camera)
	{
		Vector3f sum = new Vector3f( camera.getPosition().x + camera.getFront().x , camera.getPosition().y + camera.getFront().y , camera.getPosition().z + camera.getFront().z);
		Matrix4f matrix=Maths.lookAt(camera.getPosition(),sum /*SpinningTopModel.getSpinningTopObjects().get(0).getPosition()*/,new Vector3f(0,1,0));
		super.loadUpMatrix(location_viewMatrix, matrix);

		
		
	}
	
	public void loadshineComponents(ModelTexture texture)
	{
		super.loadUpFloat(loction_reflectivity, texture.getreflectivity());
		super.loadUpFloat(location_shineDamper, texture.getshineDamper());
		
	}
	public void loadLight(Vector3f color,Vector3f position)
	{
		super.loadUpVec3(location_LightColor, color);
		super.loadUpVec3(location_LightPos, position);
	
	
	}

}
