package RenderEngine;

import Entities.Player;
import Toolbox.Vector3;

public class Camera
{
	private static Camera camera;

	private float distanceFromPlayer = 10;
	private float angle = 0;
	private Vector3 position;
	private float pitch;
	private float yaw;
	private float roll;
	private Player player;

	public Camera(Player player)
	{
		this.player = player;
		position = new Vector3(player.getPosition());
		camera = this;
	}

	public void checkInput(double dx, double dy)
	{
		angle += dx/16;
		pitch -= dy/16;
		if(pitch < 5) pitch = 5;
		else if(pitch > 85) pitch = 85;
	}

	public void calculatePositionRotation()
	{
		float horizontalDistance = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
		float verticalDistance = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));

		position.x(player.getPosition().x() - (float) (horizontalDistance * Math.sin(Math.toRadians(angle))));
		position.y(player.getPosition().y() + verticalDistance);
		position.z(player.getPosition().z() - (float) (horizontalDistance * Math.cos(Math.toRadians(angle))));
		yaw = 180 - angle;
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

	public static void MoveCamera(double dx, double dy)
	{
		camera.checkInput(dx, dy);
	}
}
