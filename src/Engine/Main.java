package Engine;

import Entities.Entity;
import Entities.Light;
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
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main
{

	public static boolean[] keys = new boolean[65536];
	public static float AspectRatio = 0;


	private long window;
	private Loader loader;

	public void run()
	{
		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();

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

		//GLFWKeyCallback keyCallback;
		//glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());

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

		loader = new Loader();

		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stall"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3(0, 0, -50), new Vector3(0, 0, 0), 1f);
		Light light = new Light(new Vector3(0, 0, -20), new Vector3(1, 1, 1));

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();

		while(!glfwWindowShouldClose(window))
		{
			//entity.increasePosition(0, 0, -0.02f);
			entity.increaseRotation(0, 1, 0);

			camera.move();

			renderer.proccessEntity(entity);

			renderer.render(light, camera);

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	public static void main(String[] args)
	{
		new Main().run();
	}

}