package Engine;

import Entities.Entity;
import Entities.EntityFactory;
import Entities.Light;
import Entities.Player;
import GUI.MouseHandler;
import Models.OBJLoader;
import Models.TexturedModel;
import RenderEngine.Camera;
import RenderEngine.Loader;
import Models.RawModel;
import RenderEngine.MasterRenderer;
import RenderEngine.Renderer;
import Shaders.StaticShader;
import Models.ModelTexture;
import Toolbox.Vector3;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main
{

	public static boolean[] keys = new boolean[65536];

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static float AspectRatio = (float) WIDTH / HEIGHT;

	private static double lastFrameTime;
	private static double delta;

	private EntityProcessor entityProcessor;

	private long window;
	public static Camera camera;

	public void run()
	{
		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		entityProcessor.end();

		MasterRenderer.cleanUp();
		Loader.cleanUp();
	}

	private void init()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		window = glfwCreateWindow(1280, 720, "Silorex", NULL, NULL);
		if (window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

			keys[key] = action != GLFW.GLFW_RELEASE;

			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			{
				glfwSetWindowShouldClose(window, true);
			}
		});

		GLFWCursorPosCallback mouseCallback;
		glfwSetCursorPosCallback(window, mouseCallback = new MouseHandler());
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			Main.WIDTH = width;
			Main.HEIGHT = height;
			Main.AspectRatio = (float) Main.WIDTH / Main.HEIGHT;

			glViewport(0, 0, WIDTH, HEIGHT);
		});

		try(MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			AspectRatio = (float) pWidth.get() / (float) pHeight.get();
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	private void loop() {
		GL.createCapabilities();
		GLUtil.setupDebugMessageCallback();

		Loader loader = new Loader();

		RawModel model2 = OBJLoader.loadObjModel("player", loader);
		ModelTexture texture2 = new ModelTexture(loader.loadTexture("abcd"));
		TexturedModel texturedModel2 = new TexturedModel(model2, texture2);
		Player player = new Player(texturedModel2, new Vector3(0, 0, 0), new Vector3(0, 0, 0), 1f);
		camera = new Camera(player);

		entityProcessor = new EntityProcessor();
		for(int i = 0; i <= 100; i+=21)
		{
			entityProcessor.addEntityToWorld(EntityFactory.createEntity(loader, "roads/road1", new Vector3(i, 0, 0), new Vector3()));
			entityProcessor.addLightToWorld(new Light(new Vector3(i, 8, 0), new Vector3(1, 1, 1), new Vector3(1, 0.01f, 0.002f)));
		}
		//entityProcessor.addEntityToWorld(EntityFactory.createEntity(loader, "roads/road1", new Vector3(0, 0, 0), new Vector3()));
		//entityProcessor.addEntityToWorld(EntityFactory.createEntity(loader, "roads/road1", new Vector3(20, 0, 0), new Vector3()));
		//entityProcessor.addEntityToWorld(EntityFactory.createEntity(loader, "roads/road1", new Vector3(-20, 0, 0), new Vector3()));
		entityProcessor.start();

		/*List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3(0, 8, 0), new Vector3(1, 1, 1), new Vector3(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3(10, 8, 0), new Vector3(1, 1, 1), new Vector3(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3(-10, 8, 0), new Vector3(1, 1, 1), new Vector3(1, 0.01f, 0.002f)));*/

		MasterRenderer renderer = new MasterRenderer();
		lastFrameTime = getFrameTime();

		while(!glfwWindowShouldClose(window))
		{
			//entity.increasePosition(0, 0, -0.02f);
			//entity.increaseRotation(0, 1, 0);

			player.move();

			camera.calculatePositionRotation();

			renderer.proccessEntity(player);

			List<Entity> entities = entityProcessor.getEntitiesToRender();
			for(Entity entity:entities)
			{
				renderer.proccessEntity(entity);
			}

			renderer.render(entityProcessor.getLightsToRender(), camera);

			glfwSwapBuffers(window);
			glfwPollEvents();

			double currentFrameTime = getCurrentTime();
			delta = currentFrameTime - lastFrameTime;
			lastFrameTime = currentFrameTime;
		}
	}

	public static double getFrameTime()
	{
		return delta;
	}

	public static void main(String[] args)
	{
		new Main().run();
	}

	private static double getCurrentTime()
	{
		return (float) GLFW.glfwGetTime();
	}

}