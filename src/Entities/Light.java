package Entities;

import Engine.Main;
import Toolbox.Maths;
import Toolbox.Vector3;

public class Light implements  Comparable<Light>
{
	private Vector3 position;
	private Vector3 color;
	private Vector3 attenuation;

	public Light(Vector3 position, Vector3 color)
	{
		this.position = position;
		this.color = color;
		attenuation = new Vector3(1, 0, 0);
	}

	public Light(Vector3 position, Vector3 color, Vector3 attenuation)
	{
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}

	public int compareTo(Light light)
	{
		if(Maths.getDistanceBetweenPositions(Main.camera.getPosition(), position) > Maths.getDistanceBetweenPositions(Main.camera.getPosition(), light.getPosition())) return 1;
		return -1;
	}

	public Vector3 getAttenuation()
	{
		return attenuation;
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
