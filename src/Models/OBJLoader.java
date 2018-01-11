package Models;

import RenderEngine.Loader;
import Toolbox.Vector2;
import Toolbox.Vector3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader
{
	public static RawModel loadObjModel(String fileName, Loader loader)
	{
		FileReader fr = null;
		try
		{
			fr = new FileReader(new File("resources/" + fileName + ".obj"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3> vertices = new ArrayList<Vector3>();
		List<Vector2> textures = new ArrayList<Vector2>();
		List<Vector3> normals = new ArrayList<Vector3>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		try
		{
			while(true)
			{
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v "))
				{
					Vector3 vertex = new Vector3(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
				else if(line.startsWith("vt "))
				{
					Vector2 texture = new Vector2(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if(line.startsWith("vn "))
				{
					Vector3 normal = new Vector3(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if(line.startsWith("f "))
				{
					textureArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}

			while(line != null)
			{
				if(!line.startsWith("f "))
				{
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine();
			}

			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];

		int vertexPointer = 0;
		for(Vector3 vertex:vertices)
		{
			verticesArray[vertexPointer++] = vertex.x();
			verticesArray[vertexPointer++] = vertex.y();
			verticesArray[vertexPointer++] = vertex.z();
		}

		for(int i = 0; i < indices.size(); i++)
		{
			indicesArray[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesArray, indicesArray, textureArray, normalsArray);
	}

	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2> textures, List<Vector3> normals, float[] texturesArray, float[] normalsArray)
	{
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);

		Vector2 currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		texturesArray[currentVertexPointer*2] = currentTex.x();
		texturesArray[currentVertexPointer*2+1] = 1 - currentTex.y();

		Vector3 currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.x();
		normalsArray[currentVertexPointer*3+1] = currentNorm.y();
		normalsArray[currentVertexPointer*3+2] = currentNorm.z();
	}
}
