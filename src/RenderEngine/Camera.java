package RenderEngine;

import Engine.Main;
//import GUI.KeyboardHandler;
import Toolbox.Vector3;
import org.lwjgl.glfw.GLFW;

public class Camera
{
	private Vector3 position;
	private float pitch;
	private float yaw;
	private float roll;

	public Camera()
	{
		position = new Vector3(0);
	}

	public void move()
	{
		if(Main.keys[GLFW.GLFW_KEY_W])//if(KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W))
		{
			position.z(position.z() - 0.02f);
		}
		else if(Main.keys[GLFW.GLFW_KEY_S])
		{
			position.z(position.z() + 0.02f);
		}
		else if(Main.keys[GLFW.GLFW_KEY_A])
		{
			position.x(position.x() + 0.02f);
		}
		else if(Main.keys[GLFW.GLFW_KEY_D])
		{
			position.x(position.x() - 0.02f);
		}
	}

	public Vector3 getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}
}
