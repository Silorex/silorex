package Toolbox;

public class Vector3 extends Vector2
{
	private float z;

	public Vector3()
	{
		super();
		z = 0f;
	}

	public Vector3(float v)
	{
		super(v);
		z = v;
	}

	public Vector3(float x, float y, float z)
	{
		super(x, y);
		this.z = z;
	}

	public float z()
	{
		return this.z;
	}

	public void z(float z)
	{
		this.z = z;
	}

	public void add(float x, float y, float z)
	{
		super.add(x, y);
		this.z += z;
	}

	public void add(Vector3 vec)
	{
		this.add(vec.x(), vec.y(), vec.z());
	}
}