package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Entity;
import models.RawModel;
import models.TexturedModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainShader;
import toolBox.Maths;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader,Matrix4f matrix)
	{
		this.shader=shader;
		shader.start();
		shader.loadProjectionMatrix(matrix);
		shader.stop();
		
		
		
	}
	
	public void render(List<Terrain> terrains)
	{
		
		for(Terrain terrain : terrains)
		{
			setUpTerrain(terrain);
			setUpInstance(terrain);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVerexCount(), GL11.GL_UNSIGNED_INT, 0);
			
			
			unbindVao();
			
			
		}
		
	}
	
	
	
	private void setUpTerrain(Terrain terrain)
	{
		
		
		
		RawModel model=terrain.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture= terrain.getTexture();
		shader.loadshineComponents(texture);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

		
		
		
	}
	private void setUpInstance(Terrain terrain)
	{
		Matrix4f transformationMatrix =Maths.createTransformationMatrix(new Vector3f(terrain.getX(),0,terrain.getZ()), 
				0,0,0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	
		
		
	}
	private void unbindVao()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);

		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
