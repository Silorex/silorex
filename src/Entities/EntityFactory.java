package Entities;

import Models.ModelTexture;
import Models.OBJLoader;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.Loader;
import Toolbox.Vector3;

public class EntityFactory
{
	public static Entity createEntity(Loader loader, String name, Vector3 position, Vector3 rotation, float scale, float shineDamper, float reflectivity, boolean hasTransparency, boolean useFakeLightning)
	{
		RawModel model = OBJLoader.loadObjModel(name, loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture(name));
		texture.setShineDamper(shineDamper);
		texture.setReflectivity(reflectivity);
		texture.setHasTransparency(hasTransparency);
		texture.setUseFakeLightning(useFakeLightning);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, position, rotation, scale);
		return entity;
	}

	public static Entity createEntity(Loader loader, String name, Vector3 position, Vector3 rotation)
	{
		return createEntity(loader, name, position, rotation, 1, 1, 0, false, false);
	}
}
