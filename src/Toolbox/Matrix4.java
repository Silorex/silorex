package Toolbox;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.sql.Struct;

public class Matrix4
{
	public static final int LENGTH = 16;

	private float[] matrix;

	public Matrix4() {
		matrix = new float[LENGTH];
		clearToIdentity();
	}

	public Matrix4(float[] m) {
		this();
		set(m);
	}

	public Matrix4 set(Matrix4 m) {
		for(int i = 0; i < LENGTH; i++)
		{
			matrix[i] = m.get(i);
		}
		return this;
	}

	public Matrix4 clear() {
		for(int a = 0; a < LENGTH; a++)
			matrix[a] = 0;

		return this;
	}

	public Matrix4 clearToIdentity() {
		return clear().put(0, 1)
				.put(5, 1)
				.put(10, 1)
				.put(15, 1);
	}

	public Matrix4 clearToOrtho(float left, float right, float bottom, float top, float near, float far) {
		return clear().put(0, 2 / (right - left))
				.put(5, 2 / (top - bottom))
				.put(10, -2 / (far - near))
				.put(12, -(right + left) / (right - left))
				.put(13, -(top + bottom) / (top - bottom))
				.put(14, -(far + near) / (far - near))
				.put(15, 1);
	}

	public Matrix4 clearToPerspective(float fovRad, float width, float height, float near, float far) {
		float fov = 1 / (float)Math.tan(fovRad / 2);
		return clear().put(0, fov * (height / width))
				.put(5, fov)
				.put(10, (far + near) / (near - far))
				.put(14, (2 * far * near) / (near - far))
				.put(11, -1);
	}

	public Matrix4 clearToPerspectiveDeg(float fov, float width, float height, float near, float far) {
		return clearToPerspective((float)Math.toRadians(fov), width, height, near, far);
	}

	public float get(int index) {
		return matrix[index];
	}

	public float get(int col, int row) {
		return matrix[col * 4 + row];
	}

	public Matrix4 put(int index, float f) {
		matrix[index] = f;
		return this;
	}

	public Matrix4 put(int col, int row, float f) {
		matrix[col * 4 + row] = f;
		return this;
	}

	public Matrix4 putColumn3(int index, Vector3 v) {
		put(index, 0, v.x());
		put(index, 1, v.y());
		put(index, 2, v.z());
		return this;
	}

	public Matrix4 putColumn(int index, Vector3 v, float w) {
		put(index, 0, v.x());
		put(index, 1, v.y());
		put(index, 2, v.z());
		put(index, 3, w);
		return this;
	}

	public Matrix4 set(float[] m) {
		if(m.length < LENGTH) {
			throw new IllegalArgumentException("float array must have at least " + LENGTH + " values.");
		}

		for(int a = 0; a < m.length && a < LENGTH; a++) {
			matrix[a] = m[a];
		}

		return this;
	}

	public Matrix4 mult(float f) {
		for(int a = 0; a < LENGTH; a++)
			put(a, get(a) * f);

		return this;
	}

	public Matrix4 mult(float[] m) {
		if(m.length < LENGTH) {
			throw new IllegalArgumentException("float array must have at least " + LENGTH + " values.");
		}

		return mult(new Matrix4(m));
	}

	public Matrix4 mult(Matrix4 m) {
		Matrix4 temp = new Matrix4();

		for(int a = 0; a < 4; a++) {
			temp.put(a, 0, get(0) * m.get(a, 0) + get(4) * m.get(a, 1) + get(8) * m.get(a, 2) + get(12) * m.get(a, 3));
			temp.put(a, 1, get(1) * m.get(a, 0) + get(5) * m.get(a, 1) + get(9) * m.get(a, 2) + get(13) * m.get(a, 3));
			temp.put(a, 2, get(2) * m.get(a, 0) + get(6) * m.get(a, 1) + get(10) * m.get(a, 2) + get(14) * m.get(a, 3));
			temp.put(a, 3, get(3) * m.get(a, 0) + get(7) * m.get(a, 1) + get(11) * m.get(a, 2) + get(15) * m.get(a, 3));
		}

		return set(temp);
	}

	public Matrix4 transpose() {
		float old = get(1);
		put(1, get(4));
		put(4, old);

		old = get(2);
		put(2, get(8));
		put(8, old);

		old = get(3);
		put(3, get(12));
		put(12, old);

		old = get(7);
		put(7, get(13));
		put(13, old);

		old = get(11);
		put(11, get(14));
		put(14, old);

		old = get(6);
		put(6, get(9));
		put(9, old);

		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		Matrix4 temp = new Matrix4();

		temp.put(0, 1);
		temp.put(5, 1);
		temp.put(10, 1);
		temp.put(15, 1);

		temp.put(12, x);
		temp.put(13, y);
		temp.put(14, z);

		return mult(temp);
	}

	public Matrix4 translate(Vector3 vec) {
		return translate(vec.x(), vec.y(), vec.z());
	}

	public Matrix4 scale(float f) {
		return scale(f, f, f);
	}

	public Matrix4 scale(float x, float y, float z) {
		Matrix4 temp = new Matrix4();

		temp.put(0, x);
		temp.put(5, y);
		temp.put(10, z);
		temp.put(15, 1);

		return mult(temp);
	}

	public Matrix4 scale(Vector3 vec) {
		return scale(vec.x(), vec.y(), vec.z());
	}

	public Matrix4 rotate(float angle, float x, float y, float z) {
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		float oneMinusCos = 1 - cos;

		float len = (float)Math.sqrt(x * x + y * y + z * z);
		x /= len;
		y /= len;
		z /= len;

		Matrix4 temp = new Matrix4();

		temp.put(0, x * x * oneMinusCos + cos);
		temp.put(4, x * y * oneMinusCos - z * sin);
		temp.put(8, x * z * oneMinusCos + y * sin);

		temp.put(1, y * x * oneMinusCos + z * sin);
		temp.put(5, y * y * oneMinusCos + cos);
		temp.put(9, y * z * oneMinusCos - x * sin);

		temp.put(2, z * x * oneMinusCos - y * sin);
		temp.put(6, z * y * oneMinusCos + x * sin);
		temp.put(10, z * z * oneMinusCos + cos);

		temp.put(15, 1);

		return mult(temp);
	}

	public Matrix4 rotate(float angle, Vector3 vec) {
		return rotate(angle, vec.x(), vec.y(), vec.z());
	}

	public Matrix4 rotateDeg(float angle, float x, float y, float z) {
		return rotate((float)Math.toRadians(angle), x, y, z);
	}

	public Matrix4 rotateDeg(float angle, Vector3 vec) {
		return rotate((float)Math.toRadians(angle), vec);
	}

	public float determinant() {
		float a = get(5) * get(10) * get(15) + get(9) * get(14) * get(7) + get(13) * get(6) * get(11) - get(7) * get(10) * get(13) - get(11) * get(14) * get(5) - get(15) * get(6) * get(9);
		float b = get(1) * get(10) * get(15) + get(9) * get(14) * get(3) + get(13) * get(2) * get(11) - get(3) * get(10) * get(13) - get(11) * get(14) * get(1) - get(15) * get(2) * get(9);
		float c = get(1) * get(6) * get(15) + get(5) * get(14) * get(3) + get(13) * get(2) * get(7) - get(3) * get(6) * get(13) - get(7) * get(14) * get(1) - get(15) * get(2) * get(5);
		float d = get(1) * get(6) * get(11) + get(5) * get(10) * get(3) + get(9) * get(2) * get(7) - get(3) * get(6) * get(9) - get(7) * get(10) * get(1) - get(11) * get(2) * get(5);

		return get(0) * a - get(4) * b + get(8) * c - get(12) * d;
	}

	public Matrix4 inverse() {
		Matrix4 inv = new Matrix4();

		inv.put(0, +(get(5) * get(10) * get(15) + get(9) * get(14) * get(7) + get(13) * get(6) * get(11) - get(7) * get(10) * get(13) - get(11) * get(14) * get(5) - get(15) * get(6) * get(9)));
		inv.put(1, -(get(4) * get(10) * get(15) + get(8) * get(14) * get(7) + get(12) * get(6) * get(11) - get(7) * get(10) * get(12) - get(11) * get(14) * get(4) - get(15) * get(6) * get(8)));
		inv.put(2, +(get(4) * get(9) * get(15) + get(8) * get(13) * get(7) + get(12) * get(5) * get(11) - get(7) * get(9) * get(12) - get(11) * get(13) * get(4) - get(15) * get(5) * get(8)));
		inv.put(3, -(get(4) * get(9) * get(14) + get(8) * get(13) * get(6) + get(12) * get(5) * get(10) - get(6) * get(9) * get(12) - get(10) * get(13) * get(4) - get(14) * get(5) * get(8)));

		inv.put(4, -(get(1) * get(10) * get(15) + get(9) * get(14) * get(3) + get(13) * get(2) * get(11) - get(3) * get(10) * get(13) - get(11) * get(14) * get(1) - get(15) * get(2) * get(9)));
		inv.put(5, +(get(0) * get(10) * get(15) + get(8) * get(14) * get(3) + get(12) * get(2) * get(11) - get(3) * get(10) * get(12) - get(11) * get(14) * get(0) - get(15) * get(2) * get(8)));
		inv.put(6, -(get(0) * get(9) * get(15) + get(8) * get(13) * get(3) + get(12) * get(1) * get(11) - get(3) * get(9) * get(12) - get(11) * get(13) * get(0) - get(15) * get(1) * get(8)));
		inv.put(7, +(get(0) * get(9) * get(14) + get(8) * get(13) * get(2) + get(12) * get(1) * get(10) - get(2) * get(9) * get(12) - get(10) * get(13) * get(0) - get(14) * get(1) * get(8)));

		inv.put(8, +(get(1) * get(6) * get(15) + get(5) * get(14) * get(3) + get(13) * get(2) * get(7) - get(3) * get(6) * get(13) - get(7) * get(14) * get(1) - get(15) * get(2) * get(5)));
		inv.put(9, -(get(0) * get(6) * get(15) + get(4) * get(14) * get(3) + get(12) * get(2) * get(7) - get(3) * get(6) * get(12) - get(7) * get(14) * get(0) - get(15) * get(2) * get(4)));
		inv.put(10, +(get(0) * get(5) * get(15) + get(4) * get(13) * get(3) + get(12) * get(1) * get(7) - get(3) * get(5) * get(12) - get(7) * get(13) * get(0) - get(15) * get(1) * get(4)));
		inv.put(11, -(get(0) * get(5) * get(14) + get(4) * get(13) * get(2) + get(12) * get(1) * get(6) - get(2) * get(5) * get(12) - get(6) * get(13) * get(0) - get(14) * get(1) * get(4)));

		inv.put(12, -(get(1) * get(6) * get(11) + get(5) * get(10) * get(3) + get(9) * get(2) * get(7) - get(3) * get(6) * get(9) - get(7) * get(10) * get(1) - get(11) * get(2) * get(5)));
		inv.put(13, +(get(0) * get(6) * get(11) + get(4) * get(10) * get(3) + get(8) * get(2) * get(7) - get(3) * get(6) * get(8) - get(7) * get(10) * get(0) - get(11) * get(2) * get(4)));
		inv.put(14, -(get(0) * get(5) * get(11) + get(4) * get(9) * get(3) + get(8) * get(1) * get(7) - get(3) * get(5) * get(8) - get(7) * get(9) * get(0) - get(11) * get(1) * get(4)));
		inv.put(15, +(get(0) * get(5) * get(10) + get(4) * get(9) * get(2) + get(8) * get(1) * get(6) - get(2) * get(5) * get(8) - get(6) * get(9) * get(0) - get(10) * get(1) * get(4)));

		return set(inv.transpose().mult(1 / determinant()));
	}

	private final static FloatBuffer direct = BufferUtils.createFloatBuffer(16);

	public FloatBuffer toBuffer() {
		direct.clear();
		for(int a = 0; a < LENGTH; a++) {
			direct.put(matrix[a]);
		}
		direct.flip();
		return direct;
	}
}
