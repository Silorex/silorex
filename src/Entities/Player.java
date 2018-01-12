package Entities;

import Engine.Main;
import Models.TexturedModel;
import Toolbox.Vector3;
import org.lwjgl.glfw.GLFW;

public class Player extends MovingEntity
{
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;

	public Player(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		super(texturedModel, position, rotation, scale);
	}

	public void move()
	{
		checkInput();
		super.increaseRotation(0, currentTurnSpeed * (float)Main.getFrameTime(), 0);

		float distance = currentSpeed * (float)Main.getFrameTime();
		float dx = distance * (float) Math.sin(Math.toRadians(super.getRotation().y()));
		float dz = distance * (float) Math.cos(Math.toRadians(super.getRotation().y()));
		super.increasePosition(dx, 0, dz);
	}

	private void checkInput()
	{
		if(Main.keys[GLFW.GLFW_KEY_W])
		{
			this.currentSpeed = RUN_SPEED;
		}
		else if(Main.keys[GLFW.GLFW_KEY_S])
		{
			this.currentSpeed = -RUN_SPEED;
		}
		else this.currentSpeed = 0;

		if(Main.keys[GLFW.GLFW_KEY_A])
		{
			this.currentTurnSpeed = TURN_SPEED;
		}
		else if(Main.keys[GLFW.GLFW_KEY_D])
		{
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else this.currentTurnSpeed = 0;
	}
}
