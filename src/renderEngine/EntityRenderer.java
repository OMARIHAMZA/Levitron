package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Entity;
import Shaders.StaticShader;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import textures.ModelTexture;
import toolBox.Maths;
import toolBox.Quaternion;

public class EntityRenderer {

	private StaticShader shader;

	public EntityRenderer(StaticShader shader,Matrix4f projectionMatrix)
	{

		this.shader=shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();

	}




	public void render( Map<TexturedModel,List<Entity>> entities )
	{


		for(TexturedModel model : entities.keySet())
		{
			setUpModel(model);
			List<Entity> list = entities.get(model);

			for(Entity entity : list)
			{
				setUpInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVerexCount(), GL11.GL_UNSIGNED_INT, 0);


			}
			unbindVao();




		}




	}
	private void setUpModel(TexturedModel texturedmodel )
	{
		RawModel model=texturedmodel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		ModelTexture texture= texturedmodel.getTexture();
		shader.loadshineComponents(texture);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());




	}
	private void setUpInstance(Entity entity)
	{
		Vector3f EularAngles= Quaternion.getEularAngles(entity.getOrientation().normalize());
		Matrix4f transformationMatrix =Maths.createTransformationMatrix(entity.getPosition(),EularAngles.x,EularAngles.y,EularAngles.z , entity.getScale());

		entity.setTransformationMatrix(transformationMatrix);
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
