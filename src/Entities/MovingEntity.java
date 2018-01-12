package Entities;

import Models.TexturedModel;
import Toolbox.Vector3;

public class MovingEntity extends Entity
{
	protected Vector3 velocity;
	protected Vector3 turnVelocity;

	public MovingEntity(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		super(texturedModel, position, rotation, scale);
	}

	public Vector3 getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector3 velocity)
	{
		this.velocity = velocity;
	}

	public Vector3 getTurnVelocity()
	{
		return turnVelocity;
	}

	public void setTurnVelocity(Vector3 turnVelocity)
	{
		this.turnVelocity = turnVelocity;
	}
}
