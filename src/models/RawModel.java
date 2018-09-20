package models;

public class RawModel {
	
	private int vaoID;
	
	private int verexCount;
	
	public RawModel(int VaoID,int vertexcount)
	{
		this.vaoID=VaoID;
		this.verexCount=vertexcount;
		
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVerexCount() {
		return verexCount;
	}
	
	
	
	
	

}
