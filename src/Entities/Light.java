package Entities;

import Toolbox.Vector3;

public class Light
{
	private Vector3 position;
	private Vector3 color;

	public Light(Vector3 position, Vector3 color)
	{
		this.position = position;
		this.color = color;
	}

	public Vector3 getPosition()
	{
		return position;
	}

	public void setPosition(Vector3 position)
	{
		this.position = position;
	}

	public Vector3 getColor()
	{
		return color;
	}

	public void setColor(Vector3 color)
	{
		this.color = color;
	}
}
