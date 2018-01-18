package Toolbox;

public class Vector3 extends Vector2
{
	protected float z;

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

	public Vector3(Vector3 pos)
	{
		this(pos.x(), pos.y(), pos.z());
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

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float v)
	{
		set(v, v, v);
	}

	public void zero()
	{
		set(0);
	}

	public void add(Vector3 vec)
	{
		this.add(vec.x(), vec.y(), vec.z());
	}

	public String toString()
	{
		return "(" + x + "|" + y + "|" + z + ")";
	}
}
