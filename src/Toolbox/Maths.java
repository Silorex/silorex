package Toolbox;

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
		/*campos.x(-campos.x());
		campos.y(-campos.y());
		campos.z(-campos.z());*/
		viewMatrix.translate(-campos.x(), -campos.y(), -campos.z());
		return viewMatrix;
	}
}
