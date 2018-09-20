package renderEngine;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {
	
	public static RawModel loadObjModel(String fileName,Loader loader,boolean yes)
	{
		FileReader fr =null;
		try {
			fr= new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldnt load file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String Line;
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List <Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray =null;
		float[] normalsArray=null;
		float[] texturesArray=null;
		int[] indicesArray=null;
		
		try {
			
			while(true)
			{
				Line= reader.readLine();
				String[] currentLine=Line.split(" ");
				if(Line.startsWith("v "))
				{
					Vector3f vertex=new Vector3f(Float.parseFloat(currentLine[1])
					,Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
					
					
				}
				else if(Line.startsWith("vt "))
				{
					Vector2f texture=new Vector2f(Float.parseFloat(currentLine[1])
							,Float.parseFloat(currentLine[2]));
					
					textures.add(texture);
					
				}
				else if(Line.startsWith("vn "))
				{
					Vector3f normal=new Vector3f(Float.parseFloat(currentLine[1])
							,Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					normals.add(normal);
					
					
					
				}
				else if(Line.startsWith("f "))
				{
					texturesArray = new float[vertices.size()*2] ;
					normalsArray = new float[vertices.size()*3];
					break;
				
					
				}
				
				
			}
			
			while(Line!=null)
			{
				if(!Line.startsWith("f "))
				{
					Line= reader.readLine();
					continue;
					
				}
				String[] currentLine = Line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1,normals,textures,texturesArray,normalsArray,indices);
				processVertex(vertex2,normals,textures,texturesArray,normalsArray,indices);
				processVertex(vertex3,normals,textures,texturesArray,normalsArray,indices);
				Line=reader.readLine();
		
				
				
				
			}
			reader.close();
			
				
			
		}catch(Exception e){
			e.printStackTrace();
			
			
			
		}
		
		
		verticesArray =new float[vertices.size()*3];
		indicesArray= new int[indices.size()];	
		
		int vertexpointer=0;
		for(Vector3f vec:vertices)
		{
			verticesArray[vertexpointer++]=vec.x;
			verticesArray[vertexpointer++]=vec.y;
			verticesArray[vertexpointer++]=vec.z;

			
			
			
		}
		
		for(int i=0 ; i < indices.size() ; i++)
		{
			indicesArray[i]=indices.get(i);
			
			
		}



		if(yes){
			Vector3f centeroid = new Vector3f();

			float sumx =0;
			float sumy =0;
			float sumz =0;


			for(Vector3f vec:vertices){
				sumx+=vec.x;
				sumy+=vec.y;
				sumz+=vec.z;
			}


			centeroid.x=sumx/vertices.size();
			centeroid.y=sumy/vertices.size();
			centeroid.z=sumz/vertices.size();



		}



		return loader.loadToVao(verticesArray, texturesArray,normalsArray, indicesArray);
		
	}
	
	
	
	private static void processVertex(String[] vertex ,List<Vector3f> normals ,
			List<Vector2f> textures , float[] texturesArray,float[] normalsArray, List<Integer> indices) {
		
			int vertexPointer = Integer.parseInt(vertex[0]) - 1;
			indices.add(vertexPointer);
			Vector2f vector2 = textures.get(Integer.parseInt(vertex[1]) - 1);
			texturesArray[vertexPointer * 2] = vector2.x;
			texturesArray[vertexPointer * 2 + 1] = 1 - vector2.y;
			Vector3f vector3 = normals.get(Integer.parseInt(vertex[2]) - 1);
			normalsArray[vertexPointer * 3] = vector3.x;
			normalsArray[vertexPointer * 3 + 1] = vector3.y;
			normalsArray[vertexPointer * 3 + 2] = vector3.z;

	}
}
