package Models;

public class ModelTexture
{
	private int textureID;
	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean hasTransparency = false;
	private boolean useFakeLightning = false;

	public ModelTexture(int id)
	{
		textureID = id;
	}

	public boolean isUseFakeLightning()
	{
		return useFakeLightning;
	}

	public void setUseFakeLightning(boolean useFakeLightning)
	{
		this.useFakeLightning = useFakeLightning;
	}

	public boolean isHasTransparency()
	{
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency)
	{
		this.hasTransparency = hasTransparency;
	}

	public int getTextureID()
	{
		return textureID;
	}

	public float getShineDamper()
	{
		return shineDamper;
	}

	public void setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
	}

	public float getReflectivity()
	{
		return reflectivity;
	}

	public void setReflectivity(float reflectivity)
	{
		this.reflectivity = reflectivity;
	}
}
