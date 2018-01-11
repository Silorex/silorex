package Entities;

import Models.TexturedModel;
import Toolbox.Vector3;

public class MovingEntity extends Entity
{
	public MovingEntity(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		super(texturedModel, position, rotation, scale);
	}
}
