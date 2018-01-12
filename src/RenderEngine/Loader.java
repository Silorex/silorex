package RenderEngine;

import Models.RawModel;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader
{
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, int[] indices, float[] textures, float[] normals)
	{
		int vaoID = createVAO();
		bindIndices(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textures);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	public int loadTexture(String fileName)
	{
		PNGDecoder decoder = null;
		ByteBuffer image = null;
		try {
			decoder = new PNGDecoder(new FileInputStream("resources/" + fileName + ".png"));
			image = ByteBuffer.allocateDirect(3 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(image, decoder.getWidth() * 3, PNGDecoder.Format.RGB);
			image.flip();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, image);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textures.add(texture);
		return texture;
	}

	public static void cleanUp()
	{
		for(int vao:vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}

		for(int vbo:vbos)
		{
			GL30.glDeleteFramebuffers(vbo);
		}

		for(int texture:textures)
		{
			GL11.glDeleteTextures(texture);
		}
	}

	private int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		//int vaoID = ARBVertexArrayObject.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeDataInAttributeList(int attributeNumber, int dataSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void bindIndices(int[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}


	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
