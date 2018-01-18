package Entities;

import Models.TexturedModel;
import Toolbox.Vector3;

import java.util.ArrayList;
import java.util.List;

public class MovingEntity extends Entity
{
	private static List<MovingEntity> movingEntities = new ArrayList<>();
	protected Vector3 velocity;
	protected Vector3 turnVelocity;

	public MovingEntity(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		super(texturedModel, position, rotation, scale);
		velocity = new Vector3();
		turnVelocity = new Vector3();
		movingEntities.add(this);
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

	public static void updateMovingEntities()
	{
		for(MovingEntity entity:movingEntities)
		{
			entity.increasePosition(entity.getVelocity());
			entity.increaseRotation(entity.getTurnVelocity());
		}
	}
}
