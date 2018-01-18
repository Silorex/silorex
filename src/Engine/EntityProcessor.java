package Engine;

import Entities.Entity;
import Entities.Light;
import Entities.Player;
import RenderEngine.Camera;
import RenderEngine.Loader;
import Shaders.StaticShader;
import Toolbox.Maths;
import Toolbox.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityProcessor extends Thread
{
	private List<Entity> worldObjects = new ArrayList<Entity>();
	private List<Entity> entitiesToRender = new ArrayList<Entity>();

	private List<Light> worldLights = new ArrayList<>();
	private List<Light> lightsToRender = new ArrayList<>();

	private boolean shouldStop = true;

	public void run()
	{
		while(shouldStop)
		{
			entitiesToRender = getObjectsNearPosition(worldObjects, Main.camera.getPosition(), 1000f);

			Collections.sort(worldLights);
			lightsToRender = getLightsNearPosition(worldLights, Main.camera.getPosition(), 1000f);

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void end()
	{
		shouldStop = false;
	}

	public void addEntityToWorld(Entity entity)
	{
		worldObjects.add(entity);
	}

	public List<Entity> getEntitiesToRender()
	{
		return entitiesToRender;
	}

	public void addLightToWorld(Light light)
	{
		worldLights.add(light);
	}

	public List<Light> getLightsToRender()
	{
		return worldLights;
	}

	private List<Entity> getObjectsNearPosition(List<Entity> objectList, Vector3 position, float distance)
	{
		List<Entity> objects = new ArrayList<>();
		for(Entity entity : objectList)
		{
			if(Maths.getDistanceBetweenPositions(position, entity.getPosition()) <= distance)
			{
				objects.add(entity);
			}
		}
		return objects;
	}

	private List<Light> getLightsNearPosition(List<Light> lightList, Vector3 position, float distance)
	{
		List<Light> lights = new ArrayList<>();
		for(Light light : lightList)
		{
			if(lights.size() >= StaticShader.MAX_LIGHTS) break;
			if(Maths.getDistanceBetweenPositions(position, light.getPosition()) <= distance)
			{
				lights.add(light);
			}
		}
		return lights;
	}
}
