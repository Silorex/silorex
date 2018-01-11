package Toolbox;

public class Vector2
{
	private float x;
	private float y;

	public Vector2()
	{
		x = 0f;
		y = 0f;
	}

	public Vector2(float v)
	{
		x = v;
		y = v;
	}

	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	public void add(Vector2 vec)
	{
		this.add(vec.x(), vec.y());
	}

	public float x()
	{
		return this.x;
	}

	public float y()
	{
		return this.y;
	}

	public void x(float x)
	{
		this.x = x;
	}

	public void y(float y)
	{
		this.y = y;
	}
}
