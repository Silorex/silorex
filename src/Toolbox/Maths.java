package Toolbox;

import Entities.Entity;
import RenderEngine.Camera;

public class Maths
{
	public static Matrix4 createTransformationMatrix(Vector3 translation, Vector3 rotation, float scale)
	{
		Matrix4 matrix = new Matrix4();
		matrix.translate(translation);
		matrix.rotateDeg(rotation.x(), 1, 0, 0);
		matrix.rotateDeg(rotation.y(), 0, 1, 0);
		matrix.rotateDeg(rotation.z(), 0, 0, 1);
		matrix.scale(scale);
		return matrix;
	}

	public static Matrix4 createViewMatrix(Camera camera)
	{
		Matrix4 viewMatrix = new Matrix4();
		viewMatrix.rotateDeg(camera.getPitch(), 1, 0, 0);
		viewMatrix.rotateDeg(camera.getYaw(), 0, 1, 0);
		Vector3 campos = camera.getPosition();
		viewMatrix.translate(-campos.x(), -campos.y(), -campos.z());
		return viewMatrix;
	}

	public static float getDistanceBetweenPositions(Vector3 pos1, Vector3 pos2)
	{
		return (float) Math.abs(Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.y() - pos1.y(), 2) + Math.pow(pos2.z() - pos1.z(), 2)));
	}
}
