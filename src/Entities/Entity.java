package Entities;

import Models.TexturedModel;
import Toolbox.Vector3;

public class Entity
{
	private TexturedModel texturedModel;
	private Vector3 position;
	private Vector3 rotation;
	private float scale;

	public Entity(TexturedModel texturedModel, Vector3 position, Vector3 rotation, float scale)
	{
		this.texturedModel = texturedModel;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public void increasePosition(float dx, float dy, float dz)
	{
		position.add(dx, dy, dz);
	}

	public void increaseRotation(float dx, float dy, float dz)
	{
		rotation.add(dx, dy, dz);
	}

	public TexturedModel getTexturedModel() {
		return texturedModel;
	}

	public void setTexturedModel(TexturedModel texturedModel) {
		this.texturedModel = texturedModel;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getRotation() {
		return rotation;
	}

	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
