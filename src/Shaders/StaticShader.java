package Shaders;

import Entities.Light;
import RenderEngine.Camera;
import Toolbox.Maths;
import Toolbox.Matrix4;
import Toolbox.Vector3;
import java.util.List;

public class StaticShader extends ShaderProgram
{
	public static final int MAX_LIGHTS = 16;

	private static final String VERTEX_FILE = "src/Shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/Shaders/fragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColor;

	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	protected void getAllUniformLocations()
	{
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColor = super.getUniformLocation("skyColor");

		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void setSkyColor(float r, float g, float b)
	{
		super.loadVector(location_skyColor, new Vector3(r, g, b));
	}

	public void loadFakeLightingVariable(boolean useFake)
	{
		super.loadBoolean(location_useFakeLighting, useFake);
	}

	public void loadTransformationMatrix(Matrix4 matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4 matrix)
	{
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4 viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadLights(List<Light> lights)
	{
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			if(i < lights.size())
			{
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.loadVector(location_lightPosition[i], new Vector3());
				super.loadVector(location_lightColor[i], new Vector3());
				super.loadVector(location_attenuation[i], new Vector3(1, 0, 0));
			}
		}
	}

	public void loadShineVariables(float damper, float reflectivity)
	{
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
}
