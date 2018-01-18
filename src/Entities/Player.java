package Entities;

import Engine.Main;
import Models.TexturedModel;
import Toolbox.Vector3;
import org.lwjgl.glfw.GLFW;

public class Player extends MovingEntity
{
	private static final float RUN_SPEED = 10;

	public Player(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		super(texturedModel, position, rotation, scale);
	}

	public void move()
	{
		Vector3 vel = super.getVelocity();
		vel.zero();

		if(Main.keys[GLFW.GLFW_KEY_W])
		{
			float distance = RUN_SPEED * (float)Main.getFrameTime();
			Vector3 velocity = new Vector3();
			velocity.x(distance * (float) Math.sin(Math.toRadians(180-Main.camera.getYaw())));
			velocity.z(distance * (float) Math.cos(Math.toRadians(180-Main.camera.getYaw())));
			vel.add(velocity);
			setRotation(new Vector3(0, 180-Main.camera.getYaw(), 0));
		}
		if(Main.keys[GLFW.GLFW_KEY_S])
		{
			float distance = -RUN_SPEED * (float)Main.getFrameTime();
			Vector3 velocity = new Vector3();
			velocity.x(distance * (float) Math.sin(Math.toRadians(180-Main.camera.getYaw())));
			velocity.z(distance * (float) Math.cos(Math.toRadians(180-Main.camera.getYaw())));
			vel.add(velocity);
			setRotation(new Vector3(0, 180-Main.camera.getYaw(), 0));
		}
		if(Main.keys[GLFW.GLFW_KEY_A])
		{
			float distance = RUN_SPEED * (float)Main.getFrameTime();
			Vector3 velocity = new Vector3();
			velocity.x(distance * (float) Math.sin(Math.toRadians(180-Main.camera.getYaw()+90)));
			velocity.z(distance * (float) Math.cos(Math.toRadians(180-Main.camera.getYaw()+90)));
			vel.add(velocity);
			setRotation(new Vector3(0, 180-Main.camera.getYaw(), 0));
		}
		if(Main.keys[GLFW.GLFW_KEY_D])
		{
			float distance = RUN_SPEED * (float)Main.getFrameTime();
			Vector3 velocity = new Vector3();
			velocity.x(distance * (float) Math.sin(Math.toRadians(180-Main.camera.getYaw()-90)));
			velocity.z(distance * (float) Math.cos(Math.toRadians(180-Main.camera.getYaw()-90)));
			vel.add(velocity);
			setRotation(new Vector3(0, 180-Main.camera.getYaw(), 0));
		}

		super.setVelocity(vel);

		/*super.increasePosition(super.getVelocity());
		super.increaseRotation(super.getTurnVelocity());*/
		/*super.increaseRotation(0, currentTurnSpeed * (float)Main.getFrameTime(), 0);

		float distance = currentSpeed * (float)Main.getFrameTime();
		float dx = distance * (float) Math.sin(Math.toRadians(super.getRotation().y()));
		float dz = distance * (float) Math.cos(Math.toRadians(super.getRotation().y()));
		super.increasePosition(dx, 0, dz);*/
	}

	/*private void checkInput()
	{
		if(Main.keys[GLFW.GLFW_KEY_W])
		{
		}
		else if(Main.keys[GLFW.GLFW_KEY_S])
		{
		}
		else
		{
			Vector3 vel = super.getVelocity();
			vel.zero();
			super.setVelocity(vel);
		}
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
	}*/
}
