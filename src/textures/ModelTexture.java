package textures;

public class ModelTexture {
	
	private int textureID;
	private float reflectivity=1f;
	private float shineDamper=10f;
	
	public ModelTexture(int id)
	{
		this.textureID=id;
	}

	public int getTextureID() {
		return textureID;
	}

	public float getreflectivity() {
		return reflectivity;
	}

	public float getshineDamper() {
		return shineDamper;
	}
	

	
}
