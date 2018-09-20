package renderEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Shaders.StaticShader;
import models.TexturedModel;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.TerrainShader;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.GL_BACK;

public class MasterRenderer{
	private StaticShader shader = new StaticShader();	
	private EntityRenderer Entityrendrerer;
	
	private TerrainShader Terrainshader=new TerrainShader();
	private TerrainRenderer Terrainrenderer;
	private SkyboxRenderer skyboxRenderer;
	
	private Map<TexturedModel,List<Entity>> entities= new HashMap<TexturedModel,List<Entity>>();
	private List<Terrain> terrains= new ArrayList<Terrain>();
	
	
	
	private static final float FOV =70;
	private static final float NEAR_PLANE=0.4f;
//	private static final float NEAR_PLANE=50f;
	private static final float FAR_PLANE=1000;
	
	private Matrix4f projectionMatrix;
	
	
	
	
	
	public MasterRenderer(Loader loader) {
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL_BACK);
		
		createProjectionMatrix();
		Entityrendrerer= new EntityRenderer(shader,projectionMatrix);
		Terrainrenderer= new TerrainRenderer(Terrainshader,projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);

		
	}





	public void render(Camera camera,Light light)
	{

		prepare();
		
		shader.start();
		shader.loadLight(light.getColor(), light.getPosition());
		shader.loadViewMatrix(camera);
		Entityrendrerer.render(entities);
		shader.stop();
		Terrainshader.start();
		Terrainshader.loadLight(light.getColor(), light.getPosition());
		Terrainshader.loadViewMatrix(camera);
		Terrainrenderer.render(terrains);
		Terrainshader.stop();
		skyboxRenderer.render(camera);

		entities.clear();
		terrains.clear();
		
		
	}
	public void ProcessTerrain(Terrain terrain)
	{
		terrains.add(terrain);
		
	}
	public void ProcessEntity(Entity entity)
	{
		TexturedModel model= entity.getModel();
		List<Entity> list = entities.get(model);
		
		if(list==null)
		{
			list = new ArrayList<Entity>();
			list.add(entity);
			entities.put(model, list);
			
		}
		else
		{
			list.add(entity);

		}
		
		
		
	}

	

	
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.25f, 0.875f,0.8125f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void saveImage() {

		GL11.glReadBuffer(GL11.GL_FRONT);
		int w = Display.getDisplayMode().getWidth();
		int h = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * bpp);
		GL11.glReadPixels(0, 0, w, h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
		File file = new File("C:/users/HAMZA/desktop/texture.png"); // The file to save to.
		String format ="PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x < w; x++)
		{
			for(int y = 0; y < h; y++)
			{
				int i = (x + (w * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, h - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) { e.printStackTrace(); }

	}

	private void createProjectionMatrix()
	{
				float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
	        float x_scale = y_scale / aspectRatio;
	        float frustum_length = FAR_PLANE - NEAR_PLANE;
	        
	        projectionMatrix = new Matrix4f();
	        projectionMatrix.m00 = x_scale;
	        projectionMatrix.m11 = y_scale;
	        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
	        projectionMatrix.m23 = -1;
	        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
	        projectionMatrix.m33 = 0;
		
		
	}
	
	public void CleanUp()
	{
		shader.cleanUp();
		Terrainshader.cleanUp();
		
		
	}
	


	
	
	

}
